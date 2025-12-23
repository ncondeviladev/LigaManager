package org.example.vistasycontroladores.vistas.menu;

import org.example.modelos.Usuario;
import org.example.servicio.LigaServicio;
import org.example.utils.MenuUtils;

import java.util.Scanner;

public class MenuLiga {
    /*
    Muestra el menú de la liga
     */
    public static void mostrarMenuLiga(Usuario usuario) {
        int opcion;
        do {
            opcion = MenuUtils.crearMenu("=== LIGA FANTASY ===", "Clasificación", "Jornadas", "Simular partido", "Volver");

            switch (opcion) {
                case 1: mostrarClasificacion(usuario);

                case 2: mostrarJornadas(usuario);

                case 3: realizarSimulacion(usuario);
            }
        } while (opcion != 4);
    }

    private static void mostrarClasificacion(Usuario usuario) {
        System.out.println(LigaServicio.mostrarClasificacion(usuario));
    }

    private static void mostrarJornadas(Usuario usuario) {
        System.out.println(LigaServicio.mostrarJornadas(usuario));
    }

    private static void realizarSimulacion(Usuario usuario) {
        LigaServicio.realizarSimulacion(usuario);
    }
}
