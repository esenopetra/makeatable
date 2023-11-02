import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;

public class SecretKeyGenerator {
    public static void main(String[] args) {
        // Generate a secure secret key for HMAC-SHA256
        Key secretKey = generateSecureSecretKey();

        // Encode the actual key value in base64url
        String base64UrlKey = base64UrlEncode(secretKey);

        System.out.println("Generated Secure Secret Key (base64url): " + base64UrlKey);
    }

    private static Key generateSecureSecretKey() {
        // Generate a secure secret key for HMAC-SHA256
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    private static String base64UrlEncode(Key key) {
        byte[] keyBytes = key.getEncoded();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(keyBytes);
    }
}
