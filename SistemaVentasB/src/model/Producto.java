package model;

public abstract class Producto {
    protected int id;
    protected String nombre;
    protected double precio;
    protected int stock;

    public Producto(int id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public abstract String getTipo();

    @Override
    public String toString() {
        return "\nTipo: " + getTipo()
                + "\nID: " + id
                + "\nNombre: " + nombre
                + "\nPrecio: S/. " + precio
                + "\nStock: " + stock;
    }
}
