/**
 * This class represents a hero nexus cell, which belongs to a specific type of nexus cells.
 * Therefore, it inherits from the NexusCell class.
 */
public class HeroNexusCell extends NexusCell{
    public HeroNexusCell(String left, String right) {
        super(left, right);
        setHasHeroes(true);
    }
}
