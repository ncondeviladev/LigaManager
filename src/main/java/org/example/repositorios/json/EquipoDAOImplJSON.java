package org.example.repositorios.json;

import org.example.modelos.Equipo;
import org.example.repositorios.dao.EquipoDAO;
import org.example.utils.dataUtils.JsonUtils;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.google.gson.reflect.TypeToken;

// Implementación de EquipoDAO para persistencia en archivos JSON.
// Utiliza la librería Gson para serializar y deserializar.
public class EquipoDAOImplJSON implements EquipoDAO {

    private final String rutaArchivo;

    public EquipoDAOImplJSON(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
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
        Type tipoLista = new TypeToken<ArrayList<Equipo>>() {
        }.getType();
        return JsonUtils.leerListaDesdeJson(rutaArchivo, "equipos", tipoLista);
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
        JsonUtils.escribirListaEnJson(rutaArchivo, "equipos", equipos);
    }

    @Override
    public void eliminarPorId(String id) {
        List<Equipo> equipos = listarTodos();
        if (equipos.removeIf(e -> e.getId().equals(id))) {
            guardarTodos(equipos);
        }
    }
}
