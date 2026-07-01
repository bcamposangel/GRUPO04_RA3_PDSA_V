package patrones.state;

import model.Venta;

public class VentaEstadoContexto {
    private Venta venta;
    private EstadoVenta estadoActual;

    public VentaEstadoContexto(Venta venta) {
        this.venta = venta;
        this.estadoActual = new EstadoPendiente();
    }

    public void setEstado(EstadoVenta estadoActual) {
        this.estadoActual = estadoActual;
    }

    public String getNombreEstado() {
        return estadoActual.getNombreEstado();
    }

    public EstadoVenta getEstadoActual() {
        return this.estadoActual;
    }

    public Venta getVenta() {
        return this.venta;
    }

    public void pagar() {
        this.estadoActual.pagar(this);
    }

    public void entregar() {
        this.estadoActual.entregar(this);
    }

    public void cancelar() {
        this.estadoActual.cancelar(this);
    }

    public void mostrarEstado() {
        System.out.println("Estado actual de la venta: " + this.estadoActual.getNombreEstado());
    }
}
