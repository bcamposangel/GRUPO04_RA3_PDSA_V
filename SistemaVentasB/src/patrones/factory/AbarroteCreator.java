package patrones.factory;

import model.Abarrote;
import model.Producto;

public class AbarroteCreator extends ProductoCreator {
    @Override
    public Producto crearProducto() {

        return new Abarrote(
                3,
                "Arroz Costeño",
                6.50,
                200
        );
    }
}
