package org.example.modelos.competicion;

import java.util.Objects;

// Modelo simplificado para representar un gol en un partido.
// Al estar embebido dentro de Partido en el JSON, no necesita id ni partidoId.
// Solo contiene la información esencial: quién marcó y en qué minuto.
public class Gol {

    private final String jugadorId;

    private final int minuto;

    
    public Gol(String jugadorId, int minuto) {
        this.jugadorId = jugadorId;
        this.minuto = minuto;
    }

    // --- Getters ---

    public String getJugadorId() {
        return jugadorId;
    }

    public int getMinuto() {
        return minuto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Gol gol = (Gol) o;
        return minuto == gol.minuto && Objects.equals(jugadorId, gol.jugadorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jugadorId, minuto);
    }

    @Override
    public String toString() {
        return String.format("Gol -> Minuto: %-3d | Autor (ID): %s", minuto, jugadorId);
    }
}
