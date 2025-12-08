package org.example.repositorios.repo;

import org.example.utils.dataUtils.DataAccessException;

/**
 * Factoría de Repositorios (Pattern Factory).
 * Se encarga de instanciar y devolver la implementación correcta de
 * {@link LigaRepo}
 * basándose en la configuración actual del sistema (JSON o SQL).
 * 
 * Permite cambiar la fuente de datos de toda la aplicación modificando un solo
 * punto.
 */
public class RepoFactory {

    private static final String TIPO_ACCESO = "JSON";

    /**
     * Obtiene la instancia del repositorio configurada.
     *
     * @return Instancia de LigaRepo (JSON, SQL).
     * @throws DataAccessException Si el tipo de acceso configurado no es válido o
     *                             no está soportado.
     */
    public static LigaRepo getRepositorio() {
        switch (TIPO_ACCESO) {
            case "JSON":
                return new LigaRepoImplJSON();
            case "SQL":
                // Futura implementación de SQL
                throw new DataAccessException("El soporte para SQL aún no está implementado.");
            default:
                throw new DataAccessException("Tipo de acceso desconocido en configuración: " + TIPO_ACCESO);
        }
    }
}
