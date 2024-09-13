package codigocreativo.uy.servidorapp.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.ejb.Stateless;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@Stateless
public class JwtService {

    private final String secret = System.getenv("SECRET_KEY");

    public String generateToken(String email, String nombrePerfil) {
        return Jwts.builder()
                .setSubject(email)
                .claim("perfil", nombrePerfil)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000))// 30 dias de expiracion
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Claims parseToken(String token) {
    return Jwts.parser()
               .setSigningKey(secret)
               .parseClaimsJws(token)
               .getBody();
}

}

//Hola yo de despues, tenes q hacer el war y subirlo, saludos tu yo del pasado