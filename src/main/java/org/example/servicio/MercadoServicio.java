package org.example.servicio;

import org.example.modelos.Jugador;
import org.example.modelos.Mercado;
import org.example.modelos.Usuario;
import org.example.repositorios.dao.*;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MercadoServicio {
    private static final LigaRepo repo = RepoFactory.getRepositorio("JSON");

    // DAOs instanciados al inicio para uso en toda la clase
    private static final UsuarioDAO usuarioDAO = repo.getUsuarioDAO();
    private static final EquipoDAO equipoDAO =  repo.getEquipoDAO();
    private static final MercadoDAO mercadoDAO = repo.getMercadoDAO();
    private static final JugadorDAO jugadorDAO = repo.getJugadorDAO();
    private static final JornadaDAO jornadaDAO  = repo.getJornadaDAO();

    public static int comprarJugador(Usuario usuario, String id) {
        try {
            //Comprueba que el jugador no lo este vendiendo el propio usuario
            if (mercadoDAO.buscarPorId(id) != null && !mercadoDAO.buscarPorId(id).get().getVendedorId().equals(usuario.getId())) {
                //Comprueba que el usuario tenga saldo suficiente
                if (usuario.getSaldo() >= mercadoDAO.buscarPorId(id).get().getPrecioVenta()) {
                    //Comprueba que el equipo del comprador no este lleno
                    if (jugadorDAO.buscarPorIdEquipo(usuario.getIdEquipo()).size() == 22) {
                        return 2;
                    } else {
                        usuario.setSaldo(usuario.getSaldo() - mercadoDAO.buscarPorId(id).get().getPrecioVenta());
                        Usuario vendedor = usuarioDAO.buscarPorId(mercadoDAO.buscarPorId(id).get().getVendedorId()).get();
                        vendedor.setSaldo(vendedor.getSaldo() + mercadoDAO.buscarPorId(id).get().getPrecioVenta());
                        Jugador jugador = jugadorDAO.buscarPorId(mercadoDAO.buscarPorId(id).get().getJugadorId()).get();
                        jugador.setIdEquipo(usuario.getIdEquipo());
                        jugadorDAO.guardar(jugador);
                        usuarioDAO.guardar(vendedor);
                        usuarioDAO.guardar(usuario);
                        mercadoDAO.eliminarPorId(id);
                        return 1;
                    }
                } else return 4;
            } else return 3;
        } catch (RuntimeException e) {
            return 3;
        }
    }


    public static boolean venderJugador(Usuario usuario, String id, double precio) {
        List<Mercado> mercados = mercadoDAO.listarTodos();

        Set<Integer> usados = new HashSet<>();

        // Extraer los números usados
        for (Mercado m : mercados) {
            String mercadoId = m.getId();
            int numero = Integer.parseInt(mercadoId.substring(1));
            usados.add(numero);
        }

        // Buscar el primer número libre
        int nuevoNumero = 1;
        while (usados.contains(nuevoNumero)) {
            nuevoNumero++;
        }

        // Formatear el nuevo ID
        String nuevoId = String.format("M%04d", nuevoNumero);

        try {
            //Comprueba que el jugador sea propiedad del usuario que quiere venderlo y que el precio no sea negativo
            if (Objects.equals(usuario.getIdEquipo(), jugadorDAO.buscarPorId(id).get().getIdEquipo()) && precio >= 0) {
                //Comprueba que el jugador que quieres vender no este actualmente en tu alineación
                for (Jugador jugador : jugadorDAO.buscarPorIdEquipo(usuario.getIdEquipo())) {
                    for (String idJugador : usuario.getAlineacion().getJugadores()) {
                        if (jugador.getId().equals(idJugador)) {
                            return false;
                        }
                    }
                }
                Mercado mercado = new Mercado(nuevoId, id, usuario.getId(), precio);
                mercadoDAO.guardar(mercado);
                Jugador jugador = jugadorDAO.buscarPorId(id).get();
                jugador.setIdEquipo("T00");
                jugadorDAO.guardar(jugador);
                return true;
            } else return false;

        } catch (RuntimeException e) {
            return false;
        }
    }

    public static List<Mercado> jugadoresEnVentaDeUsuario(Usuario usuario) {
        return mercadoDAO.listarPropios(usuario.getId());
    }

    public static List<Mercado> jugadoresEnVenta(Usuario usuario) {
        return mercadoDAO.listarTodosExceptoPropios(usuario.getId());
    }
}
