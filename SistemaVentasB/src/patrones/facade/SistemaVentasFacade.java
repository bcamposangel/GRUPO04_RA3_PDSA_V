package patrones.facade;

import model.Cliente;
import model.Producto;
import model.Usuario;
import model.Venta;

import patrones.adapter.AdaptadorYape;
import patrones.adapter.ProcesadorPago;
import patrones.builder.VentaBuilder;
import patrones.factory.ProductoCreator;
import patrones.proxy.ReporteVentas;
import patrones.proxy.ReporteVentasProxy;
import patrones.sigleton.ConexionBD;

public class SistemaVentasFacade {

    public Venta realizarVenta(ProductoCreator creator,
                               Cliente cliente,
                               Usuario vendedor,
                               int cantidad,
                               String fecha,
                               String metodoPago) {

        System.out.println("\n===== FACADE: PROCESO COMPLETO DE VENTA =====");

        // Singleton
        ConexionBD conexion = ConexionBD.getInstancia();
        conexion.conectar();

        // Factory
        Producto producto = creator.crearProducto();

        // Builder
        Venta venta = new VentaBuilder()
                .setProducto(producto)
                .setCliente(cliente)
                .setVendedor(vendedor)
                .setCantidad(cantidad)
                .setFecha(fecha)
                .setMetodoPago(metodoPago)
                .build();

        // Adapter
        if (metodoPago.equalsIgnoreCase("Yape")) {
            ProcesadorPago pago = new AdaptadorYape();
            pago.procesarPago(venta.getTotal());
        } else {
            System.out.println("Pago procesado con metodo: " + metodoPago);
        }

        System.out.println("Venta realizada correctamente.");
        System.out.println(venta);

        return venta;
    }

    public void mostrarReporteVentas(Usuario usuario, Venta venta) {

        // Proxy
        ReporteVentas reporte = new ReporteVentasProxy(usuario, venta);
        reporte.mostrarReporte();
    }
}