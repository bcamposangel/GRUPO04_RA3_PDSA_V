package patrones.factory;

import model.Producto;

//Trabajamos el creeador de productos con una clase abstracta
public abstract class ProductoCreator {
    public abstract Producto crearProducto();
}
