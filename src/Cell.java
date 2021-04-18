/**
 * This class represents a map cell of the map.
 */
public class Cell {
    protected String leftMarker;
    protected String rightMarker;
    protected String cellType;
    protected boolean hasMonsters;

    private StringBuilder topAndBottom;
    private StringBuilder middle;

    public Cell() {}

    public Cell(String leftMarker, String rightMarker, String cellType) {
        this.leftMarker = leftMarker;
        this.rightMarker = rightMarker;
        this.cellType = cellType;

        hasMonsters = false;

        this.topAndBottom = new StringBuilder();

        this.setTopAndBottom();
        this.setMiddle();
    }

    public boolean getHasMonsters() {
        return hasMonsters;
    }

    public StringBuilder getTopAndBottom() {
        return topAndBottom;
    }

    public StringBuilder getMiddle() {
        return middle;
    }

    public void setTopAndBottom() {
        topAndBottom.append(cellType).append(" ").append("-").append(" ").append(cellType).append(" ").
                append("-").append(" ").append(cellType);
    }

    public void setMiddle() {
        this.middle = new StringBuilder();
        middle.append("| ").append(leftMarker).append("•").append(rightMarker).append(" |");
    }

    public void setHasMonsters(boolean hasMonsters) {
        this.hasMonsters = hasMonsters;
    }
}
