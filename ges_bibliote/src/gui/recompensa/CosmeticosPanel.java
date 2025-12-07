package recompensa;

import javax.swing.*;
import java.awt.*;
import modelo.Usuario;

public class CosmeticosPanel extends JPanel {

    private Usuario usuario;

    public CosmeticosPanel(Usuario usuario) {
        this.usuario = usuario;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Cosméticos disponibles", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel lista = new JPanel();
        lista.setLayout(new GridLayout(0, 1));

        for (String cosmetic : usuario.getCosmeticos()) {
            JPanel item = new JPanel(new BorderLayout());
            item.add(new JLabel(" • " + cosmetic), BorderLayout.CENTER);

            JButton equipar = new JButton("Equipar");
            equipar.addActionListener(e -> usuario.setBannerEquipado(cosmetic));

            item.add(equipar, BorderLayout.EAST);
            lista.add(item);
        }

        add(new JScrollPane(lista), BorderLayout.CENTER);

        //NUEVO
        JLabel actual = new JLabel("Equipado: " + usuario.getBannerEquipado(), SwingConstants.CENTER);
        actual.setFont(new Font("Arial", Font.BOLD, 18));
        add(actual, BorderLayout.SOUTH);
        
        //NUEVO
        
        for (String cosmetic : usuario.getCosmeticos()) {

            JPanel item = new JPanel(new BorderLayout());

            JLabel nombre = new JLabel(" " + cosmetic);
            item.add(nombre, BorderLayout.NORTH);

            // Imagen
            int indice = usuario.getCosmeticos().indexOf(cosmetic);
            ImageIcon img = new ImageIcon(getClass().getResource("/img/logro" + indice + ".png"));
            Image scaled = img.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            JLabel imagen = new JLabel(new ImageIcon(scaled));

            item.add(imagen, BorderLayout.WEST);

            JButton equipar = new JButton("Equipar");
            equipar.addActionListener(e -> usuario.setBannerEquipado(cosmetic));

            item.add(equipar, BorderLayout.EAST);
            lista.add(item);
            
        }       
    }
}