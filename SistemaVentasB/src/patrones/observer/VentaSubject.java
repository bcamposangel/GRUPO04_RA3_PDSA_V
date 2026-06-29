package patrones.observer;

import model.Venta;

// Interfaz Subject
// Define los métodos para gestionar observadores
public interface VentaSubject {
    void agregarObserver(VentaObserver observer);
    void eliminarObserver(VentaObserver observer);
    void notificarObservers(Venta venta);
}