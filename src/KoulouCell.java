/**
 * This class represents a koulou cell, which belongs to a specific type of map cells.
 * Therefore, it inherits from the Cell class.
 */
public class KoulouCell extends Cell{
    public KoulouCell(String leftMarker, String rightMarker) {
        super(leftMarker, rightMarker, Colors.GREEN + "K" + Colors.RESET);
    }
}
