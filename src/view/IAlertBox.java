
package view;

/**
 * Interfaz para realizar Alertas.
 * @author Camilo
 */
public interface IAlertBox {
    /**
     * Metodo que carga un cuadro de dialogo de información
     * @param cadena1: String
     * @param cadena2: String
     * @param cadena3: String
     */
    public void showAlert(String cadena1,String cadena2,String cadena3);
}
