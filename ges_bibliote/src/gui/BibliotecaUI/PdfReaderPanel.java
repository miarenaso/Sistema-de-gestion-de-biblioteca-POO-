package bibliotecaui;

import modelo.Libro;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.IntConsumer;

final class PdfReaderPanel extends JPanel {
    private final JLabel paginaLabel = new JLabel("Sin documento", SwingConstants.CENTER);
    private final JLabel imagenLabel = new JLabel("Selecciona un PDF para leer", SwingConstants.CENTER);
    private final JButton anterior = new JButton("Página anterior");
    private final JButton siguiente = new JButton("Página siguiente");
    private final JButton alejar = new JButton("−");
    private final JButton acercar = new JButton("+");
    private final JButton volver = new JButton("Volver a la biblioteca");
    private final JLabel zoomLabel = new JLabel("60%");
    private final Runnable onVolver;
    private final IntConsumer onPageRead;

    private PDDocument documento;
    private PDFRenderer renderer;
    private SwingWorker<BufferedImage, Void> renderWorker;
    private int paginaActual;
    private int zoomPorcentaje = 60;
    private Libro libro;

    PdfReaderPanel(Runnable onVolver, IntConsumer onPageRead) {
        this.onVolver = onVolver;
        this.onPageRead = onPageRead;
        setLayout(new BorderLayout(8, 8));

        JPanel barra = new JPanel(new BorderLayout());
        barra.add(volver, BorderLayout.WEST);
        barra.add(paginaLabel, BorderLayout.CENTER);
        JPanel controlesZoom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));
        controlesZoom.add(alejar);
        controlesZoom.add(zoomLabel);
        controlesZoom.add(acercar);
        barra.add(controlesZoom, BorderLayout.EAST);
        add(barra, BorderLayout.NORTH);

        imagenLabel.setVerticalAlignment(SwingConstants.TOP);
        add(new JScrollPane(imagenLabel), BorderLayout.CENTER);

        JPanel navegacion = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
        navegacion.add(anterior);
        navegacion.add(siguiente);
        add(navegacion, BorderLayout.SOUTH);

        anterior.addActionListener(event -> mostrarPagina(paginaActual - 1));
        siguiente.addActionListener(event -> mostrarPagina(paginaActual + 1));
        alejar.addActionListener(event -> cambiarZoom(-20));
        acercar.addActionListener(event -> cambiarZoom(20));
        volver.addActionListener(event -> onVolver.run());
        actualizarControles(false);
    }

    void abrir(Libro libro) throws IOException {
        cerrarDocumento();
        if (!libro.esPdf()) {
            throw new IOException("Este libro no tiene un PDF asociado.");
        }

        Path ruta = Path.of(libro.getRutaPdf());
        if (!Files.isRegularFile(ruta)) {
            throw new IOException("No se encuentra el archivo PDF: " + ruta);
        }

        documento = Loader.loadPDF(ruta.toFile());
        this.libro = libro;
        renderer = new PDFRenderer(documento);
        paginaActual = 0;
        mostrarPagina(paginaActual);
    }

    private void mostrarPagina(int indice) {
        if (documento == null || renderWorker != null && !renderWorker.isDone()) return;
        if (indice < 0 || indice >= documento.getNumberOfPages()) return;

        paginaActual = indice;
        paginaLabel.setText("Página " + (indice + 1) + " de " + documento.getNumberOfPages());
        zoomLabel.setText(zoomPorcentaje + "%");
        imagenLabel.setText("Renderizando página...");
        imagenLabel.setIcon(null);
        actualizarControles(true);

        renderWorker = new SwingWorker<>() {
            @Override
            protected BufferedImage doInBackground() throws IOException {
                return renderer.renderImageWithDPI(indice, 110f * zoomPorcentaje / 100f);
            }

            @Override
            protected void done() {
                try {
                    imagenLabel.setText("");
                    imagenLabel.setIcon(new ImageIcon(get()));
                    onPageRead.accept(indice + 1);
                } catch (Exception exception) {
                    imagenLabel.setText("No se pudo renderizar esta página.");
                    imagenLabel.setIcon(null);
                } finally {
                    actualizarControles(false);
                }
            }
        };
        renderWorker.execute();
    }

    private void cambiarZoom(int cambio) {
        int nuevoZoom = Math.max(40, Math.min(200, zoomPorcentaje + cambio));
        if (nuevoZoom == zoomPorcentaje) return;
        zoomPorcentaje = nuevoZoom;
        zoomLabel.setText(zoomPorcentaje + "%");
        if (documento != null) mostrarPagina(paginaActual);
    }

    private void actualizarControles(boolean renderizando) {
        anterior.setEnabled(!renderizando && documento != null && paginaActual > 0);
        siguiente.setEnabled(!renderizando && documento != null
                && paginaActual < documento.getNumberOfPages() - 1);
        alejar.setEnabled(!renderizando && documento != null && zoomPorcentaje > 40);
        acercar.setEnabled(!renderizando && documento != null && zoomPorcentaje < 200);
        volver.setEnabled(!renderizando);
    }

    private void cerrarDocumento() {
        if (renderWorker != null && !renderWorker.isDone()) {
            renderWorker.cancel(true);
        }
        if (documento != null) {
            try {
                documento.close();
            } catch (IOException ignored) {
            }
        }
        documento = null;
        renderer = null;
        libro = null;
    }
}