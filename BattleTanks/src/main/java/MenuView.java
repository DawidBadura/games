import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MenuView {
/*    public Image imageback; = new Image("file:BattleTanks/src/main/resources/background.png");
    public Image tank; = new Image("file:BattleTanks/src/main/resources/tank.png");*/

    private static final int HEIGHT = 760;
    private static final int WIDTH = 1024;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    private final static int MENU_BUTTONS_START_X = 100;
    private final static int MENU_BUTTONS_START_Y = 150;

    List<MenuItem> menuButtons;

    public MenuView() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH,HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setTitle("Battle City");

        MenuBox menu = new MenuBox();
        createButtons();
        createBackground();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void createBackground() {
        Image backgroundImage = new Image("file:BattleTanks/src/main/resources/backImage.bmp", 256,256,false,true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,null);
        mainPane.setBackground(new Background(background));

    }

    private void createButtons() {
        MenuItem itemOnePlayer = new MenuItem("One Player");
        itemOnePlayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GameView game=new GameView();
                game.createNewGame(mainStage);
            }
        });
        MenuItem itemTwoPlayers = new MenuItem("Two Players");
        itemTwoPlayers.setOnMouseClicked(event -> System.exit(0));
        MenuItem itemExit = new MenuItem("Exit");
        itemExit.setOnMouseClicked(event -> System.exit(0));

        MenuBox menu = new MenuBox(
                itemOnePlayer,
                itemTwoPlayers,
                itemExit);
        menu.setTranslateX(350);
        menu.setTranslateY(300);

        mainPane.getChildren().add(menu);

    }

/* BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
    BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
    Background background = new Background(backgroundImage);

    GridPane grid = new GridPane();
        grid.setBackground(background);

    Scene scene = new Scene(grid, 1600, 900, Color.BLACK);

        primaryStage.setTitle("BlackJack");
        primaryStage.setScene(scene);
        primaryStage.show();*/
}
