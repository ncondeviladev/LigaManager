package org.example.servicio;

import org.example.modelos.*;
import org.example.modelos.competicion.Jornada;
import org.example.repositorios.dao.*;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;

import java.util.List;

public class SimularJornada {

    private static final LigaRepo repo = RepoFactory.getRepositorio("JSON");

    // DAOs instanciados al inicio para uso en toda la clase
    private static final UsuarioDAO usuarioDAO = repo.getUsuarioDAO();
    private static final EquipoDAO equipoDAO =  repo.getEquipoDAO();
    private static final MercadoDAO mercadoDAO = repo.getMercadoDAO();
    private static final JugadorDAO jugadorDAO = repo.getJugadorDAO();
    private static final JornadaDAO jornadaDAO  = repo.getJornadaDAO();

    /**
     * Simula todos los partidos de una jornada completa
     */
    public static void simularJornada(
            Jornada jornada,
            List<Jugador> todosLosJugadores) {

        System.out.println("===== JORNADA " + jornada.getNumero() + " =====");

        for (var partido : jornada.getPartidos()) {

            Usuario local = usuarioDAO.buscarPorIdEquipo(partido.getEquipoLocalId()).get();
            Usuario visitante = usuarioDAO.buscarPorIdEquipo(partido.getEquipoVisitanteId()).get();

            Equipo equipoLocal = equipoDAO.buscarPorId(partido.getEquipoLocalId()).get();
            Equipo equipoVisitante = equipoDAO.buscarPorId(partido.getEquipoVisitanteId()).get();

            // Validaciones básicas
            if (local.getAlineacion() == null || visitante.getAlineacion() == null) {
                System.out.println("Partido no jugado por alineación incompleta");
                continue;
            }

            // Simulación del partido (OPCIÓN B)
            SimularPartido.simularPartido(
                    local,
                    visitante,
                    todosLosJugadores,
                    equipoLocal,
                    equipoVisitante
            );
        }

        jornadaDAO.guardar(jornada);

        System.out.println("===== FIN DE JORNADA =====");
    }
}

