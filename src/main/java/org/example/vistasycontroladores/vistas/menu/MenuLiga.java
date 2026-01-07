package org.example.vistasycontroladores.vistas.menu;

import org.example.modelos.Usuario;
import org.example.servicio.LigaServicio;
import org.example.servicio.SimularPartido;
import org.example.utils.MenuUtils;

import java.util.Scanner;

import static org.example.servicio.LigaServicio.mostrarPartidosJornada;

public class MenuLiga {
    private static Scanner sc = new Scanner(System.in);
    /*
    Muestra el menú de la liga
     */
    public static void mostrarMenuLiga(Usuario usuario) {
        int opcion;
        do {
            opcion = MenuUtils.crearMenu("=== LIGA FANTASY ===", "Clasificación", "Jornadas", "Simular jornada", "Volver");

            switch (opcion) {
                case 1: mostrarClasificacion();
                break;
                case 2: mostrarJornadas();
                break;
                case 3: realizarSimulacion();
                break;
            }
        } while (opcion != 4);
    }

    private static void mostrarClasificacion() {
        System.out.println(LigaServicio.mostrarClasificacion());
    }

    private static void mostrarJornadas() {
        System.out.println(LigaServicio.mostrarJornadas());
        System.out.println("¿Quiéres ver los partidos de alguna jornada? (SÍ/NO)");
        String opcion = sc.nextLine();
        opcion = opcion.toLowerCase();

        if (opcion.equals("si") || opcion.equals("sí")) {
            System.out.print("Escribe el id de la jornada que quieras mostrar: ");
            String jornada = sc.nextLine();
            System.out.println(mostrarPartidosJornada(jornada.toUpperCase()));
        }
    }

    private static void realizarSimulacion() {
        LigaServicio.realizarSimulacion();
    }
}
