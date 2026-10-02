package modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RecordatorioTest {
    @Test
    void conservaFrecuenciaYMensajeConfigurados() {
        Recordatorio recordatorio = new Recordatorio("Diaria", "Leer 20 páginas");

        assertEquals("Diaria", recordatorio.getFrecuencia());
        assertEquals("Leer 20 páginas", recordatorio.getMensaje());
    }
}