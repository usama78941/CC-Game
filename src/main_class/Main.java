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
    public void start(Stage primaryStage) {
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

        for (int rowIndex = 0; rowIndex < array.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < array[0].length; columnIndex++) {
                Rectangle rectangle = new Rectangle(60, 60);
                rectangle.setFill(Color.WHITE);
                rectangle.setArcWidth(20);
                rectangle.setArcHeight(20);
                gridPane.add(rectangle, rowIndex, columnIndex);
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
                System.out.println("diagonal swap possible? " + isDiagonalSwap());
                if (
                        (paneGotFirstClicked.getColumnIndex() < paneGotSecondClicked.getColumnIndex()) &&
                                (paneGotFirstClicked.isISame(paneGotSecondClicked))
                ) {
                    System.out.println("first: " + paneGotFirstClicked);
                    System.out.println("second: " + paneGotSecondClicked);
                    doHorizontalSwap(paneGotFirstClicked, paneGotSecondClicked);
                } else if (
                        (paneGotFirstClicked.getColumnIndex() > paneGotSecondClicked.getColumnIndex()) &&
                                (paneGotFirstClicked.isISame(paneGotSecondClicked))
                ) {
                    System.out.println("first: " + paneGotFirstClicked);
                    System.out.println("second: " + paneGotSecondClicked);
                    doHorizontalSwap(paneGotSecondClicked, paneGotFirstClicked);
                } else if (
                        (paneGotFirstClicked.getRowIndex() < paneGotSecondClicked.getRowIndex()) &&
                                (paneGotFirstClicked.isJSame(paneGotSecondClicked))
                ) {
                    System.out.println("first: " + paneGotFirstClicked);
                    System.out.println("second: " + paneGotSecondClicked);
                    doVerticalSwap(paneGotFirstClicked, paneGotSecondClicked);
                } else if (
                        (paneGotFirstClicked.getRowIndex() > paneGotSecondClicked.getRowIndex()) &&
                                (paneGotFirstClicked.isJSame(paneGotSecondClicked))
                ) {
                    System.out.println("first: " + paneGotFirstClicked);
                    System.out.println("second: " + paneGotSecondClicked);
                    doVerticalSwap(paneGotSecondClicked, paneGotFirstClicked);
                }

                if (
                        checkForHorizontalMatch(
                                paneGotFirstClicked,
                                paneGotFirstClicked.getRowIndex(),
                                paneGotFirstClicked.getColumnIndex()
                        )
                ) {
                    System.out.println("horizontal match for first is found");
                }
                if (
                        checkForHorizontalMatch(
                                paneGotSecondClicked,
                                paneGotSecondClicked.getRowIndex(),
                                paneGotSecondClicked.getColumnIndex())
                ) {
                    System.out.println("horizontal match for second is found");

                }
                if (
                        checkForVerticalMatch(
                                paneGotFirstClicked,
                                paneGotFirstClicked.getRowIndex(),
                                paneGotFirstClicked.getColumnIndex()
                        )
                ) {
                    System.out.println("vertical match for first is found");

                }
                if (
                        checkForVerticalMatch(
                                paneGotSecondClicked,
                                paneGotSecondClicked.getRowIndex(),
                                paneGotSecondClicked.getColumnIndex()
                        )
                ) {
                    System.out.println("vertical match for second is found");

                }

                percolateDownShapes();
                fillSpaces();

                paneGotFirstClicked = null;
                paneGotSecondClicked = null;
            }
        }
    }

    private void doHorizontalSwap(OwnStackPane first, OwnStackPane second) {

        first.setTranslateX(70);
        second.setTranslateX(-70);
        updateArrayEntryAndObject(first, second);

    }

    private void doVerticalSwap(OwnStackPane first, OwnStackPane second) {
        first.setTranslateY(70);
        second.setTranslateY(-70);
        updateArrayEntryAndObject(first, second);
    }

    private void updateArrayEntryAndObject(OwnStackPane first, OwnStackPane second) {

        int fromI = first.getRowIndex();
        int fromJ = first.getColumnIndex();

        int toI = second.getRowIndex();
        int toJ = second.getColumnIndex();

        array[fromI][fromJ] = second;
        array[toI][toJ] = first;

        array[fromI][fromJ].setIJ(new int[]{fromI, fromJ});
        array[toI][toJ].setIJ(new int[]{toI, toJ});
    }

//    private void doDiagonalSwap(OwnStackPane first, OwnStackPane second) {
//
//    }

    /**
     * @param shapeItHolds refers to the object in class OwnRectangle of type Shape
     * @param rowIndex     index of parameter  refers to the row index in Grid and matrix
     * @param columnIndex  refers to the column index in Grid and matrix
     * @return true if it founds a match
     */
    @Description("Main Method Zero")
    private boolean checkForVerticalMatch(OwnStackPane shapeItHolds, int rowIndex, int columnIndex) {
        if (rowIndex == 0) {
            if (
                    shapeItHolds.getId().equals(array[rowIndex + 1][columnIndex].getId()) &&
                            shapeItHolds.getId().equals(array[rowIndex + 2][columnIndex].getId())
            ) {
                array[rowIndex][columnIndex].getChildren().clear();
                array[rowIndex + 1][columnIndex].getChildren().clear();
                array[rowIndex + 2][columnIndex].getChildren().clear();
                return true;
            }
        } else if (rowIndex == 4) {
            if (
                    shapeItHolds.getId().equals(array[rowIndex - 1][columnIndex].getId()) &&
                            shapeItHolds.getId().equals(array[rowIndex - 2][columnIndex].getId())
            ) {
                array[rowIndex][columnIndex].getChildren().clear();
                array[rowIndex - 1][columnIndex].getChildren().clear();
                array[rowIndex - 2][columnIndex].getChildren().clear();
                return true;
            }
        } else {
            if (
                    shapeItHolds.getId().equals(array[rowIndex - 1][columnIndex].getId()) &&
                            shapeItHolds.getId().equals(array[rowIndex + 1][columnIndex].getId())
            ) {
                array[rowIndex][columnIndex].getChildren().clear();
                array[rowIndex - 1][columnIndex].getChildren().clear();
                array[rowIndex + 1][columnIndex].getChildren().clear();
            }
            return true;
        }
        return false;
    }

    @Description("Main method One")
    private boolean checkForHorizontalMatch(OwnStackPane shapeItHolds, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            if (
                    shapeItHolds.getId().equals(array[rowIndex][columnIndex + 1].getId()) &&
                            shapeItHolds.getId().equals(array[rowIndex][columnIndex + 2].getId())
            ) {
                array[rowIndex][columnIndex].getChildren().clear();
                array[rowIndex][columnIndex + 1].getChildren().clear();
                array[rowIndex][columnIndex + 2].getChildren().clear();
                return true;
            }
        } else if (columnIndex == 4) {
            if (
                    shapeItHolds.getId().equals(array[rowIndex][columnIndex - 1].getId()) &&
                            shapeItHolds.getId().equals(array[rowIndex][columnIndex - 2].getId())
            ) {
                array[rowIndex][columnIndex].getChildren().clear();
                array[rowIndex][columnIndex - 1].getChildren().clear();
                array[rowIndex][columnIndex - 2].getChildren().clear();
                return true;
            }
        } else {
            if (
                    shapeItHolds.getId().equals(array[rowIndex][columnIndex - 1].getId()) &&
                            shapeItHolds.getId().equals(array[rowIndex][columnIndex + 1].getId())
            ) {
                array[rowIndex][columnIndex].getChildren().clear();
                array[rowIndex][columnIndex - 1].getChildren().clear();
                array[rowIndex][columnIndex + 1].getChildren().clear();
                return true;
            }
        }
        return false;
    }

//    @Description("Main method Two")
//    private boolean checkForDiagonalMatch(OwnStackPane shapeItHolds, int rowIndex, int columnIndex) {
//
//    }

    private boolean isDiagonalSwap() {
        return ((Math.abs(paneGotFirstClicked.getRowIndex() - paneGotSecondClicked.getRowIndex()) == 1) &&
                (Math.abs(paneGotFirstClicked.getColumnIndex() - paneGotSecondClicked.getColumnIndex()) == 1)
        );
    }

    /**
     * for filling up board at start
     * --> filling up => column wise
     */
    private void fillUpBoard() {
        for (int rowIndex = 0; rowIndex < array.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < array[0].length; columnIndex++) {
                OwnStackPane stackPane = getStackPane(rowIndex, columnIndex);
                if ((array[rowIndex][columnIndex] == null)) {
                    gridPane.add(stackPane, columnIndex, rowIndex);
                    array[rowIndex][columnIndex] = stackPane;
                }
            }
        }
    }

    private void fillSpaces() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j].getChildren().isEmpty()) {
                    OwnStackPane stackPane = getStackPane(i, j);
                    gridPane.add(stackPane, j, i);
                    array[i][j] = stackPane;
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

    private OwnStackPane getStackPane(int rowIndex, int columnIndex) {
        OwnStackPane stackPane = new OwnStackPane(rowIndex, columnIndex);
        stackPane.setAlignment(Pos.CENTER);
        Shape randomShape = getRandomShape();

        int id;
        if ((randomShape instanceof Rectangle) &&
                (((Rectangle) randomShape).getWidth() == ((Rectangle) randomShape).getHeight())
        ) { // for square as in last case in getRandomShape() method
            id = 4;
        } else if (randomShape instanceof Polygon) {
            id = 1;
        } else if (randomShape instanceof Ellipse) {
            id = 2;
        } else if (randomShape instanceof Circle) {
            id = 3;
        } else { //for rectangle as in first case getRandomShape() method
            id = 0;
        }

        stackPane.setId(id + "");
        randomShape.setOpacity(0.7);
        stackPane.getChildren().add(randomShape);
        stackPane.setOnMouseClicked(this);
        stackPane.setCursor(Cursor.HAND);
        return stackPane;
    }

//    private void printArray() {
//        System.out.println();
//        for (int i = 0; i < array.length; i++) {
//            for (int j = 0; j < array[0].length; j++) {
//                System.out.println(array[i][j]);
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }

    private void percolateDownShapes() {
        for (int columnIndex = (array[0].length - 1); columnIndex >= 0; columnIndex--) {
            OwnStackPane lastPaneGot = array[0][columnIndex];
            int multiplier = 0;
            for (int rowIndex = 0; rowIndex < 5; rowIndex++) {
                OwnStackPane fromArray = array[rowIndex][columnIndex];
                if (fromArray.getChildren().isEmpty()) {
                    multiplier += 1;
                } else {
                    lastPaneGot = fromArray;
                }
            }

            while (true) {
                lastPaneGot.setTranslateY(multiplier * 70);
                lastPaneGot.setIJ(new int[]{multiplier + lastPaneGot.getRowIndex(), lastPaneGot.getColumnIndex()});
                lastPaneGot.getChildren().clear();
                System.out.println("columnIndex: " + columnIndex);
                System.out.println("rowIndex: " + lastPaneGot.getRowIndex());
                lastPaneGot = array[columnIndex][lastPaneGot.getRowIndex() - 1];
                if (!(lastPaneGot.getChildren().isEmpty())) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}