package org.example.vistasycontroladores.vistas.menu;

import org.example.servicio.UsuariosServicio;
import org.example.utils.MenuUtils;

import java.util.Scanner;

import static org.example.servicio.UsuariosServicio.*;

public class Menu {

    public static Scanner sc = new Scanner(System.in);

    //Menu para crear usuario, iniciar sesion, borrar usuario, mostrar usuarios y salir
    public static void mostrarMenu() {
        int opcion = 0;
        do {
            opcion = MenuUtils.crearMenu("=== LIGA FANTASY ===", "Iniciar Sesion", "Mostrar Menu", "Crear Usuarios", "Borrar Usuarios", "Salir");
           switch (opcion) {
               case 1: iniciarSesion();
               case 2: mostrarUsuarios();
               case 3: crearUsuario();
               case 4: borrarUsuario();
           }
        } while (opcion != 5);
    }
    // Inicia la sesion en el usuario que se le diga con su contraseña
    private static void iniciarSesion() {

        System.out.println("=== Iniciar Sesion ===");
        System.out.println("Inserta tu nombre de usuario");
        String nombre = sc.nextLine();
        System.out.println("Inserta tu contraseña de usuario");
        String contrasenya = sc.nextLine();

        UsuariosServicio.iniciarSesion(nombre, contrasenya);
    }

    // muestra todos los usuarios
    private static void mostrarUsuarios() {
       int opcion = -1;
        do {
            System.out.println("=== Usuarios ===");
            UsuariosServicio.mostrarUsuarios();
            System.out.println("0. Salir");
            sc.nextLine();
        }while (opcion != 0);
    }

    // crea usuario con su nombre i contraseña
    private static void crearUsuario() {

        System.out.println("=== Crear Usuario ===");
        System.out.println("Inserta tu nombre de usuario");
        String nombre = sc.nextLine();
        System.out.println("Inserta tu contraseña");
        String contrasenya = sc.nextLine();

        UsuariosServicio.crearUsuario(nombre, contrasenya);
    }
    
    // borra el usuario que se le indique por el nombre
    private static void borrarUsuario() {
        System.out.println("=== Borrar Usuario ===");
        System.out.println("Inserta el nombre del usuario que quiere borrar");
        String usuario = sc.nextLine();
        System.out.println("Si quieres borrar este usuario definitivamente escribe la palabra *DELETE*");
        String palabra = sc.nextLine();
        if (palabra.equals("DELETE")) {
            UsuariosServicio.borrarUsuario(usuario);
        } else {
            borrarUsuario();
        }
    }

}
