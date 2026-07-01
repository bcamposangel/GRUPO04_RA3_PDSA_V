package patrones.state;

public class EstadoPagada implements EstadoVenta {
    public EstadoPagada() {
    }

    public void pagar(VentaEstadoContexto contexto) {
        System.out.println("La venta ya se encuentra pagada.");
    }

    public void entregar(VentaEstadoContexto contexto) {
        System.out.println("La venta fue entregada correctamente.");
        contexto.setEstado(new EstadoEntregada());
    }

    public void cancelar(VentaEstadoContexto contexto) {
        System.out.println("La venta pagada fue cancelada.");
        contexto.setEstado(new EstadoCancelada());
    }

    public String getNombreEstado() {
        return "PAGADA";
    }
}
