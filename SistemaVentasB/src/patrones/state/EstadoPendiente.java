package patrones.state;

public class EstadoPendiente implements EstadoVenta {
    public EstadoPendiente() {
    }

    public void pagar(VentaEstadoContexto contexto) {
        System.out.println("La venta fue pagada correctamente.");
        contexto.setEstado(new EstadoPagada());
    }

    public void entregar(VentaEstadoContexto contexto) {
        System.out.println("No se puede entregar una venta pendiente de pago.");
    }

    public void cancelar(VentaEstadoContexto contexto) {
        System.out.println("La venta pendiente fue cancelada.");
        contexto.setEstado(new EstadoCancelada());
    }

    public String getNombreEstado() {
        return "PENDIENTE";
    }
}
