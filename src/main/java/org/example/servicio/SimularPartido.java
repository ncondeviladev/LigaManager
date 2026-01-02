package org.example.servicio;

import org.example.modelos.*;
import org.example.modelos.competicion.Gol;
import org.example.modelos.competicion.Partido;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;
import org.example.utils.dataUtils.DataAccessException;

import java.util.ArrayList;
import java.util.List;

public class SimularPartido {

    private static final LigaRepo repo = RepoFactory.getRepositorio(System.getProperty("TIPO_DATOS", "JSON"));

    private static final int JUGADAS_MAX = 120;

    public static void simularPartido(
            Partido partido,
            Alineacion alineacionLocal,
            Alineacion alineacionVisitante,
            List<Jugador> jugadores,
            Equipo equipoLocal,
            Equipo equipoVisitante) {

        try {
            Jugador[] eqLocal = construirEquipo(alineacionLocal, jugadores);
            Jugador[] eqVisitante = construirEquipo(alineacionVisitante, jugadores);

            int golesLocal = 0;
            int golesVisitante = 0;

            List<Gol> goles = new ArrayList<>();

            boolean posesionLocal = Math.random() < 0.5;
            int posicionPelota = 5;

            for (int i = 0; i < JUGADAS_MAX; i++) {

                Jugador atacante = posesionLocal
                        ? eqLocal[posicionPelota]
                        : eqVisitante[posicionPelota];

                Jugador defensor = posesionLocal
                        ? eqVisitante[10 - posicionPelota]
                        : eqLocal[10 - posicionPelota];

                int fuerzaAtacante = calcularFuerza(atacante);
                int fuerzaDefensor = calcularFuerza(defensor);

                int diferencia = fuerzaAtacante - fuerzaDefensor;

                // ---- DUELO ----
                if (diferencia > -10) {

                    // ---- PROBABILIDAD DE AVANZAR ----
                    if (Math.random() < 0.65) {
                        posicionPelota++;
                    }

                    // ---- ZONA DE GOL ----
                    if (posicionPelota > 10) {

                        // ---- PROBABILIDAD DE GOL ----
                        double probGol = 0.25 + (diferencia / 100.0);
                        probGol = Math.max(0.1, Math.min(probGol, 0.6));

                        if (Math.random() < probGol) {

                            int minuto = (int) (Math.random() * 90) + 1;

                            if (posesionLocal) {
                                golesLocal++;
                                goles.add(new Gol(atacante.getId(), minuto));
                            } else {
                                golesVisitante++;
                                goles.add(new Gol(atacante.getId(), minuto));
                            }
                        }

                        // Cambio de posesión tras ocasión
                        posesionLocal = !posesionLocal;
                        posicionPelota = 5;
                    }

                } else {
                    // ---- DEFENSA GANA ----
                    posesionLocal = !posesionLocal;
                    posicionPelota = 5;
                }
            }

            // Guardar resultado
            partido.setGolesLocal(golesLocal);
            partido.setGolesVisitante(golesVisitante);
            partido.setGoles(goles);

            // Actualizar equipos
            actualizarEquipos(equipoLocal, equipoVisitante, golesLocal, golesVisitante);

            repo.getEquipoDAO().guardar(equipoLocal);
            repo.getEquipoDAO().guardar(equipoVisitante);

            System.out.println(
                    equipoLocal.getNombre() + " " + golesLocal +
                            " - " + golesVisitante + " " + equipoVisitante.getNombre());
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    // ---------------- AUXILIARES ----------------

    private static void actualizarEquipos(
            Equipo local, Equipo visitante,
            int gl, int gv) {
        local.setGf(local.getGf() + gl);
        local.setGc(local.getGc() + gv);

        visitante.setGf(visitante.getGf() + gv);
        visitante.setGc(visitante.getGc() + gl);

        if (gl > gv) {
            local.setPuntos(local.getPuntos() + 3);
        } else if (gv > gl) {
            visitante.setPuntos(visitante.getPuntos() + 3);
        } else {
            local.setPuntos(local.getPuntos() + 1);
            visitante.setPuntos(visitante.getPuntos() + 1);
        }
    }

    private static Jugador[] construirEquipo(Alineacion alineacion, List<Jugador> jugadores) {
        Jugador[] equipo = new Jugador[11];
        int index = 0;

        equipo[index++] = buscarJugador(alineacion.getPortero(), jugadores);
        for (String id : alineacion.getDefensas())
            equipo[index++] = buscarJugador(id, jugadores);
        for (String id : alineacion.getMedios())
            equipo[index++] = buscarJugador(id, jugadores);
        for (String id : alineacion.getDelanteros())
            equipo[index++] = buscarJugador(id, jugadores);

        return equipo;
    }

    private static Jugador buscarJugador(String id, List<Jugador> jugadores) {
        return jugadores.stream()
                .filter(j -> j.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

    private static int calcularFuerza(Jugador jugador) {

        int base = switch (jugador.getPosicion()) {
            case PORTERO -> jugador.getPorteria();
            case DEFENSA -> jugador.getDefensa();
            case MEDIO -> jugador.getPase();
            case DELANTERO -> jugador.getAtaque();
        };

        int forma = jugador.getEstadoForma();
        int azar = (int) (Math.random() * 21) - 10; // ±10

        return (int) (base * 0.6 + forma * 0.4) + azar;
    }
}
