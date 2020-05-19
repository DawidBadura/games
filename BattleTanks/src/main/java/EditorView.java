import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EditorView {
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;


    private static final int GAME_WIDTH = 1280;
    private static final int GAME_HEIGHT = 960;

    private final static String BACKGROUND_IMAGE = "file:BattleTanks/src/main/resources/backImage.bmp";

    Image TREES_IMAGE = new Image("file:BattleTanks/src/main/resources/forest.png");
    Image  BRICK_IMAGE = new Image("file:BattleTanks/src/main/resources/brick.png");
    Image STEEL_IMAGE = new Image("file:BattleTanks/src/main/resources/steel.png");
    Image WATER_IMAGE = new Image("file:BattleTanks/src/main/resources/water.png");
    Image EMPTY_IMAGE = new Image("file:BattleTanks/src/main/resources/black.png");
    Image greyBorder=new Image("file:BattleTanks/src/main/resources/greyborder.png");
    private ImageView backgrondObject;

    private AnimationTimer gameTimer;

    private List<ImageView> listOfMainObjects = new ArrayList<>();

    private List<ImageView> listOfBackgroundObjects = new ArrayList<>();

    private List<ImageView> listOfBorders= new ArrayList();

    String nameText="test.txt";

    char[][] newMap= Map.getLEVEL1();

    Map mapa= new Map(nameText);



    int indexOfElement;

    public EditorView() {
        initializeStage();
    }
    public void createBorders(){
        listOfBorders.add(new ImageView(greyBorder));
        listOfBorders.add(new ImageView(greyBorder));
        listOfBorders.add(new ImageView(greyBorder));
        listOfBorders.add(new ImageView(greyBorder));

        listOfBorders.get(0).setLayoutX(1200);
        listOfBorders.get(0).setLayoutY(0);
        listOfBorders.get(0).setFitHeight(GAME_HEIGHT);
        listOfBorders.get(0).setFitWidth(80);
        gamePane.getChildren().add(listOfBorders.get(0));

        listOfBorders.get(1).setLayoutX(0);
        listOfBorders.get(1).setLayoutY(0);
        listOfBorders.get(1).setFitHeight(GAME_HEIGHT);
        listOfBorders.get(1).setFitWidth(48);
        gamePane.getChildren().add(listOfBorders.get(1));


        listOfBorders.get(2).setLayoutX(48);
        listOfBorders.get(2).setLayoutY(0);
        listOfBorders.get(2).setFitHeight(48);
        listOfBorders.get(2).setFitWidth(1152);
        gamePane.getChildren().add(listOfBorders.get(2));

        listOfBorders.get(3).setLayoutX(48);
        listOfBorders.get(3).setLayoutY(912);
        listOfBorders.get(3).setFitHeight(48);
        listOfBorders.get(3).setFitWidth(1152);
        gamePane.getChildren().add(listOfBorders.get(3));
    }
    public void createGameElements() {

        listOfMainObjects.add(new ImageView(EMPTY_IMAGE));
        listOfMainObjects.add(new ImageView(BRICK_IMAGE));
        listOfMainObjects.add(new ImageView(TREES_IMAGE));
        listOfMainObjects.add(new ImageView(STEEL_IMAGE));
        listOfMainObjects.add(new ImageView(WATER_IMAGE));

        for(int i=0;i<listOfMainObjects.size();i++){
        listOfMainObjects.get(i).setLayoutX(1228);
            listOfMainObjects.get(i).setLayoutY(425+(i*50));
            int finalI = i;
            listOfMainObjects.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    indexOfElement= finalI;
                }
            });
            gamePane.getChildren().add(listOfMainObjects.get(i));
        }
    }

    public void createBackground() {
        Image backgroundImage = new Image(BACKGROUND_IMAGE, 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(background));


        for (int i = 0; i < mapa.LEVEL1.length; i++) {
            char[] line = mapa.LEVEL1[i];
            for (int j = 0; j < line.length; j++) {
                switch (line[j]) {
                    case '0':
                        createBackgroundObject(48+i*24, 48+j*24,0);
                        break;
                    case '1':
                        createBackgroundObject(48+i*24, 48+j*24,1);
                        break;
                    case '2':
                        createBackgroundObject(48+i*24, 48+j*24,2);
                        break;
                    case '3':
                        createBackgroundObject(48+i*24, 48+j*24,3);
                        break;
                    case '4':
                        createBackgroundObject(48+i*24, 48+j*24,4);
                        break;
                }
            }
        }
    }

    public void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    public void createNewGame(Stage menuStage) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        createBackground();
        createBorders();
        createGameElements();
        createGameLoop();
        gameStage.show();

    }

    public void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for(int i=0; i<listOfBackgroundObjects.size();i++){
                    int finalI = i;
                    listOfBackgroundObjects.get(i).setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {

                            switch (indexOfElement){
                                case 0:
                                    listOfBackgroundObjects.get(finalI).setImage(EMPTY_IMAGE);
                                    newMap[(int) (listOfBackgroundObjects.get(finalI).getLayoutY()/24)-2][(int) (listOfBackgroundObjects.get(finalI).getLayoutX()/24)-2]='0';
                                    break;
                                case 1:
                                    listOfBackgroundObjects.get(finalI).setImage(BRICK_IMAGE);
                                    newMap[(int) (listOfBackgroundObjects.get(finalI).getLayoutY()/24)-2][(int) (listOfBackgroundObjects.get(finalI).getLayoutX()/24)-2]='1';
                                    break;
                                case 2:
                                    listOfBackgroundObjects.get(finalI).setImage(TREES_IMAGE);
                                    newMap[(int) (listOfBackgroundObjects.get(finalI).getLayoutY()/24)-2][(int) (listOfBackgroundObjects.get(finalI).getLayoutX()/24)-2]='2';
                                    break;
                                case 3:
                                    listOfBackgroundObjects.get(finalI).setImage(STEEL_IMAGE);
                                    newMap[(int) (listOfBackgroundObjects.get(finalI).getLayoutY()/24)-2][(int) (listOfBackgroundObjects.get(finalI).getLayoutX()/24)-2]='3';
                                    break;
                                case 4:
                                    listOfBackgroundObjects.get(finalI).setImage(WATER_IMAGE);
                                    newMap[(int) (listOfBackgroundObjects.get(finalI).getLayoutY()/24)-2][(int) (listOfBackgroundObjects.get(finalI).getLayoutX()/24)-2]='4';
                                    break;

                            }
                        }
                    });
                }

                gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if(event.getCode()== KeyCode.ESCAPE){
                         mapa.saveMap(nameText,newMap);
                            gameStage.close();
                            gameTimer.stop();
                            menuStage.show();
                        }
                    }
                });
            }
        };
        gameTimer.start();
    }

    public void createBackgroundObject(int posy, int posx, int number) {
        switch (number){
            case 0:
                backgrondObject= new ImageView(EMPTY_IMAGE);
                break;
            case 1:
                backgrondObject = new ImageView(BRICK_IMAGE);
                break;
            case 2:
                backgrondObject = new ImageView(TREES_IMAGE);
                break;
            case 3:
                backgrondObject = new ImageView(STEEL_IMAGE);
                break;
            case 4:
                backgrondObject = new ImageView(WATER_IMAGE);
                break;
        }

        backgrondObject.setLayoutX(posx);
        backgrondObject.setLayoutY(posy);


        gamePane.getChildren().add(backgrondObject);
        listOfBackgroundObjects.add(backgrondObject);
    }



}
