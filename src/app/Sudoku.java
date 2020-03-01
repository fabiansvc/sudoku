package app;

import javafx.application.Application;
import javafx.stage.Stage;
import view.TableroSudokuStage;

/**
 *
 * @author Fabián Valencia
 */
public class Sudoku extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        TableroSudokuStage.getInstance();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
