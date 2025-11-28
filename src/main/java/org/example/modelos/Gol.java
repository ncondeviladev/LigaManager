package org.example.modelos;

import java.util.Objects;

// Modelo para representar un gol.
// Es un POJO simple que contiene los detalles de un evento de gol. Necesario para la logica de partido
public class Gol {
    private final String id;
    private final String partidoId;
    private final String jugadorId;
    private final int minuto;

    /**
     * Constructor para crear una instancia de Gol.
     * El constructor recibe todos los datos. La creación de IDs es responsabilidad del repositorio.
     *
     * @param id        El identificador único del gol.
     * @param partidoId El ID del partido en el que se marcó el gol.
     * @param jugadorId El ID del jugador que marcó el gol.
     * @param minuto    El minuto en que se marcó el gol.
     */
    public Gol(String id, String partidoId, String jugadorId, int minuto) {
        this.id = id;
        this.partidoId = partidoId;
        this.jugadorId = jugadorId;
        this.minuto = minuto;
    }

    // --- Getters ---

    public String getId() {
        return id;
    }

    public String getPartidoId() {
        return partidoId;
    }

    public String getJugadorId() {
        return jugadorId;
    }

    public int getMinuto() {
        return minuto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gol gol = (Gol) o;
        return Objects.equals(id, gol.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Devuelve una representación en cadena del objeto Gol.
     *
     * @return Una cadena formateada con los detalles del gol.
     */
    @Override
    public String toString() {
        // Formato claro para mostrar la información del gol.
        return String.format("Gol -> Minuto: %-3d | Autor (ID): %s", minuto, jugadorId);
    }
}
