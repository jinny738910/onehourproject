package com.jinny.plancast.onehourproject.member.service.googlePay;

import org.springframework.stereotype.Service;

@Service
public class GooglePayServiceImpl implements GooglePayService {

//    private String recipientId;
//    private PaymentMethodTokenRecipient recipient;
//
//    @Value("${google.service.account.key.file}")
//    private String serviceAccountKeyFile;
//
//    private final ResourceLoader resourceLoader;
//
//    // 생성자를 통해 값을 명시적으로 주입받음
//    public GooglePayServiceImpl(
//            @Value("${googlepay.recipient.id}") String recipientId,
//            ResourceLoader resourceLoader) {
//
//        this.recipientId = recipientId;
//        this.resourceLoader = resourceLoader;
//    }
//
//    // JSON 파일 구조를 정의하는 내부 클래스
//    private static class ServiceAccountKey {
//        private String private_key; // JSON 필드명과 일치해야 합니다.
//    }
//
//    @PostConstruct
//    public void init() throws GeneralSecurityException, IOException {
//        // Tink 라이브러리 초기화
//        AeadConfig.register();
//
//        // 1. Spring ResourceLoader를 사용하여 JSON 키 파일을 classpath에서 찾습니다.
//        Resource resource = resourceLoader.getResource("classpath:" + serviceAccountKeyFile);
//
//        try (InputStream inputStream = resource.getInputStream()) {
//            // JSON 파싱을 통해 키 데이터만 추출하고, 자동으로 정제가 진행됩니다.
//            ECPrivateKey ecPrivateKey = loadEcPrivateKeyFromInputStream(inputStream);
//
//            // 3. Recipient 객체 빌드 (환경 확인)
//            recipient = new PaymentMethodTokenRecipient.Builder()
//                    .addRecipientPrivateKey(ecPrivateKey)
//                    .recipientId(recipientId)
//                    .fetchSenderVerifyingKeysWith(GooglePaymentsPublicKeysManager.INSTANCE_TEST)
//                    .build();
//        }
//
//        // 2. Private Key가 올바르게 로드되는지 확인
////        PrivateKey privateKey = PaymentMethodTokenRecipient.Base64EncodedPrivateKeyReader
////                .read(privateKeyBase64);
//
//    }
//
//    public static void findHiddenCharacters(String rawString) {
//        System.out.println("--- Hidden Character Analysis ---");
//        int unexpectedCharCount = 0;
//
//        // 허용되는 Base64 문자 패턴
//        java.util.regex.Pattern validBase64 =
//                java.util.regex.Pattern.compile("[a-zA-Z0-9+/=]");
//
//        for (int i = 0; i < rawString.length(); i++) {
//            char c = rawString.charAt(i);
//            String s = String.valueOf(c);
//
//            // Base64 유효 문자가 아닌 것을 찾습니다.
//            if (!validBase64.matcher(s).matches()) {
//                // 유효하지 않은 문자를 발견했을 때 해당 문자와 ASCII/유니코드 값을 출력합니다.
//                System.out.printf("Index %d: Character: '%s', Unicode Code Point: %d (0x%X)\n",
//                        i, s, (int) c, (int) c);
//                unexpectedCharCount++;
//                if (unexpectedCharCount > 50) { // 너무 많으면 중단
//                    System.out.println("... Too many unexpected characters. Stopping scan.");
//                    break;
//                }
//            }
//        }
//        System.out.println("Total unexpected characters found: " + unexpectedCharCount);
//        System.out.println("-------------------------------------");
//    }
//
//    /**
//     * [우회 로직] InputStream으로부터 JSON을 읽어 Private Key를 로드합니다.
//     */
//    public static ECPrivateKey loadEcPrivateKeyFromInputStream(InputStream inputStream)
//            throws GeneralSecurityException, IOException {
//
//        // 1. InputStream에서 JSON 파일 내용을 문자열로 읽어옵니다.
//        String jsonContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
//
//        // 2. Gson을 사용하여 JSON을 파싱하고 private_key 필드 값을 추출합니다.
//        Gson gson = new Gson();
//        ServiceAccountKey keyData = gson.fromJson(jsonContent, ServiceAccountKey.class);
//
//        if (keyData == null || keyData.private_key == null) {
//            throw new GeneralSecurityException("JSON 파일에서 'private_key' 필드를 찾을 수 없습니다.");
//        }
//
//        // 3. 추출된 키 문자열을 정제 로직에 전달합니다. (여기서 정제는 필수)
//        System.out.println("Final Key Bytes Length: " + keyData.private_key.length());
//        System.out.println("Final Key Bytes: " + keyData.private_key);
//        return loadEcPrivateKey(keyData.private_key);
//    }
//
//    public static ECPrivateKey loadEcPrivateKey(String pemKeyString) throws GeneralSecurityException {
//
//        java.util.regex.Pattern base64Pattern =
//                java.util.regex.Pattern.compile("[^a-zA-Z0-9+/=]");
//        java.util.regex.Matcher matcher = base64Pattern.matcher(pemKeyString);
//
//        System.out.println("--- Non-Base64 Characters Found ---");
//        int count = 0;
//        while (matcher.find() && count < 20) { // 너무 많을까봐 20개만 출력
//            String nonBase64Char = matcher.group();
//            // 문자열 자체와 해당 문자의 유니코드/ASCII 값을 출력합니다.
//            System.out.printf("Index %d: Char: '%s', Unicode/ASCII: %d\n",
//                    matcher.start(), nonBase64Char, (int) nonBase64Char.charAt(0));
//            count++;
//        }
//        System.out.println("----------------------------------");
//
//        // 1. 헤더/푸터 및 JSON 이스케이프 문자열 제거
//        String cleanedKey = pemKeyString
//                .replaceAll("-----BEGIN PRIVATE KEY-----", "")
//                .replaceAll("-----END PRIVATE KEY-----", "")
//                .replace("\\", "") // 💡 역슬래시 문자 자체를 모두 제거
//                .replace("\"", ""); // 💡 큰따옴표 문자 자체를 모두 제거
//
//        // 2. [가장 강력한 정제] Base64 유효 문자를 제외한 모든 문자(공백, 줄바꿈, \n, \r, 기타 유니코드)를 제거합니다.
//        // 이 정규식은 [a-z, A-Z, 0-9, +, /, =] 만 남기고 나머지를 모두 제거합니다.
//        String pkcs8KeyBase64 = cleanedKey.replaceAll("[^a-zA-Z0-9+/=]", "");
//
//        // --- 디버깅용 로그 (선택 사항) ---
//         System.out.println("Final Key Bytes Lengt: " + pkcs8KeyBase64.length());
//        System.out.println("Cleaned Base64 Key: " + pkcs8KeyBase64);
//
//        findHiddenCharacters(pkcs8KeyBase64);
//
//        // 3. Base64 디코딩
//        byte[] keyBytes;
//        try {
//            keyBytes = Base64.getDecoder().decode(pkcs8KeyBase64);
//        } catch (IllegalArgumentException e) {
//            // 정제 로직이 실패했거나 키 자체가 잘못된 경우
//            throw new GeneralSecurityException("Base64 디코딩 실패. 키 문자열을 다시 확인하세요.", e);
//        }
//
//        // 4. PKCS#8 KeySpec 및 ECPrivateKey 객체 생성
//        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
//        KeyFactory kf = KeyFactory.getInstance("EC");
//        PrivateKey genericPrivateKey = kf.generatePrivate(spec);
//
//        // 5. ECPrivateKey로 캐스팅하여 반환 (Tink 라이브러리 요구 사항)
//        if (!(genericPrivateKey instanceof ECPrivateKey)) {
//            throw new GeneralSecurityException("로드된 Private key는 EC(Elliptic Curve) 타입이 아닙니다.");
//        }
//
//        return (ECPrivateKey) genericPrivateKey;
//    }
//
//
//    // 키 문자열에서 PEM 헤더/푸터 및 줄바꿈 문자를 제거하는 헬퍼 메서드
//    public String removePemHeaders(String key) {
//
//        if (key == null) return null;
//
//        // 1) Remove PEM headers/footers (any key type)
//        String s = key.replaceAll("-----BEGIN [^-]+-----", "")
//                .replaceAll("-----END [^-]+-----", "");
//
//        // 2) Remove escaped newlines/tabs/backslashes (double and single escaped)
//        s = s.replace("\\\\n", "").replace("\\\\r", "").replace("\\\\t", "");
//        s = s.replace("\\n", "").replace("\\r", "").replace("\\t", "");
//
//        // 3) Remove actual CR/LF
//        s = s.replace("\r", "").replace("\n", "");
//
//        // 4) Keep only Base64 characters
//        s = s.replaceAll("[^A-Za-z0-9+/=]", "");
//
//        return s;
//
//    }
//
//
//    /**
//     * Google Pay 암호화 토큰을 해독하고 유효성을 검증합니다.
//     * @param encryptedToken 안드로이드 앱에서 전송된 JSON 문자열
//     * @return 해독된 결제 페이로드 (JSON 문자열)
//     */
//    public String decryptAndVerifyToken(String encryptedToken) throws GeneralSecurityException {
//        // 토큰 해독 및 서명 검증
//        byte[] decryptedBytes = recipient.unseal(encryptedToken).getBytes();
//
//        return new String(decryptedBytes, StandardCharsets.UTF_8);
//    }
//
//    /**
//     * 해독된 정보를 사용하여 결제 게이트웨이를 통해 실제 결제를 처리합니다.
//     * (이 부분은 PG사 API에 따라 완전히 달라지므로 가상의 로직입니다.)
//     */
//    public boolean processPayment(String decryptedPayloadJson, double amount, String currency) {
//        try {
//            // 1. 해독된 JSON 파싱 (예: Gson 또는 Jackson 사용)
//            // PaymentDataDecrypted payload = parseJson(decryptedPayloadJson);
//
//            // 2. PG사 (Stripe, Adyen 등) API 호출에 필요한 데이터 추출
//            // 예: payload.getPaymentMethodDetails().getGatewayToken()
//
//            // 3. PG사 API 호출 (실제 결제 승인 요청)
//            // boolean success = paymentGatewayClient.charge(token, amount, currency);
//
//            // 가상 성공
//            return true;
//        } catch (Exception e) {
//            System.err.println("Payment processing failed: " + e.getMessage());
//            return false;
//        }
//    }
}
