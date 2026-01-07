package org.example.vistasycontroladores.vistas.menu;

import org.example.modelos.Usuario;
import org.example.modelos.enums.TipoUsuario;
import org.example.servicio.EquipoServicio;
import org.example.servicio.UsuariosServicio;
import org.example.servicio.AppServicio;
import org.example.utils.MenuUtils;

import java.util.Optional;
import java.util.Scanner;

public class Menu {

    public static Scanner sc = new Scanner(System.in);

    //Menu para crear usuario, iniciar sesion, borrar usuario, mostrar usuarios y salir
    public static void mostrarMenu() {
        int opcion;
        do {
            opcion = MenuUtils.crearMenu("=== LIGA FANTASY ===", "Iniciar Sesión", "Mostrar Usuarios", "Salir");
           switch (opcion) {
               case 1: iniciarSesion();
               break;
               case 2: mostrarUsuarios();
               break;
           }
        } while (opcion != 5);
    }
    // Inicia la sesion en el usuario que se le diga con su contraseña
    private static void iniciarSesion() {

        System.out.println("=== INICIAR SESIÓN ===");
        System.out.println("Inserta tu email");
        String nombre = sc.nextLine();

        System.out.println("Inserta tu contraseña");
        String contrasenya = sc.nextLine();


        Optional<Usuario> usuario = AppServicio.login(nombre, contrasenya);
        if (usuario.isPresent()) {
            MenuUsuarios.mostrarMenuUsuarios(usuario.get());
        } else {
            System.out.println("Usuario o contraseña incorrecto");
        }
    }

    // muestra todos los usuarios
    private static void mostrarUsuarios() {
            System.out.println("=== USUARIOS ===");
            System.out.println(UsuariosServicio.mostrarUsuarios());
    }



}
