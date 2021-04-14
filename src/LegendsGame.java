import java.util.ArrayList;
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
        prepare();
        formHeroTeam();
        printTeamMembers();
        formResetHeroes();

        NotificationCenter.mapRelated(1);
        map.printMap();
    }

    @Override
    public void prepare() {
        NotificationCenter.welcome();
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
                        break;
                    }
                } else {
                    sc.next();
                    NotificationCenter.formHeroTeam(3, i);
                }
            }
        }

        team = temp.getTeam();
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

    @Override
    public void quit() {
        printTeamMembers();
        NotificationCenter.playOrQuit(2);
        System.exit(0);
    }


}
