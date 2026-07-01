package gui;

import model.*;
import patrones.builder.VentaBuilder;
import patrones.command.*;
import patrones.factory.AbarroteCreator;
import patrones.factory.BebidaCreator;
import patrones.observer.*;
import patrones.state.*;
import patrones.strategy.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

/**
 * GUI integrada del Sistema de Ventas.
 * Integra los 4 patrones de comportamiento:
 *   - Observer  : notifica cambios al registrar una venta
 *   - Strategy  : selecciona el metodo de pago
 *   - Command   : ejecuta/deshace/rehace acciones de venta
 *   - State     : gestiona el ciclo de vida del estado de la venta
 */
public class SistemaGUI extends JFrame {

    // Componentes del formulario
    private JComboBox<String> cmbProducto;
    private JTextField        txtCantidad;
    private JTextField        txtCliente;
    private JTextField        txtTelefono;
    private JComboBox<String> cmbMetodoPago;   // Strategy
    private JLabel            lblStock;
    private JLabel            lblEstado;       // State
    private JTextArea         txtLog;

    // Patrones
    private GestorVentas          gestorVentas;   // Observer - ConcreteSubject
    private GestorCommand         gestorCommand;  // Command  - Invoker
    private ReceptorVenta         receptor;       // Command  - Receiver
    private VentaEstadoContexto   estadoCtx;      // State    - Context
    private ContextoPago          contextoPago;   // Strategy - Context

    // Pilas para Undo/Redo
    private final Stack<Command> pilaUndo = new Stack<>();
    private final Stack<Command> pilaRedo = new Stack<>();

    private Venta ultimaVenta = null;
    private int   stockActual = 20;

    // Botones de acción
    private JButton btnRegistrar;
    private JButton btnUndo;
    private JButton btnRedo;
    private JButton btnPagar;
    private JButton btnEntregar;
    private JButton btnCancelar;

    public SistemaGUI() {
        configurarVentana();
        inicializarPatrones();
        actualizarBotones();
    }


    //  INICIALIZACIÓN DE PATRONES
    private void inicializarPatrones() {

        //  OBSERVER: suscribir observers
        gestorVentas = new GestorVentas();

        // Observer 1 → actualiza stock en la GUI
        gestorVentas.agregarObserver(venta -> {
            int anterior = stockActual;
            stockActual  = Math.max(stockActual - venta.getCantidad(), 0);
            lblStock.setText("Stock: " + stockActual + " uds.");
            log("[Observer-Stock] " + venta.getProducto().getNombre()
                    + ": " + anterior + " → " + stockActual);
        });

        // Observer 2 → notificación al cliente
        gestorVentas.agregarObserver(venta ->
                log("[Observer-Cliente] Notificación enviada a "
                        + venta.getCliente().getNombre()
                        + " | Total: S/. " + String.format("%.2f", venta.getTotal()))
        );

        // Observer 3 → auditoría
        gestorVentas.agregarObserver(venta -> {
            String hora = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            log("[Observer-Auditoría " + hora + "] Método: "
                    + venta.getMetodoPago());
        });

        // COMMAND
        gestorCommand = new GestorCommand();
        receptor      = new ReceptorVenta();

        //  STATE: contexto sin venta aún
        estadoCtx  = null;

        // STRATEGY
        contextoPago = new ContextoPago();
    }


    //  ACCIÓN: REGISTRAR VENTA (Command + Observer + Strategy)
    private void registrarVenta() {
        try {
            // Validar campos
            if (txtCliente.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa el nombre del cliente.");
                return;
            }
            int    cantidad = Integer.parseInt(txtCantidad.getText().trim());
            String metodoPago = (String) cmbMetodoPago.getSelectedItem();

            // Crear objetos del modelo
            Producto producto = cmbProducto.getSelectedIndex() == 0
                    ? new AbarroteCreator().crearProducto()
                    : new BebidaCreator().crearProducto();
            producto.setStock(stockActual);

            Cliente cliente = new Cliente(1,
                    txtCliente.getText().trim(),
                    txtTelefono.getText().trim().isEmpty() ? "999000000" : txtTelefono.getText().trim());
            Usuario vendedor = new Usuario(1, "Angel", Usuario.Rol.VENDEDOR);

            ultimaVenta = new VentaBuilder()
                    .setProducto(producto)
                    .setCliente(cliente)
                    .setVendedor(vendedor)
                    .setCantidad(cantidad)
                    .setFecha(LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .setMetodoPago(metodoPago)
                    .build();

            //  STRATEGY: seleccionar metodo de pago
            switch (metodoPago) {
                case "Efectivo" -> contextoPago.setMetodopago(new PagoEfectivo());
                case "Tarjeta"  -> contextoPago.setMetodopago(new PagoTarjeta());
                case "Yape"     -> contextoPago.setMetodopago(new PagoYape());
            }
            log("\n── Nueva venta ─────────────────────────");
            log("[Strategy] Método: " + metodoPago
                    + " | Total: S/. " + String.format("%.2f", ultimaVenta.getTotal()));

            //COMMAND: registrar venta
            Command cmd = new RegistrarVentaCommand(receptor, ultimaVenta);
            gestorCommand.setCommand(cmd);
            gestorCommand.ejecutarComando();
            pilaUndo.push(cmd);
            pilaRedo.clear();
            log("[Command] Venta registrada → pila undo: " + pilaUndo.size());

            // OBSERVER: notificar a todos los observers
            gestorVentas.registrarVenta(ultimaVenta);

            // STATE: iniciar ciclo de vida de la venta
            estadoCtx = new VentaEstadoContexto(ultimaVenta);
            actualizarEstado();

            actualizarBotones();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "La cantidad debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    //  COMMAND: UNDO / REDO
    private void deshacerVenta() {
        if (pilaUndo.isEmpty()) return;
        Command cmd = pilaUndo.pop();
        // Revertir: ejecutar CancelarVentaCommand como undo
        Command undo = new CancelarVentaCommand(receptor, ultimaVenta);
        undo.ejecutar();
        pilaRedo.push(cmd);
        log("↩️ [Command-Undo] Venta deshecha → redo disponible: " + pilaRedo.size());
        // Revertir stock
        stockActual = Math.min(stockActual + ultimaVenta.getCantidad(), 20);
        lblStock.setText("Stock: " + stockActual + " uds.");
        estadoCtx = null;
        actualizarEstado();
        actualizarBotones();
    }

    private void rehacerVenta() {
        if (pilaRedo.isEmpty()) return;
        Command cmd = pilaRedo.pop();
        cmd.ejecutar();
        pilaUndo.push(cmd);
        log("↪️ [Command-Redo] Venta rehecha");
        // Restaurar stock
        stockActual = Math.max(stockActual - ultimaVenta.getCantidad(), 0);
        lblStock.setText("Stock: " + stockActual + " uds.");
        estadoCtx = new VentaEstadoContexto(ultimaVenta);
        actualizarEstado();
        actualizarBotones();
    }

    //  STATE: transiciones del ciclo de vida
    private void pagarVenta() {
        if (estadoCtx == null) return;
        estadoCtx.pagar();
        log("[State] Transición: → PAGADA");
        actualizarEstado();
        actualizarBotones();
    }

    private void entregarVenta() {
        if (estadoCtx == null) return;
        estadoCtx.entregar();
        log("[State] Transición: → ENTREGADA");
        actualizarEstado();
        actualizarBotones();
    }

    private void cancelarVenta() {
        if (estadoCtx == null) return;
        estadoCtx.cancelar();
        log("[State] Transición: → CANCELADA");
        actualizarEstado();
        actualizarBotones();
    }

    private void actualizarEstado() {
        if (estadoCtx == null) {
            lblEstado.setText("Sin venta activa");
            lblEstado.setForeground(UIManager.getColor("Label.foreground"));
        } else {
            String est = estadoCtx.getNombreEstado();
            lblEstado.setText("Estado: " + est);
            lblEstado.setForeground(switch (est) {
                case "PENDIENTE"  -> new Color(180, 120,  0);
                case "PAGADA"     -> new Color( 30, 130, 30);
                case "ENTREGADA"  -> new Color( 20,  90,180);
                case "CANCELADA"  -> new Color(180,  30, 30);
                default           -> UIManager.getColor("Label.foreground");
            });
        }
    }

    private void actualizarBotones() {
        boolean hayVenta = estadoCtx != null;
        String  est      = hayVenta ? estadoCtx.getNombreEstado() : "";

        btnUndo.setEnabled(!pilaUndo.isEmpty());
        btnRedo.setEnabled(!pilaRedo.isEmpty());

        btnPagar.setEnabled(hayVenta && est.equals("PENDIENTE"));
        btnEntregar.setEnabled(hayVenta && est.equals("PAGADA"));
        btnCancelar.setEnabled(hayVenta &&
                (est.equals("PENDIENTE") || est.equals("PAGADA")));
    }

    // ════════════════════════════════════════════════════════════
    //  LOG
    // ════════════════════════════════════════════════════════════
    private void log(String msg) {
        txtLog.append(msg + "\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }

    // ════════════════════════════════════════════════════════════
    //  CONSTRUCCIÓN DE LA GUI
    // ════════════════════════════════════════════════════════════
    private void configurarVentana() {
        setTitle("Sistema de Ventas — Patrones Observer · Strategy · Command · State");
        setSize(700, 620);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        // ── Panel NORTE: formulario ──────────────────────────────
        JPanel pNorte = new JPanel(new GridLayout(1, 2, 8, 0));
        pNorte.setBorder(BorderFactory.createEmptyBorder(8, 8, 0, 8));

        // Subpanel izquierdo: datos de la venta
        JPanel pForm = new JPanel(new GridLayout(5, 2, 6, 6));
        pForm.setBorder(new TitledBorder("Datos de la venta"));

        pForm.add(new JLabel("Producto:"));
        cmbProducto = new JComboBox<>(new String[]{"Abarrote", "Bebida"});
        pForm.add(cmbProducto);

        pForm.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField("1");
        pForm.add(txtCantidad);

        pForm.add(new JLabel("Cliente:"));
        txtCliente = new JTextField("Juan Perez");
        pForm.add(txtCliente);

        pForm.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField("999888777");
        pForm.add(txtTelefono);

        pForm.add(new JLabel("Método de pago:"));
        cmbMetodoPago = new JComboBox<>(new String[]{"Efectivo", "Tarjeta", "Yape"});
        pForm.add(cmbMetodoPago);

        // Subpanel derecho: estado e indicadores
        JPanel pInfo = new JPanel(new GridLayout(3, 1, 6, 6));
        pInfo.setBorder(new TitledBorder("Estado del sistema"));

        lblStock  = new JLabel("Stock: " + stockActual + " uds.", SwingConstants.CENTER);
        lblStock.setFont(new Font("Arial", Font.BOLD, 13));

        lblEstado = new JLabel("Sin venta activa", SwingConstants.CENTER);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 13));

        pInfo.add(lblStock);
        pInfo.add(lblEstado);
        pInfo.add(new JLabel(""));

        pNorte.add(pForm);
        pNorte.add(pInfo);
        add(pNorte, BorderLayout.NORTH);

        // Panel CENTRO: log
        txtLog = new JTextArea();
        txtLog.setEditable(false);
        txtLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(txtLog);
        scroll.setBorder(new TitledBorder("Log de eventos (Observer · Command · State · Strategy)"));
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 8, 0, 8), scroll.getBorder()));
        add(scroll, BorderLayout.CENTER);

        //  Panel SUR: botones
        JPanel pSur = new JPanel(new GridLayout(2, 1, 4, 4));
        pSur.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));

        // Fila 1: acciones principales
        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));

        btnRegistrar = boton("Registrar Venta", new Color(46, 125, 50));
        btnUndo      = boton("Deshacer",         new Color(100, 100, 100));
        btnRedo      = boton("Rehacer",           new Color(100, 100, 100));

        fila1.add(btnRegistrar);
        fila1.add(btnUndo);
        fila1.add(btnRedo);

        // Fila 2: ciclo de vida (State)
        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        fila2.setBorder(new TitledBorder("Ciclo de vida del pedido (State)"));

        btnPagar    = boton("Pagar",    new Color(27, 94, 32));
        btnEntregar = boton("Entregar", new Color(13, 71, 161));
        btnCancelar = boton("Cancelar", new Color(183, 28, 28));

        fila2.add(btnPagar);
        fila2.add(btnEntregar);
        fila2.add(btnCancelar);

        pSur.add(fila1);
        pSur.add(fila2);
        add(pSur, BorderLayout.SOUTH);

        //  Listeners
        btnRegistrar.addActionListener(e -> registrarVenta());
        btnUndo.addActionListener(e      -> deshacerVenta());
        btnRedo.addActionListener(e      -> rehacerVenta());
        btnPagar.addActionListener(e     -> pagarVenta());
        btnEntregar.addActionListener(e  -> entregarVenta());
        btnCancelar.addActionListener(e  -> cancelarVenta());
    }

    private JButton boton(String texto, Color color) {
        JButton b = new JButton(texto);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        return b;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemaGUI().setVisible(true));
    }
}
