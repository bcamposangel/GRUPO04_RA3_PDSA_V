package patrones.observer;

import model.Venta;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// ConcreteObserver 3
// Registra cada venta en el log de auditoria del sistema
public class RegistroAuditoria implements VentaObserver {

    @Override
    public void actualizar(Venta venta) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        System.out.println("[Auditoría " + timestamp + "]"
                + " Vendedor: " + venta.getVendedor().getNombre()
                + " | Producto: " + venta.getProducto().getNombre()
                + " | Método pago: " + venta.getMetodoPago()
                + " | Total: S/. " + venta.getTotal());
    }
}