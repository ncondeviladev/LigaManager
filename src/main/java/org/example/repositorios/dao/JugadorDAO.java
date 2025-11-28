package org.example.repositorios.dao;

import org.example.modelos.Jugador;
import org.example.modelos.enums.Posicion;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz DAO para el modelo Jugador.
 * Define las operaciones de persistencia para la entidad Jugador.
 */
public interface JugadorDAO {
    List<Jugador> buscarTodos();
    Optional<Jugador> buscarPorId(String id);
    List<Jugador> buscarPorEquipoId(String equipoId);
    List<Jugador> buscarPorPosicion(Posicion posicion);
    void guardar(Jugador jugador);
    void guardarTodos(List<Jugador> jugadores);
    void eliminarPorId(String id);
    void eliminarTodos();
}
