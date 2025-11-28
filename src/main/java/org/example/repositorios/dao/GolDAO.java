package org.example.repositorios.dao;

import org.example.modelos.Gol;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz DAO para el modelo Gol.
 * Define las operaciones de persistencia para la entidad Gol.
 */
public interface GolDAO {
    List<Gol> buscarTodos();
    Optional<Gol> buscarPorId(String id);
    List<Gol> buscarPorPartidoId(String partidoId);
    List<Gol> buscarPorJugadorId(String jugadorId);
    void guardar(Gol gol);
    void guardarTodos(List<Gol> goles);
    void eliminarPorId(String id);
    void eliminarTodos();
}
