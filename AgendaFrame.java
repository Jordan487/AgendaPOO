import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgendaFrame extends JFrame {
    private JTable tablaEventos;
    private DefaultTableModel modeloTabla;
    private JSpinner spinnerFecha;
    private JSpinner spinnerHora;
    private JTextField txtDescripcion;
    private JButton btnAgregar, btnEliminar, btnSalir;

    public AgendaFrame() {
        setTitle("Agenda Personal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // usar posiciones absolutas

        // ----- Etiquetas -----
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(20, 20, 80, 25);
        add(lblFecha);

        JLabel lblHora = new JLabel("Hora:");
        lblHora.setBounds(20, 60, 80, 25);
        add(lblHora);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(20, 100, 80, 25);
        add(lblDescripcion);

        // ----- Entradas -----
        spinnerFecha = new JSpinner(new SpinnerDateModel());
        spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy"));
        spinnerFecha.setBounds(100, 20, 120, 25);
        add(spinnerFecha);

        spinnerHora = new JSpinner(new SpinnerDateModel());
        spinnerHora.setEditor(new JSpinner.DateEditor(spinnerHora, "HH:mm"));
        spinnerHora.setBounds(100, 60, 120, 25);
        add(spinnerHora);

        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(100, 100, 200, 25);
        add(txtDescripcion);

        // ----- Tabla -----
        modeloTabla = new DefaultTableModel(new Object[]{"Fecha", "Hora", "Descripción"}, 0);
        tablaEventos = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaEventos);
        scroll.setBounds(20, 150, 540, 150);
        add(scroll);

        // ----- Botones -----
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(20, 320, 100, 30);
        add(btnAgregar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(140, 320, 120, 30);
        add(btnEliminar);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(280, 320, 100, 30);
        add(btnSalir);

        // ----- Eventos -----
        btnAgregar.addActionListener(e -> agregarEvento());
        btnEliminar.addActionListener(e -> eliminarEvento());
        btnSalir.addActionListener(e -> dispose());
    }

    private void agregarEvento() {
        String descripcion = txtDescripcion.getText().trim();
        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La descripción no puede estar vacía",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Date fecha = (Date) spinnerFecha.getValue();
        Date hora = (Date) spinnerHora.getValue();

        SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");

        modeloTabla.addRow(new Object[]{
                sdfFecha.format(fecha),
                sdfHora.format(hora),
                descripcion
        });

        txtDescripcion.setText("");
        txtDescripcion.requestFocus();
    }

    private void eliminarEvento() {
        int fila = tablaEventos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un evento primero",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el evento seleccionado?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            modeloTabla.removeRow(fila);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AgendaFrame().setVisible(true);
        });
    }
}