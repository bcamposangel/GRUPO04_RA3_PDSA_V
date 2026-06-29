package patrones.decorator;

import model.Venta;

public abstract class VentaDecorador extends Venta {
    protected Venta ventaDecorada;

    public VentaDecorador(Venta ventaDecorada) {
        super(ventaDecorada.getProducto(),
                ventaDecorada.getCliente(),
                ventaDecorada.getVendedor(),
                ventaDecorada.getCantidad(),
                ventaDecorada.getFecha(),
                ventaDecorada.getMetodoPago());
        this.ventaDecorada = ventaDecorada;
    }

    @Override
    public double getTotal() {
        return ventaDecorada.getTotal();
    }

    @Override
    public String toString() {
        return ventaDecorada.toString();
    }
}