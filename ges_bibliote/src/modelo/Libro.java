package modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Libro implements Serializable {
    private String titulo;
    private String autor;
    private String genero;
    private int id; //Numero identificador único
    //Revisar si lo dejamos con String o buscamos otro tipo de dato. 
    private String estadoLectura; // "pendiente", "en lectura", "finalizado"(tentativos)
    private int paginas;
    private int paginasLeidas;
    private LocalDate fechaAgregado;

    //Constructors
    public Libro(String titulo, String autor, String genero, int id, LocalDate fecha, int paginas) {
        this.titulo = titulo;
        this. autor = autor;
        this.genero = genero;
        this.id = id;
        this.paginas = paginas;
        this.paginasLeidas = 0;
        this.fechaAgregado = fecha;
        this.estadoLectura = "Recién agregado."; //ACTUALIZAR 
    }

    //Methods
    //So, if we change posibilities of estadoLectura, we need to update this method.
    public void actualizarEstadoLectura() {
        if (this.paginasLeidas == 0) {
            this.estadoLectura = "Pendiente";
        } else if (this.paginasLeidas > 0 && this.paginasLeidas < this.paginas) {
            this.estadoLectura = "En lectura";
        } else if (this.paginasLeidas == this.paginas) {
            this.estadoLectura = "Finalizado";
        }
    }

    //We supose that usser will input valid data. nuevasPaginas is always positive and nuevasPaginas + paginasLeidas must be equal or less than paginas.
    public void actualizarPaginasLeidas(int nuevasPaginasLeidas) {
        if (nuevasPaginasLeidas >= 0 && (nuevasPaginasLeidas + this.paginasLeidas) <= this.paginas) {
            this.paginasLeidas += nuevasPaginasLeidas;
            actualizarEstadoLectura();
        } else {
            System.out.println("Número de páginas leídas inválido.");
        }
    }


    //Getters
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public int getId() {
        return id;
    }

    public String getEstadoLectura() {
        return estadoLectura;
    }

    public LocalDate getFechaAgregado(){
        return this.fechaAgregado;
    } 

    public int getPaginas() {
        return paginas;
    }


    //Setters

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setEstadoLectura(String estadoLectura) {
        this.estadoLectura = estadoLectura;
    }

    public void setFechaAgregado(LocalDate fechaAgregado) {this.fechaAgregado = fechaAgregado;}
    
    
    public int getPaginasLeidas() {
        return paginasLeidas;
    }

    public void setPaginasLeidas(int paginasLeidas) {
        if (paginasLeidas >= 0 && paginasLeidas <= paginas) {
            this.paginasLeidas = paginasLeidas;
        }

    }
    //We use StringBuilder to optimize memory usage when concatenating strings. Rememeber that Libro can have a lot of instances and just having String as normal can be costly. 
    @Override
    public String toString() {
        StringBuilder sbout = new StringBuilder();
        sbout.append("Título: ").append(this.titulo).append(".\n");
        sbout.append("Autor: ").append(this.autor).append(".\n");
        sbout.append("Género: ").append(this.genero).append(".\n");
        sbout.append("ID: ").append(this.id).append(".\n");
        sbout.append("Estado de lectura: ").append(this.estadoLectura).append(".\n");
        sbout.append("Páginas: ").append(this.paginas).append(".\n");
        sbout.append("Páginas leídas: ").append(this.paginasLeidas).append(".\n");
        sbout.append("Fecha agregado: ").append(this.fechaAgregado).append(".\n");
        return sbout.toString();
    }

}
