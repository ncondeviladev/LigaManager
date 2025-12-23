package org.example.repositorios.repo;

import org.example.repositorios.dao.EquipoDAO;
import org.example.repositorios.dao.JornadaDAO;
import org.example.repositorios.dao.JugadorDAO;
import org.example.repositorios.dao.MercadoDAO;
import org.example.repositorios.dao.UsuarioDAO;

public interface LigaRepo {

    UsuarioDAO getUsuarioDAO();

    EquipoDAO getEquipoDAO();

    MercadoDAO getMercadoDAO();

    JugadorDAO getJugadorDAO();

    JornadaDAO getJornadaDAO();
}
