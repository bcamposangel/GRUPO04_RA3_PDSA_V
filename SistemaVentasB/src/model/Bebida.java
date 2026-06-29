package model;

public class Bebida extends Producto{
    public Bebida(int id,
                  String nombre,
                  double precio,
                  int stock) {

        super(id, nombre, precio, stock);
    }

    @Override
    public String getTipo() {
        return "Bebida";
    }
}
