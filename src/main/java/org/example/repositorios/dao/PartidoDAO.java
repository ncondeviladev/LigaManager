package org.example.repositorios.dao;

import org.example.modelos.Partido;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz DAO para el modelo Partido.
 * Define las operaciones de persistencia para la entidad Partido.
 */
public interface PartidoDAO {
    List<Partido> buscarTodos();
    Optional<Partido> buscarPorId(String id);
    List<Partido> buscarPorJornadaId(String jornadaId);
    void guardar(Partido partido);
    void guardarTodos(List<Partido> partidos);
    void eliminarPorId(String id);
    void eliminarTodos();
}
