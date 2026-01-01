package org.example.servicio;

import org.example.modelos.Equipo;
import org.example.modelos.Usuario;
import org.example.repositorios.dao.UsuarioDAO;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;
import org.example.utils.TextTable;
import org.example.utils.dataUtils.DataAccessException;

import java.util.List;
import java.util.Objects;


public class UsuariosServicio {

    private static final LigaRepo repo = RepoFactory.getRepositorio("JSON");

    // DAOs instanciados al inicio para uso en toda la clase
    private static final UsuarioDAO usuarioDAO = repo.getUsuarioDAO();

    public static String mostrarUsuarios() {
        try {
            LigaRepo ligarepo = RepoFactory.getRepositorio("JSON");
            List<Usuario> listaUsuarios = ligarepo.getUsuarioDAO().listarTodos();
            List<Equipo> listaEquipos = ligarepo.getEquipoDAO().listarTodos();

            // Crear la tabla con padding y cabeceras
            TextTable table = new TextTable(1, "ID", "EMAIL", "EQUIPO");

            // Alinear ID a la derecha
            table.setAlign("ID", TextTable.Align.RIGHT);

            for (Usuario u : listaUsuarios) {
                String nombreEquipo = "Sin equipo";

                // Buscar el equipo asignado al usuario
                for (Equipo e : listaEquipos) {
                    if (u.getIdEquipo() != null &&
                            Objects.equals(u.getIdEquipo(), String.valueOf(e.getId()))) {
                        nombreEquipo = e.getNombre();
                        break;
                    }
                }

                // Añadir fila a la tabla
                table.addRow(
                        String.valueOf(u.getId()),
                        u.getEmail(),
                        nombreEquipo
                );
            }

            // Devolver la tabla en formato texto
            return table.toString();
        } catch (DataAccessException e) {
            return(e.getMessage());
        }
    }


    public static void borrarUsuario(String usuario) {
        try {
            usuarioDAO.eliminarPorId(usuario);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
    }
}
