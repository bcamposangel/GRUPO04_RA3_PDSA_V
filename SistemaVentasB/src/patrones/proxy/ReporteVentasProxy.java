package patrones.proxy;

import model.Usuario;
import model.Venta;

public class ReporteVentasProxy implements ReporteVentas {

    private Usuario usuario;
    private Venta venta;
    private ReporteVentasReal reporteReal;

    public ReporteVentasProxy(Usuario usuario, Venta venta) {
        this.usuario = usuario;
        this.venta = venta;
    }

    @Override
    public void mostrarReporte() {

        if (usuario.getRol() == Usuario.Rol.ADMINISTRADOR) {

            if (reporteReal == null) {
                reporteReal = new ReporteVentasReal(venta);
            }

            System.out.println("\nAcceso permitido para: " + usuario.getNombre());
            reporteReal.mostrarReporte();

        } else {
            System.out.println("\nAcceso denegado para: " + usuario.getNombre());
            System.out.println("Solo un ADMINISTRADOR puede ver reportes de ventas.");
        }
    }
}