package codigocreativo.uy.servidorapp.jwt;

import jakarta.ejb.Stateless;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Stateless
public class JwtService {

    // Decodificar la clave secreta en Base64
    private final Key secretKey = Keys.hmacShaKeyFor(DatatypeConverter.parseBase64Binary(System.getenv("SECRET_KEY")));

    public String generateToken(String email, String nombrePerfil) {
        try {
            return Jwts.builder()
                    .setSubject(email)
                    .claim("perfil", nombrePerfil)
                    .claim("email", email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000)) // 5 min de expiración
                    .signWith(secretKey, SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            return null;  // Retornar null si ocurre algún error
        }
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
