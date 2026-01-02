package org.example.servicio;

import org.example.modelos.Alineacion;
import org.example.modelos.Jugador;
import org.example.modelos.Usuario;
import org.example.modelos.enums.Formacion;
import org.example.modelos.enums.Posicion;
import org.example.repositorios.dao.*;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;
import org.example.utils.TextTable;
import org.example.utils.dataUtils.DataAccessException;

import java.util.*;
import java.util.function.BiConsumer;

public class AlineacionServicio {

    // Repositorio principal (JSON)
    private static final LigaRepo repo = RepoFactory.getRepositorio(System.getProperty("TIPO_DATOS", "JSON"));

    // DAOs para acceso a usuarios y equipos
    private static final UsuarioDAO usuarioDAO = repo.getUsuarioDAO();
    private static final EquipoDAO equipoDAO = repo.getEquipoDAO();
    private static final MercadoDAO mercadoDAO = repo.getMercadoDAO();
    private static final JugadorDAO jugadorDAO = repo.getJugadorDAO();
    private static final JornadaDAO jornadaDAO = repo.getJornadaDAO();

    /**
     * Convierte una alineación a texto legible
     */
    public static String alineacionATexto(Alineacion alineacion) {
        try {
            // Lista completa de jugadores
            List<Jugador> jugadores = JugadorServicio.getAllJugadores();

            // Crear tabla con solo POSICIÓN y NOMBRE
            TextTable table = new TextTable(1, "POSICIÓN", "NOMBRE");

            // Alinear nombre a la izquierda
            table.setAlign("NOMBRE", TextTable.Align.LEFT);

            // Función auxiliar para añadir jugadores a la tabla
            BiConsumer<List<String>, String> addJugadores = (ids, posicion) -> {
                for (String id : ids) {
                    for (Jugador j : jugadores) {
                        if (j.getId().equals(id)) {
                            table.addRow(
                                    posicion,
                                    j.getNombre());
                            break;
                        }
                    }
                }
            };

            // Añadir jugadores por posición
            addJugadores.accept(List.of(alineacion.getPortero()), "PORTERO");
            addJugadores.accept(alineacion.getDefensas(), "DEFENSA");
            addJugadores.accept(alineacion.getMedios(), "MEDIO");
            addJugadores.accept(alineacion.getDelanteros(), "DELANTERO");

            // Devolver tabla como String
            return "Formación: " + alineacion.getFormacion() + "\n" + table.toString();

        } catch (DataAccessException e) {
            return e.getMessage();
        }

    }

    /**
     * Cambia la formación según la opción elegida
     */
    public static void cambiarFormacion(Usuario usuario, int opcion) {
        Alineacion alineacion = usuario.getAlineacion();

        switch (opcion) {
            case 1 -> alineacion.setFormacion(Formacion.F442);
            case 2 -> alineacion.setFormacion(Formacion.F433);
            case 3 -> alineacion.setFormacion(Formacion.F451);
            case 4 -> alineacion.setFormacion(Formacion.F343);
            case 5 -> alineacion.setFormacion(Formacion.F352);
            default -> alineacion.setFormacion(Formacion.F442);
        }

    }

    /**
     * Cambia el portero si el jugador es PORTERO
     */
    public static boolean cambiarPortero(Usuario usuario, String idPortero) {
        try {
            Alineacion alineacion = usuario.getAlineacion();
            List<Jugador> jugadores = jugadorDAO.buscarPorIdEquipo(usuario.getIdEquipo());

            // Buscar jugador por id
            for (Jugador j : jugadores) {
                if (j.getId().equals(idPortero)) {

                    // Verificar posición
                    if (j.getPosicion() != Posicion.PORTERO) {
                        return false;
                    }

                    // Asignar portero
                    alineacion.setPortero(idPortero);
                    usuarioDAO.guardar(usuario);
                    return true;
                }
            }

            // No existe jugador con ese id
            return false;
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Devuelve el número de defensas
     */
    public static int getNumeroDefensas(Usuario usuario) {
        return usuario.getAlineacion().getDefensas().size();
    }

    /**
     * Cambia los defensas verificando posición
     */
    public static boolean cambiarDefensas(Usuario usuario, ArrayList<String> defensas) {
        try {
            Alineacion alineacion = usuario.getAlineacion();
            List<Jugador> jugadores = jugadorDAO.buscarPorIdEquipo(usuario.getIdEquipo());
            List<String> defensasValidas = new ArrayList<>();

            // Crear mapa id -> jugador
            Map<String, Jugador> mapaJugadores = new HashMap<>();
            for (Jugador j : jugadores) {
                mapaJugadores.put(j.getId(), j);
            }

            // Validar defensas
            for (String id : defensas) {
                Jugador j = mapaJugadores.get(id);
                if (j != null && j.getPosicion() == Posicion.DEFENSA) {
                    if (!defensasValidas.contains(id)) {
                        defensasValidas.add(id);
                    }
                }
            }

            if (defensasValidas.size() != getNumeroDefensas(usuario))
                return false;

            alineacion.setDefensas(defensasValidas);
            usuarioDAO.guardar(usuario);
            return true;
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Devuelve el número de medios
     */
    public static int getNumeroMedios(Usuario usuario) {
        return usuario.getAlineacion().getMedios().size();
    }

    /**
     * Cambia los medios verificando posición
     */
    public static boolean cambiarMedios(Usuario usuario, ArrayList<String> medios) {
        try {
            Alineacion alineacion = usuario.getAlineacion();
            List<Jugador> jugadores = jugadorDAO.buscarPorIdEquipo(usuario.getIdEquipo());
            List<String> mediosValidos = new ArrayList<>();

            Map<String, Jugador> mapaJugadores = new HashMap<>();
            for (Jugador j : jugadores) {
                mapaJugadores.put(j.getId(), j);
            }

            for (String id : medios) {
                Jugador j = mapaJugadores.get(id);
                if (j != null && j.getPosicion() == Posicion.MEDIO) {
                    if (!mediosValidos.contains(id)) {
                        mediosValidos.add(id);
                    }
                }
            }

            if (mediosValidos.size() != getNumeroMedios(usuario))
                return false;

            alineacion.setMedios(mediosValidos);
            usuarioDAO.guardar(usuario);
            return true;
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Devuelve el número de delanteros
     */
    public static int getNumeroDelanteros(Usuario usuario) {
        return usuario.getAlineacion().getDelanteros().size();
    }

    /**
     * Cambia los delanteros verificando posición
     */
    public static boolean cambiarDelanteros(Usuario usuario, ArrayList<String> delanteros) {
        try {
            Alineacion alineacion = usuario.getAlineacion();
            List<Jugador> jugadores = jugadorDAO.buscarPorIdEquipo(usuario.getIdEquipo());
            List<String> delanterosValidos = new ArrayList<>();

            Map<String, Jugador> mapaJugadores = new HashMap<>();
            for (Jugador j : jugadores) {
                mapaJugadores.put(j.getId(), j);
            }

            for (String id : delanteros) {
                Jugador j = mapaJugadores.get(id);
                if (j != null && j.getPosicion() == Posicion.DELANTERO) {
                    if (!delanterosValidos.contains(id)) {
                        delanterosValidos.add(id);
                    }
                }
            }

            if (delanterosValidos.size() != getNumeroDelanteros(usuario))
                return false;

            alineacion.setDelanteros(delanterosValidos);
            usuarioDAO.guardar(usuario);
            return true;
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
