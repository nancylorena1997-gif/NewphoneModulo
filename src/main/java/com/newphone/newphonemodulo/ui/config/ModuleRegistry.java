package com.newphone.newphonemodulo.ui.config;

import com.newphone.newphonemodulo.dao.AdministradorDao;
import com.newphone.newphonemodulo.dao.AtencionClienteDao;
import com.newphone.newphonemodulo.dao.CarritoDao;
import com.newphone.newphonemodulo.dao.CategoriaDao;
import com.newphone.newphonemodulo.dao.ClienteDao;
import com.newphone.newphonemodulo.dao.CuentaDao;
import com.newphone.newphonemodulo.dao.DetalleCarritoDao;
import com.newphone.newphonemodulo.dao.DetallePedidoDao;
import com.newphone.newphonemodulo.dao.EnvioDao;
import com.newphone.newphonemodulo.dao.FacturaDao;
import com.newphone.newphonemodulo.dao.PagoDao;
import com.newphone.newphonemodulo.dao.PedidoDao;
import com.newphone.newphonemodulo.dao.ProductoDao;
import com.newphone.newphonemodulo.dao.ProductoFavoritoDao;
import com.newphone.newphonemodulo.dao.ReporteVentaDao;
import com.newphone.newphonemodulo.dao.ResenaDao;
import com.newphone.newphonemodulo.model.Administrador;
import com.newphone.newphonemodulo.model.AtencionCliente;
import com.newphone.newphonemodulo.model.Carrito;
import com.newphone.newphonemodulo.model.Categoria;
import com.newphone.newphonemodulo.model.Cliente;
import com.newphone.newphonemodulo.model.Cuenta;
import com.newphone.newphonemodulo.model.DetalleCarrito;
import com.newphone.newphonemodulo.model.DetallePedido;
import com.newphone.newphonemodulo.model.Envio;
import com.newphone.newphonemodulo.model.Factura;
import com.newphone.newphonemodulo.model.Pago;
import com.newphone.newphonemodulo.model.Pedido;
import com.newphone.newphonemodulo.model.Producto;
import com.newphone.newphonemodulo.model.ProductoFavorito;
import com.newphone.newphonemodulo.model.ReporteVenta;
import com.newphone.newphonemodulo.model.Resena;
import com.newphone.newphonemodulo.ui.util.FormUtils;

import javax.swing.JComponent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class ModuleRegistry {

    private static final Map<String, ModuleDescriptor<?>> MODULES = new LinkedHashMap<>();

    static {
        registerCuentaModule();
        registerClienteModule();
        registerAdministradorModule();
        registerCategoriaModule();
        registerProductoModule();
        registerCarritoModule();
        registerDetalleCarritoModule();
        registerProductoFavoritoModule();
        registerPedidoModule();
        registerDetallePedidoModule();
        registerEnvioModule();
        registerFacturaModule();
        registerPagoModule();
        registerResenaModule();
        registerAtencionClienteModule();
        registerReporteVentaModule();
    }

    private ModuleRegistry() {
    }

    public static Map<String, ModuleDescriptor<?>> getModules() {
        return MODULES;
    }

    public static ModuleDescriptor<?> getModule(String key) {
        return MODULES.get(key);
    }

    private static void register(ModuleDescriptor<?> descriptor) {
        MODULES.put(descriptor.getKey(), descriptor);
    }

    private static void registerCuentaModule() {
        register(new ModuleDescriptor<>(
                "cuenta",
                "Gestion de Cuentas",
                new FieldConfig[]{
                        new FieldConfig("idCuenta", "ID Cuenta", FieldType.INTEGER, true, true),
                        new FieldConfig("contrasena", "Contrasena", FieldType.TEXT),
                        new FieldConfig("email", "Email", FieldType.TEXT)
                },
                new String[]{"ID", "Email"},
                new CuentaDao(),
                Cuenta::new,
                textBinder(
                        Map.of("idCuenta", (c, v) -> c.setIdCuenta(FormUtils.parseInteger(v)),
                                "contrasena", (c, v) -> c.setContrasena(v),
                                "email", (c, v) -> c.setEmail(v)),
                        Map.of("idCuenta", Cuenta::getIdCuenta,
                                "contrasena", Cuenta::getContrasena,
                                "email", Cuenta::getEmail)),
                cuenta -> new Object[]{cuenta.getIdCuenta(), cuenta.getEmail()},
                Cuenta::getIdCuenta));
    }

    private static void registerClienteModule() {
        register(new ModuleDescriptor<>(
                "cliente",
                "Gestion de Clientes",
                new FieldConfig[]{
                        new FieldConfig("cedula", "Cedula", FieldType.INTEGER, true, false),
                        new FieldConfig("nombre", "Nombre", FieldType.TEXT),
                        new FieldConfig("telefono", "Telefono", FieldType.TEXT),
                        new FieldConfig("registro", "Registro", FieldType.DATE),
                        new FieldConfig("cuentaIdCuenta", "ID Cuenta", FieldType.INTEGER)
                },
                new String[]{"Cedula", "Nombre", "Telefono", "Registro", "Cuenta"},
                new ClienteDao(),
                Cliente::new,
                textBinder(
                        Map.of("cedula", (c, v) -> c.setCedula(FormUtils.parseInteger(v)),
                                "nombre", Cliente::setNombre,
                                "telefono", Cliente::setTelefono,
                                "registro", Cliente::setRegistro,
                                "cuentaIdCuenta", (c, v) -> c.setCuentaIdCuenta(FormUtils.parseInteger(v))),
                        Map.of("cedula", Cliente::getCedula,
                                "nombre", Cliente::getNombre,
                                "telefono", Cliente::getTelefono,
                                "registro", Cliente::getRegistro,
                                "cuentaIdCuenta", Cliente::getCuentaIdCuenta)),
                cliente -> new Object[]{
                        cliente.getCedula(), cliente.getNombre(), cliente.getTelefono(),
                        cliente.getRegistro(), cliente.getCuentaIdCuenta()
                },
                Cliente::getCedula));
    }

    private static void registerAdministradorModule() {
        register(new ModuleDescriptor<>(
                "administrador",
                "Gestion de Administradores",
                new FieldConfig[]{
                        new FieldConfig("cedula", "Cedula", FieldType.INTEGER, true, false),
                        new FieldConfig("nombre", "Nombre", FieldType.TEXT),
                        new FieldConfig("permisos", "Permisos", FieldType.TEXT),
                        new FieldConfig("telefono", "Telefono", FieldType.TEXT),
                        new FieldConfig("cuentaIdCuenta", "ID Cuenta", FieldType.INTEGER)
                },
                new String[]{"Cedula", "Nombre", "Permisos", "Telefono", "Cuenta"},
                new AdministradorDao(),
                Administrador::new,
                textBinder(
                        Map.of("cedula", (a, v) -> a.setCedula(FormUtils.parseInteger(v)),
                                "nombre", Administrador::setNombre,
                                "permisos", Administrador::setPermisos,
                                "telefono", Administrador::setTelefono,
                                "cuentaIdCuenta", (a, v) -> a.setCuentaIdCuenta(FormUtils.parseInteger(v))),
                        Map.of("cedula", Administrador::getCedula,
                                "nombre", Administrador::getNombre,
                                "permisos", Administrador::getPermisos,
                                "telefono", Administrador::getTelefono,
                                "cuentaIdCuenta", Administrador::getCuentaIdCuenta)),
                administrador -> new Object[]{
                        administrador.getCedula(), administrador.getNombre(), administrador.getPermisos(),
                        administrador.getTelefono(), administrador.getCuentaIdCuenta()
                },
                Administrador::getCedula));
    }

    private static void registerCategoriaModule() {
        register(new ModuleDescriptor<>(
                "categoria",
                "Gestion de Categorias",
                new FieldConfig[]{
                        new FieldConfig("idCategoria", "ID Categoria", FieldType.INTEGER, true, true),
                        new FieldConfig("nombre", "Nombre", FieldType.TEXT)
                },
                new String[]{"ID", "Nombre"},
                new CategoriaDao(),
                Categoria::new,
                textBinder(
                        Map.of("idCategoria", (c, v) -> c.setIdCategoria(FormUtils.parseInteger(v)),
                                "nombre", Categoria::setNombre),
                        Map.of("idCategoria", Categoria::getIdCategoria,
                                "nombre", Categoria::getNombre)),
                categoria -> new Object[]{categoria.getIdCategoria(), categoria.getNombre()},
                Categoria::getIdCategoria));
    }

    private static void registerProductoModule() {
        register(new ModuleDescriptor<>(
                "producto",
                "Gestion de Productos",
                new FieldConfig[]{
                        new FieldConfig("idProducto", "ID Producto", FieldType.INTEGER, true, true),
                        new FieldConfig("nombre", "Nombre", FieldType.TEXT),
                        new FieldConfig("precio", "Precio", FieldType.DOUBLE),
                        new FieldConfig("stock", "Stock", FieldType.INTEGER),
                        new FieldConfig("descripcion", "Descripcion", FieldType.TEXT_AREA),
                        new FieldConfig("administradorCedula", "Cedula Admin", FieldType.INTEGER),
                        new FieldConfig("categoriaIdCategoria", "ID Categoria", FieldType.INTEGER)
                },
                new String[]{"ID", "Nombre", "Precio", "Stock", "Categoria", "Admin"},
                new ProductoDao(),
                Producto::new,
                textBinder(
                        Map.of("idProducto", (p, v) -> p.setIdProducto(FormUtils.parseInteger(v)),
                                "nombre", Producto::setNombre,
                                "precio", (p, v) -> p.setPrecio(FormUtils.parseDouble(v)),
                                "stock", (p, v) -> p.setStock(FormUtils.parseInteger(v)),
                                "descripcion", Producto::setDescripcion,
                                "administradorCedula", (p, v) -> p.setAdministradorCedula(FormUtils.parseInteger(v)),
                                "categoriaIdCategoria", (p, v) -> p.setCategoriaIdCategoria(FormUtils.parseInteger(v))),
                        Map.of("idProducto", Producto::getIdProducto,
                                "nombre", Producto::getNombre,
                                "precio", Producto::getPrecio,
                                "stock", Producto::getStock,
                                "descripcion", Producto::getDescripcion,
                                "administradorCedula", Producto::getAdministradorCedula,
                                "categoriaIdCategoria", Producto::getCategoriaIdCategoria)),
                producto -> new Object[]{
                        producto.getIdProducto(), producto.getNombre(), producto.getPrecio(),
                        producto.getStock(), producto.getCategoriaIdCategoria(), producto.getAdministradorCedula()
                },
                Producto::getIdProducto));
    }

    private static void registerCarritoModule() {
        register(new ModuleDescriptor<>(
                "carrito",
                "Gestion de Carritos",
                new FieldConfig[]{
                        new FieldConfig("idCarrito", "ID Carrito", FieldType.INTEGER, true, true),
                        new FieldConfig("fecha", "Fecha", FieldType.DATE),
                        new FieldConfig("clienteCedula", "Cedula Cliente", FieldType.INTEGER)
                },
                new String[]{"ID", "Fecha", "Cliente"},
                new CarritoDao(),
                Carrito::new,
                textBinder(
                        Map.of("idCarrito", (c, v) -> c.setIdCarrito(FormUtils.parseInteger(v)),
                                "fecha", Carrito::setFecha,
                                "clienteCedula", (c, v) -> c.setClienteCedula(FormUtils.parseInteger(v))),
                        Map.of("idCarrito", Carrito::getIdCarrito,
                                "fecha", Carrito::getFecha,
                                "clienteCedula", Carrito::getClienteCedula)),
                carrito -> new Object[]{carrito.getIdCarrito(), carrito.getFecha(), carrito.getClienteCedula()},
                Carrito::getIdCarrito));
    }

    private static void registerDetalleCarritoModule() {
        register(new ModuleDescriptor<>(
                "detalle_carrito",
                "Detalle de Carrito",
                new FieldConfig[]{
                        new FieldConfig("idDetalleCarrito", "ID Detalle", FieldType.INTEGER, true, true),
                        new FieldConfig("subtotal", "Subtotal", FieldType.DOUBLE),
                        new FieldConfig("cantidad", "Cantidad", FieldType.INTEGER),
                        new FieldConfig("precioUnitario", "Precio Unitario", FieldType.DOUBLE),
                        new FieldConfig("productoIdProducto", "ID Producto", FieldType.INTEGER),
                        new FieldConfig("carritoIdCarrito", "ID Carrito", FieldType.INTEGER)
                },
                new String[]{"ID", "Subtotal", "Cantidad", "Precio", "Producto", "Carrito"},
                new DetalleCarritoDao(),
                DetalleCarrito::new,
                textBinder(
                        Map.of("idDetalleCarrito", (d, v) -> d.setIdDetalleCarrito(FormUtils.parseInteger(v)),
                                "subtotal", (d, v) -> d.setSubtotal(FormUtils.parseDouble(v)),
                                "cantidad", (d, v) -> d.setCantidad(FormUtils.parseInteger(v)),
                                "precioUnitario", (d, v) -> d.setPrecioUnitario(FormUtils.parseDouble(v)),
                                "productoIdProducto", (d, v) -> d.setProductoIdProducto(FormUtils.parseInteger(v)),
                                "carritoIdCarrito", (d, v) -> d.setCarritoIdCarrito(FormUtils.parseInteger(v))),
                        Map.of("idDetalleCarrito", DetalleCarrito::getIdDetalleCarrito,
                                "subtotal", DetalleCarrito::getSubtotal,
                                "cantidad", DetalleCarrito::getCantidad,
                                "precioUnitario", DetalleCarrito::getPrecioUnitario,
                                "productoIdProducto", DetalleCarrito::getProductoIdProducto,
                                "carritoIdCarrito", DetalleCarrito::getCarritoIdCarrito)),
                detalle -> new Object[]{
                        detalle.getIdDetalleCarrito(), detalle.getSubtotal(), detalle.getCantidad(),
                        detalle.getPrecioUnitario(), detalle.getProductoIdProducto(), detalle.getCarritoIdCarrito()
                },
                DetalleCarrito::getIdDetalleCarrito));
    }

    private static void registerProductoFavoritoModule() {
        register(new ModuleDescriptor<>(
                "producto_favorito",
                "Productos Favoritos",
                new FieldConfig[]{
                        new FieldConfig("idProductoFavorito", "ID Favorito", FieldType.INTEGER, true, true),
                        new FieldConfig("fechaAgregado", "Fecha Agregado", FieldType.DATE),
                        new FieldConfig("productoIdProducto", "ID Producto", FieldType.INTEGER),
                        new FieldConfig("clienteCedula", "Cedula Cliente", FieldType.INTEGER)
                },
                new String[]{"ID", "Fecha", "Producto", "Cliente"},
                new ProductoFavoritoDao(),
                ProductoFavorito::new,
                textBinder(
                        Map.of("idProductoFavorito", (p, v) -> p.setIdProductoFavorito(FormUtils.parseInteger(v)),
                                "fechaAgregado", ProductoFavorito::setFechaAgregado,
                                "productoIdProducto", (p, v) -> p.setProductoIdProducto(FormUtils.parseInteger(v)),
                                "clienteCedula", (p, v) -> p.setClienteCedula(FormUtils.parseInteger(v))),
                        Map.of("idProductoFavorito", ProductoFavorito::getIdProductoFavorito,
                                "fechaAgregado", ProductoFavorito::getFechaAgregado,
                                "productoIdProducto", ProductoFavorito::getProductoIdProducto,
                                "clienteCedula", ProductoFavorito::getClienteCedula)),
                favorito -> new Object[]{
                        favorito.getIdProductoFavorito(), favorito.getFechaAgregado(),
                        favorito.getProductoIdProducto(), favorito.getClienteCedula()
                },
                ProductoFavorito::getIdProductoFavorito));
    }

    private static void registerPedidoModule() {
        register(new ModuleDescriptor<>(
                "pedido",
                "Gestion de Pedidos",
                new FieldConfig[]{
                        new FieldConfig("idPedido", "ID Pedido", FieldType.INTEGER, true, true),
                        new FieldConfig("fecha", "Fecha", FieldType.DATE),
                        new FieldConfig("estado", "Estado", FieldType.TEXT),
                        new FieldConfig("total", "Total", FieldType.DOUBLE),
                        new FieldConfig("clienteCedula", "Cedula Cliente", FieldType.INTEGER)
                },
                new String[]{"ID", "Fecha", "Estado", "Total", "Cliente"},
                new PedidoDao(),
                Pedido::new,
                textBinder(
                        Map.of("idPedido", (p, v) -> p.setIdPedido(FormUtils.parseInteger(v)),
                                "fecha", Pedido::setFecha,
                                "estado", Pedido::setEstado,
                                "total", (p, v) -> p.setTotal(FormUtils.parseDouble(v)),
                                "clienteCedula", (p, v) -> p.setClienteCedula(FormUtils.parseInteger(v))),
                        Map.of("idPedido", Pedido::getIdPedido,
                                "fecha", Pedido::getFecha,
                                "estado", Pedido::getEstado,
                                "total", Pedido::getTotal,
                                "clienteCedula", Pedido::getClienteCedula)),
                pedido -> new Object[]{
                        pedido.getIdPedido(), pedido.getFecha(), pedido.getEstado(),
                        pedido.getTotal(), pedido.getClienteCedula()
                },
                Pedido::getIdPedido));
    }

    private static void registerDetallePedidoModule() {
        register(new ModuleDescriptor<>(
                "detalle_pedido",
                "Detalle de Pedidos",
                new FieldConfig[]{
                        new FieldConfig("idDetallePedido", "ID Detalle", FieldType.INTEGER, true, true),
                        new FieldConfig("cantidad", "Cantidad", FieldType.INTEGER),
                        new FieldConfig("subtotal", "Subtotal", FieldType.DOUBLE),
                        new FieldConfig("pedidoIdPedido", "ID Pedido", FieldType.INTEGER),
                        new FieldConfig("productoIdProducto", "ID Producto", FieldType.INTEGER)
                },
                new String[]{"ID", "Cantidad", "Subtotal", "Pedido", "Producto"},
                new DetallePedidoDao(),
                DetallePedido::new,
                textBinder(
                        Map.of("idDetallePedido", (d, v) -> d.setIdDetallePedido(FormUtils.parseInteger(v)),
                                "cantidad", (d, v) -> d.setCantidad(FormUtils.parseInteger(v)),
                                "subtotal", (d, v) -> d.setSubtotal(FormUtils.parseDouble(v)),
                                "pedidoIdPedido", (d, v) -> d.setPedidoIdPedido(FormUtils.parseInteger(v)),
                                "productoIdProducto", (d, v) -> d.setProductoIdProducto(FormUtils.parseInteger(v))),
                        Map.of("idDetallePedido", DetallePedido::getIdDetallePedido,
                                "cantidad", DetallePedido::getCantidad,
                                "subtotal", DetallePedido::getSubtotal,
                                "pedidoIdPedido", DetallePedido::getPedidoIdPedido,
                                "productoIdProducto", DetallePedido::getProductoIdProducto)),
                detalle -> new Object[]{
                        detalle.getIdDetallePedido(), detalle.getCantidad(), detalle.getSubtotal(),
                        detalle.getPedidoIdPedido(), detalle.getProductoIdProducto()
                },
                DetallePedido::getIdDetallePedido));
    }

    private static void registerEnvioModule() {
        register(new ModuleDescriptor<>(
                "envio",
                "Gestion de Envios",
                new FieldConfig[]{
                        new FieldConfig("idEnvio", "ID Envio", FieldType.INTEGER, true, true),
                        new FieldConfig("direccion", "Direccion", FieldType.TEXT),
                        new FieldConfig("fechaEnvio", "Fecha Envio", FieldType.DATE),
                        new FieldConfig("estadoEnvio", "Estado Envio", FieldType.TEXT),
                        new FieldConfig("pedidoIdPedido", "ID Pedido", FieldType.INTEGER)
                },
                new String[]{"ID", "Direccion", "Fecha", "Estado", "Pedido"},
                new EnvioDao(),
                Envio::new,
                textBinder(
                        Map.of("idEnvio", (e, v) -> e.setIdEnvio(FormUtils.parseInteger(v)),
                                "direccion", Envio::setDireccion,
                                "fechaEnvio", Envio::setFechaEnvio,
                                "estadoEnvio", Envio::setEstadoEnvio,
                                "pedidoIdPedido", (e, v) -> e.setPedidoIdPedido(FormUtils.parseInteger(v))),
                        Map.of("idEnvio", Envio::getIdEnvio,
                                "direccion", Envio::getDireccion,
                                "fechaEnvio", Envio::getFechaEnvio,
                                "estadoEnvio", Envio::getEstadoEnvio,
                                "pedidoIdPedido", Envio::getPedidoIdPedido)),
                envio -> new Object[]{
                        envio.getIdEnvio(), envio.getDireccion(), envio.getFechaEnvio(),
                        envio.getEstadoEnvio(), envio.getPedidoIdPedido()
                },
                Envio::getIdEnvio));
    }

    private static void registerFacturaModule() {
        register(new ModuleDescriptor<>(
                "factura",
                "Gestion de Facturas",
                new FieldConfig[]{
                        new FieldConfig("idFactura", "ID Factura", FieldType.INTEGER, true, true),
                        new FieldConfig("fecha", "Fecha", FieldType.DATE),
                        new FieldConfig("iva", "IVA", FieldType.DOUBLE),
                        new FieldConfig("descuento", "Descuento", FieldType.DOUBLE),
                        new FieldConfig("total", "Total", FieldType.DOUBLE),
                        new FieldConfig("pedidoIdPedido", "ID Pedido", FieldType.INTEGER)
                },
                new String[]{"ID", "Fecha", "IVA", "Descuento", "Total", "Pedido"},
                new FacturaDao(),
                Factura::new,
                textBinder(
                        Map.of("idFactura", (f, v) -> f.setIdFactura(FormUtils.parseInteger(v)),
                                "fecha", Factura::setFecha,
                                "iva", (f, v) -> f.setIva(FormUtils.parseDouble(v)),
                                "descuento", (f, v) -> f.setDescuento(FormUtils.parseDouble(v)),
                                "total", (f, v) -> f.setTotal(FormUtils.parseDouble(v)),
                                "pedidoIdPedido", (f, v) -> f.setPedidoIdPedido(FormUtils.parseInteger(v))),
                        Map.of("idFactura", Factura::getIdFactura,
                                "fecha", Factura::getFecha,
                                "iva", Factura::getIva,
                                "descuento", Factura::getDescuento,
                                "total", Factura::getTotal,
                                "pedidoIdPedido", Factura::getPedidoIdPedido)),
                factura -> new Object[]{
                        factura.getIdFactura(), factura.getFecha(), factura.getIva(),
                        factura.getDescuento(), factura.getTotal(), factura.getPedidoIdPedido()
                },
                Factura::getIdFactura));
    }

    private static void registerPagoModule() {
        register(new ModuleDescriptor<>(
                "pago",
                "Gestion de Pagos",
                new FieldConfig[]{
                        new FieldConfig("idPagos", "ID Pago", FieldType.INTEGER, true, true),
                        new FieldConfig("metodo", "Metodo", FieldType.TEXT),
                        new FieldConfig("estadoPago", "Estado Pago", FieldType.TEXT),
                        new FieldConfig("pedidoIdPedido", "ID Pedido", FieldType.INTEGER)
                },
                new String[]{"ID", "Metodo", "Estado", "Pedido"},
                new PagoDao(),
                Pago::new,
                textBinder(
                        Map.of("idPagos", (p, v) -> p.setIdPagos(FormUtils.parseInteger(v)),
                                "metodo", Pago::setMetodo,
                                "estadoPago", Pago::setEstadoPago,
                                "pedidoIdPedido", (p, v) -> p.setPedidoIdPedido(FormUtils.parseInteger(v))),
                        Map.of("idPagos", Pago::getIdPagos,
                                "metodo", Pago::getMetodo,
                                "estadoPago", Pago::getEstadoPago,
                                "pedidoIdPedido", Pago::getPedidoIdPedido)),
                pago -> new Object[]{pago.getIdPagos(), pago.getMetodo(), pago.getEstadoPago(), pago.getPedidoIdPedido()},
                Pago::getIdPagos));
    }

    private static void registerResenaModule() {
        register(new ModuleDescriptor<>(
                "resena",
                "Gestion de Resenas",
                new FieldConfig[]{
                        new FieldConfig("idResena", "ID Resena", FieldType.INTEGER, true, true),
                        new FieldConfig("comentario", "Comentario", FieldType.TEXT_AREA),
                        new FieldConfig("calificacion", "Calificacion", FieldType.INTEGER),
                        new FieldConfig("clienteCedula", "Cedula Cliente", FieldType.INTEGER),
                        new FieldConfig("productoIdProducto", "ID Producto", FieldType.INTEGER)
                },
                new String[]{"ID", "Calificacion", "Cliente", "Producto"},
                new ResenaDao(),
                Resena::new,
                textBinder(
                        Map.of("idResena", (r, v) -> r.setIdResena(FormUtils.parseInteger(v)),
                                "comentario", Resena::setComentario,
                                "calificacion", (r, v) -> r.setCalificacion(FormUtils.parseInteger(v)),
                                "clienteCedula", (r, v) -> r.setClienteCedula(FormUtils.parseInteger(v)),
                                "productoIdProducto", (r, v) -> r.setProductoIdProducto(FormUtils.parseInteger(v))),
                        Map.of("idResena", Resena::getIdResena,
                                "comentario", Resena::getComentario,
                                "calificacion", Resena::getCalificacion,
                                "clienteCedula", Resena::getClienteCedula,
                                "productoIdProducto", Resena::getProductoIdProducto)),
                resena -> new Object[]{
                        resena.getIdResena(), resena.getCalificacion(),
                        resena.getClienteCedula(), resena.getProductoIdProducto()
                },
                Resena::getIdResena));
    }

    private static void registerAtencionClienteModule() {
        register(new ModuleDescriptor<>(
                "atencion_cliente",
                "Atencion al Cliente",
                new FieldConfig[]{
                        new FieldConfig("idTicket", "ID Ticket", FieldType.INTEGER, true, true),
                        new FieldConfig("mensaje", "Mensaje", FieldType.TEXT_AREA),
                        new FieldConfig("fecha", "Fecha", FieldType.DATE),
                        new FieldConfig("respuesta", "Respuesta", FieldType.TEXT_AREA),
                        new FieldConfig("clienteCedula", "Cedula Cliente", FieldType.INTEGER)
                },
                new String[]{"ID", "Fecha", "Cliente", "Mensaje"},
                new AtencionClienteDao(),
                AtencionCliente::new,
                textBinder(
                        Map.of("idTicket", (a, v) -> a.setIdTicket(FormUtils.parseInteger(v)),
                                "mensaje", AtencionCliente::setMensaje,
                                "fecha", AtencionCliente::setFecha,
                                "respuesta", AtencionCliente::setRespuesta,
                                "clienteCedula", (a, v) -> a.setClienteCedula(FormUtils.parseInteger(v))),
                        Map.of("idTicket", AtencionCliente::getIdTicket,
                                "mensaje", AtencionCliente::getMensaje,
                                "fecha", AtencionCliente::getFecha,
                                "respuesta", AtencionCliente::getRespuesta,
                                "clienteCedula", AtencionCliente::getClienteCedula)),
                ticket -> new Object[]{
                        ticket.getIdTicket(), ticket.getFecha(), ticket.getClienteCedula(), ticket.getMensaje()
                },
                AtencionCliente::getIdTicket));
    }

    private static void registerReporteVentaModule() {
        register(new ModuleDescriptor<>(
                "reporte_venta",
                "Reportes de Venta",
                new FieldConfig[]{
                        new FieldConfig("idReportesVenta", "ID Reporte", FieldType.INTEGER, true, true),
                        new FieldConfig("fechaInicial", "Fecha Inicial", FieldType.DATE),
                        new FieldConfig("fechaFinal", "Fecha Final", FieldType.DATE),
                        new FieldConfig("tipoReporte", "Tipo Reporte", FieldType.TEXT),
                        new FieldConfig("totalVentas", "Total Ventas", FieldType.DOUBLE),
                        new FieldConfig("cantidadPedidos", "Cantidad Pedidos", FieldType.INTEGER),
                        new FieldConfig("archivoDescargable", "Archivo", FieldType.TEXT),
                        new FieldConfig("analisisVenta", "Analisis", FieldType.TEXT),
                        new FieldConfig("administradorCedula", "Cedula Admin", FieldType.INTEGER)
                },
                new String[]{"ID", "Inicio", "Fin", "Tipo", "Total", "Pedidos", "Admin"},
                new ReporteVentaDao(),
                ReporteVenta::new,
                textBinder(
                        Map.of("idReportesVenta", (r, v) -> r.setIdReportesVenta(FormUtils.parseInteger(v)),
                                "fechaInicial", ReporteVenta::setFechaInicial,
                                "fechaFinal", ReporteVenta::setFechaFinal,
                                "tipoReporte", ReporteVenta::setTipoReporte,
                                "totalVentas", (r, v) -> r.setTotalVentas(FormUtils.parseDouble(v)),
                                "cantidadPedidos", (r, v) -> r.setCantidadPedidos(FormUtils.parseInteger(v)),
                                "archivoDescargable", ReporteVenta::setArchivoDescargable,
                                "analisisVenta", ReporteVenta::setAnalisisVenta,
                                "administradorCedula", (r, v) -> r.setAdministradorCedula(FormUtils.parseInteger(v))),
                        Map.of("idReportesVenta", ReporteVenta::getIdReportesVenta,
                                "fechaInicial", ReporteVenta::getFechaInicial,
                                "fechaFinal", ReporteVenta::getFechaFinal,
                                "tipoReporte", ReporteVenta::getTipoReporte,
                                "totalVentas", ReporteVenta::getTotalVentas,
                                "cantidadPedidos", ReporteVenta::getCantidadPedidos,
                                "archivoDescargable", ReporteVenta::getArchivoDescargable,
                                "analisisVenta", ReporteVenta::getAnalisisVenta,
                                "administradorCedula", ReporteVenta::getAdministradorCedula)),
                reporte -> new Object[]{
                        reporte.getIdReportesVenta(), reporte.getFechaInicial(), reporte.getFechaFinal(),
                        reporte.getTipoReporte(), reporte.getTotalVentas(), reporte.getCantidadPedidos(),
                        reporte.getAdministradorCedula()
                },
                ReporteVenta::getIdReportesVenta));
    }

    private static <T> FormBinder<T> textBinder(
            Map<String, BiConsumer<T, String>> readers,
            Map<String, Function<T, Object>> writers) {
        return new FormBinder<>() {
            @Override
            public void writeToForm(T entity, Map<String, JComponent> components) {
                writers.forEach((fieldName, getter) -> {
                    JComponent component = components.get(fieldName);
                    if (component != null) {
                        FormUtils.setText(component, getter.apply(entity));
                    }
                });
            }

            @Override
            public void readFromForm(T entity, Map<String, JComponent> components) {
                readers.forEach((fieldName, setter) -> {
                    JComponent component = components.get(fieldName);
                    if (component != null) {
                        setter.accept(entity, FormUtils.getText(component));
                    }
                });
            }
        };
    }
}
