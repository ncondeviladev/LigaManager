package org.example.vistasycontroladores.vistas.menu;

import org.example.vistasycontroladores.vistas.GUI.FormEquipo;
import org.example.vistasycontroladores.vistas.GUI.FormJornadas;
import org.example.vistasycontroladores.vistas.GUI.FormMercado;
import java.util.Scanner;

//clase para mostrar el menú particular de un usuario en concreto, este se ejecutará después de que el usuario haya iniciado sesión
public class MenuUsuarios {
    public static void mostrarMenuUsuarios() {
        Scanner lector = new Scanner(System.in);
        int opcion = 0;
        do {
            opcion = MenuUtils.crearMenu("=== LIGA FANTASY ===", "1. Tu Equipo", "2. Mercado", "3. Jornadas", "4. Volver");

            opcion = lector.nextInt();
            switch (opcion) {
                case 1: FormEquipo.mostrarMenuEquipo();

                case 2: FormMercado.mostrarMenuMercado();

                case 3: FormJornadas.mostrarMenuJornadas();
            }
        } while (opcion != 4);
    }
}
