package model;

public class Abarrote extends Producto{
    public Abarrote(int id,
                    String nombre,
                    double precio,
                    int stock) {

        super(id, nombre, precio, stock);
    }

    @Override
    public String getTipo() {
        return "Abarrote";
    }
}
