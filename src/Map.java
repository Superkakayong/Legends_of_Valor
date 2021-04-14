import java.util.Random;

public class Map {
    private int size;
    private Cell[][] map;

    public Map(int size) {
        this.size = size;
        this.map = new Cell[size][size];
        mapInitialization();
    }

    public int getSize() {
        return size;
    }

    public Cell[][] getMap() {
        return map;
    }

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
                        map[i][j] = new NexusCell("  ", monsterMarker);
                        ++monsterIndex;
                    }
                    else { map[i][j] = new NexusCell("  ", "  "); }
                }

                // Create heroes' nexus
                if (i == size - 1 && map[i][j] == null) {
                    if (j % 3 == 0) {
                        map[i][j] = new NexusCell(heroMarker, "  ");
                        ++heroIndex;
                    }
                    else { map[i][j] = new NexusCell("  ", "  "); }
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

    public void printMap() {
        System.out.println(Colors.PURPLE_BG + Colors.BLACK +" World Map: " + Colors.RESET);

        for (Cell[] row : map) {
            // For every row in the map
            for (int i = 1; i <= 3; ++i) {
                // For every line in the cell (a cell has three lines: top, middle, bottom)
                for (Cell cell : row) {
                    // For every cell in the row

                    // If this is the top line or the bottom line of the cell
                    if (i != 2) { System.out.print(cell.getTopAndBottom() + "   "); }
                    else { System.out.print(cell.getMiddle() + "   "); }
                }

                System.out.println();
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        Map m = new Map(8);
        m.printMap();
    }
}
