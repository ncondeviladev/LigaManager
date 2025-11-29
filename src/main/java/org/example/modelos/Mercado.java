package org.example.modelos;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

// Clase que representa una oferta de venta en el mercado de fichajes.
// Refactorizada para coincidir con market.json usando @SerializedName
public class Mercado {

    private final String id;

    // ID del jugador que está en venta
    private final String jugadorId;

    // En el JSON se llama "vendedor", mapeamos con @SerializedName
    @SerializedName("vendedor")
    private final String vendedorId;

    // En el JSON se llama "precioSalida", mapeamos con @SerializedName
    @SerializedName("precioSalida")
    private final double precioVenta;

    // Constructor POJO que recibe todos los datos necesarios
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
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Mercado mercado = (Mercado) o;
        return Objects.equals(id, mercado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Oferta Mercado -> ID: %-5s | JugadorID: %-5s | VendedorID: %-5s | Precio: %.2f M€",
                id, jugadorId, vendedorId, precioVenta);
    }
}
