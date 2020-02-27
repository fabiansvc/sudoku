package model;
/**
 *
 * @author Fabian Valencia
 */
public class Historial {
    private int fila, columna;
    private String valor; 

    public Historial(int fila, int columna, String valor) {
        this.fila = fila;
        this.columna = columna;
        this.valor = valor;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
     
}
