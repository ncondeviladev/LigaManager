package org.example.repositorios.repo;

import org.example.repositorios.dao.*;
import org.example.repositorios.json.*;
import java.nio.file.Paths;

public class LigaRepoImplJSON implements LigaRepo {

    private final UsuarioDAO usuarioDAO;
    private final EquipoDAO equipoDAO;
    private final MercadoDAO mercadoDAO;
    private final JugadorDAO jugadorDAO;
    private final JornadaDAO jornadaDAO;

    // Utilizamos Paths.get para construir la ruta de forma segura en cualquier SO.
    // Apuntamos a 'src/main/resources' para persistencia en desarrollo.
    private static final String RUTA_BASE = Paths.get("src", "main", "resources", "data", "json").toString();

    public LigaRepoImplJSON() {
        // Construimos las rutas absolutas concatenando la base
        // Nota: no se hace getResource() porque no se sabe si se ejecuta desde el IDE o
        // desde el JAR
        String usuarios = Paths.get(RUTA_BASE, "users.json").toString();
        String equipos = Paths.get(RUTA_BASE, "teams.json").toString();
        String mercado = Paths.get(RUTA_BASE, "market.json").toString();
        String jugadores = Paths.get(RUTA_BASE, "players.json").toString();
        String competicion = Paths.get(RUTA_BASE, "competicion.json").toString();

        // Inicializamos los DAOs
        this.usuarioDAO = new UsuarioDAOImplJSON(usuarios);
        this.equipoDAO = new EquipoDAOImplJSON(equipos);
        this.mercadoDAO = new MercadoDAOImplJSON(mercado);
        this.jugadorDAO = new JugadorDAOImplJSON(jugadores);
        this.jornadaDAO = new JornadaDAOImplJSON(competicion);
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
