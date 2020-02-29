package model;


/**
 *
 * @author Fabian Valencia
 */
public class Casilla {
    private String valor;
    private int fila, columna, id;

    public Casilla(String valor, int fila, int columna, int id) {
        this.valor = valor;
        this.fila = fila;
        this.columna = columna;
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   
    
   
}
