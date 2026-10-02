package modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class UsuarioTest {
    private Libro crearLibro() {
        return new Libro("Libro de prueba", "Autora", "Novela", 1,
                LocalDate.of(2025, 1, 1), 120);
    }

    @Test
    void gestionaLibrosYEvitaFavoritosDuplicados() {
        Usuario usuario = new Usuario("Ana", "ana@example.test", "clave", 3);
        Libro libro = crearLibro();

        usuario.agregarLibro(libro);
        usuario.agregarLibroFav(libro);
        usuario.agregarLibroFav(libro);

        assertEquals(1, usuario.getLibros().size());
        assertSame(libro, usuario.getLibros().get(0));
        assertEquals(1, usuario.getLibrosFavs().size());

        usuario.eliminarLibro(libro);
        usuario.eliminarLibroFav(libro);

        assertEquals(0, usuario.getLibros().size());
        assertEquals(0, usuario.getLibrosFavs().size());
    }

    @Test
    void soloAgregaUnaVezLibrosConEstadoFinalizadoEsperado() {
        Usuario usuario = new Usuario("Ana", "ana@example.test", "clave", 3);
        Libro libroEnLectura = crearLibro();
        Libro libroFinalizado = crearLibro();
        libroFinalizado.setPaginasLeidas(libroFinalizado.getPaginas());
        libroFinalizado.actualizarEstadoLectura();

        usuario.agregarLibroHistorial(libroEnLectura);
        usuario.agregarLibroHistorial(libroFinalizado);
        usuario.agregarLibroHistorial(libroFinalizado);

        assertEquals(1, usuario.getHistorialLibros().size());
        assertSame(libroFinalizado, usuario.getHistorialLibros().get(0));
    }
}