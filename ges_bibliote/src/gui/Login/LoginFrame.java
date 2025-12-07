package Login;

import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginFrame extends JFrame {

    private JTextField correoField;
    private JPasswordField passField;

    private List<Usuario> usuarios;

    public LoginFrame() {

        usuarios = GestorUsuarios.cargarUsuarios("data.dat");  //CARGAR USUARIOS DESDE DATA.DAT

        setTitle("Inicio de Sesión"); //CONFIGURACIÓN GENERAL DE VENTANA
        setSize(400, 280);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        Color naranja = Color.decode("#e28000");   //FONDO (COLORES PERSONALIZADOS)
        Color negro   = Color.BLACK;               // TEXTO

        JPanel panel = new JPanel(); //PANEL PRINCIPAL
        panel.setBackground(naranja);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titulo = new JLabel("Biblioteca - Inicio"); //TITULO
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(negro);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(15));

        JLabel lblCorreo = new JLabel("Correo / Usuario:"); //CAMPO DE USUARIO
        lblCorreo.setForeground(negro);
        lblCorreo.setAlignmentX(Component.CENTER_ALIGNMENT);

        correoField = new JTextField(20);
        correoField.setMaximumSize(correoField.getPreferredSize());

        panel.add(lblCorreo);
        panel.add(correoField);
        panel.add(Box.createVerticalStrut(10));

        JLabel lblPass = new JLabel("Contraseña:"); //CAMPO DE CONTRASEÑA
        lblPass.setForeground(negro);
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);

        passField = new JPasswordField(20);
        passField.setMaximumSize(passField.getPreferredSize());

        panel.add(lblPass);
        panel.add(passField);
        panel.add(Box.createVerticalStrut(20));

        JButton btnLogin = new JButton("Iniciar Sesión"); //BOTONES
        JButton btnRegistro = new JButton("Crear Usuario");

        btnLogin.setBackground(Color.WHITE);
        btnRegistro.setBackground(Color.WHITE);
        btnLogin.setForeground(negro);
        btnRegistro.setForeground(negro);

        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegistro.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnLogin.addActionListener(e -> login()); //EVENTOS DE LOS BOTONES
        btnRegistro.addActionListener(e -> registrar());

        panel.add(btnLogin);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnRegistro);

        add(panel);
        setVisible(true);
    }

    private void login() { //METODO PARA EL INICIO DE SESIÓN
        String correo = correoField.getText().trim();
        String pass = new String(passField.getPassword());

        for (Usuario u : usuarios) {
            if (u.getCorreo().equals(correo) && u.getContrasena().equals(pass)) {
                JOptionPane.showMessageDialog(this, "Bienvenido, " + u.getNombre());

                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
    }


    private void registrar() { //METODO PARA REGISTRAR UN NUEVO USUARIO
        String correo = correoField.getText().trim();
        String pass = new String(passField.getPassword());

        if (correo.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar correo y contraseña.");
            return;
        }

        for (Usuario u : usuarios) { //VERIFICACION DE EXISTENCIA DE USUARIO
            if (u.getCorreo().equals(correo)) {
                JOptionPane.showMessageDialog(this, "Este correo ya está registrado.");
                return;
            }
        }

        String nombre = JOptionPane.showInputDialog("Ingrese su nombre:"); //POR SI SE PIERDE EL NOMBRE DE USUARIO

        if (nombre == null || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Registro cancelado.");
            return;
        }

        long nuevoId = usuarios.size() + 1;  //CRERAR UN NUEVO USUARIO
        Usuario nuevo = new Usuario(nombre, correo, pass, nuevoId);
        usuarios.add(nuevo);

        GestorUsuarios.guardarUsuarios(usuarios, "data.dat"); //GUARDAR EL ARCHIVO

        JOptionPane.showMessageDialog(this, "Usuario creado exitosamente.");
}
}