import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClickerGame extends Application {

    private int score = 0;
    private final List<Circle> circles = new ArrayList<>();
    private final Random random = new Random();
    private long lastSpawn = 0;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();

        Label scoreLabel = new Label("Счёт: 0");
        scoreLabel.setFont(Font.font("Arial", 20));
        scoreLabel.setLayoutX(20);
        scoreLabel.setLayoutY(10);
        root.getChildren().add(scoreLabel);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Кликер");
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastSpawn > 1_000_000_000L) {
                    spawnCircle(root);
                    lastSpawn = now;
                }

                for (int i = circles.size() - 1; i >= 0; i--) {
                    Circle c = circles.get(i);
                    c.setCenterY(c.getCenterY() + 2);

                    if (c.getCenterY() > HEIGHT + 30) {
                        root.getChildren().remove(c);
                        circles.remove(i);
                    }
                }
            }
        };
        timer.start();
    }

    private void spawnCircle(Pane root) {
        double radius = 20 + random.nextInt(20);
        double x = radius + random.nextDouble() * (WIDTH - 2 * radius);


        Circle circle = new Circle(x, -radius, radius);
        circle.setFill(Color.rgb(
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
        ));

        circle.setOnMouseClicked(e -> {
            score++;
            for (var node : root.getChildren()) {
                if (node instanceof Label) {
                    ((Label) node).setText("Счёт: " + score);
                    break;
                }
            }
            root.getChildren().remove(circle);
            circles.remove(circle);
        });

        circles.add(circle);
        root.getChildren().add(circle);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
