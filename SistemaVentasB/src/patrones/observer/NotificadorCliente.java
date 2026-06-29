package patrones.observer;

import model.Venta;

// ConcreteObserver 1
// Simula el envio de una notificacion al cliente tras la venta
public class NotificadorCliente implements VentaObserver {

    @Override
    public void actualizar(Venta venta) {
        System.out.println("[Notificador] SMS enviado a "
                + venta.getCliente().getNombre()
                + " (tel: " + venta.getCliente().getTelefono() + ")"
                + " → Compra de '" + venta.getProducto().getNombre()
                + "' x" + venta.getCantidad()
                + " | Total: S/. " + venta.getTotal());
    }
}