package Configuracion;

import modelo.AppTheme;
import modelo.Usuario;
import modelo.GestorUsuarios;
import Login.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ConfiguracionFrame extends JFrame {

    private final Usuario usuario;
    private final List<Usuario> usuarios;

    private JLabel tiempoLabel;
    private JCheckBox chkModoOscuro;
    private JComboBox<String> comboFuente;
    private JSpinner spinnerTamano;
    private JButton btnColorTexto;
    private JButton btnColorFondo;
    private JButton btnCerrarSesion;

    private Timer timerTiempo;

    public ConfiguracionFrame(Usuario usuario, List<Usuario> usuarios) {
        this.usuario = usuario;
        this.usuarios = usuarios;

        setTitle("Ajustes - " + usuario.getNombre());
        setSize(470, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        construirInterfaz();
        iniciarTimerTiempo();
        aplicarTema();

        setVisible(true);
    }

    private void construirInterfaz() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        //PANEL INFERIOR
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setOpaque(false);

        JLabel titulo = new JLabel("Ajustes de la aplicación");
        titulo.setFont(AppTheme.getFuente().deriveFont(Font.BOLD, AppTheme.tamanoFuente + 2));

        tiempoLabel = new JLabel("Tiempo en la app: " + AppTheme.getTiempoEnApp());
        tiempoLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        topPanel.add(titulo, BorderLayout.WEST);
        topPanel.add(tiempoLabel, BorderLayout.EAST);

        //EL PANEL CENTRAL
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        centerPanel.setLayout(new GridLayout(6, 2, 10, 10));

        // MODO OSCURO
        centerPanel.add(new JLabel("Modo oscuro:"));
        chkModoOscuro = new JCheckBox("Activar");
        chkModoOscuro.setSelected(AppTheme.modoOscuro);
        chkModoOscuro.addActionListener(e -> cambiarModoOscuro());
        centerPanel.add(chkModoOscuro);

        // FUENTE DE LA LETRA
        centerPanel.add(new JLabel("Tipo de letra:"));
        comboFuente = new JComboBox<>(new String[]{"Arial", "Serif", "Monospaced"});
        comboFuente.setSelectedItem(AppTheme.nombreFuente);
        comboFuente.addActionListener(e -> {
            AppTheme.nombreFuente = comboFuente.getSelectedItem().toString();
            aplicarTema();
        });
        centerPanel.add(comboFuente);

        // TAMAÑO DE LA LETRA
        centerPanel.add(new JLabel("Tamaño de letra:"));
        spinnerTamano = new JSpinner(new SpinnerNumberModel(AppTheme.tamanoFuente, 10, 30, 1));
        spinnerTamano.addChangeListener(e -> {
            AppTheme.tamanoFuente = (int) spinnerTamano.getValue();
            aplicarTema();
        });
        centerPanel.add(spinnerTamano);

        // COLOR DEL TEXTO
        centerPanel.add(new JLabel("Color de letra:"));
        btnColorTexto = new JButton("Elegir color de texto");
        btnColorTexto.addActionListener(e -> elegirColorTexto());
        centerPanel.add(btnColorTexto);

        // EL COLOR DE FONDO
        centerPanel.add(new JLabel("Color de fondo:"));
        btnColorFondo = new JButton("Elegir color de fondo");
        btnColorFondo.addActionListener(e -> elegirColorFondo());
        centerPanel.add(btnColorFondo);

        // PARA EL ESPACIO VACIO
        centerPanel.add(new JLabel(""));
        centerPanel.add(new JLabel(""));

        // PARA PANEL INFERIOR
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        bottomPanel.add(btnCerrarSesion);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    //ACA COMIENZA LA LOGICA
    private void iniciarTimerTiempo() {
        timerTiempo = new Timer(1000, e -> tiempoLabel.setText("Tiempo en la app: " + AppTheme.getTiempoEnApp()));
        timerTiempo.start();
    }

    private void cambiarModoOscuro() {
        AppTheme.modoOscuro = chkModoOscuro.isSelected();
        if (AppTheme.modoOscuro) {
            AppTheme.colorFondo = Color.DARK_GRAY;
            AppTheme.colorTexto = Color.WHITE;
        } else {
            AppTheme.colorFondo = Color.decode("#e28000");
            AppTheme.colorTexto = Color.BLACK;
        }
        aplicarTema();
    }

    private void elegirColorTexto() {
        Color elegido = JColorChooser.showDialog(this, "Seleccionar color de texto", AppTheme.colorTexto);
        if (elegido != null) {
            AppTheme.colorTexto = elegido;
            aplicarTema();
        }
    }

    private void elegirColorFondo() {
        Color elegido = JColorChooser.showDialog(this, "Seleccionar color de fondo", AppTheme.colorFondo);
        if (elegido != null) {
            AppTheme.colorFondo = elegido;
            aplicarTema(); //DENTRO DE CADA CONSTRUCTOR
        }
    }

    private void cerrarSesion() {
        GestorUsuarios.guardarUsuarios(usuarios, "data.dat");
        if (timerTiempo != null) timerTiempo.stop();
        new LoginFrame();
        dispose();
    }

    // -------------------- APLICAR TEMA GLOBAL (PARA TODOS LOS CODIGOS)---------------------

    private void aplicarTema() {
        aplicarTemaRecursivo(this.getContentPane());
        revalidate();
        repaint();
    }

    private void aplicarTemaRecursivo(Component comp) {

        comp.setFont(AppTheme.getFuente());
        comp.setForeground(AppTheme.colorTexto);

        if (comp instanceof JComponent) {
            comp.setBackground(AppTheme.colorFondo);
        }

        if (comp instanceof Container cont) {
            for (Component hijo : cont.getComponents()) {
                aplicarTemaRecursivo(hijo);
            }
        }
    }
}
