package org.example.servicio;

import org.example.modelos.Equipo;
import org.example.modelos.Usuario;
import org.example.repositorios.dao.EquipoDAO;
import org.example.repositorios.dao.UsuarioDAO;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;

import java.util.List;
import java.util.Objects;

import org.example.utils.TextTable;
import org.example.utils.dataUtils.DataAccessException;


public class EquipoServicio {

    private static final LigaRepo repo = RepoFactory.getRepositorio("JSON");

    // DAOs instanciados al inicio para uso en toda la clase
    private static final EquipoDAO equipoDAO = repo.getEquipoDAO();
    private static final UsuarioDAO usuarioDAO = repo.getUsuarioDAO();

    //Debe mostrar una lista de los equipos junto a sus IDs
    public static String mostrarEquipos() {
        try {

            List<Usuario> listaUsuarios = usuarioDAO.listarTodos();
            List<Equipo> listaEquipos = equipoDAO.listarTodos();

            // Crear tabla con padding 1 y cabeceras
            TextTable table = new TextTable(1, "ID", "EQUIPO", "USUARIO");

            // Alinear ID a la derecha (opcional, queda más bonito)
            table.setAlign("ID", TextTable.Align.RIGHT);

            for (Equipo e : listaEquipos) {
                Usuario usuarioAsignado = null;

                // Buscar si algún usuario tiene asignado este equipo
                for (Usuario u : listaUsuarios) {
                    if (u.getIdEquipo() != null &&
                            Objects.equals(u.getIdEquipo(), String.valueOf(e.getId()))) {
                        usuarioAsignado = u;
                        break;
                    }
                }

                // Añadir fila a la tabla
                if (usuarioAsignado != null) {
                    table.addRow(
                            String.valueOf(e.getId()),
                            e.getNombre(),
                            usuarioAsignado.getEmail()
                    );
                } else {
                    table.addRow(
                            String.valueOf(e.getId()),
                            e.getNombre(),
                            "Sin asignar"
                    );
                }
            }

            // Devolver la tabla en formato texto
            return table.toString();
        } catch (DataAccessException e) {
            return e.getMessage();
        }
    }

}
