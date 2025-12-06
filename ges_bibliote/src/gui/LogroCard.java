/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LogroCard extends JPanel {

    private ImageIcon iconoBloqueado;
    private ImageIcon iconoDesbloqueado;

    private int diasRequeridos;
    private String caption;

    private boolean desbloqueado;

    public LogroCard(String rutaDesbloqueado, String rutaBloqueado, int diasRequeridos, String caption) {
        this.diasRequeridos = diasRequeridos;
        this.caption = caption;

        this.iconoDesbloqueado = new ImageIcon(getClass().getResource(rutaDesbloqueado));
        this.iconoBloqueado = new ImageIcon(getClass().getResource(rutaBloqueado));

        setOpaque(false);
        setPreferredSize(new Dimension(130, 150)); // tamaño base
    }

    public void actualizar(int diasRachaUsuario) {
        desbloqueado = diasRachaUsuario >= diasRequeridos;
        repaint();
    }

    private Image getScaledIcon(ImageIcon icon) {
        int w = getWidth() - 20;
        int h = getHeight() - 50;  
        return icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Activar antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Sombra de atrás pa' que se vea bonito
        g2.setColor(new Color(0, 0, 0, 120));
        g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);

        // Fondo tarjeta, es para que se vea más bonito. Al estilo de Duolinfo
        g2.setColor(new Color(45, 45, 45));
        g2.fillRoundRect(0, 0, getWidth() - 10, getHeight() - 10, 20, 20);

        // Icono escalado, pues las imágenes pueden tener muchas dimensiones. Mi hermano, solo quiero dormir
        Image img = getScaledIcon(desbloqueado ? iconoDesbloqueado : iconoBloqueado);
        int imgX = 10;
        int imgY = 10;
        g2.drawImage(img, imgX, imgY, null);

        // Caption de los logros
        g2.setFont(new Font("Arial", Font.BOLD, 13));
        g2.setColor(desbloqueado ? new Color(255, 215, 0) : Color.GRAY);
        g2.drawString(caption, 10, getHeight() - 20);

        g2.dispose();
    }
}
