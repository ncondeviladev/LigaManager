package org.example.repositorios.repo;

import org.example.repositorios.dao.*;
import org.example.repositorios.jdbc.*;

/**
 * Implementación del repositorio principal para base de datos (JDBC).
 * Actúa como orquestador, inicializando y exponiendo los DAOs específicos
 * que acceden a PostgreSQL.
 */
public class LigaRepoImplJDBC implements LigaRepo {

    private final UsuarioDAO usuarioDAO;
    private final EquipoDAO equipoDAO;
    private final MercadoDAO mercadoDAO;
    private final JugadorDAO jugadorDAO;
    private final JornadaDAO jornadaDAO;

    /**
     * Constructor que inicializa todas las implementaciones JDBC de los DAOs.
     */
    public LigaRepoImplJDBC() {
        this.equipoDAO = new EquipoDAOImplJDBC();
        this.jugadorDAO = new JugadorDAOImplJDBC();
        this.usuarioDAO = new UsuarioDAOImplJDBC();
        this.mercadoDAO = new MercadoDAOImplJDBC();
        this.jornadaDAO = new JornadaDAOImplJDBC();
    }

    @Override
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    @Override
    public EquipoDAO getEquipoDAO() {
        return equipoDAO;
    }

    @Override
    public MercadoDAO getMercadoDAO() {
        return mercadoDAO;
    }

    @Override
    public JugadorDAO getJugadorDAO() {
        return jugadorDAO;
    }

    @Override
    public JornadaDAO getJornadaDAO() {
        return jornadaDAO;
    }
}
