package org.example.repositorios.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.modelos.Equipo;
import org.example.repositorios.dao.EquipoDAO;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Implementación de EquipoDAO para persistencia en archivos JSON.
// Utiliza la librería Gson para serializar y deserializar.
public class EquipoDAOImplJSON implements EquipoDAO {

    private final String rutaArchivo;
    private final Gson gson;

    public EquipoDAOImplJSON(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.gson = new Gson();
        verificarArchivo();
    }

    // Verifica si el archivo existe. Si no, crea uno con una lista vacía.
    private void verificarArchivo() {
        if (!Files.exists(Paths.get(rutaArchivo))) {
            guardarTodos(new ArrayList<>());
        }
    }

    @Override
    public List<Equipo> listarTodos() {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Type tipoLista = new TypeToken<ArrayList<Equipo>>() {
            }.getType();
            List<Equipo> equipos = gson.fromJson(reader, tipoLista);
            return equipos != null ? equipos : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Equipo> buscarPorId(String id) {
        return listarTodos().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    @Override
    public void guardar(Equipo equipo) {
        List<Equipo> equipos = listarTodos();
        equipos.removeIf(e -> e.getId().equals(equipo.getId()));
        equipos.add(equipo);
        guardarTodos(equipos);
    }

    @Override
    public void guardarTodos(List<Equipo> equipos) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(equipos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarPorId(String id) {
        List<Equipo> equipos = listarTodos();
        if (equipos.removeIf(e -> e.getId().equals(id))) {
            guardarTodos(equipos);
        }
    }
}
