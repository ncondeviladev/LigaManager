package org.example.modelos;

import java.util.ArrayList;
import java.util.Objects;

enum Formacion {
    F442("4-4-2"),
    F433("4-3-3"),
    F451("4-5-1"),
    F343("3-4-3"),
    F352("3-5-2");

    private final String valor;

    Formacion(String valor) {
        this.valor = valor;
    }
}

public class Alineacion {
    private Jugador portero;
    private ArrayList<Jugador> defensas;
    private ArrayList<Jugador> medios;
    private ArrayList<Jugador> delanteros;
    private Formacion formacion;

    public Alineacion(Jugador portero, ArrayList<Jugador> defensas, ArrayList<Jugador> medios, ArrayList<Jugador> delanteros, Formacion formacion) {
        this.portero = portero;
        this.defensas = defensas;
        this.medios = medios;
        this.delanteros = delanteros;
        this.formacion = formacion;
    }

    //GETTERS

    public Jugador getPortero() {
        return portero;
    }

    public ArrayList<Jugador> getDefensas() {
        return defensas;
    }

    public ArrayList<Jugador> getMedios() {
        return medios;
    }

    public ArrayList<Jugador> getDelanteros() {
        return delanteros;
    }

    public Formacion getFormacion() {
        return formacion;
    }

    //SETTERS

    public void setPortero(Jugador portero) {
        this.portero = portero;
    }

    public void setDefensas(ArrayList<Jugador> defensas) {
        this.defensas = defensas;
    }

    public void setMedios(ArrayList<Jugador> medios) {
        this.medios = medios;
    }

    public void setDelanteros(ArrayList<Jugador> delanteros) {
        this.delanteros = delanteros;
    }

    public void setFormacion(Formacion formacion) {
        this.formacion = formacion;
    }

    @Override
    public String toString() {
        return "Alineacion{" +
                "portero=" + portero +
                ", defensas=" + defensas +
                ", medios=" + medios +
                ", delanteros=" + delanteros +
                ", formacion=" + formacion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Alineacion that)) return false;
        return Objects.equals(portero, that.portero) && Objects.equals(defensas, that.defensas) && Objects.equals(medios, that.medios) && Objects.equals(delanteros, that.delanteros) && formacion == that.formacion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(portero, defensas, medios, delanteros, formacion);
    }
}
