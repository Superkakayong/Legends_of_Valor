/**
 * This class represents a inaccessible cell, which belongs to a specific type of map cells.
 * Therefore, it inherits from the Cell class.
 */
public class InaccessibleCell extends Cell {
    public InaccessibleCell() {
        super(Colors.RED + "X " + Colors.RESET, Colors.RED + " X" + Colors.RESET,
                Colors.RED + "I" + Colors.RESET);
    }
}
