package org.example.servicio;

import org.example.modelos.*;
import org.example.modelos.competicion.Jornada;
import org.example.modelos.competicion.Partido;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;

import java.util.List;

public class SimularJornada {

    private static final LigaRepo repo = RepoFactory.getRepositorio("JSON");

    public static void simularJornada(
            Jornada jornada,
            List<Jugador> todosLosJugadores) {

        System.out.println("===== JORNADA " + jornada.getNumero() + " =====");

        for (Partido partido : jornada.getPartidos()) {

            Equipo equipoLocal = repo.getEquipoDAO()
                    .buscarPorId(partido.getEquipoLocalId())
                    .orElseThrow();

            Equipo equipoVisitante = repo.getEquipoDAO()
                    .buscarPorId(partido.getEquipoVisitanteId())
                    .orElseThrow();

            // Buscar usuarios (pueden no existir)
            Usuario usuarioLocal = repo.getUsuarioDAO()
                    .buscarPorIdEquipo(equipoLocal.getId())
                    .orElse(null);

            Usuario usuarioVisitante = repo.getUsuarioDAO()
                    .buscarPorIdEquipo(equipoVisitante.getId())
                    .orElse(null);

            // Determinar alineaciones
            Alineacion alineacionLocal =
                    (usuarioLocal != null && usuarioLocal.getAlineacion() != null)
                            ? usuarioLocal.getAlineacion()
                            : AlineacionIA.generar(equipoLocal.getId(), todosLosJugadores);

            Alineacion alineacionVisitante =
                    (usuarioVisitante != null && usuarioVisitante.getAlineacion() != null)
                            ? usuarioVisitante.getAlineacion()
                            : AlineacionIA.generar(equipoVisitante.getId(), todosLosJugadores);

            // Simular partido
            SimularPartido.simularPartido(
                    partido,
                    alineacionLocal,
                    alineacionVisitante,
                    todosLosJugadores,
                    equipoLocal,
                    equipoVisitante
            );
        }

        repo.getJornadaDAO().guardar(jornada);

        System.out.println("===== FIN DE JORNADA =====");
    }
}
