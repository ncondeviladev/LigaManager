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
            opcion = MenuUtils.crearMenu("=== LIGA FANTASY ===", "Iniciar Sesión", "Mostrar Usuarios", "Crear Usuario", "Borrar Usuario", "Salir");
           switch (opcion) {
               case 1: iniciarSesion();
               break;
               case 2: mostrarUsuarios();
               break;
               case 3: crearUsuario();
               break;
               case 4: borrarUsuario();
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

    // crea usuario con su nombre y contraseña
    private static void crearUsuario() {

        System.out.println("=== CREAR USUARIO ===");
        System.out.println("Inserta tu email");
        String gmail = sc.nextLine();

        System.out.println("Inserta tu contraseña");
        String contrasenya = sc.nextLine();

        System.out.println("Que tipo de usuario quieres crear? (ADMIN / ESTANDAR)");
        String tipousu = sc.nextLine();

        tipousu = tipousu.toUpperCase();
        System.out.println("Con que equipo quiere jugar?");
        System.out.print(EquipoServicio.mostrarEquipos());
        System.out.println("ID del equipo deseado:");
        String idequipo = sc.nextLine();
        idequipo = idequipo.toUpperCase();


        TipoUsuario tipousu2 = null;
        if (tipousu.equals("ADMIN")) {
            tipousu2 = TipoUsuario.ADMIN;
        } else {
            tipousu2 = TipoUsuario.ESTANDAR;
        }
         while (AppServicio.crearUsuario(gmail, contrasenya, tipousu2, idequipo) == 1) {
             System.out.println("Con que equipo quiere jugar?");
             System.out.print(EquipoServicio.mostrarEquipos());
             System.out.println("ID del equipo deseado:");
             idequipo = sc.nextLine();
             idequipo = idequipo.toUpperCase();
         }
        }


    // borra el usuario que se le indique por el nombre
    private static void borrarUsuario() {

        System.out.println("=== BORRAR USUARIO ===");
        System.out.println(UsuariosServicio.mostrarUsuarios());
        System.out.println("Inserta el Id del usuario que quiere borrar");
        String usuario = sc.nextLine();

        System.out.println("Si quieres borrar este usuario definitivamente escribe la palabra *DELETE*");
        String palabra = sc.nextLine();
        palabra = palabra.toUpperCase();

        if (palabra.equals("DELETE")) {
            UsuariosServicio.borrarUsuario(usuario);
        } else {
            System.out.println("Usuario no borrado");
        }
    }

}
