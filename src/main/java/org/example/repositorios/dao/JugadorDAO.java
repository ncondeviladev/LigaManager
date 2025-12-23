package org.example.repositorios.dao;

import org.example.modelos.Jugador;
import java.util.List;
import java.util.Optional;

// Interfaz DAO para la gestión de la entidad Jugador.
public interface JugadorDAO {
    List<Jugador> listarTodos();

    List<Jugador> buscarPorIdEquipo(String idEquipo);

    Optional<Jugador> buscarPorId(String id);

    void guardar(Jugador jugador);

    void guardarTodos(List<Jugador> jugadores);

    void eliminarPorId(String id);
}
