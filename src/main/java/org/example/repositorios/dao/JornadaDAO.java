package org.example.repositorios.dao;

import org.example.modelos.Jornada;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz DAO para el modelo Jornada.
 * Define las operaciones de persistencia para la entidad Jornada.
 */
public interface JornadaDAO {
    List<Jornada> buscarTodos();
    Optional<Jornada> buscarPorId(String id);
    Optional<Jornada> buscarPorNumero(int numero);
    void guardar(Jornada jornada);
    void guardarTodos(List<Jornada> jornadas);
    void eliminarPorId(String id);
    void eliminarTodos();
}
