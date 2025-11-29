package org.example.repositorios.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.modelos.Mercado;
import org.example.repositorios.dao.MercadoDAO;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Implementación de MercadoDAO para persistencia en archivos JSON.
// Utiliza la librería Gson para serializar y deserializar.
public class MercadoDAOImplJSON implements MercadoDAO {

    private final String rutaArchivo;
    private final Gson gson;

    public MercadoDAOImplJSON(String rutaArchivo) {
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
    public List<Mercado> listarTodos() {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Type tipoLista = new TypeToken<ArrayList<Mercado>>() {
            }.getType();
            List<Mercado> mercados = gson.fromJson(reader, tipoLista);
            return mercados != null ? mercados : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Mercado> buscarPorId(String id) {
        return listarTodos().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    @Override
    public void guardar(Mercado mercado) {
        List<Mercado> mercados = listarTodos();
        mercados.removeIf(m -> m.getId().equals(mercado.getId()));
        mercados.add(mercado);
        guardarTodos(mercados);
    }

    public void guardarTodos(List<Mercado> mercados) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(mercados, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarPorId(String id) {
        List<Mercado> mercados = listarTodos();
        if (mercados.removeIf(m -> m.getId().equals(id))) {
            guardarTodos(mercados);
        }
    }
}