package org.example.servicio;

import org.example.modelos.Alineacion;
import org.example.modelos.Equipo;
import org.example.modelos.Jugador;
import org.example.modelos.Usuario;
import org.example.modelos.enums.Formacion;

import java.util.ArrayList;
import java.util.List;

public class AlineacionServicio {
    public static String alineacionATexto(Alineacion alineacion) {
        String formacion = alineacion.getFormacion().toString();
        String portero =  alineacion.getPortero();
        List<String> defensas =  alineacion.getDefensas();
        List<String> medios =   alineacion.getMedios();


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

        String resultado = sb.length() > 0
                ? sb.substring(0, sb.length() - 2)
                : "";
    }

    public static void cambiarFormacion(Usuario usuario, int opcion) {
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
