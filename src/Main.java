import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();

        root.setOnMouseClicked((MouseEvent event) -> {
            double x = event.getX();
            double y = event.getY();

            Circle point = new Circle(x, y, 4, Color.RED);
            root.getChildren().add(point);
        });

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}git add src/Main.java