package codigocreativo.uy.servidorapp.JWT;

import io.jsonwebtoken.JwtException;
import jakarta.ejb.Stateless;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@Stateless
public class JwtService {

    private final String secret = "b0bc1f9b2228b2094f3ba7bdb1b6a58059af6cdaf143127181bd0a17e6d312e2";

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 horas
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return String.valueOf(true);
        } catch (JwtException | IllegalArgumentException e) {
            return String.valueOf(false);
        }
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
