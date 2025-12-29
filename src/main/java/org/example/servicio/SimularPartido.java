package org.example.servicio;

import org.example.modelos.Alineacion;
import org.example.modelos.Equipo;
import org.example.modelos.Jugador;
import org.example.modelos.Usuario;
import org.example.repositorios.dao.*;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;

import java.util.List;

public class SimularPartido {

    private static final LigaRepo repo = RepoFactory.getRepositorio("JSON");

    // DAOs instanciados al inicio para uso en toda la clase
    private static final UsuarioDAO usuarioDAO = repo.getUsuarioDAO();
    private static final EquipoDAO equipoDAO =  repo.getEquipoDAO();
    private static final MercadoDAO mercadoDAO = repo.getMercadoDAO();
    private static final JugadorDAO jugadorDAO = repo.getJugadorDAO();
    private static final JornadaDAO jornadaDAO  = repo.getJornadaDAO();

    private static final int JUGADAS_MAX = 150;

    public static void simularPartido(
            Usuario local,
            Usuario visitante,
            List<Jugador> todosLosJugadores,
            Equipo equipoLocal,
            Equipo equipoVisitante) {

        Jugador[] eqLocal = construirEquipo(local.getAlineacion(), todosLosJugadores);
        Jugador[] eqVisitante = construirEquipo(visitante.getAlineacion(), todosLosJugadores);

        int golesLocal = 0;
        int golesVisitante = 0;

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

            if (fuerzaAtacante >= fuerzaDefensor) {
                posicionPelota++;

                // GOL
                if (posicionPelota > 10) {
                    if (posesionLocal) {
                        golesLocal++;
                    } else {
                        golesVisitante++;
                    }
                    posesionLocal = !posesionLocal;
                    posicionPelota = 5;
                }

            } else {
                // Cambio de posesión
                posesionLocal = !posesionLocal;
                posicionPelota = 5;
            }
        }

        // Actualizar estadísticas de equipos
        equipoLocal.setGf(equipoLocal.getGf() + golesLocal);
        equipoLocal.setGc(equipoLocal.getGc() + golesVisitante);

        equipoVisitante.setGf(equipoVisitante.getGf() + golesVisitante);
        equipoVisitante.setGc(equipoVisitante.getGc() + golesLocal);

        if (golesLocal > golesVisitante) {
            equipoLocal.setPuntos(equipoLocal.getPuntos() + 3);
        } else if (golesVisitante > golesLocal) {
            equipoVisitante.setPuntos(equipoVisitante.getPuntos() + 3);
        } else {
            equipoLocal.setPuntos(equipoLocal.getPuntos() + 1);
            equipoVisitante.setPuntos(equipoVisitante.getPuntos() + 1);
        }

        equipoDAO.guardar(equipoLocal);
        equipoDAO.guardar(equipoVisitante);
        usuarioDAO.guardar(local);
        usuarioDAO.guardar(visitante);

        System.out.println(equipoLocal.getNombre() + " " + golesLocal +
                " - " + golesVisitante + " " + equipoVisitante.getNombre());
    }

    private static Jugador[] construirEquipo(Alineacion alineacion, List<Jugador> jugadores) {

        Jugador[] equipo = new Jugador[11];
        int index = 0;

        // Portero
        equipo[index++] = buscarJugador(alineacion.getPortero(), jugadores);

        // Defensas
        for (String id : alineacion.getDefensas()) {
            equipo[index++] = buscarJugador(id, jugadores);
        }

        // Medios
        for (String id : alineacion.getMedios()) {
            equipo[index++] = buscarJugador(id, jugadores);
        }

        // Delanteros
        for (String id : alineacion.getDelanteros()) {
            equipo[index++] = buscarJugador(id, jugadores);
        }

        return equipo;
    }

    private static Jugador buscarJugador(String id, List<Jugador> jugadores) {
        return jugadores.stream()
                .filter(j -> j.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado: " + id));
    }

    private static int calcularFuerza(Jugador jugador) {

        int base;

        switch (jugador.getPosicion()) {
            case PORTERO -> base = jugador.getPorteria();
            case DEFENSA -> base = jugador.getDefensa();
            case MEDIO -> base = jugador.getPase();
            case DELANTERO -> base = jugador.getAtaque();
            default -> base = 50;
        }

        int forma = jugador.getEstadoForma();
        int azar = (int) (Math.random() * 11) - 5; // [-5, +5]

        return (int) (base * 0.7 + forma * 0.3) + azar;
    }
}