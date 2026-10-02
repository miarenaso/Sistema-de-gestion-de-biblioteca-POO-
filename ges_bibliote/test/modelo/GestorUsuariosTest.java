package modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class GestorUsuariosTest {
    @TempDir
    Path directorioTemporal;

    @Test
    void devuelveUnaListaVaciaCuandoElArchivoNoExiste() {
        List<Usuario> usuarios = GestorUsuarios.cargarUsuarios(
                directorioTemporal.resolve("usuarios.dat").toString());

        assertTrue(usuarios.isEmpty());
    }

    @Test
    void guardaYCargaUsuariosConSusLibros() {
        Usuario usuario = new Usuario("Ana", "ana@example.test", "clave", 8);
        usuario.agregarLibro(new Libro("Libro", "Autor", "Novela", 4,
                LocalDate.of(2025, 2, 1), 90));
        List<Usuario> usuarios = new ArrayList<>(List.of(usuario));
        Path archivo = directorioTemporal.resolve("usuarios.dat");

        GestorUsuarios.guardarUsuarios(usuarios, archivo.toString());
        List<Usuario> cargados = GestorUsuarios.cargarUsuarios(archivo.toString());

        assertEquals(1, cargados.size());
        assertEquals("Ana", cargados.get(0).getNombre());
        assertEquals(8, cargados.get(0).getId());
        assertEquals("Libro", cargados.get(0).getLibros().get(0).getTitulo());
    }
}