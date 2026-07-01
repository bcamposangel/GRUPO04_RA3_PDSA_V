package patrones.command;

import model.Venta;

public class RegistrarVentaCommand implements Command {
    private ReceptorVenta receptor;
    private Venta venta;

    public RegistrarVentaCommand(ReceptorVenta receptor, Venta venta) {
        this.receptor = receptor;
        this.venta = venta;
    }

    public void ejecutar() {
        this.receptor.registrarVenta(this.venta);
    }
}