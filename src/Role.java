/**
 * This class represents a role in the RPG games.
 * In this game, both Hero and Monster have inherited from this class.
 */
public class Role {
    protected String name;
    protected int level;
    protected int row; // Current row position of the hero
    protected int col; // Current column position of the hero
    protected double hp;

    public Role() {}

    public Role(String name, int level) {
        this.name = name;
        this.level = level;
        row = 0;
        col = 0;
        this.hp = 100.0 * level;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setHp() {
        this.hp = 100.0 * this.level;
    }
}
