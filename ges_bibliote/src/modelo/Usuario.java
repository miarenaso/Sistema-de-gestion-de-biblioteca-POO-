package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {
    private String  nombre;
    private String correo;  
    private String contrasena;
    private int id;// Puede que tengamos que cambiarlo a long
    private ArrayList<Libro> libros;
    private ArrayList<Libro>historialLibros;
    private ArrayList<Libro>librosFavs;
    private Racha racha;//Ojo, le pasamos el id del usuario al crear la racha
    //Atributos para personalizar feed
    private String tema; // claro u oscuro
    private String tamanoletra;

    //If we change id to long, change here too
    public Usuario(String nombre, String correo, String contrasena, int id) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.id = id;
        this.libros = new ArrayList<Libro>();
        this.historialLibros = new ArrayList<Libro>();
        this.librosFavs = new ArrayList<Libro>();
        this.racha = new Racha(this.id);
         //Valores por defecto
        this.tema = "claro";
        this.tamanoletra = "mediano";
    }

    //Métodos clase Usuario
    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public void eliminarLibro(Libro libro) {
        libros.remove(libro);
    }

    //Ojo, el libro debe tener el estado de lectura "finalizado" para agregarse al historial
    public void agregarLibroHistorial(Libro libro) {
        if (libro.getEstadoLectura().equals("finalizado") && !historialLibros.contains(libro))
        historialLibros.add(libro);
    }

    //Esto garantiza q|ue no haya libros repetidos en favoritos
    public void agregarLibroFav(Libro libro) {
        if (!librosFavs.contains(libro))
        librosFavs.add(libro);
    }

    public void eliminarLibroFav(Libro libro) {
        librosFavs.remove(libro);
    }

    //Getters:
    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }   

    public String getContrasena() {
        return contrasena;
    }   

    public int getId() {
        return id;
    }  

    public ArrayList<Libro> getLibros() {
        return libros;
    }

    public ArrayList<Libro> getHistorialLibros() {
        return historialLibros;
    }

    public ArrayList<Libro> getLibrosFavs() {
        return librosFavs;
    }

    public Racha getRacha() {
        return racha;
    }

    public String getTema() {
        return tema;
    }

    public String getTamanoletra() {
        return tamanoletra;
    }

    //Setters:
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public void setTamanoletra(String tamanoletra) {
        this.tamanoletra = tamanoletra;
    }

}
