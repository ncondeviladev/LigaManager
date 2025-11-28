package org.example.repositorios.dao;

import org.example.modelos.Equipo;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz DAO para el modelo Equipo.
 * Define las operaciones de persistencia que se pueden realizar sobre la entidad Equipo.
 */
public interface EquipoDAO {
    List<Equipo> buscarTodos();
    Optional<Equipo> buscarPorId(String id);
    void guardar(Equipo equipo);
    void guardarTodos(List<Equipo> equipos);
    void eliminarPorId(String id);
    void eliminarTodos();
}
