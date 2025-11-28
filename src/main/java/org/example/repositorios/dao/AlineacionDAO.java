package org.example.repositorios.dao;

import org.example.modelos.Alineacion;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz DAO para el modelo Alineacion.
 * Define las operaciones de persistencia para la entidad Alineacion.
 */
public interface AlineacionDAO {
    List<Alineacion> buscarTodos();
    Optional<Alineacion> buscarPorId(String id);
    void guardar(Alineacion alineacion);
    void guardarTodos(List<Alineacion> alineaciones);
    void eliminarPorId(String id);
    void eliminarTodos();
}
