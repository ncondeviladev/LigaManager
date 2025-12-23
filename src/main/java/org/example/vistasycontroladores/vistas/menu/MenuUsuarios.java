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
        Scanner lector = new Scanner(System.in);
        int opcion;
        do {
            opcion = MenuUtils.crearMenu("=== LIGA FANTASY ===", "1. Tu Equipo", "2. Mercado", "3. Jornadas", "4. Volver");

            switch (opcion) {
                case 1: MenuEquipo.mostrarMenuEquipo(usuario);

                case 2: MenuMercado.mostrarMenuMercado(usuario);

                case 3: MenuJornadas.mostrarMenuJornadas();
            }
        } while (opcion != 4);
    }
}
