package com.jinny.plancast.onehourproject.member.controller.dto;

import lombok.Data;

@Data
public class GooglePayRequest {
    private String encryptedPaymentToken; // 안드로이드 앱에서 받은 토큰
    private double amount;
    private String currency;
}
