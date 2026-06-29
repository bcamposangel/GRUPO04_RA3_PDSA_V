package patrones.observer;

import model.Venta;
import java.util.ArrayList;
import java.util.List;

// ConcreteSubject
// Gestiona la lista de observadores y notifica cuando se registra una venta
public class GestorVentas implements VentaSubject {

    private List<VentaObserver> observers = new ArrayList<>();
    private List<Venta> historialVentas   = new ArrayList<>();

    @Override
    public void agregarObserver(VentaObserver observer) {
        observers.add(observer);
        System.out.println("[GestorVentas] Observer suscrito: "
                + observer.getClass().getSimpleName());
    }

    @Override
    public void eliminarObserver(VentaObserver observer) {
        observers.remove(observer);
        System.out.println("[GestorVentas] Observer eliminado: "
                + observer.getClass().getSimpleName());
    }

    @Override
    public void notificarObservers(Venta venta) {
        // Recorre y notifica a todos los observers suscritos
        for (VentaObserver observer : observers) {
            observer.actualizar(venta);
        }
    }

    // Metodo de negocio: registra la venta y dispara las notificaciones
    public void registrarVenta(Venta venta) {
        historialVentas.add(venta);
        System.out.println("\n[GestorVentas] Nueva venta registrada para: "
                + venta.getCliente().getNombre());
        notificarObservers(venta);  // <-- aquí se dispara el patrón
    }

    public List<Venta> getHistorial() {
        return historialVentas;
    }
}