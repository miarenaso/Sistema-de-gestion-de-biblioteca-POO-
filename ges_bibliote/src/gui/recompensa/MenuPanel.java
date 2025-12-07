package recompensa;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel(MainFrame frame) {
        setLayout(new GridLayout(10, 1));
        setPreferredSize(new Dimension(160, 0));

        JButton btnCosmeticos = new JButton("Cosmeticos");
        btnCosmeticos.addActionListener(e -> {
            frame.cambiarPanel(new CosmeticosPanel(frame.getUsuario()));
        });

        add(new JLabel(" Menu", SwingConstants.CENTER));
        add(btnCosmeticos);
    }
}