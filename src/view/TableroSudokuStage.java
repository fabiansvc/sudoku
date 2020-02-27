package view;
/**
 *
 * @author Fabián Stiven Valencia Córdoba
 */
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TableroSudokuStage extends Stage {

    private FXMLLoader loader;
    /**
     * Constructor de TableroTriquiStage
     */
    public TableroSudokuStage() {
        try {
            loader = new FXMLLoader(getClass().getResource("/view/FXMLTableroSudoku.fxml"));
            Parent root = loader.load();
            System.out.println(root.getClass().getName());
            Scene scene = new Scene(root);
            setScene(scene);
            setTitle("Sudoku");
            //getIcons().add(new Image("/recursos/imagenes/iconTriqui.png"));
            setResizable(false);
            show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que cierra el Stage del tablero del triqui<br>
     * y borra su instancia
     */
    public static void deleteInstance() {
        TableroSudokuStageHolder.INSTANCE.close();
        TableroSudokuStageHolder.INSTANCE = null;
    }

    /**
     * Método que por medio de una clase estatica retorna<br>
     * una instancia de la clase TableroTriquiStage
     *
     * @return
     */
    public static TableroSudokuStage getInstance() {
        return TableroSudokuStageHolder.INSTANCE = new TableroSudokuStage();
    }

    /**
     * Clase estatica que contiene una instancia de la<br>
     * clase TableroTriquiStage
     */
    private static class TableroSudokuStageHolder {

        private static TableroSudokuStage INSTANCE;
    }
    
}
