package org.example.servicio;

import org.example.modelos.Alineacion;
import org.example.modelos.Jugador;
import org.example.modelos.Mercado;
import org.example.modelos.Usuario;
import org.example.modelos.enums.Posicion;
import org.example.modelos.enums.TipoUsuario;
import org.example.repositorios.dao.*;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;
import org.example.utils.SeguridadUtils;

import java.util.*;

import static org.example.modelos.enums.Formacion.F442;

/**
 * Servicio principal de la aplicación LigaManager.
 * Gestiona la lógica de negocio relacionada con autenticación y operaciones de
 * usuario.
 * Actúa como intermediario entre la capa de presentación y la capa de datos.
 */
public class AppServicio {

    private static final LigaRepo repo = RepoFactory.getRepositorio("JSON");

    // DAOs instanciados al inicio para uso en toda la clase
    private static final UsuarioDAO usuarioDAO = repo.getUsuarioDAO();
    private static final EquipoDAO equipoDAO = repo.getEquipoDAO();
    private static final MercadoDAO mercadoDAO = repo.getMercadoDAO();
    private static final JugadorDAO jugadorDAO = repo.getJugadorDAO();
    private static final JornadaDAO jornadaDAO = repo.getJornadaDAO();


    /**
     * Autentica un usuario en el sistema.
     * Verifica que el email exista y que el hash de la contraseña coincida.
     *
     * @param email    Correo electrónico del usuario.
     * @param password Contraseña del usuario (texto plano).
     * @return Optional con el Usuario si la autenticación es exitosa, o vacío si
     *         falla.
     */
    public static Optional<Usuario> login(String email, String password) {
        // Usamos directamente el atributo de clase
        Optional<Usuario> usuarioOpt = usuarioDAO.buscarPorEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // Calculamos el hash de la contraseña introducida
            String passwordHash = SeguridadUtils.hashPassword(password);

            // Comparamos los hashes (hashPassword nunca devuelve null)
            if (passwordHash.equals(usuario.getPassword())) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

    public static void crearUsuario(String email, String password, TipoUsuario tipoUsuario, String idEquipo) {

        LigaRepo ligarepo = RepoFactory.getRepositorio("JSON");

        List<Usuario> usuarios = usuarioDAO.listarTodos();

        Set<Integer> usados = new HashSet<>();

        // Extraer los números usados
        for (Usuario u : usuarios) {
            String usuarioId = u.getId();
            int numero = Integer.parseInt(usuarioId.substring(1));
            usados.add(numero);
        }

        // Buscar el primer número libre
        int nuevoNumero = 1;
        while (usados.contains(nuevoNumero)) {
            nuevoNumero++;
        }

        // Formatear el nuevo ID
        String idUsuario = String.format("U%04d", nuevoNumero);

        List<Jugador> jugadores = ligarepo.getJugadorDAO().buscarPorIdEquipo(idEquipo);

        String portero = null;
        for (Jugador jugador : jugadores) {
            if (jugador.getPosicion().equals(Posicion.PORTERO)) {
                portero = jugador.getId();
            } else {
                break;
            }
        }

        List<String> defensas = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            if (defensas.size() == 4) break;

            if (jugador.getPosicion().equals(Posicion.DEFENSA)) {
                defensas.add(jugador.getId());
            }
        }

        List<String> medios = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            if (medios.size() == 4) break;

            if (jugador.getPosicion().equals(Posicion.MEDIO)) {
                medios.add(jugador.getId());
            }
        }

        List<String> delanteros = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            if (delanteros.size() == 2) break;

            if (jugador.getPosicion().equals(Posicion.DELANTERO)) {
                delanteros.add(jugador.getId());
            }
        }

        Alineacion alineacionnew = new Alineacion(F442, portero, defensas, medios, delanteros);

        String contrasenya = SeguridadUtils.hashPassword(password);

        Usuario usuario = new Usuario(idUsuario, email, contrasenya, tipoUsuario, 100000.0, alineacionnew, idEquipo);

        usuarioDAO.guardar(usuario);
    }
}
