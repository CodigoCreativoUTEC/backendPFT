package codigocreativo.uy.servidorapp.jwt;

import jakarta.ejb.Stateless;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@Stateless
public class JwtService {

    private final String secret = System.getenv("SECRET_KEY");

    public String generateToken(String email, String nombrePerfil) {
        try {
            return Jwts.builder()
                .setSubject(email)
                .claim("perfil", nombrePerfil)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 400 * 60 * 1000)) // 5 min de expiracion
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        } catch (Exception e) {
            System.out.println("Error al generar el token: " + e.getMessage());
            return null;  // Retornar null si ocurre algún error
        }
    }

    public Claims parseToken(String token) {
    return Jwts.parser()
               .setSigningKey(secret)
               .parseClaimsJws(token)
               .getBody();
}

}