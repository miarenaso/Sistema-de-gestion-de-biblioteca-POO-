package modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class GestorCosmeticosTest {
    private Usuario crearUsuarioConRacha(int dias) {
        Racha racha = new Racha(1);
        LocalDate fecha = LocalDate.of(2025, 1, 1);
        for (int dia = 0; dia < dias; dia++) {
            racha.actualizarRacha(true, fecha.plusDays(dia));
        }

        Usuario usuario = new Usuario("Ana", "ana@example.test", "clave", 1);
        usuario.setRacha(racha);
        return usuario;
    }

    @Test
    void desbloqueaCosmeticosHastaLaRachaAlcanzadaSinDuplicarlos() {
        Usuario usuario = crearUsuarioConRacha(20);

        GestorCosmeticos.desbloquearSegunRacha(usuario);
        GestorCosmeticos.desbloquearSegunRacha(usuario);

        assertEquals(6, usuario.getCosmeticos().size());
        assertTrue(usuario.getCosmeticos().containsAll(List.of(
                "Fondo Azul", "Marco Simple", "Icono Estrella", "Fondo Pastel",
                "Marco Dorado", "Banner Legendario")));
    }

    @Test
    void asignaElTituloMasAltoQueCorrespondeALaRacha() {
        Usuario usuario = crearUsuarioConRacha(30);

        GestorCosmeticos.actualizarTitulo(usuario);

        assertEquals("Sabio del conocimiento", usuario.getTitulo());
    }
}