package org.example.servicio;

import org.example.modelos.Alineacion;
import org.example.modelos.Equipo;
import org.example.modelos.Jugador;
import org.example.modelos.Usuario;
import org.example.modelos.enums.Formacion;
import org.example.modelos.enums.Posicion;
import org.example.repositorios.dao.EquipoDAO;
import org.example.repositorios.dao.JugadorDAO;
import org.example.repositorios.dao.UsuarioDAO;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlineacionServicio {
    private static final LigaRepo repo = RepoFactory.getRepositorio("JSON");

    // DAOs instanciados al inicio para uso en toda la clase
    private static final UsuarioDAO usuarioDAO = repo.getUsuarioDAO();
    private static final EquipoDAO equipoDAO =  repo.getEquipoDAO();

    public static String alineacionATexto(Alineacion alineacion) {
        String formacion = alineacion.getFormacion().toString();
        String portero =  alineacion.getPortero();
        List<Jugador> jugadores = JugadorServicio.getAllJugadores();
        List<String> defensas =  alineacion.getDefensas();
        List<String> medios =   alineacion.getMedios();
        List<String> delanteros =  alineacion.getDelanteros();

        StringBuilder sb = new StringBuilder();

        for (String id : defensas) {
            for (Jugador j : jugadores) {
                if (j.getId().equals(id)) {
                    sb.append(j.getNombre())
                            .append(" (")
                            .append(j.getPosicion())
                            .append("), ");
                    break;
                }
            }
        }

        String resultadodef = sb.length() > 0
                ? sb.substring(0, sb.length() - 2)
                : "";

        for (String id : medios) {
            for (Jugador j : jugadores) {
                if (j.getId().equals(id)) {
                    sb.append(j.getNombre())
                            .append(" (")
                            .append(j.getPosicion())
                            .append("), ");
                    break;
                }
            }
        }

        String resultadomed = sb.length() > 0
                ? sb.substring(0, sb.length() - 2)
                : "";

        for (String id : medios) {
            for (Jugador j : jugadores) {
                if (j.getId().equals(id)) {
                    sb.append(j.getNombre())
                            .append(" (")
                            .append(j.getPosicion())
                            .append("), ");
                    break;
                }
            }
        }

        String resultadodel = sb.length() > 0
                ? sb.substring(0, sb.length() - 2)
                : "";

        String alineacionATexto = null;
        sb.append(alineacionATexto) .append(formacion).append(portero).append(resultadodef).append(resultadomed).append(resultadodel);

        return alineacionATexto;
    }

    public static void cambiarFormacion(Usuario usuario, int opcion) {
        Alineacion alineacion = usuario.getAlineacion();

        switch (opcion) {
            case 1: alineacion.setFormacion(Formacion.F442);
            case 2: alineacion.setFormacion(Formacion.F433);
            case 3: alineacion.setFormacion(Formacion.F451);
            case 4: alineacion.setFormacion(Formacion.F343);
            case 5: alineacion.setFormacion(Formacion.F352);


        }
    }

    //comprovar si es portero
    public static boolean cambiarPortero(Usuario usuario, String idPortero) {
        Optional<Equipo> equipo = equipoDAO.buscarPorId(usuario.getIdEquipo());
        Alineacion alineacion = usuario.getAlineacion();
        List<Jugador> jugadores =  JugadorServicio.getAllJugadores();

        // Buscar jugador en la lista
        Jugador portero = null;
        for (Jugador j : jugadores) {
            if (j.getId().equals(idPortero)) {
                portero = j;
                break;
            }
        }

        // Si no existe el jugador
        if (portero == null) {
            System.out.println("No existe un jugador con ese ID.");
            return false;
        }

        // Comprobar que es portero
        if (portero.getPosicion() != Posicion.PORTERO) {
            System.out.println("El jugador no es portero.");
            return false;
        }

        // Cambiar portero
        alineacion.setPortero(idPortero);
        return true;
    }

    public static int getNumeroDefensas(Usuario usuario) {
    }

    public static boolean cambiarDefensas(Usuario usuario, ArrayList<String> defensas) {
    }

    public static int getNumeroMedios(Usuario usuario) {
    }

    public static boolean cambiarMedios(Usuario usuario, ArrayList<String> medios) {
    }

    public static int getNumeroDelanteros(Usuario usuario) {
    }

    public static boolean cambiarDelanteros(Usuario usuario, ArrayList<String> delanteros) {
    }
}
