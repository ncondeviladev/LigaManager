package org.example.repositorios.jdbc;

import org.example.modelos.competicion.Gol;
import org.example.modelos.competicion.Jornada;
import org.example.modelos.competicion.Partido;
import org.example.repositorios.dao.JornadaDAO;
import org.example.utils.dataUtils.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación JDBC para Jornadas.
 */
public class JornadaDAOImplJDBC implements JornadaDAO {

    @Override
    public List<Jornada> listarTodas() {
        List<Jornada> jornadas = new ArrayList<>();
        String sql = "SELECT * FROM jornadas ORDER BY numero";

        try (Connection conn = ConexionBD.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                jornadas.add(mapResultSetToJornada(conn, rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al listar jornadas", e);
        }
        return jornadas;
    }

    @Override
    public Optional<Jornada> buscarPorId(String id) {
        String sql = "SELECT * FROM jornadas WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToJornada(conn, rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar jornada por ID: " + id, e);
        }
        return Optional.empty();
    }

    /**
     * Guarda o actualiza una Jornada (Upsert).
     * - Unifica crear/editar para simplificar la lógica de negocio.
     * - ON CONFLICT (id): Detecta si el ID ya existe en la BD.
     * - DO UPDATE: Si existe, actualiza en lugar de fallar.
     * - EXCLUDED: Palabra clave de Postgres para acceder a los valores que
     * intentábamos insertar.
     * Permite un código más limpio sin repetir parámetros.
     */
    @Override
    public void guardar(Jornada jornada) {
        String sql = "INSERT INTO jornadas (id, numero) VALUES (?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET numero = EXCLUDED.numero";

        try (Connection conn = ConexionBD.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, jornada.getId());
                pstmt.setInt(2, jornada.getNumero());

                pstmt.executeUpdate();
            }

            // Guardar partidos de la jornada
            guardarPartidos(conn, jornada);

            conn.commit();
        } catch (SQLException e) {
            throw new DataAccessException("Error al guardar jornada", e);
        }
    }

    private void guardarPartidos(Connection conn, Jornada jornada) throws SQLException {
        if (jornada.getPartidos() == null)
            return;

        String sqlPartido = "INSERT INTO partidos (id, jornada_id, equipo_local_id, equipo_visitante_id, goles_local, goles_visitante) "
                +
                "VALUES (?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET " +
                "jornada_id = EXCLUDED.jornada_id, equipo_local_id = EXCLUDED.equipo_local_id, " +
                "equipo_visitante_id = EXCLUDED.equipo_visitante_id, goles_local = EXCLUDED.goles_local, " +
                "goles_visitante = EXCLUDED.goles_visitante";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlPartido)) {
            for (Partido partido : jornada.getPartidos()) {
                pstmt.setString(1, partido.getId());
                pstmt.setString(2, jornada.getId());
                pstmt.setString(3, partido.getEquipoLocalId());
                pstmt.setString(4, partido.getEquipoVisitanteId());
                pstmt.setInt(5, partido.getGolesLocal());
                pstmt.setInt(6, partido.getGolesVisitante());

                pstmt.executeUpdate();

                // Guardar Goles del partido
                guardarGoles(conn, partido);
            }
        }
    }

    private void guardarGoles(Connection conn, Partido partido) throws SQLException {
        String sqlDelete = "DELETE FROM goles WHERE partido_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
            pstmt.setString(1, partido.getId());
            pstmt.executeUpdate();
        }

        if (partido.getGoles() == null || partido.getGoles().isEmpty())
            return;

        String sqlInsert = "INSERT INTO goles (partido_id, jugador_id, minuto) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            for (Gol gol : partido.getGoles()) {
                pstmt.setString(1, partido.getId());
                pstmt.setString(2, gol.getJugadorId());
                pstmt.setInt(3, gol.getMinuto());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    @Override
    public Optional<Jornada> buscarPorNumero(int numero) {
        String sql = "SELECT * FROM jornadas WHERE numero = ?";
        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, numero);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToJornada(conn, rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar jornada por número: " + numero, e);
        }
        return Optional.empty();
    }

    @Override
    public void guardarTodas(List<Jornada> jornadas) {
        for (Jornada j : jornadas) {
            guardar(j);
        }
    }

    @Override
    public void eliminarPorId(String id) {
        String sql = "DELETE FROM jornadas WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar jornada: " + id, e);
        }
    }

    private Jornada mapResultSetToJornada(Connection conn, ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        int numero = rs.getInt("numero");
        List<Partido> partidos = cargarPartidos(conn, id);
        return new Jornada(id, numero, partidos);
    }

    private List<Partido> cargarPartidos(Connection conn, String jornadaId) throws SQLException {
        List<Partido> partidos = new ArrayList<>();
        String sql = "SELECT * FROM partidos WHERE jornada_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jornadaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    partidos.add(mapResultSetToPartido(conn, rs));
                }
            }
        }
        return partidos;
    }

    private Partido mapResultSetToPartido(Connection conn, ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String jId = rs.getString("jornada_id");
        String local = rs.getString("equipo_local_id");
        String visit = rs.getString("equipo_visitante_id");
        int gLocal = rs.getInt("goles_local");
        int gVisit = rs.getInt("goles_visitante");
        List<Gol> goles = cargarGoles(conn, id);

        return new Partido(id, jId, local, visit, gLocal, gVisit, goles);
    }

    private List<Gol> cargarGoles(Connection conn, String partidoId) throws SQLException {
        List<Gol> goles = new ArrayList<>();
        String sql = "SELECT * FROM goles WHERE partido_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, partidoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    goles.add(new Gol(rs.getString("jugador_id"), rs.getInt("minuto")));
                }
            }
        }
        return goles;
    }
}
