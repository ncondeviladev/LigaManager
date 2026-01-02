package org.example.servicio;

import org.example.utils.TextTable;
import org.example.utils.dataUtils.DataAccessException;
import org.example.modelos.Jugador;
import org.example.modelos.Mercado;
import org.example.modelos.Usuario;
import org.example.repositorios.dao.*;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;

import java.text.NumberFormat;
import java.util.*;

public class MercadoServicio {
    private static final LigaRepo repo = RepoFactory.getRepositorio(System.getProperty("TIPO_DATOS", "JSON"));

    // DAOs instanciados al inicio para uso en toda la clase
    private static final UsuarioDAO usuarioDAO = repo.getUsuarioDAO();
    private static final EquipoDAO equipoDAO = repo.getEquipoDAO();
    private static final MercadoDAO mercadoDAO = repo.getMercadoDAO();
    private static final JugadorDAO jugadorDAO = repo.getJugadorDAO();
    private static final JornadaDAO jornadaDAO = repo.getJornadaDAO();

    public static int comprarJugador(Usuario usuario, String id) {
        try {
            // Comprueba que el jugador no lo este vendiendo el propio usuario
            if (mercadoDAO.buscarPorId(id).isPresent()
                    && !mercadoDAO.buscarPorId(id).get().getVendedorId().equals(usuario.getId())) {
                // Comprueba que el usuario tenga saldo suficiente
                if (usuario.getSaldo() >= mercadoDAO.buscarPorId(id).get().getPrecioVenta()) {
                    // Comprueba que el equipo del comprador no este lleno
                    if (jugadorDAO.buscarPorIdEquipo(usuario.getIdEquipo()).size() == 22) {
                        return 2;
                    } else {
                        usuario.setSaldo(usuario.getSaldo() - mercadoDAO.buscarPorId(id).get().getPrecioVenta());
                        if (usuarioDAO.buscarPorId(mercadoDAO.buscarPorId(id).get().getVendedorId()).isPresent()) {
                            Usuario vendedor = usuarioDAO.buscarPorId(mercadoDAO.buscarPorId(id).get().getVendedorId())
                                    .get();
                            vendedor.setSaldo(vendedor.getSaldo() + mercadoDAO.buscarPorId(id).get().getPrecioVenta());
                            usuarioDAO.guardar(vendedor);
                        }
                        Jugador jugador = jugadorDAO.buscarPorId(mercadoDAO.buscarPorId(id).get().getJugadorId()).get();
                        jugador.setIdEquipo(usuario.getIdEquipo());
                        jugadorDAO.guardar(jugador);
                        usuarioDAO.guardar(usuario);
                        mercadoDAO.eliminarPorId(id);
                        return 1;
                    }
                } else
                    return 4;
            } else
                return 3;
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return 3;
        }
    }

    public static int venderJugador(Usuario usuario, String id, double precio) {
        List<Mercado> mercados = mercadoDAO.listarTodos();

        if (jugadorDAO.buscarPorId(id).isEmpty()) {
            return 3;
        }

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
            // Comprueba que el jugador sea propiedad del usuario que quiere venderlo y que
            // el precio no sea negativo
            if (Objects.equals(usuario.getIdEquipo(), jugadorDAO.buscarPorId(id).get().getIdEquipo())) {
                if (precio >= 0) {
                    // Comprueba que el jugador que quieres vender no este actualmente en tu
                    // alineación
                    for (Jugador jugador : jugadorDAO.buscarPorIdEquipo(usuario.getIdEquipo())) {
                        for (String idJugador : usuario.getAlineacion().getJugadores()) {
                            if (jugador.getId().equals(idJugador)) {
                                return 2;
                            }
                        }
                    }
                    Mercado mercado = new Mercado(nuevoId, id, usuario.getId(), precio);
                    mercadoDAO.guardar(mercado);
                    Jugador jugador = jugadorDAO.buscarPorId(id).get();
                    jugador.setIdEquipo("T00");
                    jugadorDAO.guardar(jugador);
                    return 0;
                } else
                    return 4;
            } else
                return 1;

        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return 5;
        }
    }

    public static String jugadoresEnVentaDeUsuario(Usuario usuario) {
        try {
            List<Mercado> mercados = mercadoDAO.listarPropios(usuario.getId());

            if (mercados == null || mercados.isEmpty()) {
                return "No tienes jugadores en venta actualmente.";
            }

            TextTable table = new TextTable(
                    "ID Mercado",
                    "Jugador",
                    "Vendedor",
                    "Precio (M€)");

            table.setAlign("Precio (M€)", TextTable.Align.RIGHT);

            for (Mercado m : mercados) {

                // Obtener jugador desde repositorio
                Optional<Jugador> jugador = jugadorDAO.buscarPorId(m.getJugadorId());
                String nombreJugador = (jugador.isPresent())
                        ? jugador.get().getNombre()
                        : "Desconocido";

                // Obtener usuario vendedor desde repositorio
                Optional<Usuario> vendedor = usuarioDAO.buscarPorId(m.getVendedorId());
                String nombreVendedor = (vendedor.isPresent())
                        ? vendedor.get().getEmail() // o getNombre() si existe
                        : "Desconocido";

                table.addRow(
                        m.getId(),
                        nombreJugador,
                        nombreVendedor,
                        String.format("%.2f", m.getPrecioVenta()));
            }

            return table.toString();

        } catch (DataAccessException e) {
            return (e.getMessage());
        }
    }

    public static String jugadoresEnVenta(Usuario usuario) {
        try {
            List<Mercado> mercados = mercadoDAO.listarTodosExceptoPropios(usuario.getId());

            if (mercados == null || mercados.isEmpty()) {
                return "No hay jugadores disponibles en el mercado actualmente.";
            }

            TextTable table = new TextTable(
                    "ID Mercado",
                    "Jugador",
                    "Vendedor",
                    "Precio (M€)");

            table.setAlign("Precio (M€)", TextTable.Align.RIGHT);

            for (Mercado m : mercados) {

                // Resolver jugador
                Optional<Jugador> jugador = jugadorDAO.buscarPorId(m.getJugadorId());
                String nombreJugador = (jugador.isPresent())
                        ? jugador.get().getNombre()
                        : "Desconocido";

                // Resolver vendedor
                Optional<Usuario> vendedor = usuarioDAO.buscarPorId(m.getVendedorId());
                String nombreVendedor = (vendedor.isPresent())
                        ? vendedor.get().getEmail() // o nombre si existiera
                        : "Desconocido";

                table.addRow(
                        m.getId(),
                        nombreJugador,
                        nombreVendedor,
                        String.format("%.2f", m.getPrecioVenta()));
            }

            return table.toString();

        } catch (DataAccessException e) {
            return (e.getMessage());
        }
    }

}
