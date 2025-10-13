package com.jinny.plancast.onehourproject.user.repository.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long index;
    private String id;
    private String name;
    private String phoneNumber;
}
