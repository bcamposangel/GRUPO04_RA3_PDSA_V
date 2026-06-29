package patrones.sigleton;

public class ConexionBD {

    private static ConexionBD instancia;

    private ConexionBD() {
        System.out.println("Conexión a BD creada");
    }

    public static ConexionBD getInstancia() {

        if (instancia == null) {
            instancia = new ConexionBD();
        }

        return instancia;
    }

    public void conectar() {
        System.out.println("Conectado a la base de datos");
    }
}
