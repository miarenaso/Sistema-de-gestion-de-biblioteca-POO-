package bibliotecaui;

import modelo.*;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;

public class BibliotecaUI extends JFrame {

    private final Usuario usuario;
    private final Runnable onChange;
    private final Runnable onReading;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel vistas = new JPanel(cardLayout);
    private final PdfBookImporter pdfImporter = new PdfBookImporter(Path.of("biblioteca-pdfs"));
    private final DefaultListModel<Libro> listaModel = new DefaultListModel<>();
    private final JList<Libro> listaLibros = new JList<>(listaModel);
    private PdfReaderPanel lectorPdf;
    private Libro libroAbierto;

    public BibliotecaUI(Usuario usuario, Runnable onChange, Runnable onReading) {
        this.usuario = usuario;
        this.onChange = onChange;
        this.onReading = onReading;

        setTitle("Biblioteca Virtual - Swing");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        listaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaLibros.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean selected, boolean focus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, selected, focus);
            Libro libro = (Libro) value;
            String marcaPdf = libro.esPdf() ? "  [PDF]" : "";
            label.setText(libro.getTitulo() + " | " + libro.getAutor() + " | "
                + libro.getEstadoLectura() + " | " + libro.getPaginasLeidas() + "/"
                + libro.getPaginas() + " páginas" + marcaPdf);
            label.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
            return label;
            }
        });

        JScrollPane scroll = new JScrollPane(listaLibros);
        JPanel panelBiblioteca = new JPanel(new BorderLayout(10, 10));
        panelBiblioteca.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBiblioteca.add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(0, 1, 5, 5));

        JButton btnAgregar = new JButton("Agregar libro");
        JButton btnImportarPdf = new JButton("Importar PDF");
        JButton btnAbrirPdf = new JButton("Leer PDF seleccionado");
        JButton btnEliminar = new JButton("Eliminar libro");
        JButton btnFiltrar = new JButton("Filtrar por categoría");
        JButton btnEstado = new JButton("Cambiar estado");
        JButton btnPaginas = new JButton("Registrar páginas leídas");
        JButton btnTodos = new JButton("Mostrar todos");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnImportarPdf);
        panelBotones.add(btnAbrirPdf);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnFiltrar);
        panelBotones.add(btnEstado);
        panelBotones.add(btnPaginas);
        panelBotones.add(btnTodos);

        panelBiblioteca.add(panelBotones, BorderLayout.EAST);

        lectorPdf = new PdfReaderPanel(
            () -> cardLayout.show(vistas, "biblioteca"), this::registrarPaginaPdf);
        vistas.add(panelBiblioteca, "biblioteca");
        vistas.add(lectorPdf, "lector");
        setContentPane(vistas);

        actualizarLista();

        // BOTONES ----------------------

        btnAgregar.addActionListener(e -> agregarLibro());
        btnImportarPdf.addActionListener(e -> importarPdf());
        btnAbrirPdf.addActionListener(e -> abrirPdfSeleccionado());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnFiltrar.addActionListener(e -> filtrarCategoria());
        btnEstado.addActionListener(e -> cambiarEstado());
        btnPaginas.addActionListener(e -> registrarPaginas());
        btnTodos.addActionListener(e -> actualizarLista());
    }

    private void agregarLibro() {
        String titulo = JOptionPane.showInputDialog("Título:");
        if (titulo == null) return;

        String autor = JOptionPane.showInputDialog("Autor:");
        if (autor == null) return;

        String categoria = JOptionPane.showInputDialog("Categoría:");
        if (categoria == null) return;

        String[] estados = {"Pendiente", "En lectura", "Finalizado"};
        String estado = (String) JOptionPane.showInputDialog(this, "Estado de lectura:",
                "Agregar libro", JOptionPane.QUESTION_MESSAGE, null, estados, estados[0]);
        if (estado == null) return;

        String paginasTexto = JOptionPane.showInputDialog(this, "Páginas totales:", "100");
        if (paginasTexto == null) return;
        int paginas;
        try {
            paginas = Integer.parseInt(paginasTexto.trim());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Ingresa un número de páginas válido.");
            return;
        }
        if (paginas < 1) {
            JOptionPane.showMessageDialog(this, "El libro debe tener al menos una página.");
            return;
        }

        int id = usuario.getLibros().stream().mapToInt(Libro::getId).max().orElse(0) + 1;
        Libro libro = new Libro(titulo, autor, categoria, id, LocalDate.now(), paginas);
        if ("En lectura".equals(estado)) libro.setPaginasLeidas(1);
        if ("Finalizado".equals(estado)) libro.setPaginasLeidas(paginas);
        libro.actualizarEstadoLectura();
        usuario.agregarLibro(libro);
        usuario.agregarLibroHistorial(libro);

        actualizarLista();
        onChange.run();
        if (libro.getPaginasLeidas() > 0) onReading.run();
    }

    private void importarPdf() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Importar libro en PDF");
        chooser.setFileFilter(new FileNameExtensionFilter("Documentos PDF", "pdf"));
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;

        String categoria = JOptionPane.showInputDialog(this, "Categoría del libro:", "General");
        if (categoria == null || categoria.isBlank()) return;

        int id = usuario.getLibros().stream().mapToInt(Libro::getId).max().orElse(0) + 1;
        try {
            Libro libro = pdfImporter.importar(chooser.getSelectedFile().toPath(), id, categoria.trim());
            usuario.agregarLibro(libro);
            actualizarLista();
            listaLibros.setSelectedValue(libro, true);
            onChange.run();
            JOptionPane.showMessageDialog(this, "PDF importado: " + libro.getTitulo());
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "No se pudo importar el PDF",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirPdfSeleccionado() {
        Libro libro = listaLibros.getSelectedValue();
        if (libro == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un libro.");
            return;
        }
        try {
            libroAbierto = libro;
            lectorPdf.abrir(libro);
            cardLayout.show(vistas, "lector");
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "No se pudo abrir el PDF",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarPaginaPdf(int pagina) {
        if (libroAbierto == null || pagina <= libroAbierto.getPaginasLeidas()) return;
        libroAbierto.setPaginasLeidas(pagina);
        libroAbierto.actualizarEstadoLectura();
        usuario.agregarLibroHistorial(libroAbierto);
        onChange.run();
        onReading.run();
    }

    private void eliminarLibro() {
        String titulo = JOptionPane.showInputDialog(this, "Título del libro a eliminar:");
        if (titulo == null) return;

        Libro libro = usuario.getLibros().stream()
                .filter(item -> item.getTitulo().equalsIgnoreCase(titulo.trim()))
                .findFirst().orElse(null);
        if (libro != null) {
            usuario.eliminarLibro(libro);
            JOptionPane.showMessageDialog(null, "Libro eliminado.");
            onChange.run();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró ese libro.");
        }

        actualizarLista();
    }

    private void filtrarCategoria() {
        String categoria = JOptionPane.showInputDialog(this, "Categoría:");
        if (categoria == null) return;

        listaModel.clear();

        for (Libro libro : usuario.getLibros()) {
            if (libro.getCategoria().equalsIgnoreCase(categoria.trim())) {
                listaModel.addElement(libro);
            }
        }
    }

    private void cambiarEstado() {
        String titulo = JOptionPane.showInputDialog(this, "Título del libro:");
        if (titulo == null) return;

        Libro libro = usuario.getLibros().stream()
            .filter(item -> item.getTitulo().equalsIgnoreCase(titulo.trim()))
            .findFirst().orElse(null);
        if (libro == null) {
            JOptionPane.showMessageDialog(null, "Libro no encontrado.");
            return;
        }

        String[] estados = {"Pendiente", "En lectura", "Finalizado"};
        String nuevo = (String) JOptionPane.showInputDialog(this, "Nuevo estado:",
                "Cambiar estado", JOptionPane.QUESTION_MESSAGE, null, estados, estados[0]);
        if (nuevo == null) return;

        if ("Pendiente".equals(nuevo)) libro.setPaginasLeidas(0);
        if ("En lectura".equals(nuevo) && libro.getPaginasLeidas() == 0) libro.setPaginasLeidas(1);
        if ("Finalizado".equals(nuevo)) libro.setPaginasLeidas(libro.getPaginas());
        libro.actualizarEstadoLectura();
        usuario.agregarLibroHistorial(libro);
        actualizarLista();
        onChange.run();
        if (libro.getPaginasLeidas() > 0) onReading.run();
    }

    private void registrarPaginas() {
        String titulo = JOptionPane.showInputDialog(this, "Título del libro:");
        if (titulo == null) return;
        Libro libro = usuario.getLibros().stream()
                .filter(item -> item.getTitulo().equalsIgnoreCase(titulo.trim()))
                .findFirst().orElse(null);
        if (libro == null) {
            JOptionPane.showMessageDialog(this, "Libro no encontrado.");
            return;
        }

        String texto = JOptionPane.showInputDialog(this, "Páginas nuevas leídas:", "1");
        if (texto == null) return;
        int nuevasPaginas;
        try {
            nuevasPaginas = Integer.parseInt(texto.trim());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Ingresa un número entero válido.");
            return;
        }
        if (nuevasPaginas <= 0 || libro.getPaginasLeidas() + nuevasPaginas > libro.getPaginas()) {
            JOptionPane.showMessageDialog(this, "El avance debe ser positivo y no superar el total.");
            return;
        }

        libro.actualizarPaginasLeidas(nuevasPaginas);
        usuario.agregarLibroHistorial(libro);
        actualizarLista();
        onChange.run();
        onReading.run();
    }

    private void actualizarLista() {
        listaModel.clear();

        for (Libro libro : usuario.getLibros()) {
            listaModel.addElement(libro);
        }
    }
}
