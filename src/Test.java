import component.gamemodel.ChessBoard;
import controller.Player;
import controller.SingleGameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Test extends Application{

    @Override
    public void start(Stage primaryStage){
        TestComponent t = new TestComponent();
        primaryStage.setScene(new Scene(t));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}