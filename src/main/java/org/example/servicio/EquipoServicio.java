package org.example.servicio;

import org.example.modelos.Equipo;
import org.example.modelos.Usuario;
import org.example.repositorios.dao.EquipoDAO;
import org.example.repositorios.dao.UsuarioDAO;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;

import java.util.List;
import java.util.Optional;


public class EquipoServicio {

    private static final LigaRepo repo = RepoFactory.getRepositorio("JSON");

    // DAOs instanciados al inicio para uso en toda la clase
    private static final EquipoDAO equipoDAO = repo.getEquipoDAO();
    private static final UsuarioDAO usuarioDAO = repo.getUsuarioDAO();

    //Debe mostrar una lista de los equipos junto a sus IDs
    public static String mostrarEquipos() {

        List<Usuario> listaUsuarios = usuarioDAO.listarTodos();
        List<Equipo> listaEquipos = equipoDAO.listarTodos();

        StringBuilder sb = new StringBuilder();

        // Cabecera de la tabla
        sb.append(String.format("%-20s %-25s %-5s%n", "EQUIPO", "USUARIO", "ID"));
        sb.append("-----------------------------------------------------------\n");

        // Contenido de la tabla
        for (Equipo e : listaEquipos) {
            Usuario usuarioAsignado = null;

            // Buscar usuario asignado al equipo
            for (Usuario u : listaUsuarios) {
                if (u.getIdEquipo() == e.getId()) {
                    usuarioAsignado = u;
                    break;
                }
            }

            if (usuarioAsignado != null) {
                sb.append(String.format(
                        "%-20s %-25s %-5d%n",
                        e.getNombre(),
                        usuarioAsignado.getEmail(),
                        usuarioAsignado.getId()
                ));
            } else {
                sb.append(String.format(
                        "%-20s %-25s %-5s%n",
                        e.getNombre(),
                        "Sin asignar",
                        "-"
                ));
            }
        }

        return sb.toString();
    }
}
