FROM ubuntu:latest
LABEL authors="jinny"

ENTRYPOINT ["top", "-b"]