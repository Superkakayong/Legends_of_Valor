import java.util.*;

/**
 * This is the main class of the project!
 * It controls all the game logics.
 * It is a role playing game, therefore it inherits from the RPGGame class.
 *
 * It also implements the Runnable interface since this class is a [thread] in the project.
 * We also have another [thread] in the project called Sound.java.
 *
 * There are also "more than enough" comments in this class to help you understand every piece of the logics!
 * In fact, you will find very detailed comments in every class of the project!
 */
public class LegendsGame extends RPGGame implements Runnable{
    private ArrayList<Hero> team; // The hero team
    private ArrayList<Monster> monsters; // The monster squad
    private ArrayList<Hero> deadHeroes; // All dead heroes
    private ArrayList<Hero> resetHeroes; // For resetting the heroes after a fight

    private Map map; // The game map
    private Market market; // The market in the market cell

    private int numOfRounds; // The number of rounds
    private int monsterSquadSize; // The size of the monster squad
    private boolean heroReachesMonsterNexus; // If a hero has reached the monster nexus
    private boolean monsterReachesHeroNexus; // If a monster has reached the hero nexus

    public LegendsGame() {
        team = new ArrayList<>();
        monsters = new ArrayList<>();
        deadHeroes = new ArrayList<>();
        resetHeroes = new ArrayList<>();

        map = new Map(8);
        market = new Market();

        numOfRounds = 0;
        monsterSquadSize = 0;
        heroReachesMonsterNexus = false;
        monsterReachesHeroNexus = false;
    }

    /*
        Override the run() method in the Runnable interface.
     */
    @Override
    public void run() { play(); }

    /*
        This is the CORE method of this class (or the entire project).
        It is concise and easy to understand, because the code is highly modularized!
     */
    @Override
    public void play() {
        prepare();
        formHeroTeam();
        printTeamMembers();
        formResetHeroes(); // Store the initial stats of all the heroes inorder to respawn them.
        initializeMonsters();
        printMonsters();

        NotificationCenter.mapRelated(1);

        while (true) {
            // Keep playing the game until players choose to quit or
            // all heroes are dead or
            // a hero/monster has reached the opponent's nexus

            if (numOfRounds == 8) {
                // If the game has gone through 9 rounds, generate 3 new monsters
                // (definition of a round: after all heroes and all monsters have performed)
                NotificationCenter.spawnNewMonsters();
                initializeMonsters();
                printMonsters();

                numOfRounds = 0; // Reset to 0
            }

            for (int i = 0; i < team.size(); ++i) {
                // Iterate through all the heroes in the hero team, and let them perform in order
                map.printMap();
                printTeamMembers();
                printMonsters();

                String action = getAction(i); // Get the action for the current hero (i.e. w/s/d/...)

                // If it is not a valid action, choose the action for the hero again (loop again).
                // e.g. the hero chooses to move outside of the map or attack a monster who is out of range.

                // However, even if the action is valid, it may fail when being performed!
                // e.g. the hero can always choose to use a potion, so isValidAction() is true.
                // But the hero may not have any potion in her/his inventory, thus performAction() is false.

                // Then we still need to loop again to choose another action for this hero
                if (!isValidAction(action, i) || !performAction(action, i)) { --i; }

                hasGameFinished(); // Check has the game finished
            }

            // Print the game map and prompt the heroes that monsters are going to REVENGE!
            map.printMap();
            NotificationCenter.attention();
            NotificationCenter.chooseAnOpponent(2);

            for (int i = 0; i < monsters.size(); ++i) {
                // Iterate through all the monsters in the monster squad, and let them perform in order

                // If the hero is within range to attack, attack (s)he
                if (shouldMonsterAttack(i)) { monsterAttack(i); }

                // If there is no heroes within range, and there is no monster one step ahead of the current monster,
                // the monster should move one step forward towards the hero nexus.
                // Otherwise the current monster will do NOTHING
                else if (!hasMonsterOneStepAhead(i)) { monsterMove(i); }

                hasGameFinished(); // Check has the game finished
            }

            ++numOfRounds; // A round has finished, +1

            if (!hasGameFinished() && !deadHeroes.isEmpty()) {
                /* If:
                   1. the fight is not finished (i.e. no one reaches any nexuses and not all heroes are dead);
                   2. and there are heroes dead,

                   respawn the dead heroes. (Please be noticed that if all heroes are dead, the game directly ENDS)
                 */
                respawn();
                sortHeroTeam(); // Show the heroes in correct order (i.e. H1 -> H2 -> H3, not any other orders)
            }
        }
    }

    /*
        Display welcome messages and relevant game instructions.
     */
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

    /*
        Form the hero team with 3 heroes.
     */
    private void formHeroTeam() {
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

    /*
        Override the printTeamMembers() method from the RPGGame class.
        Print the stats of all the heroes in the team ArrayList.
     */
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
//            name = "H" + (i + 1) + ". " + team.get(i).name;
            name = map.getMap()[team.get(i).row][team.get(i).col].leftMarker + ". " + team.get(i).name;
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

    /*
        Initialize the resetHero ArrayList to contain exactly the same information as the initial team ArrayList.
        This is to reset and update the stats of the heroes (e.g. Hp, mana) after the fight.
     */
    private void formResetHeroes() {
        for (int i = 0; i < team.size(); ++i) {
            // For every hero in the hero team
            Hero hero = team.get(i);

            resetHeroes.add(new Hero(hero.name, hero.level, hero.mana,
                    (int)hero.strength, (int)hero.dexterity, (int)hero.agility, hero.money, hero.exp));

            resetHeroes.get(i).setRow(hero.row);
            resetHeroes.get(i).setCol(hero.col);
            resetHeroes.get(i).setStartingCol(hero.startingCol);
            resetHeroes.get(i).heroMarker = hero.heroMarker;
        }
    }

    /*
        Initialize the monster squad (3 monsters for each initializing operation).
     */
    private void initializeMonsters() {
        NotificationCenter.fight(1, "", "");

        // The list of all monsters of the same level as a certain hero
        List<Monster> sameLevelMonsters = new ArrayList<>();

        Random seed = new Random();

        for (int i = 0; i < team.size(); ++i) {
            // For every hero in the hero team

            // The list of all monsters
            List<Monster> monsterList = new MonsterList().getMonsters();

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

            ++monsterSquadSize;

            int size = monsters.size();

            // Set the coordinate information of the monster
            monsters.get(size - 1).setRow(0);
            monsters.get(size - 1).setCol(3 * i);

            if (numOfRounds == 8) {
                // If the monster is created after 8 rounds, the rightMarker needs to be manually set to Mx
                map.getMap()[0][3 * i].rightMarker = "M" + monsterSquadSize;
                map.getMap()[0][3 * i].setMiddle();
            }
            monsters.get(size - 1).monsterMarker = map.getMap()[0][3 * i].rightMarker;

            // Set the current cell to be "has monsters" status
            map.getMap()[monsters.get(size - 1).row][monsters.get(size - 1).col].setHasMonsters(true);

            sameLevelMonsters.clear();
        }
    }

    /*
        Print the stats of all the monsters in the monster squad.
     */
    private void printMonsters() {
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
//            name = "M" + (i + 1) + ". " + monsters.get(i).name;
            name = map.getMap()[monsters.get(i).row][monsters.get(i).col].rightMarker + ". " + monsters.get(i).name;
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

    /*
        Get the hero's action (i.e. w/a/s/d/...).
     */
    private String getAction(int heroIndex) {
        Scanner sc = new Scanner(System.in);
        String action;

        while (true) {
            NotificationCenter.askForAction(team.get(heroIndex).heroMarker); // Ask the player to choose an action
            NotificationCenter.showOperations(); // Show the table of all operations
            NotificationCenter.mapRelated(2); // Ask the player to enter the desired action
            action = sc.nextLine();

            if (!action.equalsIgnoreCase("W") && !action.equalsIgnoreCase("A") &&
                    !action.equalsIgnoreCase("S") && !action.equalsIgnoreCase("D") &&
                    !action.equalsIgnoreCase("Q") && !action.equalsIgnoreCase("I") &&
                    !action.equalsIgnoreCase("O") && !action.equalsIgnoreCase("P") &&
                    !action.equalsIgnoreCase("F") && !action.equalsIgnoreCase("C") &&
                    !action.equalsIgnoreCase("T") && !action.equalsIgnoreCase("B") &&
                    !action.equalsIgnoreCase("V") && !action.equalsIgnoreCase("M") &&
                    !action.equalsIgnoreCase("U")) {

                // Invalid input (i.e. not w/a/s/d/q/i/o/p/f/c/t/b/v/m/u)
                NotificationCenter.mapRelated(3);
                continue;
            }

            // If the player enters i/I or m/M, print relevant stats and continue the loop.
            // Because only i/I and m/M can be performed multiple times in a single action.
            // In other words, i/I and m/M are not counted as an real action
            if (action.equalsIgnoreCase("I")) { printTeamMembers(); }
            else if (action.equalsIgnoreCase("M")) { team.get(heroIndex).printInventory(); }
            else { break; }
        }

        return action;
    }

    /*
        Check if the hero's action is valid.
     */
    private boolean isValidAction(String action, int heroIndex) {
        Hero h = team.get(heroIndex);
        Cell[][] m = map.getMap();

        // The hero can always quit the game
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

        if (action.equalsIgnoreCase("W")) {
            // Cannot move behind a monster without killing it
            // i.e. if a monster is in the hero's cell / on the hero's left / on the hero's right, cannot move forward

            if ((m[h.row][h.col].hasMonsters) ||
                    (h.col - 1 >= 0 && m[h.row][h.col - 1].hasMonsters) ||
                    (h.col + 1 < map.getSize() && m[h.row][h.col + 1].hasMonsters)) {

                // In the hero's cell || on the hero's left || on the hero's right
                NotificationCenter.mapRelated(6);
                return false;
            }
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
            int size = map.getSize();

            if ((h.col + 1 < size && m[h.row][h.col + 1].hasMonsters) || // Right
                    (h.col - 1 >= 0 && m[h.row][h.col - 1].hasMonsters) || // Left
                    (h.row - 1 >= 0 && m[h.row - 1][h.col].hasMonsters) || // Up
                    (h.row - 1 >= 0 && h.col + 1 < size && m[h.row - 1][h.col + 1].hasMonsters) || // Top right
                    (h.row - 1 >= 0 && h.col - 1 >= 0 && m[h.row - 1][h.col - 1].hasMonsters) || // Top left
                    (m[h.row][h.col].hasMonsters)) {
                // If at least one neighboring cell has a monster, return true
                return true;
            } else {
                // If no monsters in all the neighboring cells of the current hero, cannot attack or cast a spell
                NotificationCenter.mapRelated(7);
                return false;
            }
        }

        if (action.equalsIgnoreCase("V") && (!(map.getMap()[h.row][h.col] instanceof HeroNexusCell))) {
            // If the hero is not in the nexus, cannot visit the market
            NotificationCenter.mapRelated(8);
            return false;
        }

        return true;
    }

    /*
        Perform the action (provided that the action is valid).
     */
    private boolean performAction(String action, int heroIndex) {
        if (action.equalsIgnoreCase("Q")) { quit(); } // Quit

        if (action.equalsIgnoreCase("V")) { visitMarket(heroIndex); } // Visit the market

        if (action.equalsIgnoreCase("O")) { return team.get(heroIndex).changeAWeapon(); } // Change weapon

        if (action.equalsIgnoreCase("P")) { return team.get(heroIndex).changeAnArmor(); } // Change armor

        if (action.equalsIgnoreCase("U")) { return team.get(heroIndex).useAPotion(); } // Use potion

        if (action.equalsIgnoreCase("B")) { team.get(heroIndex).backToNexus(map); } // Back to nexus

        if (action.equalsIgnoreCase("T")) { return team.get(heroIndex).teleport(map, monsters); } // teleport

        if (action.equalsIgnoreCase("W") ||
                action.equalsIgnoreCase("A") ||
                action.equalsIgnoreCase("S") ||
                action.equalsIgnoreCase("D")) { heroMove(action, heroIndex); } // Move

        if (action.equalsIgnoreCase("F")) {
            // Fight (i.e. attack)
            Monster target = assignAMonster(heroIndex);
            team.get(heroIndex).attack(target, map);

            processTargetStatus(target);
            printMonsters();
        }

        if (action.equalsIgnoreCase("C")) {
            // Cast a spell
            Monster m = assignAMonster(heroIndex);
            boolean hasSucceeded = team.get(heroIndex).castASpell(m, map);

            processTargetStatus(m);
            printMonsters();

            return hasSucceeded;
        }

        return true;
    }

    /*
        All possible operations when the hero team is visiting a market.
     */
    private void visitMarket(int heroIndex) {
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
                // The hero chooses to pass, break out of the loop
                NotificationCenter.marketMessages(6);
                break;
            }
        }
    }

    /*
        A hero can choose her/his desired props from the market.
     */
    private Prop chooseAPropFromMarket() {
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

    /*
        A hero can choose props from her/his private inventory.
     */
    private int chooseAPropFromInventory(Hero hero) {
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

    /*
        Let the hero travel in the map using wW/aA/sS/dD.
     */
    private void heroMove(String action, int heroIndex) {
        Hero h = team.get(heroIndex);
        Cell[][] m = map.getMap();

        // Set the left marker of the original cell to be "  "
        m[h.row][h.col].leftMarker = "  ";
        m[h.row][h.col].setMiddle();

        // Since the hero is leaving this cell, the cell should be set to "no heroes" status
        m[h.row][h.col].setHasHeroes(false);

        if (action.equalsIgnoreCase("W")) {
            // Go up
            h.setRow(h.row - 1);

            // Calculate the lane of the hero after the move
            int lane;

            if (h.col <= 1) { lane = 0; }
            else if (map.getSize() - h.col <= 2) { lane = 2; }
            else { lane = 1; }

            // Update the highest explored level of this lane
            map.updateExploredLevel(lane);
        }
        else if (action.equalsIgnoreCase("A")) { h.setCol(h.col - 1); } // Go left
        else if (action.equalsIgnoreCase("S")) { h.setRow(h.row + 1); } // Go down
        else if (action.equalsIgnoreCase("D")) { h.setCol(h.col + 1); } // Go right

        // Update the left marker of the new cell to be the hero's marker (i.e. H1/H2/H3)
        m[h.row][h.col].leftMarker = h.heroMarker;
        m[h.row][h.col].setMiddle();

        // Since the hero is in the new cell, the cell should be set to "has heroes" status
        m[h.row][h.col].setHasHeroes(true);

        // If the hero has reached the monster nexus
        if (h.row == 0) { heroReachesMonsterNexus = true; }
    }

    /*
        When the hero attacks, since there may be more than 1 valid monsters that are within range to attack,
        the program will choose a monster that has the lowest HP for the hero.
     */
    private Monster assignAMonster(int heroIndex) {
        // We automatically assign a neighboring monster that has the lowest HP to the hero
        NotificationCenter.chooseAnOpponent(1);

        Hero h = team.get(heroIndex);
        Monster res = null;

        double minHp = Double.MAX_VALUE;

        for (Monster temp : monsters) {
            // The square of the distance between the hero and the current monster
            // Recall the MATH that calculates the distance between two points in a coordinate system :)
            double distanceSquare = Math.pow(h.row - temp.row, 2) + Math.pow(h.col - temp.col, 2);

            if (distanceSquare <= 2 && temp.hp < minHp) {
                // If the current monster is in a neighboring cell of the hero, and it has a lower HP, update [res]
                minHp = temp.hp;
                res = temp;
            }
        }

        return res;
    }

    /*
        A monster can move towards the hero nexus.
     */
    private void monsterMove(int monsterIndex) {
        Monster monster = monsters.get(monsterIndex);
        Cell[][] m = map.getMap();

        NotificationCenter.monsterAction(3, monster.monsterMarker, "", monster.name, "");

        // Set the right marker of the original cell to be "  "
        m[monster.row][monster.col].rightMarker = "  ";
        m[monster.row][monster.col].setMiddle();

        // Since the monster is leaving this cell, the cell should be set to "no monsters" status
        m[monster.row][monster.col].setHasMonsters(false);

        monster.setRow(monster.row + 1); // Go down (i.e. towards the hero nexus)

        // Update the right marker of the new cell to be the monster's marker (i.e. M1/M2/M3)
        m[monster.row][monster.col].rightMarker = monster.monsterMarker;
        m[monster.row][monster.col].setMiddle();

        // Since the monster is in the new cell, the cell should be set to "has monsters" status
        m[monster.row][monster.col].setHasMonsters(true);

        // If the monster has reached the hero's nexus
        if (monster.row == map.getSize() - 1) { monsterReachesHeroNexus = true; }
    }

    /*
        Check if the monster should attack in a specific turn.
     */
    private boolean shouldMonsterAttack(int monsterIndex) {
        Monster monster = monsters.get(monsterIndex);
        Cell[][] m = map.getMap();
        int size = map.getSize();

        if (monster.isDead()) { return false; }

        if (monster.row + 1 == size - 1) {
            // If the monster is 1 step away from the hero nexus
            if (!((monster.col + 1 < size && m[monster.row][monster.col + 1].hasHeroes) || // Right
                    (monster.col - 1 >= 0 && m[monster.row][monster.col - 1].hasHeroes) || // Left
                    (m[monster.row][monster.col].hasHeroes))) { // Monster's own cell
                // If there is no heroes on the monster's left or right side nor in the monster's own cell,
                // the monster should directly move forward to the hero nexus rather than attack!
                return false;
            }
        }

        // Check if there is a neighboring hero of the current monster
        return (monster.col + 1 < size && m[monster.row][monster.col + 1].hasHeroes) || // Right

                (monster.col - 1 >= 0 && m[monster.row][monster.col - 1].hasHeroes) || // Left

                (monster.row + 1 < size && m[monster.row + 1][monster.col].hasHeroes) || // Down

                (monster.row + 1 < size && monster.col + 1 < size &&
                        m[monster.row + 1][monster.col + 1].hasHeroes) || // Lower right

                (monster.row + 1 < size && monster.col - 1 >= 0 &&
                        m[monster.row + 1][monster.col - 1].hasHeroes) || // Lower left

                (m[monster.row][monster.col].hasHeroes); // Monster's own cell
    }

    /*
        Check if there is a monster one step ahead of the current monster.
        If true, the current monster cannot move forward since there cannot be 2 monster in a cell.
     */
    private boolean hasMonsterOneStepAhead(int monsterIndex) {
        Monster monster = monsters.get(monsterIndex);
        Cell[][] m = map.getMap();
        int size = map.getSize();

        return (monster.row + 1 < size) && !m[monster.row + 1][monster.col].rightMarker.equals("  ");
    }

    /*
        Monster attacks.
     */
    private void monsterAttack(int monsterIndex) {
        Monster m = monsters.get(monsterIndex);
        Hero target = assignAHero(monsterIndex); // Get the target hero to attack

        NotificationCenter.monsterAction(2, m.monsterMarker, target.heroMarker, m.name, target.name);

        m.attack(target, map); // Attack the target hero

        processTargetStatus(target);
        printTeamMembers();
    }

    /*
        When the monster attacks, since there may be more than 1 valid heroes that are within range to attack,
        the program will choose a hero that has the lowest HP for the monster.
     */
    private Hero assignAHero(int monsterIndex) {
        // We automatically assign a neighboring hero that has the lowest HP to the monster
//        NotificationCenter.chooseAnOpponent(2);

        Monster m = monsters.get(monsterIndex);
        Hero res = null;

        double minHp = Double.MAX_VALUE;

        for (Hero h : team) {
            // The square of the distance between the hero and the monster
            // Recall the MATH that calculates the distance between two points in a coordinate system :)
            double distanceSquare = Math.pow(h.row - m.row, 2) + Math.pow(h.col - m.col, 2);

            if (distanceSquare <= 2 && h.hp < minHp) {
                // If the current hero is in a neighboring cell of the monster, and it has a lower HP, update [res]
                minHp = h.hp;
                res = h;
            }
        }

        return res;
    }

    /*
        After a fight, process the status of the inflicted role (either a hero or a monster).
     */
    private void processTargetStatus(Role role) {
        if (role instanceof Hero) {
            // The role is a hero
            Hero h = (Hero) role;

            if (h.hasDead()) {
                // The hero is dead
                deadHeroes.add(h); // Add (s)he to the list of dead heroes
                team.remove(h); // Remove (s)he from the hero team

                // Set the left marker of the hero's cell to be "  "
                map.getMap()[h.row][h.col].leftMarker = "  ";
                map.getMap()[h.row][h.col].setMiddle();

                // Set the cell to "no heroes" status
                map.getMap()[h.row][h.col].setHasHeroes(false);
            } else {
                // For all heroes who are still ALIVE after each round, reward them with extra money and EXP
                h.money += 100 * h.level;
                h.setExpBonus(5 * h.level);

                // If the hero is qualified to level up, let it.
                // Otherwise the performLevelUp() method will just do nothing
                h.performLevelUp();

                // The survived hero will regain 10% of their hp and mana after each round
                // (i.e. at the start of the next round)
                h.hp *= 1.1;
                h.mana *= 1.1;
            }
        }

        if (role instanceof Monster) {
            // The role is a monster
            Monster m = (Monster) role;

            if (m.isDead()) {
                // Remove the dead monster from the monster squad
                monsters.remove(m);

                // Set the right marker of the monster's cell to be "  "
                map.getMap()[m.row][m.col].rightMarker = "  ";
                map.getMap()[m.row][m.col].setMiddle();

                // Set the cell to "no monsters" status
                map.getMap()[m.row][m.col].setHasMonsters(false);
            }
        }
    }

    /*
        Respawn all dead heroes.
     */
    private void respawn() {
        Iterator<Hero> iterator = deadHeroes.iterator();

        while (iterator.hasNext()) {
            // Iterate through all dead heroes in the deadHeroes ArrayList
            Hero hero = iterator.next();

            for (Hero resetHero : resetHeroes) {
                // Respawn all dead heroes with half of their initial HP and mana
                if (hero.name.equalsIgnoreCase(resetHero.name)) {
                    hero.hp = resetHero.hp * 0.5;
                    hero.mana = resetHero.mana * 0.5;
                    break;
                }
            }

            // Set its coordinates to be its starting coordinates
            hero.setRow(map.getSize() - 1);
            hero.setCol(hero.startingCol);

            team.add(hero); // Add the dead hero back to the hero team (i.e. respawn)
            iterator.remove(); // Remove the dead hero from [deadHeroes]

            // Set the left marker of the hero's cell to be the hero's marker
            map.getMap()[hero.row][hero.col].leftMarker = hero.heroMarker;
            map.getMap()[hero.row][hero.col].setMiddle();

            // Set the cell to "has heroes" status
            map.getMap()[hero.row][hero.startingCol].setHasHeroes(true);
        }
    }

    /*
        Show the heroes in correct (i.e. ascending) order (i.e. H1 -> H2 -> H3, not any other orders).
     */
    private void sortHeroTeam() {
        Collections.sort(team, new Comparator<Hero>() {
            @Override
            public int compare(Hero h1, Hero h2) {
                // If h1's heroMarker is "H1", h2's heroMarker is "H2",
                // then in the list of the hero team, h1 should appear before h2
                int index1 = Character.getNumericValue(h1.heroMarker.charAt(1));
                int index2 = Character.getNumericValue(h2.heroMarker.charAt(1));

                return index1 - index2;
            }
        });
    }

    /*
        Check if the game has finished.
     */
    public boolean hasGameFinished() {
        if (team.isEmpty() && monsters.isEmpty()) {
            // All heroes have dead, but all monsters are dead too. The WHOLE GAME ends.
            // Literally speaking, this situation could not happen, but just in case ^
            NotificationCenter.processStatus(3);
            quit();
        } else if (team.isEmpty()) {
            // All heroes have been dead, and monsters still exist. The WHOLE GAME ends.
            // Notice that NO hero will be revived if ALL heroes are down after the fight!
            NotificationCenter.processStatus(1);
            quit();
        } else if (heroReachesMonsterNexus && !monsterReachesHeroNexus) {
            // Hero team reaches the monster nexus first. Heroes win the game.
            // The WHOLE GAME ends
            NotificationCenter.processStatus(2);
            map.printMap();
            quit();
        } else if (monsterReachesHeroNexus && !heroReachesMonsterNexus) {
            // Monster squad reaches the hero nexus first. Monsters win the game.
            // The WHOLE GAME ends
            NotificationCenter.processStatus(4);
            map.printMap();
            quit();
        } else if (heroReachesMonsterNexus && monsterReachesHeroNexus) {
            // Both sides reach the opponent's nexus at the same time. TIE.
            // The WHOLE GAME ends
            NotificationCenter.processStatus(5);
            map.printMap();
            quit();
        } else {
            // The game still needs to be continued (i.e. has not finished)
            return false;
        }

        return true;
    }

    /*
        Quit the game.
     */
    @Override
    public void quit() {
        printTeamMembers();
        NotificationCenter.playOrQuit(2);
        System.exit(0);
    }
}