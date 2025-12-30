package org.example.servicio;

import org.example.modelos.Alineacion;
import org.example.modelos.Jugador;
import org.example.modelos.enums.Formacion;

import java.util.List;
import java.util.stream.Collectors;

public class AlineacionIA {

    public static Alineacion generar(
            String equipoId,
            List<Jugador> jugadores) {

        List<Jugador> delEquipo = jugadores.stream()
                .filter(j -> equipoId.equals(j.getIdEquipo()))
                .collect(Collectors.toList());

        // Selección simple (DAM-friendly)
        String portero = delEquipo.stream()
                .filter(j -> j.getPosicion().name().equals("PORTERO"))
                .findFirst()
                .orElseThrow()
                .getId();

        List<String> defensas = delEquipo.stream()
                .filter(j -> j.getPosicion().name().equals("DEFENSA"))
                .limit(4)
                .map(Jugador::getId)
                .toList();

        List<String> medios = delEquipo.stream()
                .filter(j -> j.getPosicion().name().equals("MEDIO"))
                .limit(4)
                .map(Jugador::getId)
                .toList();

        List<String> delanteros = delEquipo.stream()
                .filter(j -> j.getPosicion().name().equals("DELANTERO"))
                .limit(2)
                .map(Jugador::getId)
                .toList();

        return new Alineacion(
                Formacion.F442,
                portero,
                defensas,
                medios,
                delanteros
        );
    }
}
