package modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

class PdfBookImporterTest {
    @TempDir
    Path directorioTemporal;

    @Test
    void importaCopiaYMetadataDelPdf() throws Exception {
        Path pdfOriginal = directorioTemporal.resolve("original.pdf");
        try (PDDocument documento = new PDDocument()) {
            documento.addPage(new PDPage());
            PDDocumentInformation informacion = new PDDocumentInformation();
            informacion.setTitle("Lecturas de prueba");
            informacion.setAuthor("Autora");
            documento.setDocumentInformation(informacion);
            documento.save(pdfOriginal.toFile());
        }

        Path bibliotecaPdf = directorioTemporal.resolve("pdfs");
        Libro libro = new PdfBookImporter(bibliotecaPdf).importar(pdfOriginal, 4, "Ensayo");

        assertEquals("Lecturas de prueba", libro.getTitulo());
        assertEquals("Autora", libro.getAutor());
        assertEquals("Ensayo", libro.getCategoria());
        assertEquals(1, libro.getPaginas());
        assertTrue(libro.esPdf());
        assertTrue(Files.isRegularFile(Path.of(libro.getRutaPdf())));
        assertTrue(Files.size(Path.of(libro.getRutaPdf())) > 0);
    }
}