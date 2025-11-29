package org.example.repositorios.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.modelos.Jugador;
import org.example.repositorios.dao.JugadorDAO;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Implementación de JugadorDAO para persistencia en archivos JSON.
// Utiliza la librería Gson para serializar y deserializar.
public class JugadorDAOImplJSON implements JugadorDAO {

    private final String rutaArchivo;
    private final Gson gson;

    public JugadorDAOImplJSON(String rutaArchivo) {
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
    public List<Jugador> listarTodos() {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Type tipoLista = new TypeToken<ArrayList<Jugador>>() {
            }.getType();
            List<Jugador> jugadores = gson.fromJson(reader, tipoLista);
            return jugadores != null ? jugadores : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Jugador> buscarPorEquipoId(String equipoId) {
        return listarTodos().stream()
                .filter(j -> j.getEquipoId().equals(equipoId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Jugador> buscarPorId(String id) {
        return listarTodos().stream()
                .filter(j -> j.getId().equals(id))
                .findFirst();
    }

    @Override
    public void guardar(Jugador jugador) {
        List<Jugador> jugadores = listarTodos();
        jugadores.removeIf(j -> j.getId().equals(jugador.getId()));
        jugadores.add(jugador);
        guardarTodos(jugadores);
    }

    @Override
    public void guardarTodos(List<Jugador> jugadores) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(jugadores, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarPorId(String id) {
        List<Jugador> jugadores = listarTodos();
        if (jugadores.removeIf(j -> j.getId().equals(id))) {
            guardarTodos(jugadores);
        }
    }
}