import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Random;

public class GameView {
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;

    private Stage menuStage;

    private ImageView player;
    private ImageView bullet;
    private ImageView enemy;
    private ImageView barrel;


    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean isUpKeyPressed;
    private boolean isDownKeyPressed;

    private int keysPressed = 0;

    private AnimationTimer gameTimer;


    private final static String BACKGROUND_IMAGE = "file:BattleTanks/src/main/resources/background.png";

    private final static String TANK_IMAGE = "file:BattleTanks/src/main/resources/tank.png";
    private final static String BULLET_TANK_IMAGE = "file:BattleTanks/src/main/resources/bulletBlue1.png";
    private final static String BARREL_IMAGE = "file:BattleTanks/src/main/resources/barrelRust_side.png";


    private LinkedList<ImageView> bullets = new LinkedList<>();
    private LinkedList<ImageView> enemies = new LinkedList<>();
    private LinkedList<ImageView> barrels = new LinkedList<>();


    Random randomPositionGenerator;

    private int playerLife;

    private final static int TANK_RADIUS = 12;

    public GameView() {
        initializeStage();
        createKeyListeners();
        randomPositionGenerator = new Random();
    }


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
                if (event.getCode() == KeyCode.SPACE) {
                    bullets.add(bullet);
                    createBullet();
                }
            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
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
        createBackground();
        createPlayer();
        createBullet();
        createEnemy(90);
        //createGameElements(choosenShip);
        createGameLoop();
        gameStage.show();
    }

    public void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //moveGameElements();
                checkIfElementsCollide();
                movePlayer();
                moveBullet();

            }
        };
        gameTimer.start();
    }

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
        }
    }

    public void movePlayer() {
        if (isLeftKeyPressed && (keysPressed == 0 || keysPressed == 1)) {
            player.setLayoutX(player.getLayoutX() - 3);
            player.setRotate(90);
        }


        if (isRightKeyPressed && (keysPressed == 0 || keysPressed == 2)) {
            player.setLayoutX(player.getLayoutX() + 3);
            player.setRotate(-90);

        }


        if (isUpKeyPressed && (keysPressed == 0 || keysPressed == 3)) {
            player.setLayoutY(player.getLayoutY() - 3);
            player.setRotate(180);

        }

        if (isDownKeyPressed && (keysPressed == 0 || keysPressed == 4)) {
            player.setLayoutY(player.getLayoutY() + 3);
            player.setRotate(0);

        }

    }

    public void createPlayer() {
        player = new ImageView(TANK_IMAGE);
        player.setLayoutX(GAME_WIDTH / 2);
        player.setLayoutY(GAME_HEIGHT - 90);
        gamePane.getChildren().add(player);
        player.setRotate(180);
    }

    public void createEnemy(int posy) {
        enemy = new ImageView(TANK_IMAGE);
        enemy.setLayoutX(GAME_WIDTH / 2);
        enemy.setLayoutY(posy);
        gamePane.getChildren().add(enemy);
        enemies.add(enemy);
    }

    public void createBarrel(int posy, int posx) {
        barrel = new ImageView(BARREL_IMAGE);
        barrel.setLayoutX(posx);
        barrel.setLayoutY(posy);
        gamePane.getChildren().add(barrel);
        barrels.add(barrel);
    }

    public void createBullet() {
        bullet = new ImageView(BULLET_TANK_IMAGE);
        bullet.setLayoutX(player.getLayoutX() + 15);
        bullet.setLayoutY(player.getLayoutY() + 15);
        gamePane.getChildren().add(bullet);
        bullet.setRotate(player.getRotate());
        bullets.add(bullet);
    }

    public void createBackground() {
        Image backgroundImage = new Image("file:BattleTanks/src/main/resources/backImage.bmp", 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(background));
        Map mapa= new Map();

        for (int i = 0; i < mapa.LEVEL1.length; i++) {
            String line = mapa.LEVEL1[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '2':
                        createBarrel(j*28, i*20);
                        break;
                }
            }
        }
    }

    private void checkIfElementsCollide() {
        int i = 0;
        for (i = 0; i < bullets.size(); i++) {
            if (TANK_RADIUS*2 > calulateDistance(bullets.get(i).getLayoutX() + 15, enemy.getLayoutX() + 15, bullets.get(i).getLayoutY() + 15, enemy.getLayoutY() + 15)) {

                enemy.setLayoutX(enemy.getLayoutX() + 5);
                bullets.get(i).setLayoutX(1000);
                bullets.remove(i);
            }
        }

    }

    private double calulateDistance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
