/**
 * This class represents a cave cell, which belongs to a specific type of map cells.
 * Therefore, it inherits from the Cell class.
 */
public class CaveCell extends Cell{
    public CaveCell(String leftMarker, String rightMarker) {
        super(leftMarker, rightMarker, Colors.CYAN + "C" + Colors.RESET);
    }
}
