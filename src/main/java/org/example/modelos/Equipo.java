package org.example.modelos;

import java.util.Objects;

// La clase Equipo se convierte en un POJO.
// No debe generar sus propios IDs ni contener listas de otros objetos del modelo.
public class Equipo {
    private final String id;
    private final String nombre;
    private int puntos;
    private int gf; // Goles a favor
    private int gc; // Goles en contra

    // El constructor recibe el ID. La responsabilidad de crearlo es de la capa de persistencia.
    // Se elimina la lista de jugadores. La relación se gestiona desde Jugador (con equipoId).
    public Equipo(String id, String nombre, int puntos, int gf, int gc) {
        this.id = id;
        this.nombre = nombre;
        this.puntos = puntos;
        this.gf = gf;
        this.gc = gc;
    }

    // --- Getters y Setters ---

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getGf() {
        return gf;
    }

    public void setGf(int gf) {
        this.gf = gf;
    }

    public int getGc() {
        return gc;
    }

    public void setGc(int gc) {
        this.gc = gc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipo equipo = (Equipo) o;
        return Objects.equals(id, equipo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        // Formato mejorado para una visualización más clara en la consola.
        return String.format("Equipo -> ID: %-4s | Nombre: %-20s | Puntos: %-3d | GF: %-3d | GC: %-3d",
                id, nombre, puntos, gf, gc);
    }
}
