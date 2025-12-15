package org.example.repositorios.jdbc;

import org.example.modelos.Equipo;
import org.example.repositorios.dao.EquipoDAO;
import org.example.utils.dataUtils.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación JDBC para la gestión de Equipos.
 */
public class EquipoDAOImplJDBC implements EquipoDAO {

    @Override
    public List<Equipo> listarTodos() {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT * FROM equipos";

        try (Connection conn = ConexionBD.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                equipos.add(mapResultSetToEquipo(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al listar equipos", e);
        }
        return equipos;
    }

    @Override
    public Optional<Equipo> buscarPorId(String id) {
        String sql = "SELECT * FROM equipos WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEquipo(rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar equipo por ID: " + id, e);
        }
        return Optional.empty();
    }

    /**
     * Guarda o actualiza un equipo (Upsert).
     * - Unifica crear/editar para simplificar la lógica de negocio.
     * - ON CONFLICT (id): Detecta si el ID ya existe en la BD.
     * - DO UPDATE: Si existe, actualiza en lugar de fallar.
     * - EXCLUDED: Palabra clave de Postgres para acceder a los valores que
     * intentábamos insertar.
     * Permite un código más limpio sin repetir parámetros.
     */
    @Override
    public void guardar(Equipo equipo) {
        String sql = "INSERT INTO equipos (id, nombre, puntos, gf, gc) VALUES (?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET " +
                "nombre = EXCLUDED.nombre, puntos = EXCLUDED.puntos, gf = EXCLUDED.gf, gc = EXCLUDED.gc";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, equipo.getId());
            pstmt.setString(2, equipo.getNombre());
            pstmt.setInt(3, equipo.getPuntos());
            pstmt.setInt(4, equipo.getGf());
            pstmt.setInt(5, equipo.getGc());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error al guardar equipo", e);
        }
    }

    @Override
    public void guardarTodos(List<Equipo> equipos) {
        String sql = "INSERT INTO equipos (id, nombre, puntos, gf, gc) VALUES (?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET " +
                "nombre = EXCLUDED.nombre, puntos = EXCLUDED.puntos, gf = EXCLUDED.gf, gc = EXCLUDED.gc";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (Equipo equipo : equipos) {

                pstmt.setString(1, equipo.getId());
                pstmt.setString(2, equipo.getNombre());
                pstmt.setInt(3, equipo.getPuntos());
                pstmt.setInt(4, equipo.getGf());
                pstmt.setInt(5, equipo.getGc());

                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conn.commit();

        } catch (SQLException e) {
            throw new DataAccessException("Error al guardar lista de equipos", e);
        }
    }

    @Override
    public void eliminarPorId(String id) {
        String sql = "DELETE FROM equipos WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar equipo: " + id, e);
        }
    }

    private Equipo mapResultSetToEquipo(ResultSet rs) throws SQLException {
        return new Equipo(
                rs.getString("id"),
                rs.getString("nombre"),
                rs.getInt("puntos"),
                rs.getInt("gf"),
                rs.getInt("gc"));
    }
}
