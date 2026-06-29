package patrones.observer;

import model.Venta;

// Interfaz Observer
public interface VentaObserver {
    void actualizar(Venta venta);
}