package org.example.repositorios.repo;

import org.example.repositorios.dao.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//Se ordena la ejecucion para que primero testee los repos y luego la lectura de datos
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LigaRepoImplJSONTest {

    @Test
    @Order(1)
    void testRepositorioInicializacion() {
        System.out.println("Iniciando test de repositorio...");

        // Verificar que existen los ficheros antes de testear la lógica
        Path ruta = Paths.get("src", "main", "resources", "data", "json");
        System.out.println("Buscando ficheros en: " + ruta.toAbsolutePath());
        Assertions.assertTrue(Files.exists(ruta), "El directorio de datos debería existir en la ruta esperada");

        LigaRepo impl = new LigaRepoImplJSON();

        Assertions.assertNotNull(impl.getUsuarioDAO(), "El DAO de Usuario no debería ser null");
        Assertions.assertNotNull(impl.getEquipoDAO(), "El DAO de Equipo no debería ser null");
        Assertions.assertNotNull(impl.getJugadorDAO(), "El DAO de Jugador no debería ser null");
        Assertions.assertNotNull(impl.getMercadoDAO(), "El DAO de Mercado no debería ser null");
        Assertions.assertNotNull(impl.getJornadaDAO(), "El DAO de Jornada no debería ser null");
    }

    @Test
    @Order(2)
    void testLecturaDatos() {
        LigaRepo impl = new LigaRepoImplJSON();
        UsuarioDAO usuarioDAO = impl.getUsuarioDAO();
        EquipoDAO equipoDAO = impl.getEquipoDAO();
        JugadorDAO jugadorDAO = impl.getJugadorDAO();
        JornadaDAO jornadaDAO = impl.getJornadaDAO();

        // 1. Usuarios
        var usuarios = usuarioDAO.listarTodos();
        Assertions.assertNotNull(usuarios, "La lista de usuarios no debería ser null");
        System.out.println("Usuarios cargados: " + usuarios.size());

        // 2. Equipos
        var equipos = equipoDAO.listarTodos();
        Assertions.assertNotNull(equipos, "La lista de equipos no debería ser null");
        System.out.println("Equipos cargados: " + equipos.size());

        // 3. Jugadores
        var jugadores = jugadorDAO.listarTodos();
        Assertions.assertNotNull(jugadores, "La lista de jugadores no debería ser null");
        System.out.println("Jugadores cargados: " + jugadores.size());

        // 4. Jornadas (y Partidos anidados)
        var jornadas = jornadaDAO.listarTodas();
        Assertions.assertNotNull(jornadas, "La lista de jornadas no debería ser null");
        System.out.println("Jornadas cargadas: " + jornadas.size());

        if (!jornadas.isEmpty()) {
            System.out.println("Partidos en la primera jornada: " + jornadas.get(0).getPartidos().size());
        }
    }
}
