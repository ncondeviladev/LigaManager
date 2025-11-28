package org.example.repositorios.dao;

import org.example.modelos.Mercado;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz DAO para el modelo Mercado.
 * Define las operaciones de persistencia para las ofertas en el mercado.
 */
public interface MercadoDAO {
    List<Mercado> buscarTodos();
    Optional<Mercado> buscarPorId(String id);
    List<Mercado> buscarPorJugadorId(String jugadorId);
    List<Mercado> buscarPorVendedorId(String vendedorId);
    void guardar(Mercado mercado);
    void guardarTodos(List<Mercado> mercados);
    void eliminarPorId(String id);
    void eliminarTodos();
}
