/**
 * This class represents a map cell of the map.
 */
public class Cell {
    private String leftMarker;
    private String rightMarker;
    private String cellType;

    public Cell() {}

    public Cell(String leftMarker, String rightMarker, String cellType) {
        this.leftMarker = leftMarker;
        this.rightMarker = rightMarker;
        this.cellType = cellType;
    }

    public String getLeftMarker() {
        return leftMarker;
    }

    public String getRightMarker() {
        return rightMarker;
    }

    public String getCellType() {
        return cellType;
    }

    public String formCell() {
        String topAndBottom = cellType + " " + "-" + " " + cellType + " " + "-" + " " + cellType + "   ";
        String middle = "| " + leftMarker + " " + rightMarker + " |" + "   " + "\n";

        return topAndBottom + "\n" + middle + topAndBottom;
    }
}
