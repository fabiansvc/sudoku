package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Archivo;
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

    //Variables de interfaz gráfica.
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button btnDeshacer;
    @FXML
    private Button btnRehacer;
    @FXML
    private Button btnAyuda;
    @FXML
    private TextArea historialTxA;

    //Estructura de datos.
    private String matriz[][];
    private TextField[][] txts;
    private ArrayList<Casilla> jugadas;
    private ArrayList<Casilla> casillasEliminadas;

    //Variables para la jugabilidad.
    private int incrementadorHistorial;
    private int filaActual, columnaActual;
    private String valorActual, valorPresionado;
    private boolean activarSugerencia;
    private boolean primeraJugada;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarImagenesBotones();

        matriz = new String[9][9];
        txts = new TextField[9][9];
        jugadas = new ArrayList<>();
        casillasEliminadas = new ArrayList<>();

        incrementadorHistorial = -1;
        activarSugerencia = false;
        primeraJugada = true;

        cargarTablero();
    }

    /**
     * Método que se encarga de pintar el tablero 9x9 del sudoku con los valores
     * predeterminados en el archivo.
     */
    private void cargarTablero() {
        Archivo archivo = new Archivo();
        String cadena = archivo.cargarArchivo("archivo.txt");
        int incrementadorCadena = 0;
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
                txts[i][j] = txt;
                String valor = String.valueOf(cadena.charAt(incrementadorCadena));
                incrementadorCadena++;
                if (!(valor.equals("-"))) {
                    txt.setText(valor);
                    txt.setEditable(false);
                    matriz[i][j] = valor;
                } else {
                    txt.setStyle("-fx-background-color: white;");
                    matriz[i][j] = "";
                }
                if ((j < 3 && (i < 3 || i > 5)) || (j > 5 && (i < 3 || i > 5))
                        || (j > 2 && j < 6 && i > 2 && i < 6)) {
                    txt.setStyle("-fx-border-color: black; ");
                }
                gridpane.getChildren().addAll(txt);
                keyPressed(txt, i, j);
                txtClickPressed(txt, i, j);
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
            if (isNumeric(e.getText()) && Integer.parseInt(e.getText()) > 0 && Integer.parseInt(e.getText()) < 10) {
                validarNumero(fila, columna, e.getText());
            } else if ((e.getCode().toString()).equals("BACK_SPACE")) {
                asignarAJugadasRealizadas(fila, columna, "");
                matriz[fila][columna] = "";
                asignarACasillasBorradas(fila, columna, valorPresionado);
            } else {
                IAlertBox alert = new AlertBox();
                alert.showAlert("Error", "Caracter no permitido", "Solo se admiten numeros del 1 al 9 o la tecla borrar.");
            }
            imprimirMatriz(matriz);
        });

    }

    /**
     * Método que escucha si se presiona un campo de texto capturando la fila,
     * la columna y el valor.
     *
     * @param txt
     * @param fila
     * @param columna
     */
    private void txtClickPressed(TextField txt, int fila, int columna) {
        txt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                filaActual = fila;
                columnaActual = columna;
                valorPresionado = txt.getText();
                activarSugerencia = true;
            }
        });
    }

    /**
     * Método que se encarga de validar el numero si esta correcto.
     *
     * @param fila
     * @param columna
     * @param valor
     */
    private void validarNumero(int fila, int columna, String valor) {
        matriz[fila][columna] = valor;
        Sudoku sudoku = new Sudoku(matriz, valor, fila, columna);
        if (sudoku.buscarNumeroXCuadrante() || sudoku.encontrarNumeroXFila() || sudoku.encontrarNumeroxColumna()) {
            IAlertBox alert = new AlertBox();
            alert.showAlert("Sudoku", "Jugada invalida", "Intenta con otro número");
            matriz[fila][columna] = "0";
        } else if (sudoku.sudokuCompleto()) {
            IAlertBox alert = new AlertBox();
            alert.showAlert("Felicitaciones", "Ganastes", "Intenta de nuevo");
        } else {
            if (primeraJugada) {
                asignarAJugadasRealizadas(fila, columna, "");
                primeraJugada = false;
            }
            asignarAJugadasRealizadas(fila, columna, valor);
            historialTxA.appendText("> Se inserto el numero " + valor + " en la fila " + fila + " y columna " + columna + ".\n");
        }
    }

    /**
     * Método que se encarga de asignar a un
     *
     * @param fila
     * @param columna
     * @param valor
     */
    private void asignarAJugadasRealizadas(int fila, int columna, String valor) {
        incrementadorHistorial += 1;
        Casilla casilla = new Casilla(valor, fila, columna);
        jugadas.add(casilla);
        filaActual = fila;
        columnaActual = columna;
        valorActual = valor;
    }
    /**
     * Método que asigna una casilla borrada.
     * @param fila
     * @param columna
     * @param valor 
     */
    private void asignarACasillasBorradas(int fila, int columna, String valor) {
        Casilla casilla = new Casilla(valor, fila, columna);
        casillasEliminadas.add(casilla);
    }

    /**
     * Método que se encarga de verificar si la tecla es númerica.
     *
     * @param valor
     * @return
     */
    private boolean isNumeric(String valor) {
        try {
            Integer.parseInt(valor);
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
        for (int i = 0; i < matriz.length; i++) {
            System.out.print("|");
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j].equals("")) {
                    System.out.print("0");
                }
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println("|");
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    private void btnDeshacerPressed(ActionEvent event) {

        if (incrementadorHistorial > 0) {
            int fila = jugadas.get(incrementadorHistorial).getFila();
            int columna = jugadas.get(incrementadorHistorial).getColumna();

            if (fila == jugadas.get(incrementadorHistorial - 1).getFila() && columna == jugadas.get(incrementadorHistorial - 1).getColumna()) {
                String valor = jugadas.get(incrementadorHistorial - 1).getValor();
                txts[fila][columna].setText(valor);
                matriz[fila][columna] = valor;
            } else {
                txts[fila][columna].setText("");
                matriz[fila][columna] = "";
            }

            imprimirMatriz(matriz);
            historialTxA.appendText("> Se deshizo el numero " + valorActual
                    + " de la fila " + filaActual + " y columna " + columnaActual + ".\n");
            incrementadorHistorial--;
            establecerNuevosValores();
        }

    }

    /**
     * Método que establece nuevos valores.
     */
    private void establecerNuevosValores() {
        if (incrementadorHistorial >= 0) {
            valorActual = jugadas.get(incrementadorHistorial).getValor();
            filaActual = jugadas.get(incrementadorHistorial).getFila();
            columnaActual = jugadas.get(incrementadorHistorial).getColumna();
        }

    }

    /**
     * Método que escucha el boton rehacer.
     *
     * @param event
     */
    @FXML
    private void btnRehacerPressed(ActionEvent event) {
        if (incrementadorHistorial < jugadas.size() - 1) {
            incrementadorHistorial++;
            int fila = jugadas.get(incrementadorHistorial).getFila();
            int columna = jugadas.get(incrementadorHistorial).getColumna();
            String valor = jugadas.get(incrementadorHistorial).getValor();
            txts[fila][columna].setText(valor);
            matriz[fila][columna] = valor;
            imprimirMatriz(matriz);

            historialTxA.appendText("> Se rehizo el numero " + valor + " en la fila " + fila + " y columna " + columna + ".\n");
        }
    }

    /**
     * Método que escucha el boton ayuda, para sugerir un número en una casilla solicitada
     * por el usuario, cumpliendo la condición de que no haya ingresado anteriormente
     * un número en esa casilla.     *
     * @param event
     */
    @FXML
    private void btnAyudaPressed(ActionEvent event) {
        buscarNumeroEliminado();
        if (activarSugerencia) {
            for (int i = 1; i < 10; i++) {
                Sudoku sudoku = new Sudoku(matriz, String.valueOf(i), filaActual, columnaActual);
                if (!buscarNumeroEliminado() && (valorPresionado.equals("")) && !(sudoku.buscarNumeroXCuadrante()
                        || sudoku.encontrarNumeroXFila() || sudoku.encontrarNumeroxColumna())) {
                    IAlertBox alert = new AlertBox();
                    alert.showAlert("Sudoku", "Sugerencia", "Puedes ingresar el número: " + i);
                    break;
                }
                activarSugerencia = false;
            }
        } else {
            IAlertBox alert = new AlertBox();
            alert.showAlert("Sudoku", "Información", "Selecciona una fila y una columna vacia, "
                    + "donde no se haya ingresado un número para sugerirle un número.");
        }

    }
    /**
     * Método que busca si el numero ha sido eliminado anteriormente en esa casilla.
     * @return verdadero si lo encuentra.
     */
    private boolean buscarNumeroEliminado() {
        for (int i = 0; i < casillasEliminadas.size(); i++) {
            if (!(casillasEliminadas.get(i).getValor().equals("0"))
                    && filaActual == casillasEliminadas.get(i).getFila()
                    && columnaActual == casillasEliminadas.get(i).getColumna()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método que carga las imagenes de los botones deshacer, rehacer y ayuda.
     */
    private void cargarImagenesBotones() {
        Image imageBtnDeshacer = new Image(getClass().getResourceAsStream("/recursos/imagenes/deshacer.png"));
        btnDeshacer.setGraphic(new ImageView(imageBtnDeshacer));

        Image imageBtnRehacer = new Image(getClass().getResourceAsStream("/recursos/imagenes/rehacer.png"));
        btnRehacer.setGraphic(new ImageView(imageBtnRehacer));

        Image imageBtnAyuda = new Image(getClass().getResourceAsStream("/recursos/imagenes/ayuda.png"));
        btnAyuda.setGraphic(new ImageView(imageBtnAyuda));
    }
}
