package edu.pku.PKUToolMan.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.pku.PKUToolMan.Entity.User;

import java.util.Date;

public class TokenUtil {
    private static final long EXPIRE_TIME = 10*60*60*1000;
    private static final String SECRET = "PKUTOOLMAN";

    public static String sign(User user) {
        String token = null;
        try {
            Date expireDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("PKUToolMan")
                    .withClaim("id", user.getid())
                    .withExpiresAt(expireDate)
                    .sign(Algorithm.HMAC256(SECRET));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public static boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET))
                    .withIssuer("PKUToolMan")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            System.out.println("TOKEN VERIFY:");
            System.out.println("    id: " + decodedJWT.getClaim("id").asString());
            System.out.println("    expiresAt: " + decodedJWT.getExpiresAt());
            System.out.println("    systemTime: " + System.currentTimeMillis());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
