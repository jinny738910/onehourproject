package com.jinny.plancast.onehourproject.member.controller;

import com.jinny.plancast.onehourproject.member.controller.dto.GooglePayRequest;
import com.jinny.plancast.onehourproject.member.service.GooglePayServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.GeneralSecurityException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/googlepay")
public class GooglePayController {

//    private final GooglePayServiceImpl googlePayService;
//
//    public GooglePayController(GooglePayServiceImpl googlePayService) {
//        this.googlePayService = googlePayService;
//    }
//
//    @PostMapping("/charge")
//    public ResponseEntity<?> charge(@RequestBody GooglePayRequest request) {
//        String encryptedToken = request.getEncryptedPaymentToken();
//
//        try {
//            // 1. 토큰 해독 및 검증
//            String decryptedPayload = googlePayService.decryptAndVerifyToken(encryptedToken);
//
//            // 2. 실제 PG사를 통한 결제 승인
//            boolean paymentSuccess = googlePayService.processPayment(
//                    decryptedPayload,
//                    request.getAmount(),
//                    request.getCurrency()
//            );
//
//            if (paymentSuccess) {
//                // 3. 결제 성공 응답
//                return ResponseEntity.ok(Map.of("status", "SUCCESS", "message", "Payment processed."));
//            } else {
//                // 3. 결제 게이트웨이 처리 실패
//                return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
//                        .body(Map.of("status", "FAILED", "message", "Payment Gateway processing failed."));
//            }
//
//        } catch (GeneralSecurityException e) {
//            // 토큰 해독/검증 실패 (무효한 토큰, 변조된 토큰 등)
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(Map.of("status", "FAILED", "message", "Invalid payment token."));
//        } catch (Exception e) {
//            // 기타 서버 오류
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("status", "ERROR", "message", "An unexpected error occurred."));
//        }
//    }
}
