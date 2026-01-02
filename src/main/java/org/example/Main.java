package org.example;

import org.example.vistasycontroladores.vistas.menu.Menu;
import org.example.servicio.AppServicio;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        configurarDatos();
        AppServicio.inicializar(); // Forzamos carga inicial
        Menu.mostrarMenu();
    }

    // Es mala practica hacer esto en el main, pero lo hacemos asi para evitar
    // modificar mucho el codigo de los compañeros cambiando variables final static
    // y que se cargue la bd antes de crearlas...11
    private static void configurarDatos() {
        System.out.println("1 JSON");
        System.out.println("2 SQL");
        // Si el usuario escribe algo que no sea "2", por defecto será JSON
        if ("2".equals(Menu.sc.nextLine())) {
            System.setProperty("TIPO_DATOS", "SQL");
        } else {
            System.setProperty("TIPO_DATOS", "JSON");
        }
    }
}