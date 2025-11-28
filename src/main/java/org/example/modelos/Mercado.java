package org.example.modelos;

import java.util.Objects;

// NOTA: Esta clase representa una única oferta en el mercado.
// Para mayor claridad, sería recomendable renombrar la clase (y el fichero) a "Venta" o "Oferta".
public class Mercado {

    private final String id;
    // Se usan IDs en lugar de objetos para mantener el modelo simple y desacoplado.
    private final String jugadorId;
    private final String vendedorId;
    private final double precioVenta;

    // Se elimina la lógica de autogeneración de IDs (contador, createID).
    // Se elimina el campo 'usuarioCompra', ya que un item en venta aún no tiene comprador.

    // El constructor ahora sigue el patrón POJO, recibiendo todos los datos necesarios.
    public Mercado(String id, String jugadorId, String vendedorId, double precioVenta) {
        this.id = id;
        this.jugadorId = jugadorId;
        this.vendedorId = vendedorId;
        this.precioVenta = precioVenta;
    }

    // --- Getters ---

    public String getId() {
        return id;
    }

    public String getJugadorId() {
        return jugadorId;
    }

    public String getVendedorId() {
        return vendedorId;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mercado mercado = (Mercado) o;
        return Objects.equals(id, mercado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        // Formato mejorado para una visualización clara de la oferta.
        return String.format("Oferta Mercado -> ID: %-5s | JugadorID: %-5s | VendedorID: %-5s | Precio: %.2f M€",
                id, jugadorId, vendedorId, precioVenta);
    }
}
