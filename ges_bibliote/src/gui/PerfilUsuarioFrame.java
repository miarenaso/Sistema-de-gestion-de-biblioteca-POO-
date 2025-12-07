package gui;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import modelo.*;

public class PerfilUsuarioFrame extends JFrame {

    private final Usuario usuario;

    public PerfilUsuarioFrame(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Perfil de Usuario");
        setSize(450, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // ----- COLORES DESDE AppTheme -----
        Color colorFondo = AppTheme.colorFondo;
        Color colorTexto = AppTheme.colorTexto;

        JPanel panel = new JPanel();
        panel.setBackground(colorFondo);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // ----- TÍTULO -----
        JLabel titulo = new JLabel("Perfil del Usuario");
        titulo.setFont(new Font(AppTheme.nombreFuente, Font.BOLD, 22));
        titulo.setForeground(colorTexto);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));

        // ----- DATOS DEL USUARIO -----
        JLabel lblNombre = new JLabel("Nombre: " + usuario.getNombre());
        JLabel lblCorreo = new JLabel("Correo: " + usuario.getCorreo());
        JLabel lblId = new JLabel("ID de Usuario: " + usuario.getId());

        lblNombre.setForeground(colorTexto);
        lblCorreo.setForeground(colorTexto);
        lblId.setForeground(colorTexto);

        lblNombre.setFont(AppTheme.getFuente());
        lblCorreo.setFont(AppTheme.getFuente());
        lblId.setFont(AppTheme.getFuente());

        panel.add(lblNombre);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblCorreo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblId);
        panel.add(Box.createVerticalStrut(20));

        // ----- FECHA -----
        String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        JLabel lblFecha = new JLabel("Fecha actual: " + fechaHoy);
        lblFecha.setForeground(colorTexto);
        lblFecha.setFont(AppTheme.getFuente());
        lblFecha.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lblFecha);
        panel.add(Box.createVerticalStrut(20));

        // ----- TIEMPO EN LA APP -----
        JLabel lblTiempo = new JLabel("Tiempo en la aplicación: " + AppTheme.getTiempoEnApp());
        lblTiempo.setForeground(colorTexto);
        lblTiempo.setFont(AppTheme.getFuente());
        lblTiempo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lblTiempo);

        add(panel);
        setVisible(true);
    }
}
