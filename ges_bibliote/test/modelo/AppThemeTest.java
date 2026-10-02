package modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Font;
import org.junit.jupiter.api.Test;

class AppThemeTest {
    @Test
    void creaLaFuenteConLaConfiguracionActual() {
        String fuenteAnterior = AppTheme.nombreFuente;
        int tamanoAnterior = AppTheme.tamanoFuente;
        try {
            AppTheme.nombreFuente = "Dialog";
            AppTheme.tamanoFuente = 18;

            Font fuente = AppTheme.getFuente();

            assertEquals("Dialog", fuente.getName());
            assertEquals(Font.PLAIN, fuente.getStyle());
            assertEquals(18, fuente.getSize());
        } finally {
            AppTheme.nombreFuente = fuenteAnterior;
            AppTheme.tamanoFuente = tamanoAnterior;
        }
    }

    @Test
    void devuelveElTiempoConFormatoDeHorasMinutosYSegundos() {
        long inicioAnterior = AppTheme.tiempoInicio;
        try {
            AppTheme.tiempoInicio = System.currentTimeMillis() - 123_000L;

            String tiempo = AppTheme.getTiempoEnApp();

            assertTrue(tiempo.matches("\\d{2,}:\\d{2}:\\d{2}"));
        } finally {
            AppTheme.tiempoInicio = inicioAnterior;
        }
    }
}