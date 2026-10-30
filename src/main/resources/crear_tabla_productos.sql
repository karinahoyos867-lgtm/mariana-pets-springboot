-- Mariana Pets: estructura inicial del inventario.
-- Ejecutar en la base de datos mariana_pets.
CREATE TABLE IF NOT EXISTS mariana_pets.productos (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  codigo VARCHAR(50) NOT NULL,
  nombre VARCHAR(120) NOT NULL,
  descripcion TEXT NULL,
  categoria VARCHAR(80) NOT NULL,
  proveedor VARCHAR(120) NOT NULL,
  cantidad INT UNSIGNED NOT NULL DEFAULT 0,
  stock_minimo INT UNSIGNED NOT NULL DEFAULT 0,
  precio DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  fecha_ingreso DATE NOT NULL,
  fecha_vencimiento DATE NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_productos_codigo (codigo),
  CONSTRAINT chk_productos_precio_no_negativo CHECK (precio >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
