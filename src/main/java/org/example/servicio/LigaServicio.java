package org.example.servicio;

import org.example.modelos.Alineacion;
import org.example.modelos.Equipo;
import org.example.modelos.Jugador;
import org.example.modelos.Usuario;
import org.example.modelos.competicion.Jornada;
import org.example.repositorios.dao.*;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;

import java.util.ArrayList;
import java.util.List;

import static org.example.servicio.GeneradorJornadas.crearJornada;
import static org.example.servicio.SimularJornada.simularJornada;

public class LigaServicio {
    private static final LigaRepo repo = RepoFactory.getRepositorio("JSON");

    // DAOs instanciados al inicio para uso en toda la clase
    private static final UsuarioDAO usuarioDAO = repo.getUsuarioDAO();
    private static final EquipoDAO equipoDAO =  repo.getEquipoDAO();
    private static final MercadoDAO mercadoDAO = repo.getMercadoDAO();
    private static final JugadorDAO jugadorDAO = repo.getJugadorDAO();
    private static final JornadaDAO jornadaDAO  = repo.getJornadaDAO();

    public static List<Equipo> mostrarClasificacion() {

        // Lista donde se almacenará la clasificación final ordenada
        List<Equipo> clasificacion = new ArrayList<>();

        // Se obtienen todos los equipos desde la base de datos
        List<Equipo> equipos = equipoDAO.listarTodos();

        // Mientras la clasificación no tenga los 20 equipos
        while (clasificacion.size() != 20) {

            // Se toma el primer equipo como referencia inicial
            Equipo equipoGuardado = equipos.get(0);

            // Se recorren todos los equipos para encontrar el mejor
            for (Equipo equipo : equipos) {

                // Si el equipo actual tiene más puntos que el guardado
                if (equipo.getPuntos() > equipoGuardado.getPuntos()) {
                    equipoGuardado = equipo;

                } else {

                    // Si tienen los mismos puntos
                    if (equipo.getPuntos() == equipoGuardado.getPuntos()) {

                        // Se compara la diferencia de goles (GF - GC)
                        if (equipo.getGf() - equipo.getGc() > equipoGuardado.getGf() - equipoGuardado.getGc()) {
                            equipoGuardado = equipo;

                        } else {

                            // Si también empatan en diferencia de goles
                            if (equipo.getGf() - equipo.getGc() == equipoGuardado.getGf() - equipoGuardado.getGc()) {

                                // Se compara el número total de goles a favor
                                if (equipo.getGf() > equipoGuardado.getGf()) {
                                    equipoGuardado = equipo;
                                }
                            }
                        }
                    }
                }
            }
            // Se elimina el equipo seleccionado de la lista de equipos
            equipos.remove(equipoGuardado);

            // Se añade el mejor equipo encontrado a la clasificación
            clasificacion.add(equipoGuardado);
        }

        // Se devuelve la clasificación completa
        return clasificacion;
    }

    public static List<Jornada> mostrarJornadas() {
        return jornadaDAO.listarTodas();
    }

    public static void realizarSimulacion() {

        Jornada nuevaJornada = crearJornada(repo, equipoDAO.listarTodos());

        simularJornada(nuevaJornada, jugadorDAO.listarTodos());
    }
}
