package org.example.modelos;

import org.example.modelos.enums.Formacion;
import java.util.List;
import java.util.Objects;

// Clase Alineacion refactorizada para coincidir con la estructura del JSON users.json
// En el JSON, la alineación se guarda con campos separados por posición (portero, defensas, medios, delanteros)
// en lugar de una lista única de titulares.
public class Alineacion {

    private Formacion formacion;

    private String portero;

    private List<String> defensas;

    private List<String> medios;

    private List<String> delanteros;

    // Constructor vacío necesario para Gson
    public Alineacion() {
    }

    // Constructor completo
    public Alineacion(Formacion formacion, String portero, List<String> defensas,
            List<String> medios, List<String> delanteros) {
        this.formacion = formacion;
        this.portero = portero;
        this.defensas = defensas;
        this.medios = medios;
        this.delanteros = delanteros;
    }

    // --- Getters y Setters ---

    public Formacion getFormacion() {
        return formacion;
    }

    public void setFormacion(Formacion formacion) {
        this.formacion = formacion;
    }

    public String getPortero() {
        return portero;
    }

    public void setPortero(String portero) {
        this.portero = portero;
    }

    public List<String> getDefensas() {
        return defensas;
    }

    public void setDefensas(List<String> defensas) {
        this.defensas = defensas;
    }

    public List<String> getMedios() {
        return medios;
    }

    public void setMedios(List<String> medios) {
        this.medios = medios;
    }

    public List<String> getDelanteros() {
        return delanteros;
    }

    public void setDelanteros(List<String> delanteros) {
        this.delanteros = delanteros;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Alineacion that = (Alineacion) o;
        return Objects.equals(formacion, that.formacion) &&
                Objects.equals(portero, that.portero) &&
                Objects.equals(defensas, that.defensas) &&
                Objects.equals(medios, that.medios) &&
                Objects.equals(delanteros, that.delanteros);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formacion, portero, defensas, medios, delanteros);
    }

    @Override
    public String toString() {
        return String.format("Alineacion -> Formación: %s | Portero: %s | Defensas: %d | Medios: %d | Delanteros: %d",
                formacion, portero,
                defensas != null ? defensas.size() : 0,
                medios != null ? medios.size() : 0,
                delanteros != null ? delanteros.size() : 0);
    }
}
