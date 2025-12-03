package org.example.vistasycontroladores.vistas.menu;

import java.util.Scanner;

public class Menu {
    //Menu para crear usuario, iniciar sesion, borrar usuario, mostrar usuarios y salir
    private static Scanner lector = new Scanner(System.in);
    private static int option = 0;


    public static void mostrarMenu() {
        do {

            option = lector.nextInt();
           switch (option) {
               case 1: iniciarsesion();
               case 2: mostrarUsuarios();
               case 3: crearUsuario();
               case 4: borrarUsuario();
           }
        } while (option != 5);
    }
}
