package org.example.repositorios.jdbc;

import org.example.modelos.Mercado;
import org.example.repositorios.dao.MercadoDAO;
import org.example.utils.dataUtils.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación JDBC para el Mercado.
 */
public class MercadoDAOImplJDBC implements MercadoDAO {

    @Override
    public List<Mercado> listarTodos() {
        List<Mercado> ofertas = new ArrayList<>();
        String sql = "SELECT * FROM mercado";

        try (Connection conn = ConexionBD.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ofertas.add(mapResultSetToMercado(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al listar ofertas del mercado", e);
        }
        return ofertas;
    }

    @Override
    public List<Mercado> listarTodosExceptoPropios(String idUsuario) {
        List<Mercado> ofertas = new ArrayList<>();
        String sql = "SELECT * FROM mercado where vendedor_id != ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
             pstmt.setString(1, idUsuario);
             try (ResultSet rs = pstmt.executeQuery()){

                while (rs.next()) {
                    ofertas.add(mapResultSetToMercado(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al listar ofertas del mercado", e);
        }
        return ofertas;
    }

    @Override
    public List<Mercado> listarPropios(String idUsuario) {
        List<Mercado> ofertas = new ArrayList<>();
        String sql = "SELECT * FROM mercado where vendedor_id = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, idUsuario);
            try (ResultSet rs = pstmt.executeQuery()){

                while (rs.next()) {
                    ofertas.add(mapResultSetToMercado(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al listar ofertas del mercado", e);
        }
        return ofertas;
    }

    @Override
    public Optional<Mercado> buscarPorId(String id) {
        String sql = "SELECT * FROM mercado WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToMercado(rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar oferta por ID: " + id, e);
        }
        return Optional.empty();
    }

    /**
     * Guarda o actualiza una oferta (Upsert).
     * - Unifica crear/editar para simplificar la lógica de negocio.
     * - ON CONFLICT (id): Detecta si el ID ya existe en la BD.
     * - DO UPDATE: Si existe, actualiza en lugar de fallar.
     * - EXCLUDED: Palabra clave de Postgres para acceder a los valores que
     * intentábamos insertar.
     * Permite un código más limpio sin repetir parámetros.
     */
    @Override
    public void guardar(Mercado mercado) {
        String sql = "INSERT INTO mercado (id, jugador_id, vendedor_id, precio_venta) VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET " +
                "jugador_id = EXCLUDED.jugador_id, vendedor_id = EXCLUDED.vendedor_id, precio_venta = EXCLUDED.precio_venta";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mercado.getId());
            pstmt.setString(2, mercado.getJugadorId());
            pstmt.setString(3, mercado.getVendedorId());
            pstmt.setDouble(4, mercado.getPrecioVenta());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error al guardar oferta: " + mercado.getId(), e);
        }
    }

    @Override
    public void eliminarPorId(String id) {
        String sql = "DELETE FROM mercado WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar oferta: " + id, e);
        }
    }

    private Mercado mapResultSetToMercado(ResultSet rs) throws SQLException {
        return new Mercado(
                rs.getString("id"),
                rs.getString("jugador_id"),
                rs.getString("vendedor_id"),
                rs.getDouble("precio_venta"));
    }
}
