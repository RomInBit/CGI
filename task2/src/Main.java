import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private final List<Point2D> points = new ArrayList<>();
    private final Polyline userLine = new Polyline();

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();

        userLine.setStroke(Color.GREY);
        root.getChildren().add(userLine);

        root.setOnMouseClicked((MouseEvent event) -> {
            double x = event.getX();
            double y = event.getY();

            Point2D newPoint = new Point2D(x,y);
            points.add(newPoint);

            userLine.getPoints().addAll(x,y);

            Circle point = new Circle(x, y, 4, Color.RED);
            root.getChildren().add(point);

            if (points.size() >= 2) {
                double calculatedY = calculateLagrange(x);
                System.out.println("Текущий X: " + x);
                System.out.println("Реальный Y клика: " + y);
                System.out.println("Y по полиному Лагранжа: " + calculatedY);
            }
        });

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private double calculateLagrange(double x){
        double result = 0.0;
        int n = points.size();

        for (int i = 0; i < n; i++){
            double xi = points.get(i).getX();
            double yi = points.get(i).getY();
            double li = 1.0;

            for (int j = 0; j < n; j++){
                if (j != i){
                    double xj = points.get(j).getX();

                    if(xi - xj != 0){
                        li *= (x - xj) / (xi - xj);

                    }
                }
            }
            result += yi * li;
        }
        return result;
    }

    public static void main(String[] args) {
        launch(args);
    }
}