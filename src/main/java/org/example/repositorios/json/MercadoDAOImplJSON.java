package org.example.repositorios.json;

import org.example.modelos.Mercado;
import org.example.repositorios.dao.MercadoDAO;
import org.example.utils.dataUtils.JsonUtils;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import com.google.gson.reflect.TypeToken;

// Implementación de MercadoDAO para persistencia en archivos JSON.
// Utiliza la librería Gson para serializar y deserializar.
public class MercadoDAOImplJSON implements MercadoDAO {

    private final String rutaArchivo;

    public MercadoDAOImplJSON(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        verificarArchivo();
    }

    private void verificarArchivo() {
        if (!Files.exists(Paths.get(rutaArchivo))) {
            guardarTodos(new ArrayList<>());
        }
    }

    @Override
    public List<Mercado> listarTodos() {
        Type tipoLista = new TypeToken<ArrayList<Mercado>>() {
        }.getType();
        return JsonUtils.leerListaDesdeJson(rutaArchivo, "mercado", tipoLista);
    }

    @Override
    public List<Mercado> listarTodosExceptoPropios(String idUsuario) {
        Type tipoLista = new TypeToken<ArrayList<Mercado>>() {
        }.getType();
        List<Mercado> lista = JsonUtils.leerListaDesdeJson(rutaArchivo, "mercado", tipoLista);
        for (Mercado mercado : lista) {
            if (Objects.equals(mercado.getVendedorId(), idUsuario)) {
                lista.remove(mercado);
            }
        }
        return lista;
    }

    @Override
    public List<Mercado> listarPropios(String idUsuario) {
        Type tipoLista = new TypeToken<ArrayList<Mercado>>() {
        }.getType();
        List<Mercado> lista = JsonUtils.leerListaDesdeJson(rutaArchivo, "mercado", tipoLista);
        for (Mercado mercado : lista) {
            if (!Objects.equals(mercado.getVendedorId(), idUsuario)) {
                lista.remove(mercado);
            }
        }
        return lista;
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
        JsonUtils.escribirListaEnJson(rutaArchivo, "mercado", mercados);
    }

    @Override
    public void eliminarPorId(String id) {
        List<Mercado> mercados = listarTodos();
        if (mercados.removeIf(m -> m.getId().equals(id))) {
            guardarTodos(mercados);
        }
    }
}