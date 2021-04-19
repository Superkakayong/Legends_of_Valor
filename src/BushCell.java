/**
 * This class represents a bush cell, which belongs to a specific type of map cells.
 * Therefore, it inherits from the Cell class.
 */
public class BushCell extends Cell{
    public BushCell(String leftMarker, String rightMarker) {
        super(leftMarker, rightMarker, Colors.YELLOW + "B" + Colors.RESET);
    }
}
