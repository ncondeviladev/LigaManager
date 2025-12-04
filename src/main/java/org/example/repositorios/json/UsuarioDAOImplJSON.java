package org.example.repositorios.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.modelos.Usuario;
import org.example.repositorios.dao.UsuarioDAO;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Implementación de UsuarioDAO para persistencia en archivos JSON.
// Utiliza la librería Gson para serializar y deserializar.
public class UsuarioDAOImplJSON implements UsuarioDAO {

    private final String rutaArchivo;
    private final Gson gson;

    public UsuarioDAOImplJSON(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.gson = new Gson();
        verificarArchivo();
    }

    private void verificarArchivo() {
        if (!Files.exists(Paths.get(rutaArchivo))) {
            guardarTodos(new ArrayList<>());
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Type tipoLista = new TypeToken<ArrayList<Usuario>>() {
            }.getType();
            List<Usuario> usuarios = gson.fromJson(reader, tipoLista);
            return usuarios != null ? usuarios : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(String id) {
        return listarTodos().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Usuario> buscarPorNombre(String nombre) {
        return listarTodos().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(nombre))
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
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarPorId(String id) {
        List<Usuario> usuarios = listarTodos();
        if (usuarios.removeIf(u -> u.getId().equals(id))) {
            guardarTodos(usuarios);
        }
    }

    @Override
    public List<Usuario> buscarPorIdEquipo(String idEquipo) {
        return listarTodos().stream()
                .filter(u -> idEquipo.equals(u.getIdEquipo()))
                .toList();
    }
}