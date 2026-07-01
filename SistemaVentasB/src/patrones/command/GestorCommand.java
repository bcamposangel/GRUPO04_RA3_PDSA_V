package patrones.command;

public class GestorCommand {
    private Command comando;

    public GestorCommand() {
    }

    public void setCommand(Command comando) {
        this.comando = comando;
    }

    public void ejecutarComando() {
        if (this.comando != null) {
            this.comando.ejecutar();
        } else {
            System.out.println("No hay ningún comando asignado.");
        }

    }
}
