package patrones.command;

import model.Venta;

public class ReceptorVenta {
    public ReceptorVenta() {
    }

    public void registrarVenta(Venta venta) {
        System.out.println("\nVenta registrada correctamente.");
        System.out.println(venta);
    }

    public void cancelarVenta(Venta venta) {
        System.out.println("\nLa venta del producto " + venta.getProducto().getNombre() + " ha sido cancelada.");
    }
}
