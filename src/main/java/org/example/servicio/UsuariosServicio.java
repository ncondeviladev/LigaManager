package org.example.servicio;

import org.example.modelos.Equipo;
import org.example.modelos.Usuario;
import org.example.repositorios.dao.UsuarioDAO;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;

import java.util.List;


public class UsuariosServicio {

    private static final LigaRepo repo = RepoFactory.getRepositorio("JSON");

    // DAOs instanciados al inicio para uso en toda la clase
    private static final UsuarioDAO usuarioDAO = repo.getUsuarioDAO();

    public static String mostrarUsuarios() {

        LigaRepo ligarepo = RepoFactory.getRepositorio("JSON");
        List<Usuario> listaUsuarios = ligarepo.getUsuarioDAO().listarTodos();
        List<Equipo> listaEquipos = ligarepo.getEquipoDAO().listarTodos();
        StringBuilder sb = new StringBuilder();

        // Cabecera de la tabla
        sb.append(String.format("%-5s %-25s %-20s%n", "ID", "EMAIL", "EQUIPO"));
        sb.append("----------------------------------------------------------\n");

        // Contenido de la tabla
        for (Usuario u : listaUsuarios) {
            String nombreEquipo = "Sin equipo";

            // Buscar nombre del equipo por id
            for (Equipo e : listaEquipos) {
                if (e.getId() == u.getIdEquipo()) {
                    nombreEquipo = e.getNombre();
                    break;
                }
            }

            sb.append(String.format(
                    "%-5d %-25s %-20s%n",
                    u.getId(),
                    u.getEmail(),
                    nombreEquipo
            ));
        }

        return sb.toString();
    }

    public static void borrarUsuario(String usuario) {
        usuarioDAO.eliminarPorId(usuario);
        
    }
}
