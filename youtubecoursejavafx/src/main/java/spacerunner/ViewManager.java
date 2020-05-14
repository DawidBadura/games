package spacerunner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class
ViewManager {
    private static final int HEIGHT = 760;
    private static final int WIDTH = 1024;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    private final static int MENU_BUTTONS_START_X = 100;
    private final static int MENU_BUTTONS_START_Y = 150;


    private SpaceRunnerSubScene creditsSubScene;
    private SpaceRunnerSubScene helpSubScene;
    private SpaceRunnerSubScene scoreSubScene;
    private SpaceRunnerSubScene shipChooserScene;

    private SpaceRunnerSubScene sceneToHide;

    List<SpaceRunnerButton> menuButtons;

    List<ShipPicker> shipsList;
    private SHIP choosenShip;


    public ViewManager() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH,HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createSubScenes();
        createButtons();
        createBackground();
        createLogo();

        /*SpaceRunnerSubScene subScene = new SpaceRunnerSubScene();

        subScene.setLayoutX(200);
        subScene.setLayoutY(100);
        mainPane.getChildren().add(subScene);*/

    }

    private void showSubScene(SpaceRunnerSubScene subScene) {
        if(sceneToHide != null){
            sceneToHide.moveSubScene();
        }
        subScene.moveSubScene();
        sceneToHide=subScene;
    }

    private void createSubScenes() {
        creditsSubScene=new SpaceRunnerSubScene();
        mainPane.getChildren().add(creditsSubScene);

        helpSubScene=new SpaceRunnerSubScene();
        mainPane.getChildren().add(helpSubScene);

        scoreSubScene=new SpaceRunnerSubScene();
        mainPane.getChildren().add(scoreSubScene);

        shipChooserScene=new SpaceRunnerSubScene();
        mainPane.getChildren().add(shipChooserScene);

        createShipChooserSubScene();

    }
    private void createShipChooserSubScene() {
        shipChooserScene=new SpaceRunnerSubScene();
        mainPane.getChildren().add(shipChooserScene);

        InfoLabel chooseShipLabel = new InfoLabel("CHOOSE YOUR SHIP");
        chooseShipLabel.setLayoutX(110);
        chooseShipLabel.setLayoutY(25);
        shipChooserScene.getPane().getChildren().add(chooseShipLabel);
        shipChooserScene.getPane().getChildren().add(createShipsToChoose());
        shipChooserScene.getPane().getChildren().add(createButtonToStart());
    }

    private HBox createShipsToChoose() {
        HBox box = new HBox();
        box.setSpacing(20);
        shipsList= new ArrayList<>();
        for(SHIP ship: SHIP.values()){
            ShipPicker shipToPick = new ShipPicker(ship);
            shipsList.add(shipToPick);//po dodaniu tego znaczek zaznaczenia statku zacza przeskakiwac a nie zostawac przy statkach
            box.getChildren().add(shipToPick);
            shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (ShipPicker ship : shipsList) {
                        ship.setIsCircleChoosen(false);
                    }
                    shipToPick.setIsCircleChoosen(true);
                    choosenShip = shipToPick.getShip();
                }
            });
        }
        box.setLayoutX(300-(118*2));
        box.setLayoutY(100);
        return box;
    }

    private SpaceRunnerButton createButtonToStart(){
        SpaceRunnerButton startButton = new SpaceRunnerButton("START");
        startButton.setLayoutX(350);
        startButton.setLayoutY(300);

        startButton.setOnAction(new EventHandler<ActionEvent>() {//druga część
            @Override
            public void handle(ActionEvent event) {
                if(choosenShip!=null){
                    GameViewManager gameManager = new GameViewManager();
                    gameManager.createNewGame(mainStage, choosenShip);
                }
            }
        });
        return  startButton;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButton(SpaceRunnerButton button) {
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
        button.setLayoutX(MENU_BUTTONS_START_X);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createButtons() {
        createStartButton();
        createScoresButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();

        /*SpaceRunnerButton button = new SpaceRunnerButton("click me!");
        mainPane.getChildren().add(button);

        button.setLayoutX(200);
        button.setLayoutY(200);*/
    }

    private void createStartButton() {
        SpaceRunnerButton startButton = new SpaceRunnerButton("PLAY");
        addMenuButton(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //shipChoserScene.moveSubScene();
                showSubScene(shipChooserScene);
            }
        });
    }

    private void createScoresButton() {
        SpaceRunnerButton scoresButton = new SpaceRunnerButton("SCORES");
        addMenuButton(scoresButton);

        scoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(scoreSubScene);
            }
        });
    }

    private void createHelpButton() {
        SpaceRunnerButton helpButton = new SpaceRunnerButton("HELP");
        addMenuButton(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(helpSubScene);
            }
        });
    }

    private void createCreditsButton() {
        SpaceRunnerButton creditsButton = new SpaceRunnerButton("CREDITS");
        addMenuButton(creditsButton);

        creditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(creditsSubScene);
            }
        });
    }

    private void createExitButton() {
        SpaceRunnerButton exitButton = new SpaceRunnerButton("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }

    private void createBackground() {
    Image backgroundImage = new Image("file:youtubecoursejavafx/src/main/java/spacerunner/resources/purple.png", 256,256,false,true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,null);
        mainPane.setBackground(new Background(background));
    }
    private void createLogo() {
        ImageView logo = new ImageView("file:youtubecoursejavafx/src/main/java/spacerunner/resources/spacerunner.png");
        logo.setLayoutX(300);
        logo.setLayoutY(50);

        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(new DropShadow());
            }
        });
        logo.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(null);
            }
        });
        mainPane.getChildren().add(logo);
    }
}
        /*Button button = new Button();
        button.setLayoutX(100);
        button.setLayoutY(100);
        mainPane.getChildren().add(button);

        button.setOnMouseEntered((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("mouse entered!");
            }
        }));*/


