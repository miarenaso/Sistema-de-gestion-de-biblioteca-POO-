import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import modelo.*;
public class App {
    public static void main(String[] args) throws Exception {
        final String datos = "data.dat";
        Scanner sc = new Scanner(System.in);
   
        List<Usuario> usuarios = GestorUsuarios.cargarUsuarios(datos);
        //Ojo, we are filling it when is no data, but it is not done the log in yet.
        if(usuarios.isEmpty()){
            System.out.println("Ingrese el nombre del usuario: ");
            String name = sc.nextLine();
            System.out.println("Ingrese su correo: ");
            String corr = sc.nextLine();
            System.out.println("Ingrese la contraseña que desee registrar: ");
            String pasword = sc.nextLine();

            // toca crear la función de ponerle el id al user. Pasamo 1 para que funcione el prototipo.

            Usuario u = new Usuario(name, corr, pasword, 1);
            usuarios.add(u);
            System.out.println("¿Cuántos libros desea ingresar?: ");
            int n = sc.nextInt();
            sc.nextLine();
            // Toca testearlo
            for (int i = 0; i < n; i++) {
                System.out.println("Ingrese el nombre del libro " + (i + 1));
                String bname = sc.nextLine();
                System.out.println("Ingrese el autor del libro " + bname);
                String bautor = sc.nextLine();
                System.out.println("Ingrese el género del libro " + bname);
                String bgen = sc.nextLine();
                System.out.println("Ingrese el id del libro " + bname);
                int bid = sc.nextInt();
                sc.nextLine();
                System.out.println("Ingrese el número total de páginas del libro: " + bname);
                int bp = sc.nextInt();
                sc.nextLine();
                Libro l = new Libro(bname, bautor, bgen, bid, LocalDate.now(), bp);
                u.agregarLibro(l);
            }
        } else {
            System.out.println("Usuarios existentes:");
            for (Usuario usuario : usuarios) {
                System.out.println(usuario);
            }
        }
        
        
    sc.close();
        GestorUsuarios.guardarUsuarios(usuarios, datos);
    }
}
