package com.jinny.plancast.onehourproject.member.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PRODUCTS") // Oracle 테이블명 (대문자 권장)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    private int price;

    @Version
    private Long version;
}
