package org.example.servicio;

import org.example.modelos.Jugador;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;
import java.util.List;

public class JugadorServicio {
    /*
    Devuelve todos los jugadores de tu equipo
     */
    public static List<Jugador> getAllJugadoresfromEquipo(String idEquipo) {
        //llamada al repositorio
        LigaRepo repo = RepoFactory.getRepositorio("JSON");

        return repo.getJugadorDAO().buscarPorIdEquipo(idEquipo);
    }
}
