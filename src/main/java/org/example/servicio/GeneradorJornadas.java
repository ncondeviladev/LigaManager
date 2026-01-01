package org.example.servicio;

import org.example.modelos.Equipo;
import org.example.modelos.competicion.Jornada;
import org.example.modelos.competicion.Partido;
import org.example.repositorios.repo.LigaRepo;
import org.example.utils.dataUtils.DataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GeneradorJornadas {

    /**
     * Genera TODAS las jornadas de una liga completa (ida y vuelta)
     */
    public static List<Jornada> generarLigaCompleta(LigaRepo repo, List<Equipo> equipos) {
        try {

            List<Jornada> jornadas = new ArrayList<>();

            // Copia para no modificar la original
            List<Equipo> lista = new ArrayList<>(equipos);

            // Si es impar, añadimos equipo fantasma
            boolean hayDescanso = lista.size() % 2 != 0;
            if (hayDescanso) lista.add(null);

            int numEquipos = lista.size();
            int jornadasIda = numEquipos - 1;
            int partidosPorJornada = numEquipos / 2;

            int numeroJornada = 1; // Siempre empieza en J01

            // ----- IDA -----
            for (int j = 0; j < jornadasIda; j++) {

                List<Partido> partidos = new ArrayList<>();

                for (int i = 0; i < partidosPorJornada; i++) {
                    Equipo local = lista.get(i);
                    Equipo visitante = lista.get(numEquipos - 1 - i);

                    if (local == null || visitante == null) continue;

                    partidos.add(crearPartido(numeroJornada, local, visitante));
                }

                Jornada jornada = new Jornada(
                        "J" + String.format("%02d", numeroJornada),
                        numeroJornada,
                        partidos
                );

                repo.getJornadaDAO().guardar(jornada);
                jornadas.add(jornada);

                numeroJornada++;
                rotarEquipos(lista);
            }

            // ----- VUELTA -----
            int totalJornadas = jornadas.size();
            for (int j = 0; j < totalJornadas; j++) {

                Jornada ida = jornadas.get(j);
                List<Partido> partidosVuelta = new ArrayList<>();

                for (Partido p : ida.getPartidos()) {

                    Equipo local = repo.getEquipoDAO().buscarPorId(p.getEquipoVisitanteId()).orElseThrow();
                    Equipo visitante = repo.getEquipoDAO().buscarPorId(p.getEquipoLocalId()).orElseThrow();

                    partidosVuelta.add(crearPartido(numeroJornada, local, visitante));
                }

                Jornada vuelta = new Jornada(
                        "J" + String.format("%02d", numeroJornada),
                        numeroJornada,
                        partidosVuelta
                );

                repo.getJornadaDAO().guardar(vuelta);
                jornadas.add(vuelta);

                numeroJornada++;
            }

            return jornadas;
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // ---- MÉTODOS AUXILIARES ----

    private static Partido crearPartido(int numJornada, Equipo local, Equipo visitante) {

        return new Partido(
                "PAR" + UUID.randomUUID().toString().substring(0, 6).toUpperCase(),
                "J" + String.format("%02d", numJornada),
                local.getId(),
                visitante.getId(),
                -1,
                -1,
                new ArrayList<>()
        );
    }

    /**
     * Rota los equipos manteniendo fijo el primero
     */
    private static void rotarEquipos(List<Equipo> equipos) {
        Equipo fijo = equipos.get(0);
        Equipo ultimo = equipos.remove(equipos.size() - 1);
        equipos.add(1, ultimo);
        equipos.set(0, fijo);
    }
}
