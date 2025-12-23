package org.example.repositorios.json;

import org.example.modelos.Usuario;
import org.example.repositorios.dao.UsuarioDAO;
import org.example.utils.dataUtils.JsonUtils;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.google.gson.reflect.TypeToken;

// Implementación de UsuarioDAO para persistencia en archivos JSON.
// Utiliza la librería Gson para serializar y deserializar.
public class UsuarioDAOImplJSON implements UsuarioDAO {

    private final String rutaArchivo;

    public UsuarioDAOImplJSON(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        verificarArchivo();
    }

    private void verificarArchivo() {
        if (!Files.exists(Paths.get(rutaArchivo))) {
            guardarTodos(new ArrayList<>());
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        Type tipoLista = new TypeToken<ArrayList<Usuario>>() {
        }.getType();
        return JsonUtils.leerListaDesdeJson(rutaArchivo, "usuarios", tipoLista);
    }

    @Override
    public Optional<Usuario> buscarPorId(String id) {
        return listarTodos().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return listarTodos().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public void guardar(Usuario usuario) {
        List<Usuario> usuarios = listarTodos();
        usuarios.removeIf(u -> u.getId().equals(usuario.getId()));
        usuarios.add(usuario);
        guardarTodos(usuarios);
    }

    @Override
    public void guardarTodos(List<Usuario> usuarios) {
        JsonUtils.escribirListaEnJson(rutaArchivo, "usuarios", usuarios);
    }

    @Override
    public void eliminarPorId(String id) {
        List<Usuario> usuarios = listarTodos();
        if (usuarios.removeIf(u -> u.getId().equals(id))) {
            guardarTodos(usuarios);
        }
    }

    @Override
    public Optional<Usuario> buscarPorIdEquipo(String idEquipo) {
        return listarTodos().stream()
                .filter(u -> u.getIdEquipo().equals(idEquipo))
                .findFirst();
    }
}