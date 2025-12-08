package org.example.servicio;

import org.example.modelos.Usuario;
import org.example.repositorios.dao.*;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;
import org.example.utils.SeguridadUtils;

import java.util.Optional;

/**
 * Servicio principal de la aplicación LigaManager.
 * Gestiona la lógica de negocio relacionada con autenticación y operaciones de
 * usuario.
 * Actúa como intermediario entre la capa de presentación y la capa de datos.
 */
public class LigaServicio {

    private final LigaRepo repo;

    // DAOs instanciados al inicio para uso en toda la clase
    private final UsuarioDAO usuarioDAO;
    private final EquipoDAO equipoDAO;
    private final MercadoDAO mercadoDAO;
    private final JugadorDAO jugadorDAO;
    private final JornadaDAO jornadaDAO;

    /**
     * Constructor que inicializa el repositorio y los DAOs necesarios.
     */
    public LigaServicio() {
        this.repo = RepoFactory.getRepositorio();

        // Inicializamos las referencias a los DAOs
        this.usuarioDAO = repo.getUsuarioDAO();
        this.equipoDAO = repo.getEquipoDAO();
        this.mercadoDAO = repo.getMercadoDAO();
        this.jugadorDAO = repo.getJugadorDAO();
        this.jornadaDAO = repo.getJornadaDAO();
    }

    /**
     * Autentica un usuario en el sistema.
     * Verifica que el email exista y que el hash de la contraseña coincida.
     *
     * @param email    Correo electrónico del usuario.
     * @param password Contraseña del usuario (texto plano).
     * @return Optional con el Usuario si la autenticación es exitosa, o vacío si
     *         falla.
     */
    public Optional<Usuario> login(String email, String password) {
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
}
