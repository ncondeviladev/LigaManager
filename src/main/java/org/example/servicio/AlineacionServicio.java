package org.example.servicio;

import org.example.modelos.Alineacion;
import org.example.modelos.Jugador;
import org.example.modelos.Usuario;
import org.example.modelos.enums.Formacion;

import java.util.ArrayList;
import java.util.List;

public class AlineacionServicio {
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

    public static boolean cambiarPortero(Usuario usuario, String idPortero) {
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
