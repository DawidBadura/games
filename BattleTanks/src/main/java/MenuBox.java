import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

class MenuBox extends VBox {
    public MenuBox(MenuItem... items) {
        //getChildren().add(createSeparator());

        for (MenuItem item : items) {
            getChildren().add(item);
        }
    }

    private Line createSeparator() {
        Line sep = new Line();
        sep.setEndX(200);
        sep.setStroke(Color.DARKGREY);
        return sep;
    }
}