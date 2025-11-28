package org.example.modelos;

import org.example.modelos.enums.TipoUsuario;

import java.util.Objects;

// La clase Usuario se convierte en un POJO.
public class Usuario {
    private final String id;
    private final TipoUsuario tipoUsuario;
    private final String email;
    private final String password;
    private double saldo;

    // Se reemplazan los objetos completos por sus IDs.
    // Esto desacopla las clases y simplifica la persistencia.
    private String equipoId;
    private String alineacionId;

    // El constructor recibe el ID y las referencias a otros objetos por su ID.
    public Usuario(String id, TipoUsuario tipoUsuario, String email, String password, double saldo, String equipoId, String alineacionId) {
        this.id = id;
        this.tipoUsuario = tipoUsuario;
        this.email = email;
        this.password = password;
        this.saldo = saldo;
        this.equipoId = equipoId;
        this.alineacionId = alineacionId;
    }

    // --- Getters y Setters ---

    public String getId() {
        return id;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(String equipoId) {
        this.equipoId = equipoId;
    }

    public String getAlineacionId() {
        return alineacionId;
    }

    public void setAlineacionId(String alineacionId) {
        this.alineacionId = alineacionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        // Formato mejorado para una visualización más clara. Se omite la contraseña por seguridad.
        return String.format("Usuario -> ID: %-5s | Email: %-25s | Rol: %-10s | Saldo: %.2f M€ | EquipoID: %-4s",
                id, email, tipoUsuario, saldo, equipoId);
    }
}
