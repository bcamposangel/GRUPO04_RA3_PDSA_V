package patrones.strategy;
//Implementa el algoritmo de pago en efectivo
// Estrategia concreta para pagos en efectivo.

public class PagoEfectivo implements  MetodoPago{
    @Override
    public void pagar(double monto) {
        System.out.println("Pago realizado en efectivo.");
        System.out.println("Monto pagado: S/. " + monto);
    }
}
