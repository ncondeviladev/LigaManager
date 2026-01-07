package org.example.vistasycontroladores.vistas.menu;

import org.example.modelos.Usuario;
import org.example.servicio.MercadoServicio;
import org.example.utils.MenuUtils;

import java.util.Scanner;

import static org.example.servicio.MercadoServicio.verSaldo;

//Esta clase muestra el menú del mercado que nos permite comprar y vender jugadores
public class MenuMercado {

    public static Scanner sc = new Scanner(System.in);
    /*
    Muestra el menu del mercado
     */
    public static void mostrarMenuMercado(Usuario usuario) {
        int opcion;
        do {
            opcion = MenuUtils.crearMenu("=== MERCADO ===", "Ver saldo", "Jugadores en venta", "Vender jugador", "Tus jugadores en venta", "Volver");

            switch (opcion) {
                case 1: verDinero(usuario);
                break;
                case 2: jugadoresEnVenta(usuario);
                break;
                case 3: venderJugador(usuario);
                break;
                case 4: tusJugadoresEnVenta(usuario);
                break;
            }
        } while (opcion != 5);
    }
    private static void verDinero (Usuario usuario) {
        System.out.println(verSaldo(usuario) + "M€");
    }



    /*
    muestra todos los jugadores en venta a excepción de los que vende el propio usuarios
     */
    private static void jugadoresEnVenta(Usuario usuario) {
        System.out.println("=== MERCADO ===");
        System.out.println(MercadoServicio.jugadoresEnVenta(usuario));

        System.out.println("¿Deseas comprar alguno? (SÍ/NO)");

        String opcion = sc.nextLine();
        opcion = opcion.toLowerCase();

        if  (opcion.equals("si") || opcion.equals("sí")) {
            comprarJugador(usuario);
        }
    }

    /*
    Permite al usuario comprar un jugador que esté en venta
     */
    private static void comprarJugador(Usuario usuario) {
        System.out.print("Introduce el Id de mercado del jugador que deseas comprar: ");
        String id = sc.nextLine().toUpperCase();

        switch (MercadoServicio.comprarJugador(usuario, id)) {

            case 1: System.out.println("Jugador comprado");
            break;
            case 2: System.out.println("Jugador no comprado debido a que tu equipo está lleno");
            break;
            case 3: System.out.println("Jugador no comprado debido a que este jugador no existe o no está en venta");
            break;
            case 4: System.out.println("Jugador no comprado debido a que el saldo es insuficiente");
            break;
        }
    }

    /*
    Permite al usuario vender alguno de sus jugadores
     */
    private static void venderJugador(Usuario usuario) {
        System.out.println("Tus jugadores:");
        MenuEquipo.mostrarJugadores(usuario);
        System.out.println();
        System.out.print("Introduce el Id del jugador que deseas vender: ");
        String id = sc.nextLine().toUpperCase();

        System.out.print("Introduce el precio al que lo deseas vender: ");
        double precio = sc.nextDouble();
        sc.nextLine();

        switch (MercadoServicio.venderJugador(usuario, id, precio)) {

            case 0: System.out.println("Jugador puesto en venta");
            break;
            case 1: System.out.println("Jugador no puesto en venta debido a que no tienes este jugador en tu equipo");
            break;
            case 2: System.out.println("Jugador no puesto en venta debido a que está en tu alineación");
            break;
            case 3: System.out.println("Jugador no puesto en venta debido a que no existe");
            break;
            case 4: System.out.println("Jugador no puesto en venta debido a que el precio no puede ser negativo");
            break;
            case 5: System.out.println("Jugador no puesto en venta");
        }
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
