package gui;

import modelo.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import configuracion.ConfiguracionFrame;
import recompensa.MainFrame;

public class PerfilUsuarioFrame extends JFrame {

    private final Usuario usuario;
    private JPanel panelCentral; // Panel dinámico para mostrar contenido

    public PerfilUsuarioFrame(Usuario usuario) {
        this.usuario = usuario;

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
        JButton btnSalir = new JButton("Cerrar");

        btnPerfil.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBiblioteca.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConfiguracion.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRacha.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelBotones.add(btnPerfil);
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(btnBiblioteca);
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(btnConfiguracion);
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(btnRacha);
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
        btnSalir.addActionListener(e -> dispose());

        setVisible(true);
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
        BibliotecaUI bibliotecaUI = new BibliotecaUI();
        // Quitar setVisible(true) en BibliotecaUI
        panelCentral.add(bibliotecaUI.getContentPane(), BorderLayout.CENTER);

        // Agregar botón de regresar al perfil
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(e -> mostrarPanelPerfil());
        JPanel botonPanel = new JPanel();
        botonPanel.add(btnRegresar);
        bibliotecaUI.getContentPane().add(botonPanel, BorderLayout.SOUTH);

        panelCentral.revalidate();
        panelCentral.repaint();
    }

    private void mostrarConfiguracion() {
        panelCentral.removeAll();
        ConfiguracionFrame configFrame = new ConfiguracionFrame(usuario, GestorUsuarios.cargarUsuarios("data.dat"));
        panelCentral.add(configFrame.getContentPane(), BorderLayout.CENTER);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    private void mostrarRacha() {
        panelCentral.removeAll();
        MainFrame rachaFrame = new MainFrame(usuario);
        panelCentral.add(rachaFrame.getContentPane(), BorderLayout.CENTER);
        panelCentral.revalidate();
        panelCentral.repaint();
    }
}
