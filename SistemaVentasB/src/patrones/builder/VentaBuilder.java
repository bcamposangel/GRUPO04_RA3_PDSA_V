package patrones.builder;

import model.Cliente;
import model.Producto;
import model.Usuario;
import model.Venta;

public class VentaBuilder {
    private Producto producto;
    private Cliente cliente;
    private Usuario vendedor;
    private int cantidad;
    private String fecha;
    private String metodoPago;

    public VentaBuilder setProducto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public VentaBuilder setCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public VentaBuilder setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
        return this;
    }

    public VentaBuilder setCantidad(int cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public VentaBuilder setFecha(String fecha) {
        this.fecha = fecha;
        return this;
    }

    public VentaBuilder setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
        return this;
    }

    public Venta build() {

        return new Venta(
                producto,
                cliente,
                vendedor,
                cantidad,
                fecha,
                metodoPago
        );
    }
}
