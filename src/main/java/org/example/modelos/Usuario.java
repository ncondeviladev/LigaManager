package org.example.modelos;

import java.util.Objects;

enum TipoUsuario {
    admin, ESTANDAR
}

public class Usuario {
    private final String id;
    private final TipoUsuario tipoUsuario;
    private final String email;
    private final String password;
    private double saldo;
    private final Equipo equipo;
    private final Alineacion alineacion;
    private static int contador = 0;

    public Usuario(String tipoUsuario, String email, String password, double saldo, Equipo equipo, Alineacion alineacion) {
        this.id = createID();
        this.tipoUsuario = TipoUsuario.valueOf(tipoUsuario);
        this.email = email;
        this.password = password;
        this.saldo = saldo;
        this.equipo = equipo;
        this.alineacion = alineacion;
    }

    public Usuario(String id, String tipoUsuario, String email, String password, double saldo, Equipo equipo, Alineacion alineacion) {
        this.id = id;
        this.tipoUsuario = TipoUsuario.valueOf(tipoUsuario);
        this.email = email;
        this.password = password;
        this.saldo = saldo;
        this.equipo = equipo;
        this.alineacion = alineacion;
        contador++;
    }

    private String createID() {
        String identificador = String.format("%c%04d", 'U', contador);
        contador++;
        return identificador;
    }

    //GETTERS
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

    public Equipo getEquipo() {
        return equipo;
    }

    public Alineacion getAlineacion() {
        return alineacion;
    }

    public static int getContador() {
        return contador;
    }

    //SETTERS

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", tipoUsuario=" + tipoUsuario +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", saldo=" + saldo +
                ", equipo=" + equipo +
                ", alineacion=" + alineacion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Usuario usuario)) return false;
        return Double.compare(saldo, usuario.saldo) == 0 && Objects.equals(id, usuario.id) && tipoUsuario == usuario.tipoUsuario && Objects.equals(email, usuario.email) && Objects.equals(password, usuario.password) && Objects.equals(equipo, usuario.equipo) && Objects.equals(alineacion, usuario.alineacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoUsuario, email, password, saldo, equipo, alineacion);
    }
}
