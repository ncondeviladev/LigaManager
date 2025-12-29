package org.example.vistasycontroladores.vistas.menu;

import org.example.modelos.Usuario;
import org.example.servicio.MercadoServicio;
import org.example.utils.MenuUtils;

import java.util.Scanner;

//Esta clase muestra el menú del mercado que nos permite comprar y vender jugadores
public class MenuMercado {

    /*
    Muestra el menu del mercado
     */
    public static void mostrarMenuMercado(Usuario usuario) {
        int opcion;
        do {
            opcion = MenuUtils.crearMenu("=== MERCADO ===", "Jugadores en venta", "Vender jugador", "Tus jugadores en venta", "Volver");

            switch (opcion) {
                case 1: jugadoresEnVenta(usuario);
                break;
                case 2: venderJugador(usuario);
                break;
                case 3: tusJugadoresEnVenta(usuario);
                break;
            }
        } while (opcion != 4);
    }

    /*
    muestra todos los jugadores en venta a excepción de los que vende el propio usuarios
     */
    private static void jugadoresEnVenta(Usuario usuario) {
        System.out.println("=== MERCADO ===");
        System.out.println(MercadoServicio.jugadoresEnVenta(usuario));

        System.out.println("¿Deseas comprar alguno? (SÍ/NO)");
        Scanner sc = new Scanner(System.in);
        String opcion = sc.nextLine();
        opcion = opcion.toLowerCase();

        if  (opcion.equals("si") || opcion.equals("sí")) {
            comprarJugador(usuario);
        }
        sc.close();
    }

    /*
    Permite al usuario comprar un jugador que esté en venta
     */
    private static void comprarJugador(Usuario usuario) {
        System.out.print("Introduce el Id del jugador que deseas comprar: ");
        Scanner sc = new Scanner(System.in);
        String id = sc.nextLine().toUpperCase();

        switch (MercadoServicio.comprarJugador(usuario, id)) {

            case 1: System.out.println("Jugador comprado");

            case 2: System.out.println("Jugador no comprado debido a que tu equipo está lleno");

            case 3: System.out.println("Jugador no comprado debido a que este jugador no existe o no está en venta");

            case 4: System.out.println("Jugador no comprado debido a que el saldo es insuficiente");
        }
        sc.close();
    }

    /*
    Permite al usuario vender alguno de sus jugadores
     */
    private static void venderJugador(Usuario usuario) {
        System.out.println("Tus jugadores:");
        MenuEquipo.mostrarJugadores(usuario);
        System.out.println();
        System.out.print("Introduce el Id del jugador que deseas vender: ");
        Scanner sc = new Scanner(System.in);
        String id = sc.nextLine().toUpperCase();
        sc.nextLine();

        System.out.print("Introduce el precio al que lo deseas vender: ");
        double precio = sc.nextDouble();

        if (MercadoServicio.venderJugador(usuario, id, precio)) {
            System.out.println("Jugador puesto en venta");
        } else  {
            System.out.println("El jugador no se ha podido poner en venta");
        }
        sc.close();
    }

    /*
    Te permite ver que jugadores tienes en venta
     */
    private static void tusJugadoresEnVenta(Usuario usuario) {
        System.out.println("Tus jugadores en venta:");
        if (MercadoServicio.jugadoresEnVentaDeUsuario(usuario) != null) {
            System.out.println(MercadoServicio.jugadoresEnVentaDeUsuario(usuario));
        } else {
            System.out.println("No tienes jugadores en venta");
        }
    }
}
