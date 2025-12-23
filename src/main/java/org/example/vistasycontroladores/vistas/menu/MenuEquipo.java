package org.example.vistasycontroladores.vistas.menu;

import org.example.modelos.Usuario;
import org.example.servicio.JugadorServicio;
import org.example.utils.MenuUtils;

//Clase que nos muestra el menú para administrar el equipo
public class MenuEquipo {

    /*
    Muestra el menu del equipo
     */
    public static void mostrarMenuEquipo(Usuario usuario) {
        int opcion;
        do {
            opcion = MenuUtils.crearMenu("=== TU EQUIPO ===", "Jugadores", "Alineación", "Volver");

            switch (opcion) {
                case 1: mostrarJugadores(usuario);

                case 2: MenuAlineacion.mostrarMenuAlineacion(usuario);
            }
        } while (opcion != 3);
    }

    /*
    muestra todos los jugadores del equipo incluyendo reservas
     */
    protected static void mostrarJugadores(Usuario usuario) {
        System.out.println("=== JUGADORES ===");
        System.out.println(JugadorServicio.getAllJugadoresfromEquipo(usuario.getIdEquipo()));
    }
}
