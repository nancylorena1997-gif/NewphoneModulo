INSERT OR IGNORE INTO cuenta (idcuenta, contrasena, email) VALUES
  (1, 'admin123', 'admin@newphone.com'),
  (2, 'cliente123', 'cliente@newphone.com');

INSERT OR IGNORE INTO administrador (cedula, nombre, permisos, telefono, cuenta_idcuenta) VALUES
  (1001, 'Ana Administradora', 'TOTAL', '3001001001', 1);

INSERT OR IGNORE INTO cliente (cedula, nombre, telefono, registro, cuenta_idcuenta) VALUES
  (2001, 'Carlos Cliente', '3002002001', '2026-01-15', 2);

INSERT OR IGNORE INTO categoria (id_categoria, nombre) VALUES
  (1, 'Smartphones'),
  (2, 'Accesorios');

INSERT OR IGNORE INTO producto (idproducto, nombre, precio, stock, descripcion, administrador_cedula, categoria_id_categoria) VALUES
  (1, 'NewPhone X Pro', 1299.99, 25, 'Smartphone gama alta con pantalla AMOLED', 1001, 1),
  (2, 'Funda Silicona', 19.99, 100, 'Funda protectora universal', 1001, 2);
