package org.example.servicio;

import org.example.modelos.Jugador;
import org.example.repositorios.dao.*;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;
import java.util.List;

public class JugadorServicio {
    private static final LigaRepo repo = RepoFactory.getRepositorio("JSON");

    // DAOs instanciados al inicio para uso en toda la clase
    private static final UsuarioDAO usuarioDAO = repo.getUsuarioDAO();
    private static final EquipoDAO equipoDAO =  repo.getEquipoDAO();
    private static final MercadoDAO mercadoDAO = repo.getMercadoDAO();
    private static final JugadorDAO jugadorDAO = repo.getJugadorDAO();
    private static final JornadaDAO jornadaDAO  = repo.getJornadaDAO();
    /*
    Devuelve todos los jugadores de tu equipo
     */
    public static List<Jugador> getAllJugadoresfromEquipo(String idEquipo) {
        return jugadorDAO.buscarPorIdEquipo(idEquipo);
    }
}
