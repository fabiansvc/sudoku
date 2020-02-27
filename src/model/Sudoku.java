package model;

/**
 *
 * @author Fabian Valencia.
 */
public class Sudoku {
    private final String matriz[][];
    private final String numBuscar;
    private final int filaNumBuscar;
    private final int columnaNumBuscar;
    private int filaInicio, columnaInicio, filaFinal, columnaFinal;
    /**
     * Constructor de la clase Sudoku.
     * @param matriz
     * @param numBuscar
     * @param filaNumBuscar
     * @param columnaNumBuscar 
     */
    public Sudoku(String[][] matriz, String numBuscar, int filaNumBuscar, int columnaNumBuscar) {
        this.matriz = matriz;
        this.numBuscar = numBuscar;
        this.filaNumBuscar = filaNumBuscar;
        this.columnaNumBuscar = columnaNumBuscar;
    }    
    /**
     * Método que se encarga de buscar el numero por fila.
     * @return true si lo encuenta
     */
    public boolean encontrarNumeroXFila() {
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[filaNumBuscar][i].equals(numBuscar)) {
                if (i != columnaNumBuscar) {
                    System.out.println("Se encuentra en la fila");                    
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Método que se encarga de buscar el numero por columna.
     * @return true si lo encuenta
     */
    public boolean encontrarNumeroxColumna() {
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i][columnaNumBuscar].equals(numBuscar)) {
                if (i != filaNumBuscar) {
                    System.out.println("Se encuentra en la columna");
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Método que se encarga de buscar el numero por cuadrante.
     * @return true si lo encuenta
     */
    public boolean buscarNumeroXCuadrante() {
        buscarCuadrante();
        for (int i = filaInicio; i < filaFinal; i++) {
            for (int j = columnaInicio; j < columnaFinal; j++) {
                if (matriz[i][j].equals(numBuscar)) {
                    if (i != filaNumBuscar) {
                        System.out.println("Se encuentra en el cuadrante");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Método que busca el cuadrante donde esta ubicado el número.
     */
    public void buscarCuadrante() {
        int i = 0;
        while (i < 9) {
            int j = 0;
            while (j < 9) {
                if (filaNumBuscar >= i && filaNumBuscar <= i + 2 && columnaNumBuscar >= j && columnaNumBuscar <= j + 2) {
                    this.filaInicio = i;
                    this.columnaInicio = j;
                    this.filaFinal = i + 2;
                    this.columnaFinal = j + 2;
                }
                j += 3;
            }
            i += 3;
        }
    }

}
