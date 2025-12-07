
import recompensa.MainFrame;
import bibliotecaui.BibliotecaUI;
import Login.LoginFrame;
import modelo.*;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;


public class App {
    public static void main(String[] args) throws Exception {
        final String datos = "data.dat";

           // Crear un usuario de prueba
    Usuario usuario = new Usuario("Prueba", "correo@ejemplo.com", "1234", 1);
    usuario.setRacha(new Racha());
    
    usuario.getRacha().actualizarRacha(true, LocalDate.now());
    GestorCosmeticos.desbloquearSegunRacha(usuario);

    new MainFrame(usuario);


    new BibliotecaUI();

    
    // Crear un frame vacío como padre
    java.awt.Frame frame = new java.awt.Frame();


        //new LoginFrame();  //Para abrir la ventana 
        


        /* 
        Scanner sc = new Scanner(System.in);
        List<Usuario> usuarios = GestorUsuarios.cargarUsuarios(datos);
        //Ojo, we are filling it when is no data, but it is not done the log in yet.
        
        //Si no se tiene un usuario se crea uno nuevo   NUEVO
        if(usuarios.isEmpty()){

            System.out.println("Ingrese el nombre del usuario:");
            String name = sc.nextLine();

            System.out.println("Ingrese su correo:");
            String correo = sc.nextLine();

            System.out.println("Ingrese la contraseña que desea registrar:");
            String password = sc.nextLine();

            Usuario u = new Usuario(name, correo, password, 1);
            usuarios.add(u);

            // toca crear la función de ponerle el id al user. Pasamo 1 para que funcione el prototipo.
            
            //Nueva logica aplicada para agregar libros   NUEVO
            while(true){
                System.out.println("¿Desea agregar un libro? (s/n): ");
                String opcion = sc.nextLine().trim().toLowerCase();

                if(opcion.equals("s")){
                    Libro nuevo = crearLibroDesdeConsola(sc);
                    u.agregarLibro(nuevo);
                    System.out.println("Libro agregado.\n");
                } 
                else if(opcion.equals("n")){
                    break;
                } 
                else {
                    System.out.println("Opción no válida, escriba 's' o 'n'.");
                }
            }

        } else {
            System.out.println("Usuarios existentes:");
            for (Usuario usuario : usuarios)
                System.out.println(usuario);
        }

        sc.close();
        GestorUsuarios.guardarUsuarios(usuarios, datos);*/
    }

    //Metodo para agregar libros   NUEVO
    public static Libro crearLibroDesdeConsola(Scanner sc){

        System.out.println("Ingrese el título del libro:");
        String titulo = sc.nextLine();

        System.out.println("Ingrese el autor del libro:");
        String autor = sc.nextLine();

        System.out.println("Ingrese el género del libro:");
        String genero = sc.nextLine();

        System.out.println("Ingrese el ID del libro:");
        int id = Integer.parseInt(sc.nextLine());

        System.out.println("Ingrese el número total de páginas:");
        int paginas = Integer.parseInt(sc.nextLine());

        LocalDate fechaAgregado = LocalDate.now();

        return new Libro(titulo, autor, genero, id, fechaAgregado, paginas);
    }
    ///////////////////////////////////////////////////////////////////////////
    //public static void main(String[] args) {

    //Usuario user = new Usuario("Alex", "alex@test.com", "123", 1L);
    //user.getRacha().setDiasConsecutivos(35);
    //new gui.PerfilUsuarioFrame(user);  // Prueba del perfil épico
    
    // Cuando termines las pruebas, deja solo esto:
    // javax.swing.SwingUtilities.invokeLater(() -> new gui.LoginFrame());
}

