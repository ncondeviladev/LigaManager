package org.example.repositorios.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de utilidad para gestionar la conexión a la base de datos PostgreSQL.
 * <p>
 * Utiliza el patrón Singleton (o estático en este caso) para centralizar la
 * configuración
 * de acceso a la BD. Carga el driver JDBC al iniciarse la clase.
 * </p>
 */
public class ConexionBD {

    private static final String URL = "jdbc:postgresql://localhost:5432/ligamanager";
    private static final String USER = "ligamanager";
    private static final String PASSWORD = "ligamanager";

    // Bloque estático para cargar el driver una única vez al cargar la clase en
    // memoria.
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver de PostgreSQL", e);
        }
    }

    /**
     * Obtiene una nueva conexión a la base de datos.
     * 
     * @return Connection Objeto de conexión activo.
     * @throws SQLException Si hay error de autenticación o la BD no está accesible.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
