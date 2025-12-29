package org.example.vistasycontroladores.vistas.menu;

import org.example.modelos.Usuario;
import org.example.utils.MenuUtils;
import java.util.Scanner;

//clase para mostrar el menú particular de un usuario en concreto, este se ejecutará después de que el usuario haya iniciado sesión
public class MenuUsuarios {
    /*
    Muestra el menú de cada usuario
     */
    public static void mostrarMenuUsuarios(Usuario usuario) {
        int opcion;
        do {
            opcion = MenuUtils.crearMenu("=== LIGA FANTASY ===", "Tu Equipo", "Mercado", "Liga", "Volver");

            switch (opcion) {
                case 1: MenuEquipo.mostrarMenuEquipo(usuario);
                break;
                case 2: MenuMercado.mostrarMenuMercado(usuario);
                break;
                case 3: MenuLiga.mostrarMenuLiga(usuario);
                break;
            }
        } while (opcion != 4);
    }
}
