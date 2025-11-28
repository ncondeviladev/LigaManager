package org.example.modelos;

import java.util.List;
import java.util.Objects;

// Modelo para representar un partido entre dos equipos.
// Contiene los IDs de los equipos, el resultado y una lista de los goles.
public class Partido {
    private final String id;
    private final String jornadaId;
    private final String equipoLocalId;
    private final String equipoVisitanteId;
    private int golesLocal;
    private int golesVisitante;
    // Un partido contiene una lista de objetos Gol para detallar el resultado.
    private List<Gol> goles;

    /**
     * Constructor para crear una instancia de Partido.
     *
     * @param id                El identificador único del partido.
     * @param jornadaId         El ID de la jornada a la que pertenece el partido.
     * @param equipoLocalId     El ID del equipo local.
     * @param equipoVisitanteId El ID del equipo visitante.
     * @param golesLocal        El número de goles del equipo local.
     * @param golesVisitante    El número de goles del equipo visitante.
     * @param goles             Una lista de objetos Gol que ocurrieron en el partido.
     */
    public Partido(String id, String jornadaId, String equipoLocalId, String equipoVisitanteId, int golesLocal, int golesVisitante, List<Gol> goles) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partido partido = (Partido) o;
        return Objects.equals(id, partido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Devuelve una representación en cadena del objeto Partido.
     *
     * @return Una cadena formateada con el resultado del partido.
     */
    @Override
    public String toString() {
        // Formato para mostrar el resultado del partido de forma clara.
        return String.format("Partido -> ID: %s | Local (%s) %d - %d Visitante (%s)",
                id, equipoLocalId, golesLocal, golesVisitante, equipoVisitanteId);
    }
}
