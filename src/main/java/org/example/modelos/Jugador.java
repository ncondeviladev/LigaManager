package org.example.modelos;

import org.example.modelos.enums.Posicion;

import java.util.Objects;

// La clase Jugador se convierte en un POJO.
// Solo contiene datos y no tiene lógica para crear IDs.
public class Jugador {

    private final String id;
    private final String nombre;
    private final Posicion posicion;
    // Añadimos equipoId para relacionar al jugador con su equipo.
    // Esta es la forma correcta de gestionar relaciones en persistencia.
    private final String equipoId;
    private final int ataque;
    private final int defensa;
    private final int pase;
    private final int porteria;
    private int estadoForma;
    private double precio;

    /**
     * Constructor para crear una instancia de Jugador.
     * El constructor ahora recibe todos los datos, incluido el ID.
     * No hay lógica de autoincremento.
     *
     * @param id          El identificador único del jugador.
     * @param nombre      El nombre del jugador.
     * @param posicion    La posición en la que juega el jugador.
     * @param equipoId    El ID del equipo al que pertenece el jugador.
     * @param ataque      El valor de ataque del jugador.
     * @param defensa     El valor de defensa del jugador.
     * @param pase        El valor de pase del jugador.
     * @param porteria    El valor de portería del jugador.
     * @param estadoForma El estado de forma actual del jugador.
     * @param precio      El precio de mercado del jugador.
     */
    public Jugador(String id, String nombre, Posicion posicion, String equipoId, int ataque, int defensa, int pase, int porteria, int estadoForma, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.posicion = posicion;
        this.equipoId = equipoId;
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

    public String getEquipoId() {
        return equipoId;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return Objects.equals(id, jugador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Devuelve una representación en cadena del objeto Jugador.
     *
     * @return Una cadena formateada con los detalles del jugador.
     */
    @Override
    public String toString() {
        // Formato mejorado para mostrar todos los datos relevantes del jugador.
        return String.format("Jugador -> ID: %-5s | Nombre: %-25s | Pos: %-10s | EquipoID: %-4s | Precio: %.2f M€",
                id, nombre, posicion, equipoId, precio);
    }
}
