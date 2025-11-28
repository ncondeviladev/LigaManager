package org.example.modelos.enums;

// Enum Formacion extraído a su propio fichero para mejor organización y reutilización.
public enum Formacion {
    F442("4-4-2"),
    F433("4-3-3"),
    F451("4-5-1"),
    F343("3-4-3"),
    F352("3-5-2");

    private final String valor;

    Formacion(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
