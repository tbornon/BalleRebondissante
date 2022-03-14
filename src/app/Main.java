package app;

import javafx.application.*;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Main extends Application {
	int x = 200, y = 200;
	int dx = 5, dy = 5, dr = 5;
	int radius = 50;
	Bounds bounds;
	boolean paused = false;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		BorderPane root = new BorderPane();
		Pane ballPane = new Pane();
		HBox controlPane = new HBox();
		root.setCenter(ballPane);
		root.setBottom(controlPane);
		
		ballPane.setStyle("-fx-border-color: red;");
		Scene test = new Scene(root, 400, 400);

		Circle c = new Circle();
		c.setCenterX(x);
		c.setCenterY(y);
		c.setRadius(radius);
		c.setFill(Color.RED);

		// On instancie un nouvel objet Timeline
		Timeline tl = new Timeline();
		// On indique le nombre de fois que l'action doit être répétée
		tl.setCycleCount(Timeline.INDEFINITE);
		// La période de chaque action
		tl.getKeyFrames().add(new KeyFrame(Duration.millis(10), e -> {
			// Les instructions à exécuter
			bounds = ballPane.getLayoutBounds();
			if (x + dx > bounds.getMaxX() - 2 * radius || x + dx < bounds.getMinX())
				dx = -dx;
			x += dx;

			if (y + dy > bounds.getMaxY() - 2 * radius || y + dy < bounds.getMinY())
				dy = -dy;
			y += dy;

			c.relocate(x, y);
		}));
		// Démarrer la timeline
		tl.play();

		Label radiusLabel = new Label(radius + "px");
		
		Button pauseButton = new Button("Mettre en pause");
		pauseButton.setOnAction(e -> {
			if(!paused) {
				tl.pause();
				pauseButton.setText("Reprendre");
				paused = true;
			}
			else {
				tl.play();
				pauseButton.setText("Mettre en pause");
				paused = false;
			}
		});
		Button biggerRadButton = new Button("Rayon +");
		biggerRadButton.setOnAction(e -> {
			radius += dr;
			c.setRadius(radius);
			radiusLabel.setText(radius + "px");
		});
		Button smallerRadButton = new Button ("Rayon -");
		smallerRadButton.setOnAction(e -> {
			if(radius - dr > 0) {
				radius -= dr;
				c.setRadius(radius);
				radiusLabel.setText(radius + "px");
			}
		});
		controlPane.setSpacing(10);
		controlPane.getChildren().addAll(pauseButton, radiusLabel, biggerRadButton, smallerRadButton);

		ballPane.getChildren().add(c);

		primaryStage.setScene(test);
		primaryStage.setTitle("Balle rebondissante");
		primaryStage.show();
	}
}
