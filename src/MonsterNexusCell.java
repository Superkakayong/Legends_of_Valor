/**
 * This class represents a monster nexus cell, which belongs to a specific type of nexus cells.
 * Therefore, it inherits from the NexusCell class.
 */
public class MonsterNexusCell extends NexusCell{
    public MonsterNexusCell(String left, String right) {
        super(left, right);
        setHasMonsters(true);
    }
}
