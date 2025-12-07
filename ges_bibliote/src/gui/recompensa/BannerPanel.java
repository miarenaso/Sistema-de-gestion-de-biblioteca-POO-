package recompensa;

import javax.swing.*;
import java.awt.*;
import modelo.Usuario;

public class BannerPanel extends JPanel {

    private Usuario usuario;

    public BannerPanel(Usuario usuario) {
        this.usuario = usuario;
        setPreferredSize(new Dimension(900, 120));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fondo del banner
        g.setColor(new Color(60, 90, 150));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Texto del usuario
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString("Usuario: " + usuario.getNombre(), 20, 40);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Racha: " + usuario.getRacha().getDiasConsecutivos() + " dias ", 20, 80);

        //NUEVO
        String banner = usuario.getBannerEquipado();

        switch(banner) {
            case "Fondo Azul":
                g.setColor(new Color(70, 120, 200));
                break;

            case "Fondo Pastel":
                g.setColor(new Color(240, 200, 220));
                break;

            case "Marco Dorado":
                g.setColor(new Color(230, 190, 40));
                break;

            default:
                g.setColor(new Color(60, 90, 150));
        }
        g.fillRect(0, 0, getWidth(), getHeight());


        //NUEVO
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString("Usuario: " + usuario.getNombre(), 20, 40);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Titulo: " + usuario.getTitulo(), 20, 65);

        g.drawString("Racha: " + usuario.getRacha().getDiasConsecutivos() + " dias ", 20, 90);

        //NUEVO

        ImageIcon icono = obtenerIconoRacha(usuario.getRacha().getDiasConsecutivos());
        Image img = icono.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        g.drawImage(img, getWidth() - 120, 15, null);


    }

    //NUEVO
    private ImageIcon obtenerIconoRacha(int racha) {
    int indice = 0;

    if (racha >= 1) indice = 1;
    if (racha >= 3) indice = 2;
    if (racha >= 5) indice = 3;
    if (racha >= 7) indice = 4;
    if (racha >= 10) indice = 5;
    if (racha >= 15) indice = 6;
    if (racha >= 20) indice = 7;
    if (racha >= 30) indice = 8;

    return new ImageIcon(getClass().getResource("/img/logro" + indice + ".png"));
}

}