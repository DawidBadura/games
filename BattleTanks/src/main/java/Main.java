import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            MenuView manager = new MenuView();
            primaryStage = manager.getMainStage();
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}



