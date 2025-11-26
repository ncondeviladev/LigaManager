package org.example.modelos;

import java.util.ArrayList;
import java.util.Objects;

public class Equipo {
    private final String id;
    private final String nombre;
    private int puntos;
    private int gf;
    private int gc;
    private ArrayList<Jugador> jugadores;
    private static int contador = 0;

    public Equipo(String id, String nombre, int puntos, int gf, int gc, ArrayList<Jugador> jugadores) {
        this.id = id;
        this.nombre = nombre;
        this.puntos = puntos;
        this.gf = gf;
        this.gc = gc;
        this.jugadores = jugadores;
        contador++;
    }

    public Equipo(String nombre, int puntos, int gf, int gc, ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
        this.id = createID();
        this.nombre = nombre;
        this.puntos = puntos;
        this.gf = gf;
        this.gc = gc;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getGf() {
        return gf;
    }

    public int getGc() {
        return gc;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public static int getContador() {
        return contador;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void setGf(int gf) {
        this.gf = gf;
    }

    public void setGc(int gc) {
        this.gc = gc;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    private String createID() {
        String identificador = String.format("%c%02d", 'T', contador);
        contador++;
        return identificador;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", puntos=" + puntos +
                ", gf=" + gf +
                ", gc=" + gc +
                ", jugadores=" + jugadores +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Equipo equipo)) return false;
        return puntos == equipo.puntos && gf == equipo.gf && gc == equipo.gc && Objects.equals(id, equipo.id) && Objects.equals(nombre, equipo.nombre) && Objects.equals(jugadores, equipo.jugadores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, puntos, gf, gc, jugadores);
    }
}
