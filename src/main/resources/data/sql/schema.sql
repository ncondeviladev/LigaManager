-- Eliminar tablas si existen (orden inverso por claves foráneas)
DROP TABLE IF EXISTS mercado CASCADE;

DROP TABLE IF EXISTS goles CASCADE;

DROP TABLE IF EXISTS partidos CASCADE;

DROP TABLE IF EXISTS jornadas CASCADE;

DROP TABLE IF EXISTS alineaciones_detalles CASCADE;

DROP TABLE IF EXISTS usuarios CASCADE;

DROP TABLE IF EXISTS jugadores CASCADE;

DROP TABLE IF EXISTS equipos CASCADE;

-- 1. Tabla Equipos
CREATE TABLE equipos (
    id VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    puntos INT DEFAULT 0,
    gf INT DEFAULT 0, -- Goles a favor
    gc INT DEFAULT 0 -- Goles en contra
);

-- 2. Tabla Jugadores
CREATE TABLE jugadores (
    id VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    posicion VARCHAR(20) NOT NULL, -- PORTERO, DEFENSA, MEDIO, DELANTERO
    id_equipo VARCHAR(50),
    ataque INT DEFAULT 0,
    defensa INT DEFAULT 0,
    pase INT DEFAULT 0,
    porteria INT DEFAULT 0,
    estado_forma INT DEFAULT 100, -- condition en JSON
    precio DECIMAL(15, 2) DEFAULT 0.0,
    FOREIGN KEY (id_equipo) REFERENCES equipos (id) ON DELETE SET NULL
);

-- 3. Tabla Usuarios
CREATE TABLE usuarios (
    id VARCHAR(50) PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    tipo VARCHAR(20) NOT NULL, -- ADMIN, ESTANDAR
    saldo DECIMAL(15, 2) DEFAULT 0.0,
    id_equipo VARCHAR(50), -- Equipo gestionado por el usuario
    formacion VARCHAR(10), -- Ej: "4-4-2"
    FOREIGN KEY (id_equipo) REFERENCES equipos (id) ON DELETE SET NULL
);

-- 4. Tabla Alineaciones (Detalles)
-- Relaciona usuarios con los jugadores que han alineado y su posición específica en el campo
CREATE TABLE alineaciones_detalles (
    usuario_id VARCHAR(50) NOT NULL,
    jugador_id VARCHAR(50) NOT NULL,
    posicion_campo VARCHAR(20), -- La posición donde lo ha colocado (puede ser distinta a la natural del jugador)
    PRIMARY KEY (usuario_id, jugador_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios (id) ON DELETE CASCADE,
    FOREIGN KEY (jugador_id) REFERENCES jugadores (id) ON DELETE CASCADE
);

-- 5. Tabla Jornadas
CREATE TABLE jornadas (
    id VARCHAR(50) PRIMARY KEY,
    numero INT NOT NULL UNIQUE
);

-- 6. Tabla Partidos
CREATE TABLE partidos (
    id VARCHAR(50) PRIMARY KEY,
    jornada_id VARCHAR(50) NOT NULL,
    equipo_local_id VARCHAR(50) NOT NULL,
    equipo_visitante_id VARCHAR(50) NOT NULL,
    goles_local INT DEFAULT 0,
    goles_visitante INT DEFAULT 0,
    FOREIGN KEY (jornada_id) REFERENCES jornadas (id) ON DELETE CASCADE,
    FOREIGN KEY (equipo_local_id) REFERENCES equipos (id) ON DELETE CASCADE,
    FOREIGN KEY (equipo_visitante_id) REFERENCES equipos (id) ON DELETE CASCADE
);

-- 7. Tabla Goles
-- Registra los goles de cada partido
CREATE TABLE goles (
    id SERIAL PRIMARY KEY,
    partido_id VARCHAR(50) NOT NULL,
    jugador_id VARCHAR(50), -- Puede ser NULL si es autogol o desconodico 
    minuto INT NOT NULL,
    FOREIGN KEY (partido_id) REFERENCES partidos (id) ON DELETE CASCADE,
    FOREIGN KEY (jugador_id) REFERENCES jugadores (id) ON DELETE SET NULL
);

-- 8. Tabla Mercado
-- Ofertas de jugadores en venta
CREATE TABLE mercado (
    id VARCHAR(50) PRIMARY KEY,
    jugador_id VARCHAR(50) NOT NULL,
    vendedor_id VARCHAR(50) NOT NULL, -- Usuario que vende
    precio_venta DECIMAL(15, 2) NOT NULL,
    FOREIGN KEY (jugador_id) REFERENCES jugadores (id) ON DELETE CASCADE,
    FOREIGN KEY (vendedor_id) REFERENCES usuarios (id) ON DELETE CASCADE
);