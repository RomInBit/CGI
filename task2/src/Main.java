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
import java.util.Comparator;
import java.util.List;

public class Main extends Application {

    private final List<Point2D> points = new ArrayList<>();
    private final Polyline lagrangeCurve = new Polyline();

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();

        lagrangeCurve.setStroke(Color.BLACK);
        lagrangeCurve.setStrokeWidth(2);
        root.getChildren().addAll( lagrangeCurve);

        root.setOnMouseClicked((MouseEvent event) -> {
            double x = event.getX();
            double y = event.getY();

            Point2D newPoint = new Point2D(x,y);
            points.add(newPoint);
            points.sort(Comparator.comparingDouble(Point2D::getX));

            Circle point = new Circle(x, y, 4, Color.RED);
            root.getChildren().add(point);

            if (points.size() >= 2) {
                updateLagrangeCurve();
            }
        });

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Полином Лагранжа");
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

    private  void updateLagrangeCurve(){
        lagrangeCurve.getPoints().clear();

        double startX = points.get(0).getX();
        double endX = points.get(points.size() - 1).getX();

        if(startX == endX) return;

        for (double x = startX; x <= endX; x += 1.0){
            double y = calculateLagrange(x);
            lagrangeCurve.getPoints().addAll(x, y);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}