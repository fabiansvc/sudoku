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
     * @param url
     * @return 
     */
    
    public Archivo() {
    }

    public String cargarArchivo(String url) {
        File archivo;
        FileReader fr;
        BufferedReader br;
        String cadena = "";
        archivo = new File(url);
        try {
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            String line;
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
