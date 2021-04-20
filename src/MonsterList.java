import java.util.ArrayList;

/**
 * This class stores all the monsters in the game.
 */
public class MonsterList {
    private ArrayList<Monster> monsters;

    public MonsterList() {
        monsters = new ArrayList<>();

        monsters.add(new Dragon("Chinese Fireball", 8, 850, 600, 40));
        monsters.add(new Dragon("Hungarian Horntail", 8, 900, 500, 40));
        monsters.add(new Dragon("Welsh Green", 6, 400, 400, 30));
        monsters.add(new Dragon("Swedish Short Snout", 7, 750, 400, 45));
        monsters.add(new Dragon("T-Rex", 10, 1500, 900, 45));
        monsters.add(new Dragon("Peruvian Vipertooth", 2, 500, 500, 10));
        monsters.add(new Dragon("Norwegian Ridgeback", 3, 580, 400, 15));

        monsters.add(new Exoskeleton("Erumpent", 7, 600, 800, 30));
        monsters.add(new Exoskeleton("Grindylow", 8, 650, 850, 45));
        monsters.add(new Exoskeleton("Centaur", 6, 350, 650, 35));
        monsters.add(new Exoskeleton("Basilisk", 9, 700, 800, 45));
        monsters.add(new Exoskeleton("Unicorn", 2, 400, 500, 10));

        monsters.add(new Spirit("FallenAngel", 4, 300, 500, 25));
        monsters.add(new Spirit("Bellatrix", 8, 800, 700, 50));
        monsters.add(new Spirit("Malfoy", 5, 380, 400, 20));
        monsters.add(new Spirit("Death Eaters", 7, 600, 500, 40));
        monsters.add(new Spirit("Voldemort", 10, 1200, 1000, 50));
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }
}
