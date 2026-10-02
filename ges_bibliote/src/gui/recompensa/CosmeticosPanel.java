package recompensa;

import javax.swing.*;
import java.awt.*;
import modelo.Usuario;

public class CosmeticosPanel extends JPanel {

    private Usuario usuario;
    private final Runnable onChange;
    private JLabel actual;

    public CosmeticosPanel(Usuario usuario, Runnable onChange) {
        this.usuario = usuario;
        this.onChange = onChange;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Cosméticos disponibles", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel lista = new JPanel(new GridLayout(0, 1, 8, 8));
        if (usuario.getCosmeticos().isEmpty()) {
            lista.add(new JLabel("Aún no hay cosméticos desbloqueados.", SwingConstants.CENTER));
        }

        actual = new JLabel("Equipado: " + usuario.getBannerEquipado(), SwingConstants.CENTER);
        actual.setFont(new Font("Arial", Font.BOLD, 18));
        for (String cosmetic : usuario.getCosmeticos()) {
            JPanel item = new JPanel(new BorderLayout());
            item.add(new JLabel(cosmetic), BorderLayout.NORTH);
            ImageIcon icono = crearIcono(cosmetic);
            if (icono != null) {
                Image scaled = icono.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                item.add(new JLabel(new ImageIcon(scaled)), BorderLayout.WEST);
            }

            JButton equipar = new JButton(cosmetic.equals(usuario.getBannerEquipado())
                    ? "Equipado" : "Equipar");
            equipar.setEnabled(!cosmetic.equals(usuario.getBannerEquipado()));
            equipar.addActionListener(e -> {
                usuario.setBannerEquipado(cosmetic);
                actual.setText("Equipado: " + cosmetic);
                equipar.setText("Equipado");
                equipar.setEnabled(false);
                onChange.run();
            });
            item.add(equipar, BorderLayout.EAST);
            lista.add(item);
        }

        add(new JScrollPane(lista), BorderLayout.CENTER);
        add(actual, BorderLayout.SOUTH);
    }

    private ImageIcon crearIcono(String cosmetic) {
        int indice = switch (cosmetic) {
            case "Fondo Azul" -> 1;
            case "Marco Simple" -> 2;
            case "Icono Estrella" -> 3;
            case "Fondo Pastel" -> 4;
            case "Marco Dorado" -> 5;
            case "Banner Legendario" -> 7;
            default -> 0;
        };
        String extension = indice == 5 ? ".jpeg" : ".png";
        java.net.URL recurso = getClass().getResource("/img/logro" + indice + extension);
        return recurso == null ? null : new ImageIcon(recurso);
    }
}