/**
 * This class represents a map cell of the map.
 */
public class Cell {
    private String leftMarker;
    private String rightMarker;
    private String cellType;

    private StringBuilder topAndBottom;
    private StringBuilder middle;

    public Cell() {}

    public Cell(String leftMarker, String rightMarker, String cellType) {
        this.leftMarker = leftMarker;
        this.rightMarker = rightMarker;
        this.cellType = cellType;

        this.topAndBottom = new StringBuilder();
        this.middle = new StringBuilder();

        this.setTopAndBottom();
        this.setMiddle();
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

    public StringBuilder getTopAndBottom() {
        return topAndBottom;
    }

    public StringBuilder getMiddle() {
        return middle;
    }

    private void setTopAndBottom() {
        topAndBottom.append(cellType).append(" ").append("-").append(" ").append(cellType).append(" ").
                append("-").append(" ").append(cellType);
    }

    private void setMiddle() {
        middle.append("| ").append(leftMarker).append("•").append(rightMarker).append(" |");
    }
}
