package classes;

import javafx.scene.layout.StackPane;

public class OwnStackPane extends StackPane {
    private int rowIndex;
    private int columnIndex;

    public OwnStackPane(int rowIndex, int columnIndex) {
        super();
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setIJ(int[] array) {
        setRowIndex(array[0]);
        setColumnIndex(array[1]);
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public boolean isISame(OwnStackPane second) {
        return this.getRowIndex() == second.getRowIndex();
    }

    public boolean isJSame(OwnStackPane second) {
        return (this.getColumnIndex() == second.getColumnIndex());
    }

    @Override
    public String toString() {
        return "OwnStackPane{" +
                "i=" + rowIndex +
                ", j=" + columnIndex +
                '}';
    }
}
