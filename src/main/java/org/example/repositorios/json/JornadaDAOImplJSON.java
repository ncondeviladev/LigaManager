package org.example.repositorios.json;

import org.example.modelos.competicion.Jornada;
import org.example.repositorios.dao.JornadaDAO;
import org.example.utils.JsonUtils;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

// Implementación de JornadaDAO para persistencia en competicion.json
// Gestiona todas las jornadas con sus partidos y goles anidados.
public class JornadaDAOImplJSON implements JornadaDAO {

    private final String rutaArchivo;

    public JornadaDAOImplJSON(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        verificarArchivo();
    }

    // Verifica si el archivo existe. Si no, crea uno con estructura vacía.
    private void verificarArchivo() {
        if (!Files.exists(Paths.get(rutaArchivo))) {
            guardarTodas(new ArrayList<>());
        }
    }

    @Override
    public List<Jornada> listarTodas() {
        Type tipoLista = new TypeToken<ArrayList<Jornada>>() {
        }.getType();
        return JsonUtils.leerListaDesdeJson(rutaArchivo, "jornadas", tipoLista);
    }

    @Override
    public Optional<Jornada> buscarPorId(String id) {
        return listarTodas().stream()
                .filter(j -> j.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Jornada> buscarPorNumero(int numero) {
        return listarTodas().stream()
                .filter(j -> j.getNumero() == numero)
                .findFirst();
    }

    @Override
    public void guardar(Jornada jornada) {
        List<Jornada> jornadas = listarTodas();
        jornadas.removeIf(j -> j.getId().equals(jornada.getId()));
        jornadas.add(jornada);
        guardarTodas(jornadas);
    }

    @Override
    public void guardarTodas(List<Jornada> jornadas) {
        JsonUtils.escribirListaEnJson(rutaArchivo, "jornadas", jornadas);
    }

    @Override
    public void eliminarPorId(String id) {
        List<Jornada> jornadas = listarTodas();
        if (jornadas.removeIf(j -> j.getId().equals(id))) {
            guardarTodas(jornadas);
        }
    }
}
