package patrones.state;

public class EstadoCancelada implements EstadoVenta {
    public EstadoCancelada() {
    }

    public void pagar(VentaEstadoContexto contexto) {
        System.out.println("No se puede pagar una venta cancelada.");
    }

    public void entregar(VentaEstadoContexto contexto) {
        System.out.println("No se puede entregar una venta cancelada.");
    }

    public void cancelar(VentaEstadoContexto contexto) {
        System.out.println("La venta ya se encuentra cancelada.");
    }

    public String getNombreEstado() {
        return "CANCELADA";
    }
}
