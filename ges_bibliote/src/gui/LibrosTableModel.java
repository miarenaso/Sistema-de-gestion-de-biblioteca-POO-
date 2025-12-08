package ges_bibliote.gui;

import modelo.Libro;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * TableModel personalizado para List<Libro>.
 * Provee métodos para agregar/editar/eliminar y notificar cambios a la JTable.
 */
public class LibrosTableModel extends AbstractTableModel {
    private final String[] columnas = {"ID", "Título", "Autor", "Categoría", "Estado"};
    private final List<Libro> libros;

    public LibrosTableModel() {
        this.libros = new ArrayList<>();
    }

    public LibrosTableModel(List<Libro> libros) {
        this.libros = libros != null ? libros : new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return libros.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Libro l = libros.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return l.getId();
            case 1:
                return l.getTitulo();
            case 2:
                return l.getAutor();
            case 3:
                return l.getCategoria();
            case 4:
                return l.getEstadoLectura();
            default:
                return null;
        }
    }

    public void addLibro(Libro libro) {
        int idx = libros.size();
        libros.add(libro);
        fireTableRowsInserted(idx, idx);
    }

    public void removeLibroAt(int index) {
        libros.remove(index);
        fireTableRowsDeleted(index, index);
    }

    public void updateLibroAt(int index) {
        fireTableRowsUpdated(index, index);
    }

    public Libro getLibroAt(int index) {
        return libros.get(index);
    }

    public List<Libro> getLibros() {
        return libros;
    }
}