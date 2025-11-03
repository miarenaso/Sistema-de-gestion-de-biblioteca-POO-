package modelo;

import java.time.LocalDate;

public class Racha {
    private int id;
    private int diasConsecutivos;
    private LocalDate ultimaFechaLectura;
    
    public Racha(int id){
        this.id = id;
        this.diasConsecutivos = 0;
        this.ultimaFechaLectura = null;
    }

    public int getId(){
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
