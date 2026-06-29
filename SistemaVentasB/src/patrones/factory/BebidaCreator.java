package patrones.factory;

import model.Bebida;
import model.Producto;

public class BebidaCreator extends ProductoCreator {
    @Override
    public Producto crearProducto() {

        return new Bebida(
                1,
                "Coca Cola",
                4.50,
                100
        );
    }
}
