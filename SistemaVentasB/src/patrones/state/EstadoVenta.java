package patrones.state;

public interface EstadoVenta {
    void pagar(VentaEstadoContexto var1);

    void entregar(VentaEstadoContexto var1);

    void cancelar(VentaEstadoContexto var1);

    String getNombreEstado();
}
