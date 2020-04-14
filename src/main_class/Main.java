package main_class;

import classes.OwnRectangle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<MouseEvent> {

    //to change size of grid just change the below array row and column count
    private static final OwnRectangle[][] array = new OwnRectangle[5][5];
    private static GridPane gridPane;
    private static OwnRectangle rectangleGotFirstClicked = null, rectangleGotSecondClicked = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label label = getLabel(primaryStage);
        gridPane = getGridPane();

        Button startButton = new Button("Start");
        startButton.setMinHeight(35);
        startButton.setMinWidth(60);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: blue");

        BorderPane.setAlignment(label, Pos.CENTER);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        borderPane.setTop(startButton);

        startButton.setOnMouseClicked(e -> {
            startButton.setVisible(false);
            borderPane.setTop(label);
            Platform.runLater(this::fillUpBoard);
        });

        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setTitle("CC GAME");
        primaryStage.show();
    }

    private GridPane getGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setStyle("-fx-background-color: blue");

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                OwnRectangle rectangle = new OwnRectangle(60, 60, i, j);
                rectangle.setFill(Color.WHITE);
                rectangle.setArcWidth(20);
                rectangle.setArcHeight(20);
                rectangle.setOnMouseClicked(this);
                rectangle.setCursor(Cursor.HAND);

                gridPane.add(rectangle, i, j);
                array[i][j] = rectangle;
            }
        }
        return gridPane;
    }

    private Label getLabel(Stage primaryStage) {
        Label label = new Label("Score: 0");
        label.setStyle("-fx-background-color: blue");
        label.setScaleX(2);
        label.setScaleY(2);
        label.setMinSize(primaryStage.getMinWidth(), 35);
        label.setAlignment(Pos.CENTER);
        label.setTextFill(Color.WHITE);
        return label;
    }

    @Override
    public void handle(MouseEvent event) {
        Object object = event.getSource();
        if (object instanceof OwnRectangle) {
            OwnRectangle rectangle = (OwnRectangle) object;
            if (rectangleGotFirstClicked != null) {
                rectangleGotSecondClicked = rectangle;
            } else {
                rectangleGotFirstClicked = rectangle;
            }
            if ((rectangleGotSecondClicked != null)) {
                checkForMatch();
            }
        }
    }

    public void checkForMatch() {

    }

    /**
     * @param shapeItHolds refers to the object in class OwnRectangle of type Shape
     * @param i            index of parameter  refers to the row index in Grid and matrix
     * @param j            refers to the column index in Grid and matrix
     * @return true if it founds a match
     */
    private boolean checkForVerticalMatch(StackPane shapeItHolds, int i, int j) {
        if (i == 0) {
            if (
                    shapeItHolds.equals(array[i + 1][j].getShapeItHolds()) &&
                            shapeItHolds.equals(array[i + 2][j].getShapeItHolds())
            ) {
                array[i][j].getShapeItHolds().getChildren().clear();
                array[i + 1][j].getShapeItHolds().getChildren().clear();
                array[i + 2][j].getShapeItHolds().getChildren().clear();
                return true;
            }
        } else if (i == 4) {
            if (
                    shapeItHolds.equals(array[i - 1][j].getShapeItHolds()) &&
                            shapeItHolds.equals(array[i - 2][j].getShapeItHolds())
            ) {
                array[i][j].getShapeItHolds().getChildren().clear();
                array[i - 1][j].getShapeItHolds().getChildren().clear();
                array[i - 2][j].getShapeItHolds().getChildren().clear();
                return true;
            }
        } else {
            if (
                    shapeItHolds.equals(array[i - 1][j].getShapeItHolds()) &&
                            shapeItHolds.equals(array[i + 1][j].getShapeItHolds())
            ) {
                array[i][j].getShapeItHolds().getChildren().clear();
                array[i - 1][j].getShapeItHolds().getChildren().clear();
                array[i + 1][j].getShapeItHolds().getChildren().clear();
            }
            return true;
        }
        return false;
    }
    private boolean checkForHorizontalMatch(StackPane shapeItHolds, int i, int j) {
        if (j == 0) {
            if (
                    shapeItHolds.equals(array[i][j + 1].getShapeItHolds()) &&
                            shapeItHolds.equals(array[i][j + 2].getShapeItHolds())
            ) {
                array[i][j].getShapeItHolds().getChildren().clear();
                array[i][j + 1].getShapeItHolds().getChildren().clear();
                array[i][j + 2].getShapeItHolds().getChildren().clear();
                return true;
            }
        } else if (j == 4) {
            if (
                    shapeItHolds.equals(array[i][j - 1].getShapeItHolds()) &&
                            shapeItHolds.equals(array[i][j - 2].getShapeItHolds())
            ) {
                array[i][j].getShapeItHolds().getChildren().clear();
                array[i][j - 1].getShapeItHolds().getChildren().clear();
                array[i][j - 2].getShapeItHolds().getChildren().clear();
                return true;
            }
        } else {
            if (
                    shapeItHolds.equals(array[i][j - 1].getShapeItHolds()) &&
                            shapeItHolds.equals(array[i][j + 1].getShapeItHolds())
            ) {
                array[i][j].getShapeItHolds().getChildren().clear();
                array[i][j - 1].getShapeItHolds().getChildren().clear();
                array[i][j + 1].getShapeItHolds().getChildren().clear();
                return true;
            }
        }
        return false;
    }
    private boolean checkForDiagonalMatch(StackPane shapeItHolds, int i, int j) {
        
    }

    /**
     * for filling up board at start &
     * after every triplets match
     */
    private void fillUpBoard() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                StackPane stackPane = getStackPane();
                if (array[j][i].getShapeItHolds() == null) {
                    gridPane.add(stackPane, j, i);
                    array[j][i].setShapeItHolds(stackPane);
                }
            }
        }
    }

    private Shape getRandomShape() {
        int randomInteger = (int) (0 + Math.random() * 5);
        return switch (randomInteger) {
            case 0 -> new Rectangle(40, 20);
            case 1 -> {
                Polygon polygon = new Polygon();
                polygon.getPoints().addAll(37.5, 6.25,
                        56.25, 18.75,
                        37.5, 31.25,
                        18.75, 18.75);
                yield polygon;
            }
            case 2 -> new Ellipse(15, 20);
            case 3 -> new Circle(10);
            default -> new Rectangle(30, 30);
        };
    }

    private StackPane getStackPane() {
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        Shape randomShape = getRandomShape();
        randomShape.setOpacity(0.5);
        randomShape.setFill(Color.BLUE);
        stackPane.getChildren().add(randomShape);
        return stackPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}