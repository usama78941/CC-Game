package classes;

import javafx.scene.layout.StackPane;

public class OwnStackPane extends StackPane {
    private int i;
    private int j;

    public OwnStackPane(int i, int j) {
        super();
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void setIJ(int[] array) {
        this.i = array[0];
        this.j = array[1];
    }

    public boolean isISame(OwnStackPane second) {
        return (this.getI() == second.getI());
    }

    public boolean isJSame(OwnStackPane second) {
        return (this.getJ() == second.getJ());
    }

    @Override
    public String toString() {
        return "OwnStackPane{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }
}
