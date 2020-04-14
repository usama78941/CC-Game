package main_class;

import classes.OwnStackPane;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import jdk.jfr.Description;

public class Main extends Application implements EventHandler<MouseEvent> {

    //to change size of grid just change the below array's row and column count
    private static final OwnStackPane[][] array = new OwnStackPane[5][5];
    private static GridPane gridPane;
    private static OwnStackPane paneGotFirstClicked = null;
    private static OwnStackPane paneGotSecondClicked = null;

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
                Rectangle rectangle = new Rectangle(60, 60);
                rectangle.setFill(Color.WHITE);
                rectangle.setArcWidth(20);
                rectangle.setArcHeight(20);
                gridPane.add(rectangle, i, j);
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
        if (object instanceof OwnStackPane) {
            OwnStackPane stackPane = (OwnStackPane) (object);
            if (paneGotFirstClicked != null) {
                paneGotSecondClicked = stackPane;
            } else {
                paneGotFirstClicked = stackPane;
            }

            if (paneGotSecondClicked != null) {
                if ((paneGotFirstClicked.getJ() < paneGotSecondClicked.getJ()) &&
                        (paneGotFirstClicked.isISame(paneGotSecondClicked))) { // to move from left to right
                    doHorizontalSwap(paneGotFirstClicked, paneGotSecondClicked);
                    // TODO need to update array in order to check for a triplet to match and also for again swapping further
                } else if ((paneGotFirstClicked.getJ() > paneGotSecondClicked.getJ()) &&
                        (paneGotFirstClicked.isISame(paneGotSecondClicked))) { //to move from right to left
                    doHorizontalSwap(paneGotSecondClicked, paneGotFirstClicked);
                    // TODO need to update array in order to check for a triplet to match and also for again swapping further
                } else if ((paneGotFirstClicked.getI() < paneGotSecondClicked.getI()) &&
                        (paneGotFirstClicked.isJSame(paneGotSecondClicked))) { //to move from top to bottom
                    doVerticalSwap(paneGotFirstClicked, paneGotSecondClicked);
                    // TODO need to update array in order to check for a triplet to match and also for again swapping further
                } else if ((paneGotFirstClicked.getI() > paneGotSecondClicked.getI()) &&
                        (paneGotFirstClicked.isJSame(paneGotSecondClicked))) { //to move from bottom to top
                    doVerticalSwap(paneGotSecondClicked, paneGotFirstClicked);
                    // TODO need to update array in order to check for a triplet to match and also for again swapping further
                }

                System.out.println("Is swap diagonal? " + isDiagonalSwap());

                paneGotFirstClicked = null;
                paneGotSecondClicked = null;
            }
        }
    }

    private void doHorizontalSwap(OwnStackPane first, OwnStackPane second) {
        first.setTranslateX(70);
        second.setTranslateX(-70);
    }

    private void doVerticalSwap(OwnStackPane first, OwnStackPane second) {
        first.setTranslateY(70);
        second.setTranslateY(-70);
    }

    private void doDiagonalSwap(OwnStackPane first, OwnStackPane second) {

    }

    /**
     * @param shapeItHolds refers to the object in class OwnRectangle of type Shape
     * @param i            index of parameter  refers to the row index in Grid and matrix
     * @param j            refers to the column index in Grid and matrix
     * @return true if it founds a match
     */
    @Description("Main Method Zero")
    private boolean checkForVerticalMatch(OwnStackPane shapeItHolds, int i, int j) {
        if (i == 0) {
            if (
                    shapeItHolds.equals(array[i + 1][j]) &&
                            shapeItHolds.equals(array[i + 2][j])
            ) {
                array[i][j].getChildren().clear();
                array[i + 1][j].getChildren().clear();
                array[i + 2][j].getChildren().clear();
                return true;
            }
        } else if (i == 4) {
            if (
                    shapeItHolds.equals(array[i - 1][j]) &&
                            shapeItHolds.equals(array[i - 2][j])
            ) {
                array[i][j].getChildren().clear();
                array[i - 1][j].getChildren().clear();
                array[i - 2][j].getChildren().clear();
                return true;
            }
        } else {
            if (
                    shapeItHolds.equals(array[i - 1][j]) &&
                            shapeItHolds.equals(array[i + 1][j])
            ) {
                array[i][j].getChildren().clear();
                array[i - 1][j].getChildren().clear();
                array[i + 1][j].getChildren().clear();
            }
            return true;
        }
        return false;
    }

    @Description("Main method One")
    private boolean checkForHorizontalMatch(OwnStackPane shapeItHolds, int i, int j) {
        if (j == 0) {
            if (
                    shapeItHolds.equals(array[i][j + 1]) &&
                            shapeItHolds.equals(array[i][j + 2])
            ) {
                array[i][j].getChildren().clear();
                array[i][j + 1].getChildren().clear();
                array[i][j + 2].getChildren().clear();
                return true;
            }
        } else if (j == 4) {
            if (
                    shapeItHolds.equals(array[i][j - 1]) &&
                            shapeItHolds.equals(array[i][j - 2])
            ) {
                array[i][j].getChildren().clear();
                array[i][j - 1].getChildren().clear();
                array[i][j - 2].getChildren().clear();
                return true;
            }
        } else {
            if (
                    shapeItHolds.equals(array[i][j - 1]) &&
                            shapeItHolds.equals(array[i][j + 1])
            ) {
                array[i][j].getChildren().clear();
                array[i][j - 1].getChildren().clear();
                array[i][j + 1].getChildren().clear();
                return true;
            }
        }
        return false;
    }

//    @Description("Main method Two")
//    private boolean checkForDiagonalMatch(OwnStackPane shapeItHolds, int i, int j) {
//
//    }

    private boolean isDiagonalSwap() {
        return ((Math.abs(paneGotFirstClicked.getI() - paneGotSecondClicked.getI()) == 1) &&
                (Math.abs(paneGotFirstClicked.getJ() - paneGotSecondClicked.getJ()) == 1)
        );
    }

    /**
     * for filling up board at start &
     * after every triplets match
     */
    private void fillUpBoard() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                OwnStackPane stackPane = getStackPane(i, j);
                if (array[j][i] == null) {
                    gridPane.add(stackPane, j, i);
                    array[j][i] = (stackPane);
                }
            }
        }
    }

    private Shape getRandomShape() {
        int randomInteger = (int) (0 + Math.random() * 5);
        return switch (randomInteger) {
            case 0 -> {
                Rectangle rectangle = new Rectangle(40, 20);
                rectangle.setFill(Color.GREY);
                yield rectangle;
            }
            case 1 -> {
                Polygon polygon = new Polygon();
                polygon.getPoints().addAll(37.5, 6.25,
                        56.25, 18.75,
                        37.5, 31.25,
                        18.75, 18.75);
                polygon.setFill(Color.BLUE);
                yield polygon;
            }
            case 2 -> {
                Ellipse ellipse = new Ellipse(15, 20);
                ellipse.setFill(Color.DARKGRAY);
                yield ellipse;
            }
            case 3 -> {
                Circle circle = new Circle(10);
                circle.setFill(Color.DARKOLIVEGREEN);
                yield circle;
            }
            default -> {
                Rectangle rectangle = new Rectangle(30, 30);
                rectangle.setFill(Color.DARKSLATEBLUE);
                yield rectangle;
            }
        };
    }

    private OwnStackPane getStackPane(int i, int j) {
        OwnStackPane stackPane = new OwnStackPane(i, j);
        stackPane.setAlignment(Pos.CENTER);
        Shape randomShape = getRandomShape();
        randomShape.setOpacity(0.7);
        stackPane.getChildren().add(randomShape);
        stackPane.setOnMouseClicked(this);
        stackPane.setCursor(Cursor.HAND);
        return stackPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}