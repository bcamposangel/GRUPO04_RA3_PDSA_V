package patrones.observer;

import model.Venta;

// ConcreteObserver 2
// Descuenta el stock del producto vendido
public class ActualizadorStock implements VentaObserver {

    @Override
    public void actualizar(Venta venta) {
        int stockAnterior = venta.getProducto().getStock();
        int nuevoStock    = stockAnterior - venta.getCantidad();

        // Actualiza el stock directamente en el objeto Producto
        venta.getProducto().setStock(Math.max(nuevoStock, 0));

        System.out.println("[Stock] Producto: '" + venta.getProducto().getNombre()
                + "' | Antes: " + stockAnterior
                + " → Ahora: " + venta.getProducto().getStock());
    }
}