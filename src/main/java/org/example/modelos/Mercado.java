package org.example.modelos;

import java.util.Objects;

public class Mercado {

    private final String id ;
    private final Usuario usuarioVenta;
    private final Jugador jugador;
    private final double precioVenta;
    private final Usuario usuarioCompra;
    private static int contador = 0;

    public Mercado(String id, Usuario usuarioVenta, Jugador jugador, double precioVenta, Usuario usuarioCompra) {
        this.id = id;
        this.usuarioVenta = usuarioVenta;
        this.jugador = jugador;
        this.precioVenta = precioVenta;
        this.usuarioCompra = usuarioCompra;
        contador++;
    }
    public Mercado(Usuario usuarioVenta, Jugador jugador, double precioVenta, Usuario usuarioCompra) {
        this.id = createID();
        this.usuarioVenta = usuarioVenta;
        this.jugador = jugador;
        this.precioVenta = precioVenta;
        this.usuarioCompra = usuarioCompra;
    }

    public String getId() {
        return id;
    }

    public Usuario getUsuarioVenta() {
        return usuarioVenta;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public Usuario getUsuarioCompra() {
        return usuarioCompra;
    }

    public static int getContador() {
        return contador;
    }

    private String createID() {
        String identificador = String.format("%c%04d", 'M', contador);
        contador++;
        return identificador;
    }

    @Override
    public String toString() {
        return "Mercado{" +
                "id='" + id + '\'' +
                ", usuarioVenta=" + usuarioVenta +
                ", jugador=" + jugador +
                ", precioVenta=" + precioVenta +
                ", usuarioCompra=" + usuarioCompra +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Mercado mercado)) return false;
        return Double.compare(precioVenta, mercado.precioVenta) == 0 && Objects.equals(id, mercado.id) && Objects.equals(usuarioVenta, mercado.usuarioVenta) && Objects.equals(jugador, mercado.jugador) && Objects.equals(usuarioCompra, mercado.usuarioCompra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuarioVenta, jugador, precioVenta, usuarioCompra);
    }
}
