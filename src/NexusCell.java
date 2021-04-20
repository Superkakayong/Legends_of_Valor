/**
 * This class represents a nexus cell, which belongs to a specific type of cells.
 * Therefore, it inherits from the Cell class.
 */
public class NexusCell extends Cell {
    public NexusCell(String left, String right) {
        super(left, right, Colors.BLUE + "N" + Colors.RESET);
    }
}
