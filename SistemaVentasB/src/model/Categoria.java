package model;

// se declara abstracta ya que Categoria es muy general
public abstract class Categoria {

    protected String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract void mostrar();
}