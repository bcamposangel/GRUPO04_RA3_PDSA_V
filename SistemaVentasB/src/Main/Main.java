package Main;

import model.*;
import patrones.adapter.AdaptadorYape;
import patrones.adapter.ProcesadorPago;
import patrones.builder.VentaBuilder;
import patrones.decorator.*;
import patrones.factory.AbarroteCreator;
import patrones.factory.BebidaCreator;
import patrones.factory.ProductoCreator;
import patrones.sigleton.ConexionBD;

// IMPORTS DE PROXY Y FACADE
import patrones.proxy.ReporteVentas;
import patrones.proxy.ReporteVentasProxy;
import patrones.facade.SistemaVentasFacade;
import patrones.strategy.ContextoPago;
import patrones.strategy.PagoEfectivo;
import patrones.strategy.PagoTarjeta;
import patrones.strategy.PagoYape;

import javax.swing.*;
import patrones.observer.VentaObserverGUI;


public class Main {

    public static void main(String[] args) {

        // 1. FACTORY crea productos

        ProductoCreator creator = new AbarroteCreator();

        Producto producto = creator.crearProducto();



        // DATOS DEL SISTEMA

        Cliente cliente = new Cliente(
                1,
                "Juan Perez",
                "999888777"
        );

        Usuario vendedor = new Usuario(
                1,
                "Angel",
                Usuario.Rol.VENDEDOR
        );



        // 2. BUILDER usa los productos creados por FACTORY

        Venta venta = new VentaBuilder()
                .setProducto(producto)
                .setCliente(cliente)
                .setVendedor(vendedor)
                .setCantidad(3)
                .setFecha("07/06/2026")
                .setMetodoPago("Yape")
                .build();

        System.out.println(venta);



        // 3. SINGLETON garantiza una única instancia de conexión

        ConexionBD bd1 = ConexionBD.getInstancia();
        ConexionBD bd2 = ConexionBD.getInstancia();

        bd1.conectar();

        System.out.println("¿Es la misma conexión?: " + (bd1 == bd2));



        // 4. ADAPTER adapta Yape al sistema de pagos

        ProcesadorPago pago = new AdaptadorYape();

        pago.procesarPago(50.0);



        // 5. COMPOSITE: Creación de un Combo

        System.out.println("\n===== 5. PRUEBA PATRÓN COMPOSITE =====");

        ProductoCreator factoryAbarrote = new AbarroteCreator();
        Producto arroz = factoryAbarrote.crearProducto();

        ProductoCreator factoryBebida = new BebidaCreator();
        Producto gaseosa = factoryBebida.crearProducto();

        Combo comboAlmuerzo = new Combo(10, "Combo Familiar Almuerzo");
        comboAlmuerzo.agregarProducto(arroz);
        comboAlmuerzo.agregarProducto(gaseosa);

        System.out.println(comboAlmuerzo);



        // 6. DECORATOR: Añadir Delivery a la venta del combo

        System.out.println("\n===== 6. PRUEBA PATRÓN DECORATOR =====");

        Venta ventaCombo = new VentaBuilder()
                .setProducto(comboAlmuerzo)
                .setCliente(cliente)
                .setVendedor(vendedor)
                .setCantidad(1)
                .setFecha("07/06/2026")
                .setMetodoPago("Efectivo")
                .build();

        System.out.println("-> Venta Original (Sin extras):" + ventaCombo);

        Venta ventaConEnvio = new ConDelivery(ventaCombo, 8.00);

        System.out.println("\n-> Venta Decorada (Con adicionales):" + ventaConEnvio);



        // 7. PROXY: Control de acceso a reportes

        System.out.println("\n===== 7. PRUEBA PATRÓN PROXY =====");

        Usuario administrador = new Usuario(
                2,
                "Maria",
                Usuario.Rol.ADMINISTRADOR
        );

        ReporteVentas reporteVendedor = new ReporteVentasProxy(
                vendedor,
                ventaCombo
        );

        reporteVendedor.mostrarReporte();

        ReporteVentas reporteAdministrador = new ReporteVentasProxy(
                administrador,
                ventaCombo
        );

        reporteAdministrador.mostrarReporte();



        // 8. FACADE: Simplifica el proceso completo de venta

        System.out.println("\n===== 8. PRUEBA PATRÓN FACADE =====");

        SistemaVentasFacade sistema = new SistemaVentasFacade();

        Venta ventaPorFacade = sistema.realizarVenta(
                new BebidaCreator(),
                cliente,
                vendedor,
                2,
                "07/06/2026",
                "Yape"
        );

        sistema.mostrarReporteVentas(
                administrador,
                ventaPorFacade
        );

        System.out.println("\n===== 9. PRUEBA PATRÓN STRATEGY =====");

        ContextoPago contexto = new ContextoPago();

        contexto.setMetodopago(new PagoEfectivo());
        contexto.procesarPago(venta.getTotal());

        contexto.setMetodopago(new PagoTarjeta());
        contexto.procesarPago(venta.getTotal());

        contexto.setMetodopago(new PagoYape());
        contexto.procesarPago(venta.getTotal());

        // Lanzar GUI del Observer
        System.out.println("\n===== 10. PATRÓN OBSERVER CON GUI =====");
        SwingUtilities.invokeLater(() -> new VentaObserverGUI().setVisible(true));

    }



}