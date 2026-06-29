package patrones.strategy;
//Mantiene la estrategia actual y ejecuta el método de pago seleccionado.
// Contexto del patrón Strategy.
// Mantiene una referencia al método de pago seleccionado.

public class ContextoPago {

    private MetodoPago metodopago;

    // Permite cambiar la estrategia en tiempo de ejecución.
    public void setMetodopago(MetodoPago metodopago){
        this.metodopago = metodopago;
    }

    public void procesarPago(double monto){
        if (metodopago == null){
            System.out.println("No se ha seleccionado un método de pago");
            return;
        }

        metodopago.pagar(monto);
    }
}
