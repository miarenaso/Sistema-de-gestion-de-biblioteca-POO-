package modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Racha implements Serializable {
    // this is for the Serializable
    private static final long serialVersionUID = 1L;
    //Attributes
    private long id;
    private int diasConsecutivos;
    private LocalDate ultimaFechaLectura;
    
    public Racha(long id){
        this.id = id;
        this.diasConsecutivos = 0;
        this.ultimaFechaLectura = null;
    }

    public long getId(){
        return id;
    }

    public int getDiasConsecutivos(){
        return diasConsecutivos;
    }

    public LocalDate getUltimaFechaLectura(){
        return ultimaFechaLectura;
    }

    public void setUltimaFechaLectura(LocalDate ultimaFechaLectura){
        this.ultimaFechaLectura = ultimaFechaLectura;
    }
//crear metodo, actualizar dias consecutivos
public void actualizarRacha(boolean leyoHoy, LocalDate fechaHoy) {
    if (leyoHoy){
        if (ultimaFechaLectura == null){
        diasConsecutivos = 1;
        } else {
            long diffDias = java.time.temporal.ChronoUnit.DAYS.between(ultimaFechaLectura, fechaHoy);
            if (diffDias == 1){
                diasConsecutivos++;
            } else if (diffDias > 1){
                diasConsecutivos = 1;
            }
        }
    ultimaFechaLectura = fechaHoy;
}
}

}
