package org.example.servicio;

import org.example.modelos.competicion.Jornada;
import org.example.modelos.*;
import org.example.modelos.enums.Formacion;
import org.example.modelos.enums.Posicion;
import org.example.modelos.enums.TipoUsuario;
import org.example.repositorios.dao.*;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;
import org.example.utils.SeguridadUtils;
import org.example.utils.dataUtils.DataAccessException;

import java.util.*;

import static org.example.modelos.enums.Formacion.F442;

/**
 * Servicio principal de la aplicación LigaManager.
 * Gestiona la lógica de negocio relacionada con autenticación y operaciones de
 * usuario.
 * Actúa como intermediario entre la capa de presentación y la capa de datos.
 */
public class AppServicio {

    // Cambiamos el acceso a la base de datos a través de la factoría, json por
    // defecto
    private static final LigaRepo repo = RepoFactory.getRepositorio(System.getProperty("TIPO_DATOS", "JSON"));

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
        try {
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
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void inicializar() {
        // Metodo dummy para forzar la carga de la clase y la configuracion del
        // repositorio

        // Si estamos en modo SQL y no hay datos, migramos desde JSON
        if ("SQL".equalsIgnoreCase(System.getProperty("TIPO_DATOS"))) {
            migrarDatos();
        }
    }

    private static void migrarDatos() {
        boolean datosMigrados = false;
        try {
            System.out.println(">> Verificando estado de la base de datos para migración...");

            // Instanciamos el repo JSON
            LigaRepo repoJson = RepoFactory.getRepositorio("JSON");

            if (equipoDAO.listarTodos().isEmpty()) {
                System.out.println(">> Base de datos vacía. Iniciando migración COMPLETA desde JSON...");

                System.out.println("... Migrando Equipos desde JSON ...");
                equipoDAO.guardarTodos(repoJson.getEquipoDAO().listarTodos());

                System.out.println("... Migrando Jugadores desde JSON ...");
                jugadorDAO.guardarTodos(repoJson.getJugadorDAO().listarTodos());

                System.out.println("... Migrando Usuarios ...");
                usuarioDAO.guardarTodos(repoJson.getUsuarioDAO().listarTodos());

                System.out.println("... Migrando Mercado ...");
                List<Mercado> mercado = repoJson.getMercadoDAO().listarTodos();
                for (Mercado m : mercado) {
                    mercadoDAO.guardar(m);
                }

                System.out.println("... Migrando Jornadas ...");
                jornadaDAO.guardarTodas(repoJson.getJornadaDAO().listarTodas());

                System.out.println(">> Migración completada exitosamente.");
                datosMigrados = true;
            } else {
                System.out.println(">> La base de datos ya contiene datos. No se realiza migración.");
                datosMigrados = true;
            }

        } catch (Exception e) {
            System.err.println(">> ERROR CRÍTICO durante la migración: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (!datosMigrados) {
                System.err.println(">> La migración falló o quedó incompleta. Base de datos reiniciada...");
                borrarDatosParciales();
            }
        }
    }

    private static void borrarDatosParciales() {
        try {
            // Borrar en orden inverso a la inserción para respetar FKs

            List<Jornada> jornadas = jornadaDAO.listarTodas();
            jornadas.forEach(j -> jornadaDAO.eliminarPorId(j.getId()));

            List<Mercado> mercados = mercadoDAO.listarTodos();
            mercados.forEach(m -> mercadoDAO.eliminarPorId(m.getId()));

            List<Usuario> usuarios = usuarioDAO.listarTodos();
            usuarios.forEach(u -> usuarioDAO.eliminarPorId(u.getId()));

            List<Jugador> jugadores = jugadorDAO.listarTodos();
            jugadores.forEach(j -> jugadorDAO.eliminarPorId(j.getId()));

            List<Equipo> equipos = equipoDAO.listarTodos();
            equipos.forEach(eq -> equipoDAO.eliminarPorId(eq.getId()));

        } catch (Exception e) {
            System.err.println("Error durante el borrado de bd: " + e.getMessage());
        }
    }

    public static void crearUsuario(String email, String password, TipoUsuario tipoUsuario, String idEquipo) {
        try {
            // Comprobar que el equipo existe
            Optional<Equipo> equipoOpt = equipoDAO.buscarPorId(idEquipo);
            if (equipoOpt.isEmpty()) {
                System.out.println("El equipo seleccionado no existe.");
                return;
            }

            // 2Comprobar que el equipo no esté ya asignado
            if (!usuarioDAO.buscarPorIdEquipo(idEquipo).isEmpty()) {
                System.out.println("El equipo que has elegido ya pertenece a otro usuario.");
                return;
            }

            List<Usuario> usuarios = usuarioDAO.listarTodos();
            Set<Integer> usados = new HashSet<>();

            // Extraer los números usados de los IDs
            for (Usuario u : usuarios) {
                int numero = Integer.parseInt(u.getId().substring(1));
                usados.add(numero);
                if (Objects.equals(u.getEmail(), email)) {
                    System.out.println("El usuario existe en el sistema.");
                    return;
                }
            }

            // Buscar el primer número libre
            int nuevoNumero = 1;
            while (usados.contains(nuevoNumero)) {
                nuevoNumero++;
            }

            // Formatear ID del usuario
            String idUsuario = String.format("U%04d", nuevoNumero);

            // Obtener jugadores del equipo
            List<Jugador> jugadores = jugadorDAO.buscarPorIdEquipo(idEquipo);

            String portero = null;
            List<String> defensas = new ArrayList<>();
            List<String> medios = new ArrayList<>();
            List<String> delanteros = new ArrayList<>();

            for (Jugador jugador : jugadores) {
                switch (jugador.getPosicion()) {
                    case PORTERO -> portero = jugador.getId();
                    case DEFENSA -> {
                        if (defensas.size() < 4)
                            defensas.add(jugador.getId());
                    }
                    case MEDIO -> {
                        if (medios.size() < 4)
                            medios.add(jugador.getId());
                    }
                    case DELANTERO -> {
                        if (delanteros.size() < 2)
                            delanteros.add(jugador.getId());
                    }
                }
            }

            Alineacion alineacion = new Alineacion(Formacion.F442, portero, defensas, medios, delanteros);

            String contrasenya = SeguridadUtils.hashPassword(password);

            Usuario usuario = new Usuario(
                    idUsuario,
                    email,
                    contrasenya,
                    tipoUsuario,
                    100000.0,
                    alineacion,
                    idEquipo);

            usuarioDAO.guardar(usuario);

            System.out.println("Usuario creado correctamente.");
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
    }

}
