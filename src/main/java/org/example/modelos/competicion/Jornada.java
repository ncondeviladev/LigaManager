package org.example.modelos.competicion;

import java.util.List;
import java.util.Objects;

// Modelo para representar una jornada de la liga.
// Refactorizado para usar composición: contiene objetos Partido completos
// en lugar de solo IDs, facilitando el acceso a los datos y la serialización JSON.
public class Jornada {

    // ID de la jornada (ej: "J01", "J02")
    private final String id;

    // Número de la jornada (1, 2, 3...)
    private final int numero;

    // Lista de partidos que se juegan en esta jornada (objetos completos)
    private List<Partido> partidos;

    // Constructor
    public Jornada(String id, int numero, List<Partido> partidos) {
        this.id = id;
        this.numero = numero;
        this.partidos = partidos;
    }

    // --- Getters y Setters ---

    public String getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Jornada jornada = (Jornada) o;
        return Objects.equals(id, jornada.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Jornada %d (ID: %s) -> %d partidos",
                numero, id, partidos != null ? partidos.size() : 0);
    }
}
