package org.example.modelos;

import java.util.List;
import java.util.Objects;

// Modelo para representar una jornada de la liga.
// Agrupa una lista de partidos que se disputan en esa jornada.
public class Jornada {
    private final String id;
    private final int numero;
    // Contiene una lista de los IDs de los partidos, not los objetos completos.
    private List<String> partidosIds;

    /**
     * Constructor para crear una instancia de Jornada.
     *
     * @param id          El identificador único de la jornada.
     * @param numero      El número de la jornada en la temporada.
     * @param partidosIds Una lista de IDs de los partidos que componen la jornada.
     */
    public Jornada(String id, int numero, List<String> partidosIds) {
        this.id = id;
        this.numero = numero;
        this.partidosIds = partidosIds;
    }

    // --- Getters y Setters ---

    public String getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public List<String> getPartidosIds() {
        return partidosIds;
    }

    public void setPartidosIds(List<String> partidosIds) {
        this.partidosIds = partidosIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jornada jornada = (Jornada) o;
        return Objects.equals(id, jornada.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Devuelve una representación en cadena del objeto Jornada.
     *
     * @return Una cadena formateada con los detalles de la jornada.
     */
    @Override
    public String toString() {
        // Formato claro para identificar la jornada.
        return String.format("Jornada %d (ID: %s) -> %d partidos",
                numero, id, partidosIds.size());
    }
}
