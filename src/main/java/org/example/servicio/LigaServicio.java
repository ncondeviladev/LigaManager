package org.example.servicio;

import org.example.modelos.Alineacion;
import org.example.modelos.Equipo;
import org.example.modelos.Jugador;
import org.example.modelos.Usuario;
import org.example.modelos.competicion.Jornada;
import org.example.repositorios.dao.*;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;
import org.example.utils.TextTable;
import org.example.utils.dataUtils.DataAccessException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.example.servicio.GeneradorJornadas.generarLigaCompleta;
import static org.example.servicio.SimularJornada.simularJornada;

public class LigaServicio {
    private static final LigaRepo repo = RepoFactory.getRepositorio(System.getProperty("TIPO_DATOS", "JSON"));

    // DAOs instanciados al inicio para uso en toda la clase
    private static final UsuarioDAO usuarioDAO = repo.getUsuarioDAO();
    private static final EquipoDAO equipoDAO = repo.getEquipoDAO();
    private static final MercadoDAO mercadoDAO = repo.getMercadoDAO();
    private static final JugadorDAO jugadorDAO = repo.getJugadorDAO();
    private static final JornadaDAO jornadaDAO = repo.getJornadaDAO();

    private static int contadorJornadas = getJornadas();

    private static int getJornadas() {
        if (jornadaDAO.listarTodas().isEmpty()) {
            return 1;
        } else {
            for (Jornada j : jornadaDAO.listarTodas()) {
                if (j.getPartidos().get(1).getGolesLocal() == -1) {
                    return j.getNumero();
                }
            }
        }
        return -1;
    }

    public static String mostrarClasificacion() {
        try {
            // Lista donde se almacenará la clasificación final ordenada
            List<Equipo> clasificacion = new ArrayList<>();

            // Se obtienen todos los equipos desde la base de datos
            List<Equipo> equipos = new ArrayList<>(equipoDAO.listarTodos());

            // Ordenar manualmente los equipos según puntos, diferencia de goles y GF
            while (clasificacion.size() != 20 && !equipos.isEmpty()) {

                Equipo equipoGuardado = equipos.get(0);

                for (Equipo equipo : equipos) {
                    if (equipo.getPuntos() > equipoGuardado.getPuntos()) {
                        equipoGuardado = equipo;
                    } else if (equipo.getPuntos() == equipoGuardado.getPuntos()) {
                        int diffGoles = equipo.getGf() - equipo.getGc();
                        int diffGolesGuardado = equipoGuardado.getGf() - equipoGuardado.getGc();
                        if (diffGoles > diffGolesGuardado) {
                            equipoGuardado = equipo;
                        } else if (diffGoles == diffGolesGuardado) {
                            if (equipo.getGf() > equipoGuardado.getGf()) {
                                equipoGuardado = equipo;
                            }
                        }
                    }
                }

                equipos.remove(equipoGuardado);
                clasificacion.add(equipoGuardado);
            }

            // Crear tabla con cabeceras
            TextTable table = new TextTable(1,
                    "POS", "EQUIPO", "PUNTOS", "GF", "GC");

            table.setAlign("POS", TextTable.Align.RIGHT);
            table.setAlign("PUNTOS", TextTable.Align.RIGHT);
            table.setAlign("GF", TextTable.Align.RIGHT);
            table.setAlign("GC", TextTable.Align.RIGHT);

            // Añadir filas con la clasificación
            int posicion = 1;
            for (Equipo e : clasificacion) {
                table.addRow(
                        String.valueOf(posicion),
                        e.getNombre(),
                        String.valueOf(e.getPuntos()),
                        String.valueOf(e.getGf()),
                        String.valueOf(e.getGc()));
                posicion++;
            }

            return table.toString();
        } catch (DataAccessException e) {
            return (e.getMessage());
        }
    }

    public static String mostrarJornadas() {
        try {
            List<Jornada> jornadas = jornadaDAO.listarTodas();

            // Ordenar por ID de menor a mayor
            jornadas.sort(Comparator.comparing(Jornada::getId));

            // Crear tabla con cabeceras
            TextTable table = new TextTable(1, "JORNADA", "ID", "PARTIDOS");

            table.setAlign("JORNADA", TextTable.Align.RIGHT);
            table.setAlign("PARTIDOS", TextTable.Align.RIGHT);

            for (Jornada j : jornadas) {
                if (j.getPartidos().get(1).getGolesLocal() < 0) {
                    table.addRow("-", "-", "No jugada todavía");
                    return table.toString();
                } else {
                    table.addRow(
                            String.valueOf(j.getNumero()),
                            j.getId(),
                            String.valueOf(j.getPartidos().size()));
                }
            }

            return table.toString();
        } catch (DataAccessException e) {
            return (e.getMessage());
        }
    }

    public static void realizarSimulacion() {
        try {
            List<Jornada> jornadas = jornadaDAO.listarTodas();
            if (jornadas.isEmpty()) {
                jornadas = generarLigaCompleta(repo, equipoDAO.listarTodos());
            }


            List<Jugador> jugadores = repo.getJugadorDAO().listarTodos();

            assert jornadas != null;
            jornadas.sort(Comparator.comparing(Jornada::getId));

            for (Jornada j : jornadas) {
                if (j.getNumero() == contadorJornadas) {
                    simularJornada(j, jugadores);
                    contadorJornadas++;
                    return;
                }
            }
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
    }
}
