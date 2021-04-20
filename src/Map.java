/**
 * This class represents the game map.
 */
import java.util.Random;

public class Map {
    private int size;
    private Cell[][] map;
    private int[] maxExploredLevels = new int[3];

    public Map(int size) {
        this.size = size;
        this.map = new Cell[size][size];

        setMaxExploredLevels();
        mapInitialization();
    }

    public int getSize() {
        return size;
    }

    public Cell[][] getMap() {
        return map;
    }

    public int[] getMaxExploredLevels() {
        return maxExploredLevels;
    }

    public void setMaxExploredLevels() {
        for (int i = 0; i < 3; ++i) { maxExploredLevels[i] = size - 1; }
    }

    /*
        Update the explored level of a specific lane.
     */
    public void updateExploredLevel(int lane) {
        if (lane < 0 || lane >= maxExploredLevels.length) { return; } // Check if it is a valid lane

        // Minus one actually means PLUS ONE on the explored level
        // because the hero moves forward by deducting one from her/his row position
        --maxExploredLevels[lane];
    }

    /*
        Initialize the map.
     */
    private void mapInitialization() {
        Random seed = new Random();
        int heroIndex = 1, monsterIndex = 1;

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                // We have derived the formula of the column index of Inaccessible Cells
                // according to the [General Term Formula of Arithmetic Sequence].
                // i.e. Inaccessible Cells should appear in column 2, 5, 8, etc
                if (3 * j + 2 < size) { map[i][3 * j + 2] = new InaccessibleCell(); }

                String heroMarker = "H" + heroIndex;
                String monsterMarker = "M" + monsterIndex;

                // Create monsters' nexus
                if (i == 0 && map[i][j] == null) {
                    if (j % 3 == 0) {
                        map[i][j] = new MonsterNexusCell("  ", monsterMarker);
                        map[i][j].setHasMonsters(true);
                        ++monsterIndex;
                    }
                    else { map[i][j] = new MonsterNexusCell("  ", "  "); }
                }

                // Create heroes' nexus
                if (i == size - 1 && map[i][j] == null) {
                    if (j % 3 == 0) {
                        map[i][j] = new HeroNexusCell(heroMarker, "  ");
                        map[i][j].setHasHeroes(true);
                        ++heroIndex;
                    }
                    else { map[i][j] = new HeroNexusCell("  ", "  "); }
                }

                int temp = seed.nextInt(10) + 1;

                if (map[i][j] == null) {
                    if (1 <= temp && temp <= 4) { map[i][j] = new PlainCell("  ", "  "); }
                    else if (5 <= temp && temp <= 6) { map[i][j] = new BushCell("  ", "  "); }
                    else if (7 <= temp && temp <= 8) { map[i][j] = new CaveCell("  ", "  "); }
                    else if (9 <= temp && temp <= 10) { map[i][j] = new KoulouCell("  ", "  "); }
                }
            }
        }
    }

    /*
        Print the map.
     */
    public void printMap() {
        System.out.println(Colors.PURPLE_BG + Colors.BLACK +" World Map: " + Colors.RESET);
        System.out.println();

        // Print all the column indexes (i.e. "col i: ")
        System.out.print("        ");
        for (int i = 0; i < map.length; ++i) {
            // For every column in the map
            System.out.print(" Col " + i + ":     ");
        }
        System.out.println();

        for (int i = 0; i < map.length; ++i) {
            // For every row in the map
            for (int j = 1; j <= 3; ++j) {
                // For every line in the cell (a cell has three lines: top, middle, bottom)

                // Print all the row indexes (i.e. "row i: ")
                if (j == 2) { System.out.print("Row " + i + ": "); }
                else { System.out.print("       "); }

                for (Cell cell : map[i]) {
                    // For every cell in the row

                    // If this is the top line or the bottom line of the cell
                    if (j != 2) { System.out.print(cell.getTopAndBottom() + "   "); }
                    else { System.out.print(cell.getMiddle() + "   "); }
                }

                System.out.println();
            }

            System.out.println();
        }
    }
}
