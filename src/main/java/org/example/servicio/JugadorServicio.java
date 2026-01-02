package org.example.servicio;

import org.example.modelos.Jugador;
import org.example.repositorios.dao.*;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;
import org.example.utils.TextTable;
import org.example.utils.dataUtils.DataAccessException;

import java.util.List;

public class JugadorServicio {
    private static final LigaRepo repo = RepoFactory.getRepositorio(System.getProperty("TIPO_DATOS", "JSON"));

    // DAOs instanciados al inicio para uso en toda la clase
    private static final UsuarioDAO usuarioDAO = repo.getUsuarioDAO();
    private static final EquipoDAO equipoDAO = repo.getEquipoDAO();
    private static final MercadoDAO mercadoDAO = repo.getMercadoDAO();
    private static final JugadorDAO jugadorDAO = repo.getJugadorDAO();
    private static final JornadaDAO jornadaDAO = repo.getJornadaDAO();

    /*
     * Devuelve todos los jugadores de tu equipo
     */
    /*
     * Devuelve una tabla con los jugadores de un equipo concreto
     */
    public static String getAllJugadoresfromEquipo(String idEquipo) {
        try {

            List<Jugador> jugadores = jugadorDAO.buscarPorIdEquipo(idEquipo);

            TextTable table = new TextTable(1,
                    "ID",
                    "NOMBRE",
                    "POS",
                    "EQUIPOID",
                    "PRECIO (M€)");

            table.setAlign("ID", TextTable.Align.RIGHT);
            table.setAlign("PRECIO (M€)", TextTable.Align.RIGHT);

            if (jugadores.isEmpty()) {
                table.addRow("-", "No hay jugadores", "-", "-", "-");
                return table.toString();
            }

            for (Jugador j : jugadores) {
                table.addRow(
                        j.getId(),
                        j.getNombre(),
                        j.getPosicion().name(),
                        j.getIdEquipo(),
                        String.format("%.2f", j.getPrecio()));
            }

            return table.toString();
        } catch (DataAccessException e) {
            return e.getMessage();
        }
    }

    /*
     * Devuelve una tabla con todos los jugadores de la liga
     */
    public static List<Jugador> getAllJugadores() {
        try {
            return jugadorDAO.listarTodos();
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
