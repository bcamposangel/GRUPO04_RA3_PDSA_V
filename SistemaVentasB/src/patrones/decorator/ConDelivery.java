package patrones.decorator;

import model.Venta;

public class ConDelivery extends VentaDecorador {
    private double costoEnvio;

    public ConDelivery(Venta ventaDecorada, double costoEnvio) {
        super(ventaDecorada);
        this.costoEnvio = costoEnvio;
    }

    @Override
    public double getTotal() {
        return super.getTotal() + costoEnvio;
    }

    @Override
    public String toString() {
        return super.toString() + "\n[+] Cargo Extra: Envío a Domicilio (S/. " + costoEnvio + ")" +
                "\n=========================" +
                "\nTOTAL NETO A PAGAR: S/. " + getTotal();
    }
}