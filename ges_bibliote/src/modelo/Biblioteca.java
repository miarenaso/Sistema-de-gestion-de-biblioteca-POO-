import java.util.*;

public class Biblioteca {
    private Map<String, Libro> libros;

    public Biblioteca() {
        libros = new HashMap<>();
    }

    public void agregarLibro(Libro libro) {
        libros.put(libro.getTitulo(), libro);
    }

    public boolean eliminarLibro(String titulo) {
        return libros.remove(titulo) != null;
    }

    public Libro buscarLibro(String titulo) {
        return libros.get(titulo);
    }

    public Collection<Libro> obtenerTodos() {
        return libros.values();
    }

    public List<Libro> filtrarPorCategoria(String categoria) {
        List<Libro> resultado = new ArrayList<>();

        for (Libro l : libros.values()) {
            if (l.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(l);
            }
        }

        return resultado;
    }
}
