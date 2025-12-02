package modelo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
//En esta clase haremos la gestión de la fata de los usuarios :)
public class GestorUsuarios {
    //Methods to save and load users from a file using serialization, It's gonna be in an array. Ruta is the final String that is at App's class
    //@SuppressWarnings("unchecked")
     public static void guardarUsuarios(List<Usuario> usuarios, String rutaArchivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(usuarios);
            System.out.println("Usuarios guardados correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This method returns an ArrayList of users and if the file doesn't exist, it returns an empty ArrayList
    public static List<Usuario> cargarUsuarios(String rutaArchivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (List<Usuario>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
}