package org.example.repositorios.dao;

import org.example.modelos.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz DAO para el modelo Usuario.
 * Define las operaciones de persistencia para la entidad Usuario.
 */
public interface UsuarioDAO {
    List<Usuario> buscarTodos();
    Optional<Usuario> buscarPorId(String id);
    Optional<Usuario> buscarPorEmail(String email);
    void guardar(Usuario usuario);
    void guardarTodos(List<Usuario> usuarios);
    void eliminarPorId(String id);
    void eliminarTodos();
}
