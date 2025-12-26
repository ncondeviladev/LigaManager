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
    public static void mostrarEquipos(){

        List<Usuario> listausu = usuarioDAO.listarTodos();

        List<Equipo> listaequ = equipoDAO.listarTodos();

        for (Equipo e : listaequ) {
            Usuario usuarioAsignado = null;

            for (Usuario u : listausu) {
                if (u.getIdEquipo() == e.getId()) {
                    usuarioAsignado = u;
                    break;
                }
            }

            if (usuarioAsignado != null) {
                System.out.println(
                        "Equipo: " + e.getNombre() +
                                " | Usuario: " + usuarioAsignado.getEmail() +
                                " (ID: " + usuarioAsignado.getId() + ")"
                );
            } else {
                System.out.println(
                        "Equipo: " + e.getNombre() + " | Usuario: Sin asignar"
                );
            }
        }
    }
}
