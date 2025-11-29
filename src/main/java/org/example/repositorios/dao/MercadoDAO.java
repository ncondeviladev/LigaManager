package org.example.repositorios.dao;

import org.example.modelos.Mercado;
import java.util.List;
import java.util.Optional;

// Interfaz DAO para la gestión del Mercado de fichajes.
public interface MercadoDAO {
    List<Mercado> listarTodos();

    Optional<Mercado> buscarPorId(String id);

    void guardar(Mercado mercado);

    void eliminarPorId(String id);
}
