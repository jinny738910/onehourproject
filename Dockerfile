# syntax=docker/dockerfile:1

############################################
# Build stage: compile Spring Boot artifact #
############################################
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy Maven wrapper and pom to leverage layer caching for dependencies
COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml pom.xml

# Pre-fetch dependencies (offline cache)
RUN ./mvnw -q -DskipTests dependency:go-offline

# Copy sources and build
COPY src src
RUN ./mvnw -q -DskipTests package

############################################
# Runtime stage: run the application        #
############################################
FROM eclipse-temurin:17-jre AS runtime

WORKDIR /app

# Environment knobs
ENV JAVA_OPTS="" \
    SPRING_PROFILES_ACTIVE=default

# Copy the fat jar built in the previous stage
COPY --from=build /app/target/*.jar /app/app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Run
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]

# ----------------------------------------------------
# 1단계: 빌드 스테이지 (BUILDER)
# Maven과 JDK가 모두 포함된 환경을 사용하여 빌드합니다.
# ----------------------------------------------------
FROM maven:3.8.7-eclipse-temurin-17 AS builder

# 작업 디렉토리 설정
WORKDIR /app

# Maven 캐시 활용을 위해 설정 파일만 먼저 복사
COPY pom.xml .

# 종속성 다운로드 (코드 변경 시에도 캐시를 활용하여 빌드 속도를 높임)
# 이 단계는 pom.xml 파일이 변경되지 않는 한 재실행되지 않습니다.
RUN mvn dependency:go-offline

# 소스 코드 복사
COPY src src

# 애플리케이션 빌드 및 JAR 파일 생성
RUN mvn package -DskipTests

# Jar Layer 추출 (레이어별로 분리된 파일들을 'extracted' 디렉토리에 생성)
# ${project.build.finalName}.jar는 pom.xml에 정의된 최종 아티팩트 이름입니다.
ARG JAR_FILE=/app/target/*.jar
RUN java -Djarmode=layertools -jar ${JAR_FILE} extract --destination extracted/

# ----------------------------------------------------
# 2단계: 최종 실행 스테이지 (PRODUCTION)
# JRE만 포함된 경량 이미지를 사용하여 최종 이미지 크기를 최소화하고 보안을 강화합니다.
# ----------------------------------------------------
FROM eclipse-temurin:17-jre-jammy

# 컨테이너 내부에 'spring' 그룹 및 유저 생성 (보안 최적화)
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring

# 컨테이너 포트 노출
EXPOSE 8080

# 최종 실행 환경에 레이어별로 복사 (캐시 활용을 위해 변경 가능성이 낮은 것부터)
WORKDIR /app
COPY --from=builder /app/extracted/dependencies/ ./
COPY --from=builder /app/extracted/spring-boot-loader/ ./
COPY --from=builder /app/extracted/snapshot-dependencies/ ./
COPY --from=builder /app/extracted/application/ ./

# 컨테이너가 시작될 때 실행할 명령어
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
FROM ubuntu:latest
LABEL authors="jinny"
# ----------------------------------------------------
# 1단계: 빌드 스테이지 (BUILDER)
# Maven과 JDK가 모두 포함된 환경을 사용하여 빌드합니다.
# ----------------------------------------------------
FROM maven:3.8.7-eclipse-temurin-17 AS builder

# 작업 디렉토리 설정
WORKDIR /app

# Maven 캐시 활용을 위해 설정 파일만 먼저 복사
COPY pom.xml .

# 종속성 다운로드 (코드 변경 시에도 캐시를 활용하여 빌드 속도를 높임)
# 이 단계는 pom.xml 파일이 변경되지 않는 한 재실행되지 않습니다.
RUN mvn dependency:go-offline

# 소스 코드 복사
COPY src src

# 애플리케이션 빌드 및 JAR 파일 생성
RUN mvn package -DskipTests

# Jar Layer 추출 (레이어별로 분리된 파일들을 'extracted' 디렉토리에 생성)
# ${project.build.finalName}.jar는 pom.xml에 정의된 최종 아티팩트 이름입니다.
ARG JAR_FILE=/app/target/*.jar
RUN java -Djarmode=layertools -jar ${JAR_FILE} extract --destination extracted/

ENTRYPOINT ["top", "-b"]