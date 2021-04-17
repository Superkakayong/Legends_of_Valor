import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
//        map.printMap();
//        map.getMap()[7][0].leftMarker = "qq";
//        map.getMap()[7][0].setMiddle();
//        map.printMap();
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


                // If it is not a valid action, choose the action for the hero again.
                // e.g. the hero chooses to move outside of the map or attack a monster who is out of range

                // However, even if the action is valid, it may fail when being performed!
                // e.g. the hero can always choose to use a potion, so isValidAction() is true.
                // But the hero may not have any potion in her/his inventory, thus performAction() is false.
                // Then we still need to loop again to choose another action for this hero
                if (!isValidAction(action, i) || !performAction(action, i)) { --i; }
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
                        team.get(i - 1).heroMarker = map.getMap()[map.getSize() - 1][3 * (i - 1)].leftMarker;
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

    public boolean isValidAction(String action, int heroIndex) {
        Hero h = team.get(heroIndex);
        Cell[][] m = map.getMap();

        if (action.equalsIgnoreCase("Q")) { return true; }

        if ((action.equalsIgnoreCase("A") && (h.col - 1 < 0)) ||
                (action.equalsIgnoreCase("D") && (h.col + 1 >= map.getSize())) ||
                (action.equalsIgnoreCase("S") && (h.row + 1 >= map.getSize())) ||
                (action.equalsIgnoreCase("W") && (h.row - 1 < 0))) {

            // Cannot go outside of the map
            NotificationCenter.mapRelated(4);
            return false;
        }

        if (action.equalsIgnoreCase("A") && (m[h.row][h.col - 1] instanceof InaccessibleCell) ||
                action.equalsIgnoreCase("D") && (m[h.row][h.col + 1] instanceof InaccessibleCell) ||
                action.equalsIgnoreCase("S") && (m[h.row + 1][h.col] instanceof InaccessibleCell) ||
                action.equalsIgnoreCase("W") && (m[h.row - 1][h.col] instanceof InaccessibleCell)) {
            // Cannot move to an inaccessible cell
            NotificationCenter.mapRelated(5);
            return false;
        }

        if (action.equalsIgnoreCase("W") && h.row == monsters.get(heroIndex).row) {
            // Cannot move behind a monster without killing it
            NotificationCenter.mapRelated(6);
            return false;
        }

        if (action.equalsIgnoreCase("A") && (!m[h.row][h.col - 1].leftMarker.equals("  ")) ||
                action.equalsIgnoreCase("D") && (!m[h.row][h.col + 1].leftMarker.equals("  ")) ||
                action.equalsIgnoreCase("S") && (!m[h.row + 1][h.col].leftMarker.equals("  ")) ||
                action.equalsIgnoreCase("W") && (!m[h.row - 1][h.col].leftMarker.equals("  "))) {
            // Cannot move to a cell that has already been taken by another hero
            NotificationCenter.mapRelated(9);
            return false;
        }

        if (action.equalsIgnoreCase("F") || action.equalsIgnoreCase("C")) {
            if ((h.col + 1 < map.getSize() && !map.getMap()[h.row][h.col + 1].getHasMonsters()) ||
                    (h.col - 1 >= 0 && !map.getMap()[h.row][h.col - 1].getHasMonsters()) ||
                    (h.row - 1 >= 0 && !map.getMap()[h.row - 1][h.col].getHasMonsters()) ||
                    (h.row - 1 >= 0 && h.col + 1 < map.getSize() && !map.getMap()[h.row - 1][h.col + 1].getHasMonsters()) ||
                    (h.row - 1 >= 0 && h.col - 1 >= 0 && !map.getMap()[h.row - 1][h.col - 1].getHasMonsters()) ||
                    (!map.getMap()[h.row][h.col].getHasMonsters())) {
                // If no monsters in all the neighboring cells of the current hero, cannot attack or cast a spell
                NotificationCenter.mapRelated(7);
                return false;
            }
        }

        if (action.equalsIgnoreCase("V") && (!(map.getMap()[h.row][h.col] instanceof HeroNexusCell))) {
            // If the hero is not in the nexus, cannot visit the market'
            NotificationCenter.mapRelated(8);
            return false;
        }

        return true;
    }

    public boolean performAction(String action, int heroIndex) {
        if (action.equalsIgnoreCase("Q")) { quit(); }

        if (action.equalsIgnoreCase("V")) { visitMarket(heroIndex); }

        if (action.equalsIgnoreCase("O")) { return team.get(heroIndex).changeAWeapon(); }

        if (action.equalsIgnoreCase("P")) { return team.get(heroIndex).changeAnArmor(); }

        if (action.equalsIgnoreCase("U")) { return team.get(heroIndex).useAPotion(); }

        if (action.equalsIgnoreCase("B")) { team.get(heroIndex).backToNexus(map.getSize()); }

        if (action.equalsIgnoreCase("T")) { team.get(heroIndex).teleport(map); }

        if (action.equalsIgnoreCase("W") ||
                action.equalsIgnoreCase("A") ||
                action.equalsIgnoreCase("S") ||
                action.equalsIgnoreCase("D")) { heroMove(action, heroIndex); }




        return true;
    }

    public void visitMarket(int heroIndex) {
        Scanner sc = new Scanner(System.in);

        NotificationCenter.visitMarket(1, heroIndex + 1);

        int choice; // Get the hero's action choice

        while (true) {
            while (true) {
                // Get the choice of the hero (i.e. buy/sell/pass)
                printTeamMembers();
                NotificationCenter.visitMarket(2, heroIndex + 1);

                if (sc.hasNextInt()) {
                    choice = sc.nextInt();

                    // There are only 3 options for the hero (i.e. buy/sell/pass)
                    if (choice < 1 || choice > 3) { NotificationCenter.visitMarket(3, heroIndex + 1); }
                    else { break; }
                } else {
                    // If input is not an INTEGER
                    sc.next();
                    NotificationCenter.visitMarket(4, heroIndex + 1);
                }
            }

            if (choice == 1) {
                // The hero chooses to buy a prop
                market.printMarket();

                // Successfully bought the prop, print this hero's private inventory
                if (team.get(heroIndex).buy(chooseAPropFromMarket())) { team.get(heroIndex).printInventory(); }
            } else if (choice == 2) {
                // The hero chooses to sell a prop
                team.get(heroIndex).printInventory();
                int propIndex = chooseAPropFromInventory(team.get(heroIndex));

                if (propIndex != Integer.MIN_VALUE) {
                    // Successfully sold the prop, print this hero's private inventory
                    team.get(heroIndex).sell(propIndex);
                    team.get(heroIndex).printInventory();
                }
            } else {
                // The hero chooses to pass, break out the loop
                NotificationCenter.marketMessages(6);
                break;
            }
        }
    }

    public Prop chooseAPropFromMarket() {
        Scanner sc = new Scanner(System.in);

        // Input validation checking
        while (true) {
            NotificationCenter.chooseAProp(1);
            int choice;

            if (sc.hasNextInt()) {
                choice = sc.nextInt();

                if (choice < 1 || choice > market.getProps().size()) {
                    NotificationCenter.chooseAProp(2);
                } else { return market.getProps().get(choice - 1); }
            } else {
                sc.next();
                NotificationCenter.chooseAProp(3);
            }
        }
    }

    public int chooseAPropFromInventory(Hero hero) {
        if (hero.props.isEmpty()) {
            // The hero's inventory is empty
            NotificationCenter.chooseAProp(6);
            return Integer.MIN_VALUE;
        }

        Scanner sc = new Scanner(System.in);

        // Input validation checking
        while (true) {
            NotificationCenter.chooseAProp(4);
            int choice;

            if (sc.hasNextInt()) {
                choice = sc.nextInt();

                if (choice < 1 || choice > hero.props.size()) {
                    NotificationCenter.chooseAProp(5);
                } else { return choice; }
            } else {
                sc.next();
                NotificationCenter.chooseAProp(3);
            }
        }
    }

    public void heroMove(String action, int heroIndex) {
        Hero h = team.get(heroIndex);
        Cell[][] m = map.getMap();

        // Set the left marker of the original cell to be "  "
        m[h.row][h.col].leftMarker = "  ";
        m[h.row][h.col].setMiddle();

        if (action.equalsIgnoreCase("W")) {
            // Go forward
            h.setRow(h.row - 1);

            // Calculate the lane of the hero after the move
            int lane;

            if (h.col <= 1) { lane = 0; }
            else if (map.getSize() - h.col <= 2) { lane = 2; }
            else { lane = 1; }

            // Update the highest explored level of this lane
            map.updateExploredLevel(lane);
        }
        else if (action.equalsIgnoreCase("A")) { h.setCol(h.col - 1); }
        else if (action.equalsIgnoreCase("S")) { h.setRow(h.row + 1); }
        else if (action.equalsIgnoreCase("D")) { h.setCol(h.col + 1); }

        // Update the left marker of the new cell to be the hero's marker (i.e. H1/H2/H3)
        m[h.row][h.col].leftMarker = h.heroMarker;
        m[h.row][h.col].setMiddle();
    }

    @Override
    public void quit() {
        printTeamMembers();
        NotificationCenter.playOrQuit(2);
        System.exit(0);
    }
}
