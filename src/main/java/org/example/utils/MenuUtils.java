package org.example.utils;

import org.example.vistasycontroladores.vistas.menu.Menu;

import java.util.Scanner;

public class MenuUtils {

    /**
     * Muestra un menú dinámico y gestiona la entrada del usuario.
     *
     * @param titulo  El título a mostrar encima del menú.
     * @param opciones La lista de opciones a mostrar en el menú (varargs).
     * @return El número (1-based) de la opción seleccionada por el usuario.
     */
    public static int crearMenu(String titulo, String... opciones) {
        Scanner sc = Menu.sc;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < titulo.length(); i++) {
            sb.append("=");
        }

        String linea = sb.toString();

        while (true) {
            System.out.println(linea);
            System.out.println(titulo.toUpperCase());
            System.out.println(linea);
            for (int i = 0; i < opciones.length; i++) {
                System.out.printf("%d. %s%n", (i + 1), opciones[i]);
            }
            System.out.println("====================");
            System.out.print("Elige una opción: ");

            try {
                int opcion = Integer.parseInt(sc.nextLine());
                if (opcion >= 1 && opcion <= opciones.length) {
                    return opcion; // Devuelve la opción válida y sale del método
                } else {
                    System.out.println("Opción no válida. Introduce un número entre 1 y " + opciones.length);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debes introducir un valor numérico.");
            }
            System.out.println(); // Línea en blanco para separar reintentos
            sc.close();
        }
    }


}
