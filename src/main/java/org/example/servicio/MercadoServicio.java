package org.example.servicio;

import org.example.modelos.Mercado;
import org.example.modelos.Usuario;
import org.example.repositorios.dao.*;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;

import java.text.NumberFormat;
import java.util.List;

public class MercadoServicio {
    private static final LigaRepo repo = RepoFactory.getRepositorio("JSON");

    // DAOs instanciados al inicio para uso en toda la clase
    private static final UsuarioDAO usuarioDAO = repo.getUsuarioDAO();
    private static final EquipoDAO equipoDAO =  repo.getEquipoDAO();
    private static final MercadoDAO mercadoDAO = repo.getMercadoDAO();
    private static final JugadorDAO jugadorDAO = repo.getJugadorDAO();
    private static final JornadaDAO jornadaDAO  = repo.getJornadaDAO();

    public static int comprarJugador(Usuario usuario, String id) {
        mercadoDAO.eliminarPorId(id);
    }

    public static boolean venderJugador(Usuario usuario, String id, double precio) {
        int numMercados = usuarioDAO.listarTodos().toArray().length - 1;
        String idMercado = String.format("M%04d", numMercados);
        if (usuario.getIdEquipo())
        Mercado mercado = new Mercado(idMercado, id, usuario.getId(), precio);
        mercadoDAO.guardar(mercado);
    }

    public static List<Mercado> jugadoresEnVentaDeUsuario(Usuario usuario) {
        return mercadoDAO.listarPropios(usuario.getId());
    }

    public static List<Mercado> jugadoresEnVenta(Usuario usuario) {
        return mercadoDAO.listarTodosExceptoPropios(usuario.getId());
    }
}
