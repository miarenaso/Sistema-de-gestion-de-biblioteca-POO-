package modelo;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

public final class PdfBookImporter {
    private final Path storageDirectory;

    public PdfBookImporter(Path storageDirectory) {
        this.storageDirectory = storageDirectory;
    }

    public Libro importar(Path source, int id, String categoria) throws IOException {
        Path sourcePath = source.toAbsolutePath().normalize();
        if (!Files.isRegularFile(sourcePath)
                || !sourcePath.getFileName().toString().toLowerCase().endsWith(".pdf")) {
            throw new IOException("Selecciona un archivo PDF válido.");
        }

        String titulo;
        String autor;
        int paginas;
        try (PDDocument document = Loader.loadPDF(sourcePath.toFile())) {
            paginas = document.getNumberOfPages();
            if (paginas < 1) {
                throw new IOException("El PDF no contiene páginas.");
            }

            PDDocumentInformation informacion = document.getDocumentInformation();
            titulo = valorMetadata(informacion.getTitle(), nombreSinExtension(sourcePath));
            autor = valorMetadata(informacion.getAuthor(), "Autor desconocido");
        }

        Files.createDirectories(storageDirectory);
        Path copia = storageDirectory.resolve(UUID.randomUUID() + "-" + sourcePath.getFileName());
        Files.copy(sourcePath, copia, StandardCopyOption.COPY_ATTRIBUTES);

        Libro libro = new Libro(titulo, autor, categoria, id, LocalDate.now(), paginas);
        libro.setRutaPdf(copia.toString());
        return libro;
    }

    private static String valorMetadata(String valor, String fallback) {
        return valor == null || valor.isBlank() ? fallback : valor.trim();
    }

    private static String nombreSinExtension(Path path) {
        String nombre = path.getFileName().toString();
        return nombre.substring(0, nombre.length() - ".pdf".length());
    }
}