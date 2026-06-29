package patrones.observer;

import model.*;
import patrones.builder.VentaBuilder;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * GUI simple que demuestra el patrón Observer en acción.
 * Cada vez que se registra una venta, los observers actualizan
 * visualmente las etiquetas y el log de la interfaz.
 */
public class VentaObserverGUI extends JFrame {

    // --- Componentes de la GUI ---
    private JTextField txtProducto;
    private JTextField txtCantidad;
    private JTextField txtCliente;
    private JTextField txtPrecio;
    private JLabel     lblStock;
    private JTextArea  txtLog;

    // --- Patrón Observer ---
    private GestorVentas gestorVentas;
    private int stockActual = 20; // stock inicial simulado

    public VentaObserverGUI() {
        configurarVentana();
        inicializarObserver();
    }

    /** Configura el ConcreteSubject y suscribe los 3 observers */
    private void inicializarObserver() {
        gestorVentas = new GestorVentas();

        // Observer 1: actualiza el label de stock en la GUI
        gestorVentas.agregarObserver(venta -> {
            int anterior = stockActual;
            stockActual  = Math.max(stockActual - venta.getCantidad(), 0);
            lblStock.setText("Stock actual: " + stockActual + " unidades");
            agregarLog(" [Stock] " + venta.getProducto().getNombre()
                    + ": " + anterior + " → " + stockActual);
        });

        // Observer 2: notifica al cliente (simula SMS)
        gestorVentas.agregarObserver(venta ->
                agregarLog("📱 [Cliente] SMS enviado a "
                        + venta.getCliente().getNombre()
                        + " | Total: S/. " + venta.getTotal())
        );

        // Observer 3: registra en auditoría
        gestorVentas.agregarObserver(venta -> {
            String hora = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            agregarLog("📋 [Auditoría " + hora + "] Vendedor: "
                    + venta.getVendedor().getNombre()
                    + " | Método: " + venta.getMetodoPago());
        });
    }

    /** Construye y organiza los componentes Swing */
    private void configurarVentana() {
        setTitle("Sistema de Ventas — Patrón Observer");
        setSize(480, 420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ── Panel superior: formulario ──
        JPanel panelForm = new JPanel(new GridLayout(3, 4, 8, 8));
        panelForm.setBorder(BorderFactory.createTitledBorder("Nueva Venta"));

        panelForm.add(new JLabel("Producto:"));
        txtProducto = new JTextField("Arroz");
        panelForm.add(txtProducto);

        panelForm.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField("2");
        panelForm.add(txtCantidad);

        panelForm.add(new JLabel("Cliente:"));
        txtCliente = new JTextField("Juan Perez");
        panelForm.add(txtCliente);

        panelForm.add(new JLabel("Precio unit. S/."));
        txtPrecio = new JTextField("5.50");
        panelForm.add(txtPrecio);

        JButton btnRegistrar = new JButton("✔ Registrar Venta");
        btnRegistrar.setBackground(new Color(70, 130, 180));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 13));
        panelForm.add(btnRegistrar);
        panelForm.add(new JLabel()); // espaciador

        add(panelForm, BorderLayout.NORTH);

        // ── Panel central: stock + log ──
        JPanel panelInfo = new JPanel(new BorderLayout(5, 5));

        lblStock = new JLabel("Stock actual: " + stockActual + " unidades");
        lblStock.setFont(new Font("Arial", Font.BOLD, 13));
        lblStock.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        panelInfo.add(lblStock, BorderLayout.NORTH);

        txtLog = new JTextArea();
        txtLog.setEditable(false);
        txtLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtLog.setBackground(new Color(245, 245, 245));
        JScrollPane scroll = new JScrollPane(txtLog);
        scroll.setBorder(BorderFactory.createTitledBorder("Log de eventos (Observer)"));
        panelInfo.add(scroll, BorderLayout.CENTER);

        add(panelInfo, BorderLayout.CENTER);

        // ── Acción del botón: dispara el patrón Observer ──
        btnRegistrar.addActionListener(e -> registrarVenta());
    }

    /**
     * Se ejecuta al pulsar el botón.
     * Construye la Venta con los datos del formulario
     * y llama a registrarVenta() del ConcreteSubject,
     * lo que dispara la notificación a todos los observers.
     */
    private void registrarVenta() {
        try {
            String nombreProducto = txtProducto.getText().trim();
            int    cantidad        = Integer.parseInt(txtCantidad.getText().trim());
            String nombreCliente   = txtCliente.getText().trim();
            double precio          = Double.parseDouble(txtPrecio.getText().trim());

            // Crear objetos del modelo con datos del formulario
            Producto producto = new Abarrote(
                    (int)(Math.random() * 100),
                    nombreProducto,
                    precio,
                    stockActual
            );
            Cliente cliente   = new Cliente(1, nombreCliente, "999000111");
            Usuario vendedor  = new Usuario(1, "Angel", Usuario.Rol.VENDEDOR);

            // Construir la venta usando el Builder ya existente
            Venta venta = new VentaBuilder()
                    .setProducto(producto)
                    .setCliente(cliente)
                    .setVendedor(vendedor)
                    .setCantidad(cantidad)
                    .setFecha(LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .setMetodoPago("Efectivo")
                    .build();

            agregarLog("\n── Venta registrada ──────────────────");

            // ★ Aquí se dispara el patrón Observer ★
            gestorVentas.registrarVenta(venta);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ingresa valores numéricos válidos en Cantidad y Precio.",
                    "Error de entrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Agrega una línea al área de log con scroll automático */
    private void agregarLog(String mensaje) {
        txtLog.append(mensaje + "\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }

    /** Punto de entrada para demostrar el Observer con GUI */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentaObserverGUI().setVisible(true);
        });
    }
}