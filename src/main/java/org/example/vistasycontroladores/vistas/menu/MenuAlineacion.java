package org.example.vistasycontroladores.vistas.menu;

import org.example.modelos.Alineacion;
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
            opcion = MenuUtils.crearMenu("=== TU ALINEACIÓN ===", "Ver Alineación", "Cambiar Formación", "Cambiar Alineación", "Volver");

            switch (opcion) {
                case 1: mostrarAlineacion(usuario);
                break;
                case 2: cambiarFormacion(usuario);
                break;
                case 3: cambiarAlineacion(usuario);
                break;
            }
        } while (opcion != 4);
    }

    /*
    Te muestra tu alineación titular
     */
    private static void mostrarAlineacion(Usuario usuario) {
        System.out.println("=== ALINEACIÓN ===");
        System.out.println(AlineacionServicio.alineacionATexto(usuario.getAlineacion()));
    }

    /*
    Te permite cambiar el tipo de formación de tu alineación
     */
    private static void cambiarFormacion(Usuario usuario) {
        int opcion;
            opcion = MenuUtils.crearMenu("=== TIPO DE FORMACIÓN ===", "4-4-2", "4-3-3", "4-5-1", "3-4-3", "3-5-2");

            AlineacionServicio.cambiarFormacion(usuario, opcion);
    }

    /*
    Te permite cambiar tus jugadores titulares según la formación que tengas asignada
     */
    private static void cambiarAlineacion(Usuario usuario) {
        MenuEquipo.mostrarJugadores(usuario);
        Scanner sc = new Scanner(System.in);

        boolean cambiado = false;

        do {
            System.out.println("Elige el portero:");
            String idPortero = sc.nextLine();

            cambiado = AlineacionServicio.cambiarPortero(usuario, idPortero);
        } while (cambiado == false);

        sc.nextLine();

        do {
            int numDefensas = AlineacionServicio.getNumeroDefensas(usuario);
            ArrayList<String> defensas = new ArrayList<>();

            for (int i = 0; i < numDefensas; i++) {
                System.out.println("Elige un defensa:");
                String idDefensa = sc.nextLine();
                sc.nextLine();
                defensas.add(idDefensa);
            }

            cambiado = AlineacionServicio.cambiarDefensas(usuario, defensas);
        } while (cambiado == false);

        do {
            int numMedios = AlineacionServicio.getNumeroMedios(usuario);
            ArrayList<String> medios = new ArrayList<>();

            for (int i = 0; i < numMedios; i++) {
                System.out.println("Elige un medio:");
                String idDefensa = sc.nextLine();
                sc.nextLine();
                medios.add(idDefensa);
            }

            cambiado = AlineacionServicio.cambiarMedios(usuario, medios);
        } while (cambiado == false);

        do {
            int numDelanteros = AlineacionServicio.getNumeroDelanteros(usuario);
            ArrayList<String> delanteros = new ArrayList<>();

            for (int i = 0; i < numDelanteros; i++) {
                System.out.println("Elige un delantero:");
                String idDefensa = sc.nextLine();
                sc.nextLine();
                delanteros.add(idDefensa);
            }

            cambiado = AlineacionServicio.cambiarDelanteros(usuario, delanteros);
        } while (cambiado == false);

        sc.close();
    }
}
