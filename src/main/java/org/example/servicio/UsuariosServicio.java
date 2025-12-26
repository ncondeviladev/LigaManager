package org.example.servicio;

import org.example.modelos.Equipo;
import org.example.modelos.Usuario;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;

import java.util.List;

public class UsuariosServicio {

    public static void mostrarUsuarios() {

        LigaRepo ligarepo = RepoFactory.getRepositorio("JSON");

        List<Usuario> listausu = ligarepo.getUsuarioDAO().listarTodos();

        List<Equipo> listaequ = ligarepo.getEquipoDAO().listarTodos();

        for (Usuario u : listausu) {
            String nombreEquipo = "Sin equipo";

            for (Equipo e : listaequ) {
                if (e.getId() == u.getIdEquipo()) {
                    nombreEquipo = e.getNombre();
                    break;
                }
            }

            System.out.println(u.getEmail() + " - " + nombreEquipo);
        }
    }

    public static void borrarUsuario(String usuario) {
        
    }
}
