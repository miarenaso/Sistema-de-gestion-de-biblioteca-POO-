package recompensa;

import javax.swing.*;
import java.awt.*;
import modelo.Usuario;

public class MainFrame extends JFrame {

    private BannerPanel bannerPanel;
    private MenuPanel menuPanel;
    private JPanel panelCentral;

    private Usuario usuario;
    private final Runnable onChange;

    public MainFrame(Usuario usuario, Runnable onChange) {
        this.usuario = usuario;
        this.onChange = onChange;
        setTitle("Gestor de Lectura - " + usuario.getNombre());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Banner superior
        bannerPanel = new BannerPanel(usuario);
        add(bannerPanel, BorderLayout.NORTH);

        // Panel lateral con menú
        menuPanel = new MenuPanel(this);
        add(menuPanel, BorderLayout.WEST);

        // Panel central dinámico
        panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());
        add(panelCentral, BorderLayout.CENTER);

    }

    // Método para cambiar el panel central
    public void cambiarPanel(JPanel nuevoPanel) {
        panelCentral.removeAll();
        panelCentral.add(nuevoPanel, BorderLayout.CENTER);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void mostrarCosmeticos() {
        cambiarPanel(new CosmeticosPanel(usuario, () -> {
            bannerPanel.repaint();
            onChange.run();
        }));
    }
}
