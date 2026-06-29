package patrones.strategy;
//Implementa el algoritmo de pago con tarjeta.
// Estrategia concreta para pagos con tarjeta.

public class PagoTarjeta implements MetodoPago{
    @Override
    public void pagar(double monto) {
        System.out.println("Pago realizado con Tarjeta.");
        System.out.println("Monto Pagado: S/. " + monto);
    }
}
