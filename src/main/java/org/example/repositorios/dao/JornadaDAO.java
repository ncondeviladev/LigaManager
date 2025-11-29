package org.example.repositorios.dao;

import org.example.modelos.competicion.Jornada;
import java.util.List;
import java.util.Optional;

// Interfaz DAO para la gestión de Jornadas.
// Gestiona el acceso a competicion.json que contiene todas las jornadas con sus partidos y goles.
public interface JornadaDAO {
    List<Jornada> listarTodas();

    Optional<Jornada> buscarPorId(String id);

    Optional<Jornada> buscarPorNumero(int numero);

    void guardar(Jornada jornada);

    void guardarTodas(List<Jornada> jornadas);

    void eliminarPorId(String id);
}
