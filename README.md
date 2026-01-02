# Liga Manager - Memoria del Proyecto - Acceso a Datos

**Módulo:** Acceso a datos  
**Ciclo Formativo:** 2º Desarrollo de Aplicaciones Multiplataforma (DAM)  
**Proyecto:** Liga Manager  
**Autores:**

- Dayron Notario
- Ivan Frasquet
- Noé Conde

---

<div style="page-break-after: always;"></div>

## 0. Índice

1.  [Introducción General](#1-introducción-general)
2.  [Capa de Lógica de Negocio](#2-capa-de-lógica-de-negocio)
3.  [Capa de Acceso a Datos](#3-capa-de-acceso-a-datos)
4.  [Instalación y Despliegue](#4-instalación-y-despliegue)

---

<div style="page-break-after: always;"></div>

## 1. Introducción General

El proyecto **Liga Manager** es una aplicación Java para gestionar una liga de fútbol fantasy. Permite gestionar equipos, fichajes, alineaciones y simular jornadas. El sistema evoluciona de una persistencia básica en ficheros JSON a una base de datos relacional PostgreSQL, utilizando patrones de diseño profesionales.

---

## 2. Capa de Lógica de Negocio

-- A completar Dayron Ivan --

---

## 3. Capa de Acceso a Datos

Esta capa gestiona la persistencia de la información, permitiendo cambiar entre almacenamiento en ficheros y base de datos sin afectar a la lógica de negocio.

### 3.1. Arquitectura

- **Patrón DAO:** Interfaces (`EquipoDAO`, `JugadorDAO`, etc.) que definen las operaciones CRUD, ocultando los detalles de implementación (SQL o JSON).
- **Factory:** La clase `RepoFactory` decide qué implementación instanciar al inicio según la configuración.

### 3.2. Implementación Técnica

1.  **Modo JSON (Gson):**

    - Uso de librería Gson para serializar/deserializar objetos a ficheros de texto plano.
    - Ideal para desarrollo rápido.

2.  **Modo SQL (PostgreSQL + JDBC):**
    - **Base de Datos Relacional:** Esquema normalizado con claves foráneas para integridad referencial.
    - **JDBC Puro:** Control total de las consultas mediante `PreparedStatement` para seguridad y rendimiento.
    - **Manejo de Errores:** Excepción propia `DataAccessException` (Runtime) que encapsula `SQLException` para no ensuciar el código superior.
    - **Operaciones Seguras:** Uso de transacciones y sentencias `UPSERT` (`ON CONFLICT DO UPDATE`) para evitar duplicados.

### 3.3. Migración Automática

Se implementó un sistema en `AppServicio` para pasar los datos de JSON a SQL automáticamente:

- Si la base de datos está vacía al inicio, lee los JSON y los inserta en SQL.
- **Orden estricto:** Equipos -> Jugadores -> Usuarios -> Mercado -> Jornadas (para respetar claves foráneas).
- **Rollback:** Si algo falla, se borran los datos parciales para dejar la BD limpia.

---

## 4. Instalación y Despliegue

1.  **Base de Datos:**

    ```bash
    docker-compose up -d
    ```

2.  **Ejecutar:**
    ```bash
    ./gradlew run
    ```
    (Seleccionar opción 1 para JSON o 2 para SQL).

---

_Documento elaborado por Dayron Notario, Ivan Frasquet y Noé Conde - 2026_
