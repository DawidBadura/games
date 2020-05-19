import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.LinkedList;
import java.util.Random;

public class GameView {

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;

    private static final int GAME_WIDTH = 1280;
    private static final int GAME_HEIGHT = 960;

    private ImageView player;
    private ImageView bullet;
    private ImageView bullet1;
    private ImageView enemy;
    private ImageView backgrondObject;
    private ImageView buffer;
    private ImageView buffer1;

    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean isUpKeyPressed;
    private boolean isDownKeyPressed;
    boolean ableToShoot=true;


    private int keysPressed = 0;
    private int dealy=0;
    private int direct;

    private AnimationTimer gameTimer;
    private String nameText = "test.txt";
    private File file = new File(nameText);
    private Random randomPositionGenerator;

    private final static String BACKGROUND_IMAGE = "file:BattleTanks/src/main/resources/backImage.bmp";

    Image TANK_IMAGE = new Image("file:BattleTanks/src/main/resources/Battle_City_Tank_Enemy4.png");
    Image BLUE_TANK = new Image("file:BattleTanks/src/main/resources/tank.png");
    Image BULLET_TANK_IMAGE = new Image("file:BattleTanks/src/main/resources/bulletBlue1.png");
    Image BRICK_IMAGE = new Image("file:BattleTanks/src/main/resources/brick.png");
    Image TREES_IMAGE = new Image("file:BattleTanks/src/main/resources/forest.png");
    Image STEEL_IMAGE = new Image("file:BattleTanks/src/main/resources/steel.png");
    Image WATER_IMAGE = new Image("file:BattleTanks/src/main/resources/water.png");
    Image greyBorder=new Image("file:BattleTanks/src/main/resources/greyborder.png");

    private LinkedList<ImageView> enemyBullets = new LinkedList<>();
    private LinkedList<ImageView> bullets = new LinkedList<>();
    private LinkedList<ImageView> enemies = new LinkedList<>();
    private LinkedList<ImageView> players = new LinkedList<>();
    private LinkedList<ImageView> listOfBackgroundObjects = new LinkedList<>();
    private LinkedList<ImageView> backgroundObjects=new LinkedList<>();
    private LinkedList<ImageView> enemiesBuffers=new LinkedList<>();

    //private int playerLife;

    private final static int TANK_RADIUS = 12;

    public GameView() {
        initializeStage();
        createKeyListeners();
        randomPositionGenerator = new Random();
    }

    public void createBorders(){
        listOfBackgroundObjects.add(new ImageView(greyBorder));
        listOfBackgroundObjects.add(new ImageView(greyBorder));
        listOfBackgroundObjects.add(new ImageView(greyBorder));
        listOfBackgroundObjects.add(new ImageView(greyBorder));

        listOfBackgroundObjects.get(0).setLayoutX(1200);
        listOfBackgroundObjects.get(0).setLayoutY(0);
        listOfBackgroundObjects.get(0).setFitHeight(GAME_HEIGHT);
        listOfBackgroundObjects.get(0).setFitWidth(80);
        gamePane.getChildren().add(listOfBackgroundObjects.get(0));

        listOfBackgroundObjects.get(1).setLayoutX(0);
        listOfBackgroundObjects.get(1).setLayoutY(0);
        listOfBackgroundObjects.get(1).setFitHeight(GAME_HEIGHT);
        listOfBackgroundObjects.get(1).setFitWidth(48);
        gamePane.getChildren().add(listOfBackgroundObjects.get(1));


        listOfBackgroundObjects.get(2).setLayoutX(48);
        listOfBackgroundObjects.get(2).setLayoutY(0);
        listOfBackgroundObjects.get(2).setFitHeight(48);
        listOfBackgroundObjects.get(2).setFitWidth(1152);
        gamePane.getChildren().add(listOfBackgroundObjects.get(2));

        listOfBackgroundObjects.get(3).setLayoutX(48);
        listOfBackgroundObjects.get(3).setLayoutY(912);
        listOfBackgroundObjects.get(3).setFitHeight(48);
        listOfBackgroundObjects.get(3).setFitWidth(1152);
        gamePane.getChildren().add(listOfBackgroundObjects.get(3));
    }

//fxfill color(css)

    public void createKeyListeners() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = true;
                    keysPressed = 1;

                } else if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = true;
                    keysPressed = 2;


                } else if (event.getCode() == KeyCode.UP) {
                    isUpKeyPressed = true;
                    keysPressed = 3;

                } else if (event.getCode() == KeyCode.DOWN) {
                    isDownKeyPressed = true;
                    keysPressed = 4;
                }
                if (event.getCode() == KeyCode.SPACE&&player.isVisible()&&ableToShoot) {
                    createBullet();
                    bullets.add(bullet);
                    ableToShoot=false;

                }
                if (event.getCode() == KeyCode.ESCAPE) {
                    gameStage.close();
                    gameTimer.stop();
                    menuStage.show();
                }
            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()== KeyCode.SPACE){
                    ableToShoot=true;
                }
                if (event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = false;
                    keysPressed = 0;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = false;
                    keysPressed = 0;
                } else if (event.getCode() == KeyCode.DOWN) {
                    isDownKeyPressed = false;
                    keysPressed = 0;
                } else if (event.getCode() == KeyCode.UP) {
                    isUpKeyPressed = false;
                    keysPressed = 0;
                }


            }

        });
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
        createBorders();
        createEnemy(190);
        createEnemy(754);
        createEnemy(364);
        createEnemy(66);
        createPlayer();
        createBackground();
        createBullet();
        createGameLoop();

        gameStage.show();



    }

    public void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Random r = new Random();
                removeColidingItems();
                moveEnemy(r.nextInt(4));
                movePlayer();
                moveBullet();
            }
        };
        gameTimer.start();
    }

    private void moveEnemy(int direction) {
        for(int i=0;i<enemiesBuffers.size();i++){
            enemiesBuffers.get(i).setLayoutX(enemies.get(i).getLayoutX());
            enemiesBuffers.get(i).setLayoutY(enemies.get(i).getLayoutY());
        if(dealy<26){
            if(dealy==6&&enemies.get(i).isVisible()){
                createEnemyBullet(enemies.get(i));
            }

            direction=direct;

            if(direction==(1+i)%4){
                enemiesBuffers.get(i).setLayoutX(enemies.get(i).getLayoutX() - 3);
                enemiesBuffers.get(i).setLayoutY(enemies.get(i).getLayoutY());
                enemies.get(i).setRotate(90);
                if (!checkIfElementsCollide(enemiesBuffers.get(i), listOfBackgroundObjects)&&!checkIfElementsCollide(enemiesBuffers.get(i),players)) {
                    enemies.get(i).setLayoutX(enemies.get(i).getLayoutX() - 3);
                }
            }

            if(direction==(2+i)%4){
                enemiesBuffers.get(i).setLayoutX(enemies.get(i).getLayoutX()+3);
                enemiesBuffers.get(i).setLayoutY(enemies.get(i).getLayoutY());
                enemies.get(i).setRotate(-90);

                if (!checkIfElementsCollide(enemiesBuffers.get(i), listOfBackgroundObjects)&&!checkIfElementsCollide(enemiesBuffers.get(i),players)) {
                    enemies.get(i).setLayoutX(enemies.get(i).getLayoutX() + 3);
                }
            }

            if(direction==(3+i)%4){
                enemiesBuffers.get(i).setLayoutX(enemies.get(i).getLayoutX());
                enemiesBuffers.get(i).setLayoutY(enemies.get(i).getLayoutY()-3);
                enemies.get(i).setRotate(180);

                if (!checkIfElementsCollide(enemiesBuffers.get(i), listOfBackgroundObjects)&&!checkIfElementsCollide(enemiesBuffers.get(i),players)) {
                    enemies.get(i).setLayoutY(enemies.get(i).getLayoutY() - 3);
                }
            }

            if(direction==(0+i)%4){
                enemiesBuffers.get(i).setLayoutX(enemies.get(i).getLayoutX());
                enemiesBuffers.get(i).setLayoutY(enemies.get(i).getLayoutY()+3);
                enemies.get(i).setRotate(0);
                if (!checkIfElementsCollide(enemiesBuffers.get(i), listOfBackgroundObjects)&&!checkIfElementsCollide(enemiesBuffers.get(i),players)) {
                    enemies.get(i).setLayoutY(enemies.get(i).getLayoutY() + 3);
                }
            }
            dealy++;
        }
    else {direct=direction;
    dealy=0;}
    }}

    private void moveBullet() {
        int i = 0;
        for (i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).getRotate() == 90) {
                bullets.get(i).setLayoutX(bullets.get(i).getLayoutX() - 6);
            } else if (bullets.get(i).getRotate() == -90) {
                bullets.get(i).setLayoutX(bullets.get(i).getLayoutX() + 6);
            } else if (bullets.get(i).getRotate() == 180) {
                bullets.get(i).setLayoutY(bullets.get(i).getLayoutY() - 6);
            } else if (bullets.get(i).getRotate() == 0) {
                bullets.get(i).setLayoutY(bullets.get(i).getLayoutY() + 6);
            }
        }int j = 0;
        for (j = 0; j < enemyBullets.size(); j++) {
            if (enemyBullets.get(j).getRotate() == 90) {
                enemyBullets.get(j).setLayoutX(enemyBullets.get(j).getLayoutX() - 6);
            } else if (enemyBullets.get(j).getRotate() == -90) {
                enemyBullets.get(j).setLayoutX(enemyBullets.get(j).getLayoutX() + 6);
            } else if (enemyBullets.get(j).getRotate() == 180) {
                enemyBullets.get(j).setLayoutY(enemyBullets.get(j).getLayoutY() - 6);
            } else if (enemyBullets.get(j).getRotate() == 0) {
                enemyBullets.get(j).setLayoutY(enemyBullets.get(j).getLayoutY() + 6);
            }
        }
    }

    public void movePlayer() {
        buffer1.setLayoutX(player.getLayoutX());
        buffer1.setLayoutY(player.getLayoutY());


        if (isLeftKeyPressed && (keysPressed == 0 || keysPressed == 1)) {
            buffer1.setLayoutX(player.getLayoutX() - 3);
            buffer1.setLayoutY(player.getLayoutY());
            player.setRotate(90);
            if (!checkIfElementsCollide(buffer1, listOfBackgroundObjects)&&!checkIfElementsCollide(buffer1,enemies)) {
                player.setLayoutX(player.getLayoutX() - 3);
            }
        }


        if (isRightKeyPressed && (keysPressed == 0 || keysPressed == 2)) {
                buffer1.setLayoutX(player.getLayoutX()+3);
                buffer1.setLayoutY(player.getLayoutY());
                player.setRotate(-90);

            if (!checkIfElementsCollide(buffer1, listOfBackgroundObjects)&&!checkIfElementsCollide(buffer1,enemies)) {
                player.setLayoutX(player.getLayoutX() + 3);
            }
        }


        if (isUpKeyPressed && (keysPressed == 0 || keysPressed == 3)) {
                buffer1.setLayoutX(player.getLayoutX());
                buffer1.setLayoutY(player.getLayoutY()-3);
                player.setRotate(180);

            if (!checkIfElementsCollide(buffer1, listOfBackgroundObjects)&&!checkIfElementsCollide(buffer1,enemies)) {
                player.setLayoutY(player.getLayoutY() - 3);
            }
        }

        if (isDownKeyPressed && (keysPressed == 0 || keysPressed == 4)) {
            buffer1.setLayoutX(player.getLayoutX());
            buffer1.setLayoutY(player.getLayoutY()+3);
            player.setRotate(0);
            if (!checkIfElementsCollide(buffer1, listOfBackgroundObjects)&&!checkIfElementsCollide(buffer1,enemies)) {
                player.setLayoutY(player.getLayoutY() + 3);
            }
        }

    }

    public void createPlayer() {
        buffer1 = new ImageView(BLUE_TANK);
        buffer1.setVisible(false);
        buffer1.setFitHeight(48);
        buffer1.setFitWidth(48);
        player = new ImageView(BLUE_TANK);
        player.setLayoutX(155);
        player.setLayoutY(GAME_HEIGHT - 190);
        player.setFitHeight(48);
        player.setFitWidth(48);
        gamePane.getChildren().add(player);
        player.setRotate(180);
        players.add(player);

    }

    public void createEnemy(int posy) {
        buffer = new ImageView(TANK_IMAGE);
        buffer.setFitHeight(48);
        buffer.setFitWidth(48);
        buffer.setVisible(false);
        enemy = new ImageView(TANK_IMAGE);
        enemy.setLayoutX(GAME_WIDTH / 2-50);
        enemy.setLayoutY(posy);
        enemy.setFitWidth(48);
        enemy.setFitHeight(48);
        gamePane.getChildren().add(enemy);
        enemies.add(enemy);
        enemiesBuffers.add(buffer);

    }

    public void createBackgroundObject(int posy, int posx, int number) {
        switch (number){
            case 1:
                backgrondObject = new ImageView(BRICK_IMAGE);
                backgroundObjects.add(backgrondObject);
                listOfBackgroundObjects.add(backgrondObject);

                break;
            case 2:
                backgrondObject = new ImageView(TREES_IMAGE);
                backgrondObject.setSmooth(true);

                break;
            case 3:
                backgrondObject = new ImageView(STEEL_IMAGE);
                backgroundObjects.add(backgrondObject);
                listOfBackgroundObjects.add(backgrondObject);

                break;
            case 4:
                backgrondObject = new ImageView(WATER_IMAGE);
                listOfBackgroundObjects.add(backgrondObject);

                break;


        }




        backgrondObject.setLayoutX(posx);
        backgrondObject.setLayoutY(posy);
        gamePane.getChildren().add(backgrondObject);
    }

    public void createBullet() {
        bullet = new ImageView(BULLET_TANK_IMAGE);
        bullet.setLayoutX(player.getLayoutX() + 23.5);
        bullet.setLayoutY(player.getLayoutY() + 23.5);
        gamePane.getChildren().add(bullet);
        bullet.setRotate(player.getRotate());
        bullets.add(bullet);
    }

    public void createEnemyBullet(ImageView enemy) {

        bullet1 = new ImageView(BULLET_TANK_IMAGE);
        bullet1.setLayoutX(enemy.getLayoutX() + 23.5);
        bullet1.setLayoutY(enemy.getLayoutY() + 23.5);
        gamePane.getChildren().add(bullet1);
        bullet1.setRotate(enemy.getRotate());
        enemyBullets.add(bullet1);

    }

    public void createBackground() {
        Image backgroundImage = new Image(BACKGROUND_IMAGE, 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(background));
        Map mapa= new Map(nameText);

        for (int i = 0; i < mapa.LEVEL1.length; i++) {
            char[] line = mapa.LEVEL1[i];

            for (int j = 0; j < line.length; j++) {

                switch (line[j]) {
                    case '0':
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

    private void removeColidingItems(){
        for(int i=0;i<enemiesBuffers.size();i++){
        if(checkAndDestroyCollide(enemies.get(i),bullets)){
            enemies.get(i).setVisible(false);}

        }
        if(checkAndDestroyCollide(player,enemyBullets)){
            player.setVisible(false);
        }
        for (int x=0;x<backgroundObjects.size();x++) {
            if(checkAndDestroyCollide(backgroundObjects.get(x),bullets)||checkAndDestroyCollide(backgroundObjects.get(x),enemyBullets)){
                backgroundObjects.get(x).setVisible(false);
            }
        }

    }

    private boolean checkIfElementsCollide(ImageView object, LinkedList<ImageView> objects2) {
        for (ImageView y:objects2) {
            if (y.isVisible()==true&&object.getBoundsInParent().intersects(y.getBoundsInParent())) {
                return true;
            }
        }
        return false;

    }
    private boolean checkAndDestroyCollide(ImageView object, LinkedList<ImageView> objects2) {
        for (ImageView y:objects2) {
            if (y.isVisible()==true&&object.isVisible()==true&&object.getBoundsInParent().intersects(y.getBoundsInParent())) {
                y.setVisible(false);
                return true;
            }
        }
        return false;

    }



}
