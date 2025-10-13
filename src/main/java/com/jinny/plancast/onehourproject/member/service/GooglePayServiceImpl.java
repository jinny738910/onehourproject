package com.jinny.plancast.onehourproject.member.service;

import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.apps.paymentmethodtoken.PaymentMethodTokenRecipient;
import com.google.crypto.tink.apps.paymentmethodtoken.GooglePaymentsPublicKeysManager;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Service
public class GooglePayService {

    private String recipientId;
    private String privateKeyBase64;
    private PaymentMethodTokenRecipient recipient;

    // 생성자를 통해 값을 명시적으로 주입받음
    public GooglePayService(
            @Value("${googlepay.recipient.id}") String recipientId,
            @Value("${googlepay.private.key}") String privateKeyBase64) {

        this.recipientId = recipientId;
        this.privateKeyBase64 = privateKeyBase64;
    }

    @PostConstruct
    public void init() throws GeneralSecurityException, IOException {
        // Tink 라이브러리 초기화
        AeadConfig.register();

        // 2. Private Key가 올바르게 로드되는지 확인
//        PrivateKey privateKey = PaymentMethodTokenRecipient.Base64EncodedPrivateKeyReader
//                .read(privateKeyBase64);

        // 1. Private Key 문자열을 Base64 디코딩
        // Base64Utils는 Spring Framework의 유틸리티 클래스입니다.
        byte[] keyBytes = Base64.getDecoder().decode(
                // -----BEGIN PRIVATE KEY-----\n 및 -----END PRIVATE KEY-----\n을 제거해야 합니다.
                removePemHeaders(privateKeyBase64)
        );

        // 2. PKCS#8 KeyFactory를 사용하여 PrivateKey 객체 생성
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("EC"); // Google Pay는 ECC (타원 곡선 암호화) 사용
        PrivateKey privateKey = kf.generatePrivate(spec);


        // 2. ★ PrivateKey를 ECPrivateKey로 캐스팅합니다. (해결책)
        if (!(privateKey instanceof ECPrivateKey)) {
            throw new GeneralSecurityException("Private key is not an instance of ECPrivateKey.");
        }
        ECPrivateKey ecPrivateKey = (ECPrivateKey) privateKey;

        // 3. Recipient 객체 빌드 (환경 확인)
        recipient = new PaymentMethodTokenRecipient.Builder()
                .addRecipientPrivateKey(ecPrivateKey)
                .recipientId(recipientId)
                .fetchSenderVerifyingKeysWith(GooglePaymentsPublicKeysManager.INSTANCE_TEST)
                .build();
    }

    // 키 문자열에서 PEM 헤더/푸터 및 줄바꿈 문자를 제거하는 헬퍼 메서드
    private String removePemHeaders(String key) {
        return key.replaceAll("-----BEGIN PRIVATE KEY-----\n", "")
                .replaceAll("-----END PRIVATE KEY-----\n", "")
                .replaceAll("\n", "");
    }


    /**
     * Google Pay 암호화 토큰을 해독하고 유효성을 검증합니다.
     * @param encryptedToken 안드로이드 앱에서 전송된 JSON 문자열
     * @return 해독된 결제 페이로드 (JSON 문자열)
     */
    public String decryptAndVerifyToken(String encryptedToken) throws GeneralSecurityException {
        // 토큰 해독 및 서명 검증
        byte[] decryptedBytes = recipient.unseal(encryptedToken).getBytes();

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * 해독된 정보를 사용하여 결제 게이트웨이를 통해 실제 결제를 처리합니다.
     * (이 부분은 PG사 API에 따라 완전히 달라지므로 가상의 로직입니다.)
     */
    public boolean processPayment(String decryptedPayloadJson, double amount, String currency) {
        try {
            // 1. 해독된 JSON 파싱 (예: Gson 또는 Jackson 사용)
            // PaymentDataDecrypted payload = parseJson(decryptedPayloadJson);

            // 2. PG사 (Stripe, Adyen 등) API 호출에 필요한 데이터 추출
            // 예: payload.getPaymentMethodDetails().getGatewayToken()

            // 3. PG사 API 호출 (실제 결제 승인 요청)
            // boolean success = paymentGatewayClient.charge(token, amount, currency);

            // 가상 성공
            return true;
        } catch (Exception e) {
            System.err.println("Payment processing failed: " + e.getMessage());
            return false;
        }
    }
}
