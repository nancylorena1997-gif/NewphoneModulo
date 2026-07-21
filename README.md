# NewPhone Modulo

Aplicación de escritorio para gestionar la información de una tienda de celulares. Fue desarrollada con **Java Swing**, **JDBC** y una base de datos **SQLite** que queda guardada dentro del mismo proyecto.

La ventana principal usa un diseño **MDI** (ventanas internas), y desde el menú se pueden abrir los módulos de cuentas, clientes, productos, pedidos, carritos, reportes, entre otros. Cada módulo permite **crear, consultar, actualizar y eliminar** registros.

## Tecnologías usadas

- Java 17 o superior
- Java Swing (interfaz gráfica)
- JDBC (conexión a base de datos)
- SQLite (base de datos local)
- Maven (opcional, para compilar y ejecutar)

## Requisitos

Antes de empezar, asegúrate de tener instalado:

1. **JDK 17** o una versión más reciente.
2. Un IDE como **NetBeans**, **IntelliJ** o **VS Code** (recomendado NetBeans si ya lo usas en el SENA).
3. **Maven** (opcional). Si no lo tienes, puedes abrir y ejecutar el proyecto directamente desde el IDE.

Para comprobar que Java está instalado, abre una terminal y escribe:

```bash
java -version
```

## Instalación

1. Descarga o clona este proyecto en tu computadora.
2. Abre la carpeta `NewphoneModulo` con tu IDE.
3. Si usas Maven, el IDE descargará automáticamente la dependencia de SQLite (`sqlite-jdbc`).
4. La primera vez que ejecutes la aplicación, se creará sola la base de datos en:

```
database/newphone.db
```

No necesitas instalar MySQL ni configurar un servidor. Todo queda en ese archivo.

## Cómo ejecutar el proyecto

### Opción 1: Desde NetBeans o tu IDE (la más fácil)

1. Abre el proyecto.
2. Busca la clase principal: `com.newphone.newphonemodulo.NewphoneModulo`
3. Haz clic derecho y selecciona **Run** o **Ejecutar**.

### Opción 2: Con Maven

Abre una terminal dentro de la carpeta del proyecto y ejecuta:

```bash
mvn compile exec:java
```

### Opción 3: Desde la terminal (sin Maven)

Si ya compilaste el proyecto y tienes la carpeta `target/classes`, puedes ejecutar:

**Windows:**

```powershell
java -cp "target/classes;lib/sqlite-jdbc-3.49.1.0.jar" com.newphone.newphonemodulo.NewphoneModulo
```

**Linux / macOS:**

```bash
java -cp "target/classes:lib/sqlite-jdbc-3.49.1.0.jar" com.newphone.newphonemodulo.NewphoneModulo
```

## Estructura básica del proyecto

```
NewphoneModulo/
├── database/              # Aquí se guarda newphone.db
├── src/main/java/         # Código Java
│   └── com/newphone/newphonemodulo/
│       ├── dao/           # Consultas e inserciones JDBC
│       ├── database/      # Conexión e inicialización de SQLite
│       ├── model/         # Clases del modelo (Cliente, Producto, etc.)
│       └── ui/            # Ventanas Swing y menús MDI
├── src/main/resources/database/
│   ├── schema.sql         # Script de tablas adaptado a SQLite
│   └── seed.sql           # Datos de ejemplo
└── pom.xml                # Configuración Maven
```

## Menús de la aplicación

- **Usuarios:** cuentas, clientes, administradores
- **Catálogo:** categorías y productos
- **Compras:** carritos, detalle de carrito y favoritos
- **Pedidos:** pedidos, detalle, envíos, facturas y pagos
- **Atención:** reseñas y tickets de atención al cliente
- **Reportes:** reportes de venta

## Notas

- Las fechas se manejan en formato `AAAA-MM-DD` (por ejemplo: `2026-07-21`).
- Si borras `database/newphone.db`, la aplicación la vuelve a crear al iniciar.
- El script original estaba en MySQL; en este proyecto fue adaptado para SQLite.

## Autor

Proyecto académico – SENA
