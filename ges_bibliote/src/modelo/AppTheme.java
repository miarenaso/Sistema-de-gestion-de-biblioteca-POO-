package modelo;

import java.awt.Color;
import java.awt.Font;

public class AppTheme {

    public static boolean modoOscuro = false; //MODO OSCURO

    public static Color colorFondo = Color.decode("#e28000"); //COLORES GLOBALES
    public static Color colorTexto = Color.BLACK;
  
    public static String nombreFuente = "Arial"; //FUENTE GLOBAL
    public static int tamanoFuente = 14;

    public static long tiempoInicio = System.currentTimeMillis(); //TIEMPO DENTRO DE LA APP

    public static String getTiempoEnApp() { //PARA QUE EL TIEMPO SE VEA EN HH/MM/SS
        long ahora = System.currentTimeMillis();
        long diff = ahora - tiempoInicio;

        long segundos = (diff / 1000) % 60;
        long minutos  = (diff / (1000 * 60)) % 60;
        long horas    =  diff / (1000 * 60 * 60);

        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }

    public static Font getFuente() { //FUENTE GLOBAL
        return new Font(nombreFuente, Font.PLAIN, tamanoFuente);
    }
}
