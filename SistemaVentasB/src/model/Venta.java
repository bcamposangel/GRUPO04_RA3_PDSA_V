package model;

public class Venta {
    private Producto producto;
    private Cliente cliente;
    private Usuario vendedor;
    private int cantidad;
    private String fecha;
    private String metodoPago;

    public Venta(Producto producto,
                 Cliente cliente,
                 Usuario vendedor,
                 int cantidad,
                 String fecha,
                 String metodoPago) {

        this.producto = producto;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
    }

    public Producto getProducto() {
        return producto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public double getTotal() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {

        return "\n===== VENTA =====" +
                "\nCliente: " + cliente.getNombre() +
                "\nProducto: " + producto.getNombre() +
                "\nCantidad: " + cantidad +
                "\nVendedor: " + vendedor.getNombre() +
                "\nFecha: " + fecha +
                "\nMetodo de pago: " + metodoPago +
                "\nTotal: S/. " + getTotal();
    }
}
