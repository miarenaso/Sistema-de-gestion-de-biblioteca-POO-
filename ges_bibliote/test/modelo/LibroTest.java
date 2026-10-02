package modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class LibroTest {
    private Libro crearLibro() {
        return new Libro("El principito", "Antoine de Saint-Exupery", "Novela", 7,
                LocalDate.of(2024, 1, 10), 100);
    }

    @Test
    void actualizaElEstadoAlAvanzarLaLectura() {
        Libro libro = crearLibro();

        libro.actualizarEstadoLectura();
        assertEquals("Pendiente", libro.getEstadoLectura());

        libro.actualizarPaginasLeidas(25);
        assertEquals("En lectura", libro.getEstadoLectura());

        libro.actualizarPaginasLeidas(75);
        assertEquals("Finalizado", libro.getEstadoLectura());
        assertEquals(100, libro.getPaginasLeidas());
    }

    @Test
    void ignoraIncrementosYValoresTotalesFueraDeRango() {
        Libro libro = crearLibro();
        libro.actualizarPaginasLeidas(40);

        libro.actualizarPaginasLeidas(-1);
        libro.actualizarPaginasLeidas(61);
        libro.setPaginasLeidas(-1);
        libro.setPaginasLeidas(101);

        assertEquals(40, libro.getPaginasLeidas());
        assertEquals("En lectura", libro.getEstadoLectura());
    }

    @Test
    void representaLosDatosPrincipalesEnTexto() {
        Libro libro = crearLibro();

        String texto = libro.toString();

        assertTrue(texto.contains("El principito"));
        assertTrue(texto.contains("Antoine de Saint-Exupery"));
        assertTrue(texto.contains("ID: 7"));
        assertTrue(texto.contains("Páginas: 100"));
    }
}