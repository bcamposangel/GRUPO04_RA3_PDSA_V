package model;

import java.util.ArrayList;
import java.util.List;

public class Combo extends Producto {
    private List<Producto> listaProductos;

    public Combo(int id, String nombre) {
        super(id, nombre, 0, 0);
        this.listaProductos = new ArrayList<>();
    }

    public void agregarProducto(Producto p) {
        listaProductos.add(p);
    }

    public void eliminarProducto(Producto p) {
        listaProductos.remove(p);
    }

    @Override
    public double getPrecio() {
        double totalPrecio = 0;
        for (Producto p : listaProductos) {
            totalPrecio += p.getPrecio();
        }
        return totalPrecio;
    }

    @Override
    public int getStock() {
        if (listaProductos.isEmpty()) return 0;
        int stockMinimo = Integer.MAX_VALUE;
        for (Producto p : listaProductos) {
            if (p.getStock() < stockMinimo) {
                stockMinimo = p.getStock();
            }
        }
        return stockMinimo;
    }

    @Override
    public String getTipo() {
        return "Combo Promocional";
    }
}