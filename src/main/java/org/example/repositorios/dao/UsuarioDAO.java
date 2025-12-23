package org.example.repositorios.dao;

import org.example.modelos.Usuario;
import java.util.List;
import java.util.Optional;

// Interfaz DAO para la gestión de Usuarios.
public interface UsuarioDAO {
    List<Usuario> listarTodos();

    Optional<Usuario> buscarPorId(String id);

    Optional<Usuario> buscarPorEmail(String email);

    void guardar(Usuario usuario);

    void guardarTodos(List<Usuario> usuarios);

    void eliminarPorId(String id);

    Optional<Usuario> buscarPorIdEquipo(String idEquipo);
}
