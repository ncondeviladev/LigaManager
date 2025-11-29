package org.example.modelos;

import java.util.List;
import java.util.Objects;

// Modelo para representar un partido entre dos equipos.
// Contiene los IDs de los equipos, el resultado y una lista de goles (composición).
// El jornadaId se mantiene para saber a qué jornada pertenece este partido.
public class Partido {

    // ID del partido (ej: "PAR0001", "PAR0002")
    private final String id;

    // ID de la jornada a la que pertenece (ej: "J01")
    private final String jornadaId;

    // ID del equipo local (ej: "T01")
    private final String equipoLocalId;

    // ID del equipo visitante (ej: "T02")
    private final String equipoVisitanteId;

    // Número de goles del equipo local
    private int golesLocal;

    // Número de goles del equipo visitante
    private int golesVisitante;

    // Lista de goles marcados en el partido (objetos completos)
    private List<Gol> goles;

    // Constructor completo
    public Partido(String id, String jornadaId, String equipoLocalId, String equipoVisitanteId,
            int golesLocal, int golesVisitante, List<Gol> goles) {
        this.id = id;
        this.jornadaId = jornadaId;
        this.equipoLocalId = equipoLocalId;
        this.equipoVisitanteId = equipoVisitanteId;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.goles = goles;
    }

    // --- Getters y Setters ---

    public String getId() {
        return id;
    }

    public String getJornadaId() {
        return jornadaId;
    }

    public String getEquipoLocalId() {
        return equipoLocalId;
    }

    public String getEquipoVisitanteId() {
        return equipoVisitanteId;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(int golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    public List<Gol> getGoles() {
        return goles;
    }

    public void setGoles(List<Gol> goles) {
        this.goles = goles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Partido partido = (Partido) o;
        return Objects.equals(id, partido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Partido -> ID: %s | Local (%s) %d - %d Visitante (%s)",
                id, equipoLocalId, golesLocal, golesVisitante, equipoVisitanteId);
    }
}
