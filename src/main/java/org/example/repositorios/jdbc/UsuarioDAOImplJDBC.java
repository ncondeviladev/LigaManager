package org.example.repositorios.jdbc;

import org.example.modelos.Alineacion;
import org.example.modelos.Usuario;
import org.example.modelos.enums.Formacion;
import org.example.modelos.enums.TipoUsuario;
import org.example.repositorios.dao.UsuarioDAO;
import org.example.utils.dataUtils.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación JDBC para Usuarios.
 */
public class UsuarioDAOImplJDBC implements UsuarioDAO {

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = ConexionBD.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usuarios.add(mapResultSetToUsuario(conn, rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al listar usuarios", e);
        }
        return usuarios;
    }

    @Override
    public Optional<Usuario> buscarPorId(String id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUsuario(conn, rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar usuario por ID: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUsuario(conn, rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar usuario por email: " + email, e);
        }
        return Optional.empty();
    }

    /**
     * Guarda o actualiza un Usuario (Upsert) y gestiona la transacción.
     * - ON CONFLICT (id): Detecta si el ID ya existe en la BD.
     * - DO UPDATE: Si existe, actualiza en lugar de fallar.
     * - EXCLUDED: Palabra clave de Postgres para acceder a los valores que
     * intentábamos insertar.
     * Permite un código más limpio sin repetir parámetros.
     */
    @Override
    public void guardar(Usuario usuario) {
        String sqlUser = "INSERT INTO usuarios (id, email, password, tipo, saldo, id_equipo, formacion) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET " +
                "email = EXCLUDED.email, password = EXCLUDED.password, tipo = EXCLUDED.tipo, " +
                "saldo = EXCLUDED.saldo, id_equipo = EXCLUDED.id_equipo, formacion = EXCLUDED.formacion";

        try (Connection conn = ConexionBD.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(sqlUser)) {
                pstmt.setString(1, usuario.getId());
                pstmt.setString(2, usuario.getEmail());
                pstmt.setString(3, usuario.getPassword());
                pstmt.setString(4, usuario.getTipoUsuario().name());
                pstmt.setDouble(5, usuario.getSaldo());
                pstmt.setString(6, usuario.getIdEquipo());

                String formacion = usuario.getAlineacion() != null && usuario.getAlineacion().getFormacion() != null
                        ? usuario.getAlineacion().getFormacion().toString()
                        : null;
                pstmt.setString(7, formacion);

                pstmt.executeUpdate();
            }

            // Guardar detalles de la alineación
            guardarAlineacion(conn, usuario);

            conn.commit();

        } catch (SQLException e) {
            // ... (rest of exception handling irrelevant for this snippet replacement but
            // keeping for context if needed, though simple block replace is safer)
            e.printStackTrace();
            try {
            } catch (Exception ex) {
            }
            throw new DataAccessException("Error al guardar usuario", e);
        }
    }

    private void guardarAlineacion(Connection conn, Usuario usuario) throws SQLException {
        if (usuario.getAlineacion() == null)
            return;

        // 1. Borrar alineación anterior
        String sqlDelete = "DELETE FROM alineaciones_detalles WHERE usuario_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
            pstmt.setString(1, usuario.getId());
            pstmt.executeUpdate();
        }

        // 2. Insertar nueva alineación
        String sqlInsert = "INSERT INTO alineaciones_detalles (usuario_id, jugador_id, posicion_campo) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            Alineacion alineacion = usuario.getAlineacion();

            if (alineacion.getPortero() != null) {
                addBatchAlineacion(pstmt, usuario.getId(), alineacion.getPortero(), "PORTERO");
            }
            if (alineacion.getDefensas() != null) {
                for (String idJugador : alineacion.getDefensas()) {
                    addBatchAlineacion(pstmt, usuario.getId(), idJugador, "DEFENSA");
                }
            }
            if (alineacion.getMedios() != null) {
                for (String idJugador : alineacion.getMedios()) {
                    addBatchAlineacion(pstmt, usuario.getId(), idJugador, "MEDIO");
                }
            }
            if (alineacion.getDelanteros() != null) {
                for (String idJugador : alineacion.getDelanteros()) {
                    addBatchAlineacion(pstmt, usuario.getId(), idJugador, "DELANTERO");
                }
            }
            pstmt.executeBatch();
        }
    }

    /**
     * Helper para rellenar los parámetros del PreparedStatement de Alineación.
     * Se usa para reutilizar la lógica de asignación de parámetros en operaciones
     * por lotes.
     */
    private void addBatchAlineacion(PreparedStatement pstmt, String usuarioId, String jugadorId, String posicion)
            throws SQLException {
        pstmt.setString(1, usuarioId);
        pstmt.setString(2, jugadorId);
        pstmt.setString(3, posicion);
        pstmt.addBatch();
    }

    @Override
    public void guardarTodos(List<Usuario> usuarios) {
        for (Usuario u : usuarios) {
            guardar(u);
        }
    }

    @Override
    public void eliminarPorId(String id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar usuario: " + id, e);
        }
    }

    @Override
    public Optional<Usuario> buscarPorIdEquipo(String idEquipo) {
        String sql = "SELECT * FROM usuarios WHERE id_equipo = ?";
        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idEquipo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUsuario(conn, rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al buscar usuario por equipo: " + idEquipo, e);
        }
        return Optional.empty();
    }

    private Usuario mapResultSetToUsuario(Connection conn, ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String email = rs.getString("email");
        String password = rs.getString("password");
        TipoUsuario tipo = TipoUsuario.valueOf(rs.getString("tipo"));
        double saldo = rs.getDouble("saldo");
        String idEquipo = rs.getString("id_equipo");
        String formacionStr = rs.getString("formacion");

        Alineacion alineacion = cargarAlineacion(conn, id, formacionStr);

        return new Usuario(id, email, password, tipo, saldo, alineacion, idEquipo);
    }

    private Alineacion cargarAlineacion(Connection conn, String usuarioId, String formacionStr) throws SQLException {
        if (formacionStr == null)
            return null;

        Alineacion alineacion = new Alineacion();
        try {
            alineacion.setFormacion(Formacion.valueOf(formacionStr));
        } catch (IllegalArgumentException e) {
            // Ignorar
        }

        String sql = "SELECT jugador_id, posicion_campo FROM alineaciones_detalles WHERE usuario_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuarioId);
            try (ResultSet rs = pstmt.executeQuery()) {

                List<String> defensas = new ArrayList<>();
                List<String> medios = new ArrayList<>();
                List<String> delanteros = new ArrayList<>();

                while (rs.next()) {
                    String jId = rs.getString("jugador_id");
                    String pos = rs.getString("posicion_campo");

                    switch (pos) {
                        case "PORTERO":
                            alineacion.setPortero(jId);
                            break;
                        case "DEFENSA":
                            defensas.add(jId);
                            break;
                        case "MEDIO":
                            medios.add(jId);
                            break;
                        case "DELANTERO":
                            delanteros.add(jId);
                            break;
                    }
                }
                alineacion.setDefensas(defensas);
                alineacion.setMedios(medios);
                alineacion.setDelanteros(delanteros);
            }
        }
        return alineacion;
    }
}
