package org.example.modelos;

import org.example.modelos.enums.Formacion;

import java.util.ArrayList;
import java.util.Objects;

// La clase Alineacion ahora es un POJO (Plain Old Java Object) simple.
// No contiene lógica de negocio ni definiciones de otros tipos.
public class Alineacion {
    // Añadimos un ID para que la alineación pueda ser identificada de forma única.
    // Esto es crucial para referenciarla desde la clase Usuario.
    private String id;
    private ArrayList<String> titulares; // Almacenamos solo los IDs de los jugadores.
    private Formacion formacion;

    /**
     * Constructor para crear una instancia de Alineacion.
     *
     * @param id        El identificador único de la alineación.
     * @param titulares Una lista de IDs de los jugadores titulares.
     * @param formacion La formación táctica utilizada.
     */
    public Alineacion(String id, ArrayList<String> titulares, Formacion formacion) {
        this.id = id;
        this.titulares = titulares;
        this.formacion = formacion;
    }

    // --- Getters y Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getTitulares() {
        return titulares;
    }

    public void setTitulares(ArrayList<String> titulares) {
        this.titulares = titulares;
    }

    public Formacion getFormacion() {
        return formacion;
    }

    public void setFormacion(Formacion formacion) {
        this.formacion = formacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alineacion that = (Alineacion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Devuelve una representación en cadena del objeto Alineacion.
     *
     * @return Una cadena formateada con los detalles de la alineación.
     */
    @Override
    public String toString() {
        // Formato mejorado para mostrar la formación y el número de titulares.
        return String.format("Alineacion -> ID: %-5s | Formación: %-5s | Titulares: %s",
                id, formacion.getValor(), titulares.toString());
    }
}
