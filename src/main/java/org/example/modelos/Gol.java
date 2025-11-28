package org.example.modelos;

import java.util.Objects;

// Modelo para representar un gol necesario para la logica del partido.
// Es un POJO simple que contiene los detalles de un evento de gol.
public class Gol {
    private final String id;
    private final String partidoId;
    private final String jugadorId;
    private final int minuto;

    // El constructor recibe todos los datos. La creación de IDs es responsabilidad del repositorio.
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

    @Override
    public String toString() {
        // Formato claro para mostrar la información del gol.
        return String.format("Gol -> Minuto: %-3d | Autor (ID): %s", minuto, jugadorId);
    }
}
