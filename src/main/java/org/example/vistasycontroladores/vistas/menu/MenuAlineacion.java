package org.example.vistasycontroladores.vistas.menu;

import org.example.modelos.Usuario;
import org.example.servicio.AlineacionServicio;
import org.example.utils.MenuUtils;
import java.util.ArrayList;
import java.util.Scanner;

//En esta clase se muestra el menú Alineación que te permite configurarla
public class MenuAlineacion {
    /*
    Te muestra el menú de tu alineación
     */
    public static void mostrarMenuAlineacion(Usuario usuario) {
        int opcion;
        do {
            opcion = MenuUtils.crearMenu("=== TU ALINEACIÓN ===", "Ver Alineación", "Cambiar Alineación", "Volver");

            switch (opcion) {
                case 1: mostrarAlineacion(usuario);
                break;
                case 2: cambiarAlineacion(usuario);
                break;
            }
        } while (opcion != 3);
    }

    /*
    Te muestra tu alineación titular
     */
    private static void mostrarAlineacion(Usuario usuario) {
        System.out.println("=== ALINEACIÓN ===");
        System.out.println(AlineacionServicio.alineacionATexto(usuario.getAlineacion()));
    }

    /*
    Te permite cambiar tus jugadores titulares según la formación que tengas asignada
     */
    private static void cambiarAlineacion(Usuario usuario) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Quieres cambiar la formación?(SÍ/NO)");
        String respuesta = sc.nextLine().toUpperCase();

        if  (respuesta.equals("SI") || respuesta.equals("SÍ")) {
            cambiarFormacion(usuario);

            MenuEquipo.mostrarJugadores(usuario);

            boolean cambiado;

            do {
                System.out.print("Elige el portero: ");
                String idPortero = sc.nextLine();

                cambiado = AlineacionServicio.cambiarPortero(usuario, idPortero);
                if (cambiado == false) {
                    System.out.println("No es un portero válido");
                }
            } while (cambiado == false);

            sc.nextLine();

            do {
                int numDefensas = AlineacionServicio.getNumeroDefensas(usuario);
                ArrayList<String> defensas = new ArrayList<>();

                for (int i = 0; i < numDefensas; i++) {
                    System.out.print("Elige un defensa: ");
                    String idDefensa = sc.nextLine();
                    sc.nextLine();
                    defensas.add(idDefensa);
                }

                cambiado = AlineacionServicio.cambiarDefensas(usuario, defensas);
                if (cambiado == false) {
                    System.out.println("Los defensas no son válidos");
                }
            } while (cambiado == false);

            do {
                int numMedios = AlineacionServicio.getNumeroMedios(usuario);
                ArrayList<String> medios = new ArrayList<>();

                for (int i = 0; i < numMedios; i++) {
                    System.out.print("Elige un medio: ");
                    String idMedio = sc.nextLine();
                    sc.nextLine();
                    medios.add(idMedio);
                }

                cambiado = AlineacionServicio.cambiarMedios(usuario, medios);
                if (cambiado == false) {
                    System.out.println("Los medios no son válidos");
                }
            } while (cambiado == false);

            do {
                int numDelanteros = AlineacionServicio.getNumeroDelanteros(usuario);
                ArrayList<String> delanteros = new ArrayList<>();

                for (int i = 0; i < numDelanteros; i++) {
                    System.out.print("Elige un delantero: ");
                    String idDelantero = sc.nextLine();
                    sc.nextLine();
                    delanteros.add(idDelantero);
                }

                cambiado = AlineacionServicio.cambiarDelanteros(usuario, delanteros);
                if (cambiado == false) {
                    System.out.println("Los delanteros no son válidos");
                }
            } while (cambiado == false);

        }
    }

    /*
    Te permite cambiar el tipo de formación de tu alineación
     */
    private static void cambiarFormacion(Usuario usuario) {
        int opcion;
        opcion = MenuUtils.crearMenu("=== TIPO DE FORMACIÓN ===", "4-4-2", "4-3-3", "4-5-1", "3-4-3", "3-5-2");

        AlineacionServicio.cambiarFormacion(usuario, opcion);
    }
}
