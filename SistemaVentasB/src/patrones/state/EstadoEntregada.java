package patrones.state;

public class EstadoEntregada implements EstadoVenta {
    public EstadoEntregada() {
    }

    public void pagar(VentaEstadoContexto contexto) {
        System.out.println("La venta ya fue pagada anteriormente.");
    }

    public void entregar(VentaEstadoContexto contexto) {
        System.out.println("La venta ya fue entregada.");
    }

    public void cancelar(VentaEstadoContexto contexto) {
        System.out.println("No se puede cancelar una venta entregada.");
    }

    public String getNombreEstado() {
        return "ENTREGADA";
    }
}
