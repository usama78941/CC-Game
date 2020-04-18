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

public class Main extends Application implements EventHandler<MouseEvent> {

    //to change size of grid just change the below array's row and column count
    private static final OwnStackPane[][] array = new OwnStackPane[5][5];
    private static GridPane gridPane;
    private static OwnStackPane paneGotFirstClicked = null;
    private static OwnStackPane paneGotSecondClicked = null;
    private static Label label;
    private static final int horizontalVerticalScore = 3;
    private static final int diagonalScore = 10;

    @Override
    public void start(Stage primaryStage) {
        label = getLabel(primaryStage);
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
                if (isDiagonalSwap()) {
                    if (
                            (paneGotFirstClicked.getRowIndex() < paneGotSecondClicked.getRowIndex()) &&
                                    (paneGotFirstClicked.getColumnIndex() > paneGotSecondClicked.getColumnIndex())
                    ) {
                        doDiagonalSwapRTL(paneGotFirstClicked, paneGotSecondClicked);
                    } else {
                        doDiagonalSwapLTR(paneGotFirstClicked, paneGotSecondClicked);
                    }
                } else {
                    if ((paneGotFirstClicked.getColumnIndex() < paneGotSecondClicked.getColumnIndex()) && (paneGotFirstClicked.isISame(paneGotSecondClicked))) {
                        doHorizontalSwap(paneGotFirstClicked, paneGotSecondClicked);
                    } else if ((paneGotFirstClicked.getColumnIndex() > paneGotSecondClicked.getColumnIndex()) &&
                            (paneGotFirstClicked.isISame(paneGotSecondClicked))) {
                        doHorizontalSwap(paneGotSecondClicked, paneGotFirstClicked);
                    } else if ((paneGotFirstClicked.getRowIndex() < paneGotSecondClicked.getRowIndex()) && (paneGotFirstClicked.isJSame(paneGotSecondClicked))) {
                        doVerticalSwap(paneGotFirstClicked, paneGotSecondClicked);
                    } else if ((paneGotFirstClicked.getRowIndex() > paneGotSecondClicked.getRowIndex()) && (paneGotFirstClicked.isJSame(paneGotSecondClicked))
                    ) {
                        doVerticalSwap(paneGotSecondClicked, paneGotFirstClicked);
                    }
                    if (
                            checkForHorizontalMatch(
                                    paneGotFirstClicked,
                                    paneGotFirstClicked.getRowIndex(),
                                    paneGotFirstClicked.getColumnIndex()
                            )
                    ) {
                        label.setText("Score: " +
                                (Integer.parseInt(label.getText().split(":")[1].trim()) + horizontalVerticalScore));
                    } else if (
                            checkForHorizontalMatch(
                                    paneGotSecondClicked,
                                    paneGotSecondClicked.getRowIndex(),
                                    paneGotSecondClicked.getColumnIndex())
                    ) {
                        label.setText("Score: " +
                                (Integer.parseInt(label.getText().split(":")[1].trim()) + horizontalVerticalScore));
                    } else if (
                            checkForVerticalMatch(
                                    paneGotFirstClicked,
                                    paneGotFirstClicked.getRowIndex(),
                                    paneGotFirstClicked.getColumnIndex()
                            )
                    ) {
                        label.setText("Score: " +
                                (Integer.parseInt(label.getText().split(":")[1].trim()) + horizontalVerticalScore));

                        System.out.println("vertical match for first is found");
                    } else if (
                            checkForVerticalMatch(
                                    paneGotSecondClicked,
                                    paneGotSecondClicked.getRowIndex(),
                                    paneGotSecondClicked.getColumnIndex()
                            )
                    ) {
                        label.setText("Score: " +
                                (Integer.parseInt(label.getText().split(":")[1].trim()) + horizontalVerticalScore));
                    } else if (
                            checkForDiagonalMatch(
                                    paneGotFirstClicked,
                                    paneGotFirstClicked.getRowIndex(),
                                    paneGotFirstClicked.getColumnIndex())
                    ) {
                        label.setText("Score: " +
                                (Integer.parseInt(label.getText().split(":")[1].trim()) + diagonalScore));
                        System.out.println("diagonal match for first is found");
                    } else if (
                            checkForDiagonalMatch(
                                    paneGotSecondClicked,
                                    paneGotSecondClicked.getRowIndex(),
                                    paneGotSecondClicked.getColumnIndex()
                            )
                    ) {
                        label.setText("Score: " +
                                (Integer.parseInt(label.getText().split(":")[1].trim()) + diagonalScore));
                        System.out.println("diagonal match for second is found");

                    }
                }
                percolateDownShapes();
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

    private boolean isDiagonalSwap() {
        return ((Math.abs(paneGotFirstClicked.getRowIndex() - paneGotSecondClicked.getRowIndex()) == 1) &&
                (Math.abs(paneGotFirstClicked.getColumnIndex() - paneGotSecondClicked.getColumnIndex()) == 1)
        );
    }

    private void doDiagonalSwapRTL(OwnStackPane first, OwnStackPane second) {
        first.setTranslateY(70);
        first.setTranslateX(-70);
        second.setTranslateY(-70);
        second.setTranslateX(70);
        updateArrayEntryAndObject(first, second);
    }

    private void doDiagonalSwapLTR(OwnStackPane first, OwnStackPane second) {
        first.setTranslateY(70);
        first.setTranslateX(70);
        second.setTranslateX(-70);
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

    /**
     * @param shapeItHolds refers to the object in class OwnRectangle of type Shape
     * @param rowIndex     index of parameter  refers to the row index in Grid and matrix
     * @param columnIndex  refers to the column index in Grid and matrix
     * @return true if it founds a match
     */
    private boolean checkForVerticalMatch(OwnStackPane shapeItHolds, int rowIndex, int columnIndex) {
        if (rowIndex <= 2) {
            if (
                    shapeItHolds.getId().equals(array[rowIndex + 1][columnIndex].getId()) &&
                            shapeItHolds.getId().equals(array[rowIndex + 2][columnIndex].getId())
            ) {
                array[rowIndex][columnIndex].getChildren().clear();
                array[rowIndex + 1][columnIndex].getChildren().clear();
                array[rowIndex + 2][columnIndex].getChildren().clear();
                return true;
            }
        }
        if (rowIndex >= 2) {
            if (
                    shapeItHolds.getId().equals(array[rowIndex - 1][columnIndex].getId()) &&
                            shapeItHolds.getId().equals(array[rowIndex - 2][columnIndex].getId())
            ) {
                array[rowIndex][columnIndex].getChildren().clear();
                array[rowIndex - 1][columnIndex].getChildren().clear();
                array[rowIndex - 2][columnIndex].getChildren().clear();
                return true;
            }
        }

        if (rowIndex > 1) {
            if (
                    shapeItHolds.getId().equals(array[rowIndex - 1][columnIndex].getId()) &&
                            shapeItHolds.getId().equals(array[rowIndex + 1][columnIndex].getId())
            ) {
                array[rowIndex][columnIndex].getChildren().clear();
                array[rowIndex - 1][columnIndex].getChildren().clear();
                array[rowIndex + 1][columnIndex].getChildren().clear();
                return true;
            }
        }

        return false;
    }

    // same as for checkForVerticalMatch
    private boolean checkForHorizontalMatch(OwnStackPane shapeItHolds, int rowIndex, int columnIndex) {
        if (columnIndex <= 2) {
            if (
                    shapeItHolds.getId().equals(array[rowIndex][columnIndex + 1].getId()) &&
                            shapeItHolds.getId().equals(array[rowIndex][columnIndex + 2].getId())
            ) {
                array[rowIndex][columnIndex].getChildren().clear();
                array[rowIndex][columnIndex + 1].getChildren().clear();
                array[rowIndex][columnIndex + 2].getChildren().clear();
                return true;
            }
        }
        if (columnIndex >= 2) {
            if (
                    shapeItHolds.getId().equals(array[rowIndex][columnIndex - 1].getId()) &&
                            shapeItHolds.getId().equals(array[rowIndex][columnIndex - 2].getId())
            ) {
                array[rowIndex][columnIndex].getChildren().clear();
                array[rowIndex][columnIndex - 1].getChildren().clear();
                array[rowIndex][columnIndex - 2].getChildren().clear();
                return true;
            }
        }

        if (columnIndex > 1) {
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

    // same as for checkForVerticalMatch
    private boolean checkForDiagonalMatch(OwnStackPane paneGotFirstClicked, int rowIndex, int columnIndex) {
        if (paneGotFirstClicked.getRowIndex() < 3 && paneGotFirstClicked.getColumnIndex() < 3) {
            if (
                    paneGotFirstClicked.getId().equals(array[rowIndex + 1][columnIndex + 1].getId()) &&
                            paneGotFirstClicked.getId().equals(array[rowIndex + 2][columnIndex + 2].getId())
            ) {
                paneGotFirstClicked.getChildren().clear();
                array[rowIndex + 1][columnIndex + 1].getChildren().clear();
                array[rowIndex + 2][columnIndex + 2].getChildren().clear();
                return true;
            }
        } else if (paneGotFirstClicked.getRowIndex() < 0 && paneGotFirstClicked.getColumnIndex() < 0) {
            if (paneGotFirstClicked.getId().equals(array[rowIndex + 1][columnIndex + 1].getId()) && paneGotFirstClicked.getId().equals(array[rowIndex - 1][columnIndex - 1].getId())) {
                paneGotFirstClicked.getChildren().clear();
                array[rowIndex + 1][columnIndex + 1].getChildren().clear();
                array[rowIndex - 1][columnIndex - 1].getChildren().clear();
                return true;
            }
        } else if ((paneGotFirstClicked.getRowIndex() > 2) && paneGotFirstClicked.getColumnIndex() > 2) {
            if (paneGotFirstClicked.getId().equals(array[rowIndex - 1][columnIndex - 1].getId()) && paneGotFirstClicked.getId().equals(array[rowIndex - 2][columnIndex - 2].getId())) {
                paneGotFirstClicked.getChildren().clear();
                array[rowIndex - 1][columnIndex - 1].getChildren().clear();
                array[rowIndex - 2][columnIndex - 2].getChildren().clear();
                return true;
            }
        }
        return false;
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

    private void fillSpaces(int columnIndex) {
        for (int rowIndex = (array.length - 1); rowIndex >= 0; rowIndex--) {
            if (array[rowIndex][columnIndex].getChildren().isEmpty()) {
                OwnStackPane stackPane = getStackPane(rowIndex, columnIndex);
                gridPane.add(stackPane, columnIndex, rowIndex);
                array[rowIndex][columnIndex] = stackPane;
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

    private void percolateDownShapes() {
        for (int columnIndex = 0; columnIndex < array[0].length; columnIndex++) {
            int multiplier = 0;
            OwnStackPane lastOne = array[0][columnIndex];

            //noinspection ForLoopReplaceableByForEach
            for (int rowIndex = 0; rowIndex < array.length; rowIndex++) {
                OwnStackPane now = array[rowIndex][columnIndex];
                if (now.getChildren().isEmpty()) {
                    multiplier += 1;
                } else if ((multiplier >= 1) && !(now.getChildren().isEmpty())) {
                    break;
                } else {
                    lastOne = now;
                }
            }

            while (true) {
                if (multiplier > 0) {
                    lastOne.setTranslateY(multiplier * 70);
                    int i = lastOne.getRowIndex(), j = lastOne.getColumnIndex();
                    lastOne.setIJ(new int[]{(lastOne.getRowIndex() + (multiplier)), lastOne.getColumnIndex()});
                    array[lastOne.getRowIndex()][lastOne.getColumnIndex()] = lastOne;
                    if (i > 0) {
                        lastOne = array[i - 1][j];
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            fillSpaces(columnIndex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}