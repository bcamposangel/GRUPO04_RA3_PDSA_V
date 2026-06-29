package model;

public class Usuario {

    //Enum para que el atributo rol tenga de constante administrador y vendedor
    public enum Rol {
        ADMINISTRADOR,
        VENDEDOR
    }

    private int id;
    private String nombre;
    private Rol rol;

    public Usuario(int id, String nombre, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Rol getRol() {
        return rol;
    }

    @Override
    public String toString() {
        return nombre + " - " + rol;
    }
}
