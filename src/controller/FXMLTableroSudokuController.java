package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import static model.Archivo.cargarArchivo;
import model.Casilla;
import model.Sudoku;
import view.AlertBox;
import view.IAlertBox;

/**
 * FXML Controller class
 *
 * @author Fabian Valencia.
 */
public class FXMLTableroSudokuController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    private String matriz[][];
    private TextField[][] txts;
    private ArrayList<Casilla> jugadas;
    private int incrementador;
    @FXML
    private TextArea historialTxA;
    private int filaActual, columnaActual;
    private String valorActual;
    @FXML
    private Button btnDeshacer;
    @FXML
    private Button btnRehacer;
    @FXML
    private Button btnAyuda;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarImagenesBotones();
        jugadas = new ArrayList<>();
        incrementador = -1;
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
        int incrementadorCadena = 0;
        int id = 0;
        String cadena = cargarArchivo("archivo.txt");
        txts = new TextField[9][9];
        GridPane gridpane = new GridPane();
        gridpane.setLayoutX(25);
        gridpane.setLayoutY(25);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField txt = new TextField();
                String a = String.valueOf(cadena.charAt(incrementadorCadena));
                incrementadorCadena++;
                if (!(a.equals("-"))) {
                    txt.setText(a);
                    txt.setEditable(false);
                    matriz[i][j] = a;
                    txt.setStyle("-fx-background-color: #ffeeef;");

                }
                txt.setPrefHeight(35);
                txt.setPrefWidth(35);

                GridPane.setRowIndex(txt, i);
                GridPane.setColumnIndex(txt, j);
                gridpane.getChildren().addAll(txt);
                txts[i][j] = txt;
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
                if (sudoku.buscarNumeroXCuadrante() || sudoku.encontrarNumeroXFila() || sudoku.encontrarNumeroxColumna() || sudoku.sudokuCompleto()) {
                    IAlertBox alert = new AlertBox();
                    alert.showAlert("Sudoku", "Jugada invalida", "Intenta con otro número");
                    matriz[fila][columna] = "0";
                } else {
                    incrementador += 1;
                    Casilla casilla = new Casilla(e.getText(), fila, columna, incrementador);
                    jugadas.add(casilla);
                    historialTxA.appendText("> Se inserto el numero " + e.getText() + " en la fila " + fila + " y columna " + columna + ".\n");
                    filaActual = fila;
                    columnaActual = columna;
                    valorActual = e.getText();
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
        if (incrementador >= 0) {
            int fila = jugadas.get(incrementador).getFila();
            int columna = jugadas.get(incrementador).getColumna();
            txts[fila][columna].setText("");
            matriz[fila][columna] = "0";
            imprimirMatriz(matriz);
            historialTxA.appendText("> Se deshizo el numero " + valorActual
                    + " de la fila " + filaActual + " y columna " + columnaActual + ".\n");
            incrementador--;

        }

        if (incrementador >= 0) {
            valorActual = jugadas.get(incrementador).getValor();
            filaActual = jugadas.get(incrementador).getFila();
            columnaActual = jugadas.get(incrementador).getColumna();
        }

    }

    @FXML
    private void btnRehacerPressed(ActionEvent event) {
        if (incrementador < jugadas.size() - 1) {
            incrementador++;
            int fila = jugadas.get(incrementador).getFila();
            int columna = jugadas.get(incrementador).getColumna();
            String valor = jugadas.get(incrementador).getValor();
            txts[fila][columna].setText(valor);
            matriz[fila][columna] = valor;
            imprimirMatriz(matriz);
            historialTxA.appendText("> Se rehizo el numero " + valor + " en la fila " + fila + " y columna " + columna + ".\n");
        }

    }

    private void cargarImagenesBotones() {
        Image imageBtnDeshacer = new Image(getClass().getResourceAsStream("/recursos/imagenes/deshacer.png"));
        btnDeshacer.setGraphic(new ImageView(imageBtnDeshacer));

        Image imageBtnRehacer = new Image(getClass().getResourceAsStream("/recursos/imagenes/rehacer.png"));
        btnRehacer.setGraphic(new ImageView(imageBtnRehacer));

        Image imageBtnAyuda = new Image(getClass().getResourceAsStream("/recursos/imagenes/ayuda.png"));
        btnAyuda.setGraphic(new ImageView(imageBtnAyuda));
    }

    @FXML
    private void btnAyudaPressed(ActionEvent event) {
        buscarCasillaDisponible();
    }

    private void buscarCasillaDisponible() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (matriz[i][j].equals("0")) {
                    sugerirNumero(i, j);
                    
                }
            }
        }       
    }

    private void sugerirNumero(int fila, int columna) {
        for (int i = 0; i < 10; i++) {
            Sudoku sudoku = new Sudoku(matriz, String.valueOf(i), fila, columna);
            if (!(sudoku.buscarNumeroXCuadrante() || sudoku.encontrarNumeroXFila() || sudoku.encontrarNumeroxColumna())) {
                IAlertBox alert = new AlertBox();
                alert.showAlert("Sudoku", "Ayuda", "Podrias colocar el número " + i + " en la fila " + fila + " y columna " + columna);
                break;
            }
        }

    }
}
