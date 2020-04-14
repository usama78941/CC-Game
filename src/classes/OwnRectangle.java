package classes;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class OwnRectangle extends Rectangle {
    private final int i;
    private final int j;
    private StackPane shapeItHolds;

    public OwnRectangle(double width, double height, int i, int j) {
        super(width, height);
        this.i = i;
        this.j = j;
        shapeItHolds = null;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void setShapeItHolds(StackPane shapeItHolds) {
        this.shapeItHolds = shapeItHolds;
    }

    public StackPane getShapeItHolds() {
        return shapeItHolds;
    }

    @Override
    public String toString() {
        return "OwnRectangle{" +
                "i=" + i +
                ", j=" + j +
                ", shapeItHolds=" + shapeItHolds +
                '}';
    }
}
