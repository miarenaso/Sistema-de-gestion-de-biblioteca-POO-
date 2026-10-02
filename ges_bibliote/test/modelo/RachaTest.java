package modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class RachaTest {
    @Test
    void iniciaYContinuaUnaRachaEnDiasConsecutivos() {
        Racha racha = new Racha(12);
        LocalDate primerDia = LocalDate.of(2025, 3, 1);

        racha.actualizarRacha(true, primerDia);
        racha.actualizarRacha(true, primerDia.plusDays(1));

        assertEquals(12, racha.getId());
        assertEquals(2, racha.getDiasConsecutivos());
        assertEquals(primerDia.plusDays(1), racha.getUltimaFechaLectura());
    }

    @Test
    void reiniciaLaRachaTrasUnDiaSinLectura() {
        Racha racha = new Racha(12);
        LocalDate primerDia = LocalDate.of(2025, 3, 1);
        racha.actualizarRacha(true, primerDia);
        racha.actualizarRacha(true, primerDia.plusDays(1));

        racha.actualizarRacha(true, primerDia.plusDays(3));

        assertEquals(1, racha.getDiasConsecutivos());
        assertEquals(primerDia.plusDays(3), racha.getUltimaFechaLectura());
    }

    @Test
    void noModificaLaRachaCuandoNoLeeEseDia() {
        Racha racha = new Racha(12);
        LocalDate fecha = LocalDate.of(2025, 3, 1);
        racha.actualizarRacha(true, fecha);

        racha.actualizarRacha(false, fecha.plusDays(1));

        assertEquals(1, racha.getDiasConsecutivos());
        assertEquals(fecha, racha.getUltimaFechaLectura());
    }
}