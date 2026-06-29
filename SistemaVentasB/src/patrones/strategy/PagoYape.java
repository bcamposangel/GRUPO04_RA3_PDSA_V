package patrones.strategy;
//Implementa el algoritmo de pago mediante Yape reutilizando el Adapter.

import patrones.adapter.AdaptadorYape;
import patrones.adapter.ProcesadorPago;

// Estrategia concreta para pagos mediante Yape.

public class PagoYape implements MetodoPago{

    private ProcesadorPago adaptador;

    public PagoYape(){

        adaptador = new AdaptadorYape();
    }

    @Override
    public void pagar(double monto) {

        adaptador.procesarPago(monto);
    }
}
