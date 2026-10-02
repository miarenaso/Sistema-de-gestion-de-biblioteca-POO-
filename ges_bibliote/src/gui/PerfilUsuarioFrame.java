package gui;

import modelo.*;
import Login.LoginFrame;
import java.awt.*;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import Configuracion.ConfiguracionFrame;
import bibliotecaui.BibliotecaUI;
import recompensa.MainFrame;

public class PerfilUsuarioFrame extends JFrame {

    private final Usuario usuario;
    private final List<Usuario> usuarios;
    private JPanel panelCentral; // Panel dinámico para mostrar contenido
    private JToggleButton btnPantallaCompleta;
    private Rectangle boundsVentana;
    private GraphicsDevice dispositivoPantalla;
    private boolean pantallaCompleta;

    public PerfilUsuarioFrame(Usuario usuario, List<Usuario> usuarios) {
        this.usuario = usuario;
        this.usuarios = usuarios;

        setTitle("Perfil de Usuario - " + usuario.getNombre());
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel lateral con botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panelBotones.setBackground(AppTheme.colorFondo);

        JButton btnPerfil = new JButton("Perfil");
        JButton btnBiblioteca = new JButton("Biblioteca");
        JButton btnConfiguracion = new JButton("Configuración");
        JButton btnRacha = new JButton("Racha / Recompensas");
        btnPantallaCompleta = new JToggleButton("Pantalla completa (F11)");
        JButton btnSalir = new JButton("Cerrar");

        btnPerfil.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBiblioteca.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConfiguracion.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRacha.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPantallaCompleta.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelBotones.add(btnPerfil);
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(btnBiblioteca);
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(btnConfiguracion);
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(btnRacha);
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(btnPantallaCompleta);
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.WEST);

        // Panel central
        panelCentral = new JPanel(new BorderLayout());
        add(panelCentral, BorderLayout.CENTER);

        // Mostrar panel inicial de perfil
        mostrarPanelPerfil();

        // Eventos
        btnPerfil.addActionListener(e -> mostrarPanelPerfil());
        btnBiblioteca.addActionListener(e -> mostrarBiblioteca());
        btnConfiguracion.addActionListener(e -> mostrarConfiguracion());
        btnRacha.addActionListener(e -> mostrarRacha());
        btnPantallaCompleta.addActionListener(e -> cambiarPantallaCompleta(btnPantallaCompleta.isSelected()));
        btnSalir.addActionListener(e -> dispose());
        configurarAtajosPantalla();

        setVisible(true);
    }

    private void configurarAtajosPantalla() {
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("F11"), "alternarPantallaCompleta");
        getRootPane().getActionMap().put("alternarPantallaCompleta", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent event) {
                btnPantallaCompleta.setSelected(!pantallaCompleta);
                cambiarPantallaCompleta(!pantallaCompleta);
            }
        });
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ESCAPE"), "salirPantallaCompleta");
        getRootPane().getActionMap().put("salirPantallaCompleta", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent event) {
                if (pantallaCompleta) {
                    btnPantallaCompleta.setSelected(false);
                    cambiarPantallaCompleta(false);
                }
            }
        });
    }

    private void cambiarPantallaCompleta(boolean activar) {
        if (activar == pantallaCompleta) return;

        if (activar) {
            boundsVentana = getBounds();
            dispositivoPantalla = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice();
            dispose();
            setUndecorated(true);
            setVisible(true);
            dispositivoPantalla.setFullScreenWindow(this);
            pantallaCompleta = true;
        } else {
            dispositivoPantalla.setFullScreenWindow(null);
            dispose();
            setUndecorated(false);
            setBounds(boundsVentana);
            setVisible(true);
            pantallaCompleta = false;
        }
    }

    private void mostrarPanelPerfil() {
        panelCentral.removeAll();
        JPanel perfilPanel = new JPanel();
        perfilPanel.setLayout(new BoxLayout(perfilPanel, BoxLayout.Y_AXIS));
        perfilPanel.setBackground(AppTheme.colorFondo);
        perfilPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Datos del usuario
        JLabel titulo = new JLabel("Perfil del Usuario");
        titulo.setFont(new Font(AppTheme.nombreFuente, Font.BOLD, 22));
        titulo.setForeground(AppTheme.colorTexto);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblNombre = new JLabel("Nombre: " + usuario.getNombre());
        JLabel lblCorreo = new JLabel("Correo: " + usuario.getCorreo());
        JLabel lblId = new JLabel("ID: " + usuario.getId());
        String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        JLabel lblFecha = new JLabel("Fecha: " + fechaHoy);

        lblNombre.setForeground(AppTheme.colorTexto);
        lblCorreo.setForeground(AppTheme.colorTexto);
        lblId.setForeground(AppTheme.colorTexto);
        lblFecha.setForeground(AppTheme.colorTexto);

        lblNombre.setFont(AppTheme.getFuente());
        lblCorreo.setFont(AppTheme.getFuente());
        lblId.setFont(AppTheme.getFuente());
        lblFecha.setFont(AppTheme.getFuente());

        perfilPanel.add(titulo);
        perfilPanel.add(Box.createVerticalStrut(20));
        perfilPanel.add(lblNombre);
        perfilPanel.add(Box.createVerticalStrut(10));
        perfilPanel.add(lblCorreo);
        perfilPanel.add(Box.createVerticalStrut(10));
        perfilPanel.add(lblId);
        perfilPanel.add(Box.createVerticalStrut(10));
        perfilPanel.add(lblFecha);

        panelCentral.add(perfilPanel, BorderLayout.CENTER);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    private void mostrarBiblioteca() {
        panelCentral.removeAll();
        BibliotecaUI bibliotecaUI = new BibliotecaUI(usuario, this::guardarUsuarios, this::registrarLectura);
        panelCentral.add(bibliotecaUI.getContentPane(), BorderLayout.CENTER);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    private void guardarUsuarios() {
        GestorUsuarios.guardarUsuarios(usuarios, "data.dat");
    }

    private void registrarLectura() {
        usuario.getRacha().actualizarRacha(true, LocalDate.now());
        GestorCosmeticos.desbloquearSegunRacha(usuario);
        GestorCosmeticos.actualizarTitulo(usuario);
        guardarUsuarios();
    }

    private void mostrarConfiguracion() {
        panelCentral.removeAll();
        ConfiguracionFrame configFrame = new ConfiguracionFrame(usuario, usuarios, () -> {
            dispose();
            new LoginFrame();
        });
        panelCentral.add(configFrame.getContentPane(), BorderLayout.CENTER);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    private void mostrarRacha() {
        panelCentral.removeAll();
        MainFrame rachaFrame = new MainFrame(usuario, this::guardarUsuarios);
        panelCentral.add(rachaFrame.getContentPane(), BorderLayout.CENTER);
        panelCentral.revalidate();
        panelCentral.repaint();
    }
}
