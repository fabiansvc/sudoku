package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yefri Montero
 */
public class Archivo {
    /**
     * Método que se encarga de cargar el archivo txt.
     * @param nomArchivo
     * @return 
     */
    public static String cargarArchivo(String nomArchivo) {
        String[][] var = new String[9][9];
        File archivo;
        FileReader fr;
        BufferedReader br;
        String cadena = "";
        archivo = new File(nomArchivo);
        try {
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                cadena += line;
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cadena;
    }
}
