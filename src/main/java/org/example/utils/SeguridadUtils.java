package org.example.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/**
 * Utilidades de seguridad para la aplicación.
 * Proporciona métodos para el hashing de contraseñas.
 */
public class SeguridadUtils {

    /**
     * Genera un hash SHA-256 de la contraseña proporcionada.
     * 
     * @param password La contraseña en texto plano.
     * @return El hash hexadecimal de la contraseña, o null si ocurre un error
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] passHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(passHash);
        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException("Error crítico: Algoritmo de seguridad SHA-256 no disponible en el sistema.", e);
        }
    }

    /**
     * Convierte un array de bytes a una cadena hexadecimal
     */
    private static String bytesToHex(byte[] hash) {
        return HexFormat.of().formatHex(hash);
    }

    // Método privado para evitar instanciación
    private SeguridadUtils() {
    }
}
