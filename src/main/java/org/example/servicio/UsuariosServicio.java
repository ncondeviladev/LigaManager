package org.example.servicio;

import org.example.modelos.Alineacion;
import org.example.modelos.Jugador;
import org.example.modelos.Usuario;
import org.example.modelos.enums.Posicion;
import org.example.modelos.enums.TipoUsuario;
import org.example.repositorios.repo.LigaRepo;
import org.example.repositorios.repo.RepoFactory;

import java.util.ArrayList;
import java.util.List;

import static org.example.modelos.enums.Formacion.F442;
import static org.example.vistasycontroladores.vistas.menu.Menu.sc;

public class UsuariosServicio {

    public static void mostrarUsuarios() {
    }

    public static void crearUsuario(String email, String password, TipoUsuario tipoUsuario, String idEquipo) {

        LigaRepo ligarepo = RepoFactory.getRepositorio("JSON");

        int numusus = ligarepo.getUsuarioDAO().listarTodos().toArray().length;
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


        Usuario usuario = new Usuario(String.valueOf(numusus), email, password, tipoUsuario, 100000.0, alineacionnew, idEquipo);

    }

    public static void borrarUsuario(String usuario) {

    }
}
