/**
 * This class represents a map cell of the map.
 */
public class Cell {
    protected String leftMarker;
    protected String rightMarker;
    protected String cellType;
    protected boolean hasMonsters;
    protected boolean hasHeroes;

    private StringBuilder topAndBottom;
    private StringBuilder middle;

    public Cell() {}

    public Cell(String leftMarker, String rightMarker, String cellType) {
        this.leftMarker = leftMarker;
        this.rightMarker = rightMarker;
        this.cellType = cellType;

        hasMonsters = false;
        hasHeroes = false;

        this.topAndBottom = new StringBuilder();

        this.setTopAndBottom();
        this.setMiddle();
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
        middle.append(Colors.WHITE + "| " + Colors.RESET).append(Colors.PURPLE + leftMarker + Colors.RESET).append("•").
                append(Colors.PURPLE + rightMarker + Colors.RESET).append(Colors.WHITE + " |" + Colors.RESET);
    }

    public void setHasMonsters(boolean hasMonsters) {
        this.hasMonsters = hasMonsters;
    }

    public void setHasHeroes(boolean hasHeroes) {
        this.hasHeroes = hasHeroes;
    }
}
