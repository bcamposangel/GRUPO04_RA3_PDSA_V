package patrones.proxy;

import model.Venta;

public class ReporteVentasReal implements ReporteVentas {

    private Venta venta;

    public ReporteVentasReal(Venta venta) {
        this.venta = venta;
    }

    @Override
    public void mostrarReporte() {
        System.out.println("\n===== REPORTE DE VENTA =====");
        System.out.println("Cliente: " + venta.getCliente().getNombre());
        System.out.println("Producto: " + venta.getProducto().getNombre());
        System.out.println("Cantidad: " + venta.getCantidad());
        System.out.println("Metodo de pago: " + venta.getMetodoPago());
        System.out.println("Total vendido: S/. " + venta.getTotal());
    }
}