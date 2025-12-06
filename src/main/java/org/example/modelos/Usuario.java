package org.example.modelos;

import com.google.gson.annotations.SerializedName;
import org.example.modelos.enums.TipoUsuario;
import java.util.Objects;

// Clase Usuario refactorizada para coincidir con users.json
// Cambios principales:
// - "nombre" → "email" (el JSON usa email como identificador de usuario)
// - "tipoUsuario" → "tipo" en JSON
// - "alineacionId" → objeto Alineacion completo (composición, como en JSON)
public class Usuario {

    private String id;

    // En el JSON se llama "email", pero en Java usamos "email" como nombre de
    // variable
    // para mayor claridad semántica
    @SerializedName("email")
    private String email;

    private String password;

    // En el JSON se llama "tipo", mapeamos con @SerializedName
    @SerializedName("tipo")
    private TipoUsuario tipoUsuario;

    private double saldo;

    // La alineación se guarda como objeto completo dentro del usuario (composición)
    // Esto coincide con la estructura del JSON donde "alineacion" es un objeto
    // anidado
    private Alineacion alineacion;

    // ID del equipo que gestiona el usuario
    @SerializedName("idEquipo")
    private String idEquipo;

    // Constructor vacío para GSON/JPA
    public Usuario() {
    }

    // Constructor básico
    public Usuario(String id, String email, String password, TipoUsuario tipoUsuario, double saldo) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.tipoUsuario = tipoUsuario;
        this.saldo = saldo;
    }

    // Constructor completo con alineación (compatibilidad)
    public Usuario(String id, String email, String password, TipoUsuario tipoUsuario,
            double saldo, Alineacion alineacion) {
        this(id, email, password, tipoUsuario, saldo, alineacion, null);
    }

    // Constructor completo con alineación y equipo
    public Usuario(String id, String email, String password, TipoUsuario tipoUsuario,
            double saldo, Alineacion alineacion, String idEquipo) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.tipoUsuario = tipoUsuario;
        this.saldo = saldo;
        this.alineacion = alineacion;
        this.idEquipo = idEquipo;
    }

    // --- Getters y Setters ---

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Alineacion getAlineacion() {
        return alineacion;
    }

    public void setAlineacion(Alineacion alineacion) {
        this.alineacion = alineacion;
    }

    public String getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(String idEquipo) {
        this.idEquipo = idEquipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Usuario -> ID: %-5s | Email: %-20s | Rol: %-10s | Saldo: %.2f M€ | EquipoID: %s",
                id, email, tipoUsuario, saldo, (idEquipo != null ? idEquipo : "Sin equipo"));
    }
}
