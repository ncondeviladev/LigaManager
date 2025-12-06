package org.example.repositorios.json;

import org.example.modelos.Jugador;
import org.example.repositorios.dao.JugadorDAO;
import org.example.utils.JsonUtils;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.google.gson.reflect.TypeToken;

// Implementación de JugadorDAO para persistencia en archivos JSON.
// Utiliza la librería Gson para serializar y deserializar.
public class JugadorDAOImplJSON implements JugadorDAO {

    private final String rutaArchivo;

    public JugadorDAOImplJSON(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        verificarArchivo();
    }

    private void verificarArchivo() {
        if (!Files.exists(Paths.get(rutaArchivo))) {
            guardarTodos(new ArrayList<>());
        }
    }

    @Override
    public List<Jugador> listarTodos() {
        Type tipoLista = new TypeToken<ArrayList<Jugador>>() {
        }.getType();
        return JsonUtils.leerListaDesdeJson(rutaArchivo, "jugadores", tipoLista);
    }

    @Override
    public List<Jugador> buscarPorIdEquipo(String idEquipo) {
        return listarTodos().stream()
                .filter(j -> j.getIdEquipo().equals(idEquipo))
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
        JsonUtils.escribirListaEnJson(rutaArchivo, "jugadores", jugadores);
    }

    @Override
    public void eliminarPorId(String id) {
        List<Jugador> jugadores = listarTodos();
        if (jugadores.removeIf(j -> j.getId().equals(id))) {
            guardarTodos(jugadores);
        }
    }
}