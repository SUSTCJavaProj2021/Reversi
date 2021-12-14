import component.gamemodel.ChessBoard;
import controller.Player;
import controller.SingleGameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Test extends Application{

    @Override
    public void start(Stage primaryStage){
        ChessBoard cb = new ChessBoard(new SingleGameController(new Player("K"), new Player("X"), false));
        primaryStage.setScene(new Scene(cb));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}