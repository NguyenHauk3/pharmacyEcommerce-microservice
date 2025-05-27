package com.microsevice.ePharmaMS.user.service;
import com.microsevice.ePharmaMS.user.modal.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.Jwts.*;

@Component
public class JwtService {

    private static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000; // 15 phút
    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000; // 7 ngày
    private final SecretKey Key;

    public JwtService() {
        String secreteString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
        byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateToken(UserDetails userDetails){
//        return builder()
//                .subject(userDetails.getUsername())
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
//                .signWith(Key)
//                .compact();
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities()); // Thêm quyền vào token

        return builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 15 phút
                .signWith(Key) // Thay secretKey bằng khóa bí mật bạn dùng
                .compact();
    }
    public  String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails){
        return builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(Key)
                .compact();
    }


    public  String extractUsername(String token){
        return  extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());
    }

    public  boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public  boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());

    }
//    public UserDetails convertToUserDetails(User user) {
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getEmail())
//                .password(user.getPassword())
//                .authorities(user.getRole())
//                .build();
//    }


//    public  String extractUsername(String token){
//        return  extractClaims(token, Claims::getSubject);
//    }
//
//    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
//        Claims claims = Jwts
//                .parser()
//                .verifyWith(Key)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//
//        return claimsResolver.apply(claims);
//    }
//
//    public  boolean isTokenValid(String token, UserDetails userDetails){
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    public  boolean isTokenExpired(String token){
//        return extractClaims(token, Claims::getExpiration).before(new Date());
//    }

}
