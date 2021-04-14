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

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                // We have derived the formula of the column index of Inaccessible Cells
                // according to the [General Term Formula of Arithmetic Sequence].
                // i.e. Inaccessible Cells should appear in column 2, 5, 8, etc
                if (3 * j + 2 < size) { map[i][3 * j + 2] = new InaccessibleCell(); }

                String heroMarker = "H" + (j + 1);
                String monsterMarker = "M" + (j + 1);

                // Create monsters' nexus
                if (i == 0 && map[i][j] == null) {
                    map[i][j] = new NexusCell(monsterMarker);
                    continue;
                }

                // Create heroes' nexus
                if (i == size - 1 && map[i][j] == null) {
                    map[i][j] = new NexusCell(heroMarker);
                    continue;
                }

                int temp = seed.nextInt(10) + 1;

                if (1 <= temp && temp <= 4) { map[i][j] = new PlainCell(" ", " "); }
                else if (5 <= temp && temp <= 6) { map[i][j] = new BushCell(" ", " "); }
                else if (7 <= temp && temp <= 8) { map[i][j] = new CaveCell(" ", " "); }
                else if (9 <= temp && temp <= 10) { map[i][j] = new KoulouCell(" ", " "); }
            }
        }
    }
}
