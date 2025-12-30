package org.example.vistasycontroladores.vistas.menu;

import org.example.modelos.Usuario;
import org.example.servicio.LigaServicio;
import org.example.servicio.SimularPartido;
import org.example.utils.MenuUtils;

import java.util.Scanner;

public class MenuLiga {
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
    }

    private static void realizarSimulacion() {
        LigaServicio.realizarSimulacion();
    }
}
