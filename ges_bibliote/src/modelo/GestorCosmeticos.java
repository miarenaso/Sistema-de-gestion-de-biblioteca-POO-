package modelo;

public class GestorCosmeticos {

    public static void desbloquearSegunRacha(Usuario usuario) {

        int racha = usuario.getRacha().getDiasConsecutivos();

        // Ejemplo de cosméticos desbloqueables
        if (racha >= 1 && !usuario.getCosmeticos().contains("Fondo Azul")) {
            usuario.desbloquearCosmetico("Fondo Azul");
        }

        if (racha >= 3 && !usuario.getCosmeticos().contains("Marco Simple")) {
            usuario.desbloquearCosmetico("Marco Simple");
        }

        if (racha >= 5 && !usuario.getCosmeticos().contains("Icono Estrella")) {
            usuario.desbloquearCosmetico("Icono Estrella");
        }

        if (racha >= 7 && !usuario.getCosmeticos().contains("Fondo Pastel")) {
            usuario.desbloquearCosmetico("Fondo Pastel");
        }

        if (racha >= 10 && !usuario.getCosmeticos().contains("Marco Dorado")) {
            usuario.desbloquearCosmetico("Marco Dorado");
        }

        if (racha >= 20 && !usuario.getCosmeticos().contains("Banner Legendario")) {
            usuario.desbloquearCosmetico("Banner Legendario");

        }
    }

    //NUEVO
    public static void actualizarTitulo(Usuario usuario) {
    int racha = usuario.getRacha().getDiasConsecutivos();

    if (racha >= 1) usuario.setTitulo("Nuevo lector");
    if (racha >= 3) usuario.setTitulo("Lector rápido");
    if (racha >= 5) usuario.setTitulo("Come libros");
    if (racha >= 10) usuario.setTitulo("Maratonista de lectura");
    if (racha >= 20) usuario.setTitulo("Maestro lector");
    if (racha >= 30) usuario.setTitulo("Sabio del conocimiento");
}

}

