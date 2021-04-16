import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This is the main class of the project!
 * It controls all the game logics.
 * It is a role playing game, therefore it inherits from the RPGGame class.
 */
public class LegendsGame extends RPGGame{
    private ArrayList<Hero> team; // The hero team
    private ArrayList<Monster> monsters; // The monster squad
    private ArrayList<Hero> faintedHeroes; // All fainted heroes
    private ArrayList<Hero> resetHeroes; // For resetting the heroes after a fight

    private Map map; // The game map
    private Market market; // The market in the market cell

    public LegendsGame() {
        team = new ArrayList<>();
        monsters = new ArrayList<>();
        faintedHeroes = new ArrayList<>();
        resetHeroes = new ArrayList<>();

        map = new Map(8);
        market = new Market();
    }

    @Override
    public void play() {
        prepare();
        formHeroTeam();
        printTeamMembers();
        formResetHeroes();
        initializeMonsters();
        printMonsters();

        NotificationCenter.mapRelated(1);

        while (true) {
            for (int i = 0; i < team.size(); ++i) {
                map.printMap();
                String action = getAction(i + 1);

            }
        }
    }

    @Override
    public void prepare() {
        NotificationCenter.welcome();
        NotificationCenter.showOperations();
        NotificationCenter.playOrQuit(1);

        Scanner sc = new Scanner(System.in);
        String decision = sc.nextLine();

        // Check if the player wants to start the game or quit
        if (decision.equalsIgnoreCase("Q")) {
            quit();
        } else {
            NotificationCenter.playOrQuit(3);
        }
    }

    public void formHeroTeam() {
        Scanner sc = new Scanner(System.in);
        HeroTeam temp = new HeroTeam();
        HeroList.printHeroList();

        for (int i = 1; i <= 3; ++i) {
            // Get the user desired hero for every hero headcount
            NotificationCenter.formHeroTeam(1, i);
            int choice;

            // Input validation checking
            while (true) {
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();

                    if (choice < 1 || choice > HeroList.getList().size()) {
                        NotificationCenter.formHeroTeam(2, i);
                    } else {
                        temp.addMembers(HeroList.getList().get(choice - 1));
                        team = temp.getTeam();

                        // Set the coordinate information of the hero
                        team.get(i - 1).setRow(map.getSize() - 1);
                        team.get(i - 1).setCol(3 * (i - 1));
                        team.get(i - 1).setStartingCol(3 * (i - 1));
                        break;
                    }
                } else {
                    sc.next();
                    NotificationCenter.formHeroTeam(3, i);
                }
            }
        }
    }

    @Override
    public void printTeamMembers() {
        if (team.isEmpty()) { return; }

        NotificationCenter.teamInfo(1);

        String name;
        double hp, strength, dexterity, agility, money, mana;
        int level, exp;

        String splitLine = Colors.YELLOW +
                "--><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><--" +
                Colors.RESET + "\n";

        System.out.println("Name                       HP    Level    Mana    Strength    Dexterity    Agility    Money    Exp");
        System.out.print(splitLine);

        for (int i = 0; i < team.size(); ++i) {
            // Print all the team members together with their stats
            name = "H" + (i + 1) + ". " + team.get(i).name;
            hp = team.get(i).hp;
            level = team.get(i).level;
            mana = team.get(i).mana;
            strength = team.get(i).strength;
            dexterity = team.get(i).dexterity;
            agility = team.get(i).agility;
            money = team.get(i).money;
            exp = team.get(i).exp;

            System.out.printf("%-25s %-8.0f %-6d %-9.0f %-12.0f %-11.0f %-9.0f %-8.0f %-5d", name, hp, level, mana, strength,
                    dexterity, agility, money, exp);
            System.out.println();
        }
        System.out.println(splitLine);
    }

    public void formResetHeroes() {
        for (Hero hero : team) {
            resetHeroes.add(new Hero(hero.name, hero.level, hero.mana,
                    (int)hero.strength, (int)hero.dexterity, (int)hero.agility, hero.money, hero.exp));
        }
    }

    public void initializeMonsters() {
        NotificationCenter.fight(1, "", "");

        // The list of all monsters
        List<Monster> monsterList = new MonsterList().getMonsters();

        // The list of all monsters of the same level as a certain hero
        List<Monster> sameLevelMonsters = new ArrayList<>();

        Random seed = new Random();

        for (int i = 0; i < team.size(); ++i) {
            // For every hero in the hero team
            for (int j = 0; j < monsterList.size(); ++j) {
                // For every monster in the monster list
                if (team.get(i).level == monsterList.get(j).level) {
                    // If the monster has the same level as the current hero,
                    // add it to [sameLevelMonsters]
                    sameLevelMonsters.add(monsterList.get(j));
                }
            }

            // Randomly pick a monster from [sameLevelMonsters] and add it into our monster squad
            int monsterIndex = seed.nextInt(sameLevelMonsters.size());
            monsters.add(sameLevelMonsters.get(monsterIndex));

            sameLevelMonsters.clear();
        }
    }

    public void printMonsters() {
        if (monsters.isEmpty()) { return; }

        NotificationCenter.monstersInfo(1);

        String name;
        double hp, damage, defenseStat, dodgeChance;
        int level;

        String splitLine = Colors.YELLOW +
                "--><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><--" +
                Colors.RESET + "\n";

        System.out.println("Name                       HP    Level    Damage    Defense    Dodge Chance");
        System.out.print(splitLine);

        for (int i = 0; i < monsters.size(); ++i) {
            // Print all the monsters together with their stats
            name = "M" + (i + 1) + ". " + monsters.get(i).name;
            hp = monsters.get(i).hp;
            level = monsters.get(i).level;
            damage = monsters.get(i).damage;
            defenseStat = monsters.get(i).defenseStat;
            dodgeChance = monsters.get(i).dodgeChance;

            System.out.printf("%-25s %-8.0f %-7d %-9.0f %-11.0f %-6.2f", name, hp, level, damage, defenseStat, dodgeChance);
            System.out.println();
        }
        System.out.println(splitLine);
    }

    public String getAction(int heroIndex) {
        Scanner sc = new Scanner(System.in);
        String action;
        int size = map.getSize();

        while (true) {
            NotificationCenter.askForAction(heroIndex); // Ask the player to choose an action
            NotificationCenter.showOperations(); // Show the table of all operations
            NotificationCenter.mapRelated(2); // Ask the player to enter the desired action
            action = sc.nextLine();

            if (!action.equalsIgnoreCase("W") && !action.equalsIgnoreCase("A") &&
                    !action.equalsIgnoreCase("S") && !action.equalsIgnoreCase("D") &&
                    !action.equalsIgnoreCase("Q") && !action.equalsIgnoreCase("I") &&
                    !action.equalsIgnoreCase("O") && !action.equalsIgnoreCase("P") &&
                    !action.equalsIgnoreCase("F") && !action.equalsIgnoreCase("C") &&
                    !action.equalsIgnoreCase("T") && !action.equalsIgnoreCase("B") &&
                    !action.equalsIgnoreCase("V")) {

                // Invalid input (i.e. not a/w/s/d/q/i)
                NotificationCenter.mapRelated(3);
                continue;
            }

            // If the player enters i/I, print relevant stats and continue the loop.
            // Because only i/I can be performed multiple times within a round
            if (action.equalsIgnoreCase("I")) { printTeamMembers(); }
            else { break; }
        }

        return action;
    }

    @Override
    public void quit() {
        printTeamMembers();
        NotificationCenter.playOrQuit(2);
        System.exit(0);
    }
}
