package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Historial;
import model.Sudoku;

/**
 * FXML Controller class
 *
 * @author Fabian Valencia.
 */
public class FXMLTableroSudokuController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    private String matriz[][];
    private TextField casillas[][];
    private ArrayList<Historial> historiales;
    private int incrementadorHistorial = 0;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        historiales = new ArrayList<>();
        llenarTablero();
        cargarTablero();
    }

    /**
     * Método que llena toda la matriz con ceros.
     */
    private void llenarTablero() {
        matriz = new String[9][9];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                matriz[i][j] = "0";
            }
        }
    }

    /**
     * Método que se encarga de pintar el tablero.
     */
    private void cargarTablero() {
        casillas = new TextField[9][9];
        GridPane gridpane = new GridPane();
        gridpane.setLayoutX(25);
        gridpane.setLayoutY(25);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField txt = new TextField();
                txt.setPrefHeight(35);
                txt.setPrefWidth(35);
                GridPane.setRowIndex(txt, i);
                GridPane.setColumnIndex(txt, j);
                gridpane.getChildren().addAll(txt);
                casillas[i][j] = txt;
                keyPressed(txt, i, j);
            }
        }
        anchorPane.getChildren().add(gridpane);
    }

    /**
     * Método que se encarga de escuchar el evento del teclado comparando si es
     * numerico, si solo escribe numeros del 1 al 9.
     *
     * @param txt
     * @param fila
     * @param columna
     */
    private void keyPressed(TextField txt, int fila, int columna) {
        txt.setOnKeyPressed((KeyEvent e) -> {
            txt.deletePreviousChar();
            if (isNumeric(e.getText()) && txt.getText().length() < 1) {
                matriz[fila][columna] = e.getText();
                Sudoku sudoku = new Sudoku(matriz, e.getText(), fila, columna);
                if (sudoku.buscarNumeroXCuadrante() || sudoku.encontrarNumeroXFila() || sudoku.encontrarNumeroxColumna()) {
                    matriz[fila][columna] = "0";
                } else {
                    Historial historial = new Historial(fila, columna, e.getText());
                    historiales.add(historial);
                    incrementadorHistorial++;
                }
            } else {
                matriz[fila][columna] = "0";
            }
            imprimirMatriz(matriz);
        });

    }

    /**
     * Método que se encarga de verificar si la tecla es númerica.
     *
     * @param key
     * @return
     */
    private boolean isNumeric(String key) {
        try {
            Integer.parseInt(key);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Método que imprime la matriz.
     *
     * @param matriz
     */
    private void imprimirMatriz(String[][] matriz) {
        System.out.println("-----------------------------");
        for (int x = 0; x < matriz.length; x++) {
            System.out.print("|");
            for (int y = 0; y < matriz[x].length; y++) {
                System.out.print(matriz[x][y]);
                if (y != matriz[x].length - 1) {
                    System.out.print("\t");
                }
            }
            System.out.println("|");
        }
    }

    @FXML
    private void btnDeshacerPressed(ActionEvent event) {
        casillas[historiales.get(incrementadorHistorial).getFila()][historiales.get(incrementadorHistorial).getColumna()].setText("");
        incrementadorHistorial -= 1;
        int fila = historiales.get(incrementadorHistorial).getFila();
        int columna = historiales.get(incrementadorHistorial).getColumna();
        String valor = historiales.get(incrementadorHistorial).getValor();
        casillas[fila][columna].setText(valor);

    }

    @FXML
    private void btnRehacerPressed(ActionEvent event) {
        incrementadorHistorial += 1;
        int fila = historiales.get(incrementadorHistorial + 1).getFila();
        int columna = historiales.get(incrementadorHistorial).getColumna();
        String valor = historiales.get(incrementadorHistorial).getValor();
        casillas[fila][columna].setText(valor);
    }
}
