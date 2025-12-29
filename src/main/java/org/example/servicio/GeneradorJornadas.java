package org.example.servicio;

import org.example.modelos.*;
import org.example.modelos.competicion.Jornada;
import org.example.modelos.competicion.Partido;
import org.example.repositorios.repo.LigaRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GeneradorJornadas {

    /**
     * Crea una nueva jornada y la agrega al repositorio
     */
    public static Jornada crearJornada(
            LigaRepo repo,
            List<Equipo> equipos) {

        int numeroJornada = repo.getJornadaDAO().listarTodas().size() + 1;
        List<Partido> partidos = new ArrayList<>();

        int total = equipos.size();

        for (int i = 0; i < total / 2; i++) {

            Equipo local = equipos.get(i);
            Equipo visitante = equipos.get(total - 1 - i);

            String jornadaId = "J" + String.format("%02d", numeroJornada);
            String partidoId = "PAR" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();

            Partido partido = new Partido(
                    partidoId,
                    jornadaId,
                    local.getId(),
                    visitante.getId(),
                    0,
                    0,
                    new ArrayList<>() // goles vacíos
            );

            partidos.add(partido);
        }

        Jornada nuevaJornada = new Jornada(
                "J" + String.format("%02d", numeroJornada),
                numeroJornada,
                partidos
        );

        // Agregar al repositorio
        repo.getJornadaDAO().guardar(nuevaJornada);

        return nuevaJornada;
    }
}