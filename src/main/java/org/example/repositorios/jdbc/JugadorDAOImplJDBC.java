package org.example.repositorios.jdbc;

import org.example.modelos.Jugador;
import org.example.modelos.enums.Posicion;
import org.example.repositorios.dao.JugadorDAO;
import org.example.utils.dataUtils.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación JDBC para la gestión de Jugadores.
 */
public class JugadorDAOImplJDBC implements JugadorDAO {

    @Override
    public List<Jugador> listarTodos() {
        List<Jugador> jugadores = new ArrayList<>();
        String sql = "SELECT * FROM jugadores";

        try (Connection conn = ConexionBD.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                jugadores.add(mapResultSetToJugador(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al listar jugadores", e);
        }
        return jugadores;
    }

    @Override
    public List<Jugador> buscarPorIdEquipo(String idEquipo) {
        List<Jugador> jugadores = new ArrayList<>();
        String sql = "SELECT * FROM jugadores WHERE id_equipo = ?";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idEquipo);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    jugadores.add(mapResultSetToJugador(rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar jugadores por equipo: " + idEquipo, e);
        }
        return jugadores;
    }

    @Override
    public Optional<Jugador> buscarPorId(String id) {
        String sql = "SELECT * FROM jugadores WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToJugador(rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar jugador por ID: " + id, e);
        }
        return Optional.empty();
    }

    /**
     * Guarda o actualiza un jugador (Upsert).
     * - Unifica crear/editar para simplificar la lógica de negocio.
     * - ON CONFLICT (id): Detecta si el ID ya existe en la BD.
     * - DO UPDATE: Si existe, actualiza en lugar de fallar.
     * - EXCLUDED: Palabra clave de Postgres para acceder a los valores que
     * intentábamos insertar.
     * Permite un código más limpio sin repetir parámetros.
     */
    @Override
    public void guardar(Jugador jugador) {
        String sql = "INSERT INTO jugadores (id, nombre, posicion, id_equipo, ataque, defensa, pase, porteria, estado_forma, precio) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET " +
                "nombre = EXCLUDED.nombre, posicion = EXCLUDED.posicion, id_equipo = EXCLUDED.id_equipo, " +
                "ataque = EXCLUDED.ataque, defensa = EXCLUDED.defensa, pase = EXCLUDED.pase, " +
                "porteria = EXCLUDED.porteria, estado_forma = EXCLUDED.estado_forma, precio = EXCLUDED.precio";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            setStatementParams(pstmt, jugador, 1);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error al guardar jugador", e);
        }
    }

    @Override
    public void guardarTodos(List<Jugador> jugadores) {
        String sql = "INSERT INTO jugadores (id, nombre, posicion, id_equipo, ataque, defensa, pase, porteria, estado_forma, precio) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET " +
                "nombre = EXCLUDED.nombre, posicion = EXCLUDED.posicion, id_equipo = EXCLUDED.id_equipo, " +
                "ataque = EXCLUDED.ataque, defensa = EXCLUDED.defensa, pase = EXCLUDED.pase, " +
                "porteria = EXCLUDED.porteria, estado_forma = EXCLUDED.estado_forma, precio = EXCLUDED.precio";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (Jugador jugador : jugadores) {
                setStatementParams(pstmt, jugador, 1);
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conn.commit();

        } catch (SQLException e) {
            throw new DataAccessException("Error al guardar lista de jugadores: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarPorId(String id) {
        String sql = "DELETE FROM jugadores WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar jugador: " + id, e);
        }
    }

    /**
     * Helper para rellenar los parámetros del PreparedStatement.
     * Se usa tanto en guardar() como en guardarTodos() para no repetir
     * el bloque de codigo de asignación de variables (pstmt.set...).
     */
    private void setStatementParams(PreparedStatement pstmt, Jugador jugador, int starIndex) throws SQLException {
        pstmt.setString(starIndex, jugador.getId());
        pstmt.setString(starIndex + 1, jugador.getNombre());
        pstmt.setString(starIndex + 2, jugador.getPosicion().name());
        pstmt.setString(starIndex + 3, jugador.getIdEquipo());
        pstmt.setInt(starIndex + 4, jugador.getAtaque());
        pstmt.setInt(starIndex + 5, jugador.getDefensa());
        pstmt.setInt(starIndex + 6, jugador.getPase());
        pstmt.setInt(starIndex + 7, jugador.getPorteria());
        pstmt.setInt(starIndex + 8, jugador.getEstadoForma());
        pstmt.setDouble(starIndex + 9, jugador.getPrecio());
    }

    private Jugador mapResultSetToJugador(ResultSet rs) throws SQLException {
        return new Jugador(
                rs.getString("id"),
                rs.getString("nombre"),
                Posicion.valueOf(rs.getString("posicion")),
                rs.getString("id_equipo"),
                rs.getInt("ataque"),
                rs.getInt("defensa"),
                rs.getInt("pase"),
                rs.getInt("porteria"),
                rs.getInt("estado_forma"),
                rs.getDouble("precio"));
    }
}
