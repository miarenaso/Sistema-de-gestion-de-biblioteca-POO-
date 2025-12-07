import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BibliotecaUI extends JFrame {

    private Biblioteca biblioteca;
    private DefaultListModel<String> listaModel;
    private JList<String> listaLibros;

    public BibliotecaUI() {
        biblioteca = new Biblioteca();

        setTitle("Biblioteca Virtual - Swing");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        listaModel = new DefaultListModel<>();
        listaLibros = new JList<>(listaModel);

        JScrollPane scroll = new JScrollPane(listaLibros);
        add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 1, 5, 5));

        JButton btnAgregar = new JButton("Agregar libro");
        JButton btnEliminar = new JButton("Eliminar libro");
        JButton btnFiltrar = new JButton("Filtrar por categoría");
        JButton btnEstado = new JButton("Cambiar estado");
        JButton btnTodos = new JButton("Mostrar todos");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnFiltrar);
        panelBotones.add(btnEstado);
        panelBotones.add(btnTodos);

        add(panelBotones, BorderLayout.EAST);

        actualizarLista();

        // BOTONES ----------------------

        btnAgregar.addActionListener(e -> agregarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnFiltrar.addActionListener(e -> filtrarCategoria());
        btnEstado.addActionListener(e -> cambiarEstado());
        btnTodos.addActionListener(e -> actualizarLista());

        setVisible(true);
    }

    private void agregarLibro() {
        String titulo = JOptionPane.showInputDialog("Título:");
        if (titulo == null) return;

        String autor = JOptionPane.showInputDialog("Autor:");
        if (autor == null) return;

        String categoria = JOptionPane.showInputDialog("Categoría:");
        if (categoria == null) return;

        String estado = JOptionPane.showInputDialog("Estado (Pendiente / En lectura / Finalizado):");
        if (estado == null) return;

        Libro libro = new Libro(titulo, autor, categoria, estado);
        biblioteca.agregarLibro(libro);

        actualizarLista();
    }

    private void eliminarLibro() {
        String titulo = JOptionPane.showInputDialog("Título del libro a eliminar:");
        if (titulo == null) return;

        if (biblioteca.eliminarLibro(titulo)) {
            JOptionPane.showMessageDialog(null, "Libro eliminado.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró ese libro.");
        }

        actualizarLista();
    }

    private void filtrarCategoria() {
        String categoria = JOptionPane.showInputDialog("Categoría:");
        if (categoria == null) return;

        listaModel.clear();

        for (Libro l : biblioteca.filtrarPorCategoria(categoria)) {
            listaModel.addElement(l.toString());
        }
    }

    private void cambiarEstado() {
        String titulo = JOptionPane.showInputDialog("Título del libro:");
        if (titulo == null) return;

        Libro libro = biblioteca.buscarLibro(titulo);
        if (libro == null) {
            JOptionPane.showMessageDialog(null, "Libro no encontrado.");
            return;
        }

        String nuevo = JOptionPane.showInputDialog("Nuevo estado:");
        if (nuevo == null) return;

        libro.setEstado(nuevo);
        actualizarLista();
    }

    private void actualizarLista() {
        listaModel.clear();

        for (Libro l : biblioteca.obtenerTodos()) {
            listaModel.addElement(l.toString());
        }
    }
}
