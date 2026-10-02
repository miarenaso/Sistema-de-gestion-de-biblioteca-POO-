package modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class BibliotecaTest {
    @Test
    void buscaFiltraSinDistinguirMayusculasYEliminaLibros() {
        Biblioteca biblioteca = new Biblioteca();
        Libro novela = new Libro("Novela", "Autor A", "Ficción", 1,
                LocalDate.of(2025, 1, 1), 100);
        Libro ensayo = new Libro("Ensayo", "Autor B", "No ficción", 2,
                LocalDate.of(2025, 1, 2), 80);
        biblioteca.agregarLibro(novela);
        biblioteca.agregarLibro(ensayo);

        assertSame(novela, biblioteca.buscarLibro("Novela"));
        assertEquals(List.of(novela), biblioteca.filtrarPorCategoria("ficción"));
        assertEquals(2, biblioteca.obtenerTodos().size());

        assertTrue(biblioteca.eliminarLibro("Novela"));
        assertFalse(biblioteca.eliminarLibro("No existe"));
        assertEquals(1, biblioteca.obtenerTodos().size());
    }
}