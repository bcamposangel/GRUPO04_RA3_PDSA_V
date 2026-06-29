package patrones.adapter;

public class AdaptadorYape implements ProcesadorPago{

    private YapeAPI yape;

    public AdaptadorYape(){
        this.yape = new YapeAPI();
    }
    @Override
    public void procesarPago(double monto) {

        yape.enviarPago(monto);
    }
}
