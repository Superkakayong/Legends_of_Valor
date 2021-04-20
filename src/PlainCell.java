/**
 * This class represents a plain cell, which belongs to a specific type of map cells.
 * Therefore, it inherits from the Cell class.
 */
public class PlainCell extends Cell {
    public PlainCell(String leftMarker, String rightMarker) {
        super(leftMarker, rightMarker, Colors.WHITE + "P" + Colors.RESET);
    }
}
