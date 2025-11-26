package org.example.modelos;

import java.util.Objects;

enum Posicion {
    PORTERO, DEFENSA, MEDIO, DELANTERO;
}

public class Jugador {

    private final String id;
    private final String nombre;
    private final Posicion posicion;
    private final int ataque;
    private final int defensa;
    private final int pase;
    private final int porteria;
    private int estadoForma;
    private double precio;
    private static int contador = 0;

    public Jugador(String id, String nombre, String posicion, int ataque, int defensa, int pase, int porteria, int estadoForma, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.posicion = Posicion.valueOf(posicion);
        this.ataque = ataque;
        this.defensa = defensa;
        this.pase = pase;
        this.porteria = porteria;
        this.estadoForma = estadoForma;
        this.precio = precio;
        contador++;
    }
    public Jugador(String nombre, String posicion, int ataque, int defensa, int pase, int porteria, int estadoForma, double precio) {
        this.id = createID();
        this.nombre = nombre;
        this.posicion = Posicion.valueOf(posicion);
        this.ataque = ataque;
        this.defensa = defensa;
        this.pase = pase;
        this.porteria = porteria;
        this.estadoForma = estadoForma;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getPase() {
        return pase;
    }

    public int getPorteria() {
        return porteria;
    }

    public int getEstadoForma() {
        return estadoForma;
    }

    public double getPrecio() {
        return precio;
    }

    public static int getContador() {
        return contador;
    }

    public void setEstadoForma(int estadoForma) {
        this.estadoForma = estadoForma;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    private String createID() {
        String identificador = String.format("%c%04d", 'P', contador);
        contador++;
        return identificador;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Jugador jugador)) return false;
        return ataque == jugador.ataque && defensa == jugador.defensa && pase == jugador.pase && porteria == jugador.porteria && estadoForma == jugador.estadoForma && Double.compare(precio, jugador.precio) == 0 && Objects.equals(id, jugador.id) && Objects.equals(nombre, jugador.nombre) && posicion == jugador.posicion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, posicion, ataque, defensa, pase, porteria, estadoForma, precio);
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", posicion=" + posicion +
                ", ataque=" + ataque +
                ", defensa=" + defensa +
                ", pase=" + pase +
                ", porteria=" + porteria +
                ", estadoForma=" + estadoForma +
                ", precio=" + precio +
                '}';
    }
}
