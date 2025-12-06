package org.example.modelos;

import com.google.gson.annotations.SerializedName;
import org.example.modelos.enums.Posicion;
import java.util.Objects;

// Clase Jugador refactorizada para coincidir con players.json
// El campo "condition" del JSON se mapea a "estadoForma" en Java para mayor claridad
public class Jugador {

    private final String id;
    private final String nombre;
    private final Posicion posicion;
    private final String idEquipo;
    private final int ataque;
    private final int defensa;
    private final int pase;
    private final int porteria;

    // En el JSON se llama "condition", mapeamos con @SerializedName
    // Representa el estado de forma del jugador (usado en simulación de partidos)
    @SerializedName("condition")
    private int estadoForma;

    private double precio;

    // Constructor completo
    public Jugador(String id, String nombre, Posicion posicion, String idEquipo,
            int ataque, int defensa, int pase, int porteria, int estadoForma, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.posicion = posicion;
        this.idEquipo = idEquipo;
        this.ataque = ataque;
        this.defensa = defensa;
        this.pase = pase;
        this.porteria = porteria;
        this.estadoForma = estadoForma;
        this.precio = precio;
    }

    // --- Getters y Setters ---

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public String getIdEquipo() {
        return idEquipo;
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

    public void setEstadoForma(int estadoForma) {
        this.estadoForma = estadoForma;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Jugador jugador = (Jugador) o;
        return Objects.equals(id, jugador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Jugador -> ID: %-5s | Nombre: %-25s | Pos: %-10s | EquipoID: %-4s | Precio: %.2f M€",
                id, nombre, posicion, idEquipo, precio);
    }
}
