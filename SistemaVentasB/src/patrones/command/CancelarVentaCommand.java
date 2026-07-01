package patrones.command;

import model.Venta;

public class CancelarVentaCommand implements Command {
    private ReceptorVenta receptor;
    private Venta venta;

    public CancelarVentaCommand(ReceptorVenta receptor, Venta venta) {
        this.receptor = receptor;
        this.venta = venta;
    }

    public void ejecutar() {
        this.receptor.cancelarVenta(this.venta);
    }
}
