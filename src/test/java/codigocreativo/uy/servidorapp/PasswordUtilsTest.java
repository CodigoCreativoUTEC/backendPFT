package codigocreativo.uy.servidorapp;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilsTest {

    @Test
    void generateSalt() {
        String salt = PasswordUtils.generateSalt();
        assertNotNull(salt);
        assertEquals(24, salt.length());  // Base64 encoded 16 bytes should be 24 characters
    }

    @Test
    void generateSaltedHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "password123";
        String saltedHash = PasswordUtils.generateSaltedHash(password);
        assertNotNull(saltedHash);
        assertTrue(saltedHash.contains(":"));
    }

    @Test
    void verifyPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "password123";
        String saltedHash = PasswordUtils.generateSaltedHash(password);
        assertTrue(PasswordUtils.verifyPassword(password, saltedHash));
        assertFalse(PasswordUtils.verifyPassword("wrongpassword", saltedHash));
    }
}