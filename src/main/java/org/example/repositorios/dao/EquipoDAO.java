package org.example.repositorios.dao;

import org.example.modelos.Equipo;
import java.util.List;
import java.util.Optional;

// Interfaz DAO para la gestión de la entidad Equipo.
// Define las operaciones CRUD básicas.
public interface EquipoDAO {
    List<Equipo> listarTodos();

    Optional<Equipo> buscarPorId(String id);

    void guardar(Equipo equipo);

    void guardarTodos(List<Equipo> equipos);

    void eliminarPorId(String id);
}
