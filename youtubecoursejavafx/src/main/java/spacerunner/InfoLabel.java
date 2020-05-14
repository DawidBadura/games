package spacerunner;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InfoLabel extends Label {
    public final String FONT_PATH = "C:\\Users\\Dawid\\IdeaProjects\\Games\\youtubecoursejavafx\\src\\main\\java\\spacerunner\\resources\\kenvector_future.ttf";

    public final String BACKGROUND_IMAGE = "file:youtubecoursejavafx/src/main/java/spacerunner/resources/yellow_white_panel.png";

    public InfoLabel(String text){

        setPrefWidth(380);
        setPrefHeight(49);
        //setPadding(new Insets(40,40,40,40));
        setText(text);
        setWrapText(true);
        setLabelFont();
        setAlignment(Pos.CENTER);

        BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE,380,49,false,true), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);

        setBackground(new Background(backgroundImage));
    }

    private void setLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana",23));
        }

    }

}
