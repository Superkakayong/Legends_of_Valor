/**
 * This class serves as a center of all the notifications/prompts/messages throughout the game.
 */
public final class NotificationCenter {
    public static String welcome =Colors.BLUE + "            _            _.,----,\n" +
            " __  _.-._ / '-.        -  ,._  \\) \n" +
            "|  `-)_   '-.   \\       / < _ )/\" }\n" +
            "/__    '-.   \\   '-, ___(c-(6)=(6)\n" +
            " , `'.    `._ '.  _,'   >\\    \"  )\n" +
            " :;;,,'-._   '---' (  ( \"/`. -='/\n" +
            ";:;;:;;,  '..__    ,`-.`)'- '--'\n" +
            ";';:;;;;;'-._ /'._|   Y/   _/' \\\n" +
            "      '''\"._ F    |  _/ _.'._   `\\\n" +
            "             L    \\   \\/     '._  \\\n" +
            "      .-,-,_ |     `.  `'---,  \\_ _|\n" +
            "      //    'L    /  \\,   (\"--',=`)7\n" +
            "     | `._       : _,  \\  /'`-._L,_'-._\n" +
            "     '--' '-.\\__/ _L   .`'         './/\n" +
            "                 [ (  /\n" +
            "                  ) `{\n" +
            "                  \\__)" + Colors.RESET + "\n";

    public static String market = Colors.CYAN + "                                           __________________________\n" +
            "   _______________________-------------------                       `\\\n" +
            " /:--__                                                              |\n" +
            "||< > |                                   ___________________________/\n" +
            "| \\__/_________________-------------------                         |\n" +
            "|                                                                  |\n" +
            " |                      "+Colors.PURPLE_BG + Colors.BLACK + " Welcome to the market! " +
            Colors.RESET+Colors.CYAN+"                    |\n" +
            " |                                                                  |\n" +
            " |        We offer all kinds of weapons, armors, and spells         |\n" +
            "  |        to help you fight better!                                 |\n" +
            "  |      Weapons can increase your damage to the monsters,           |\n" +
            "  |        armors can increase your defense,                         |\n" +
            "  |      spells allow you to perform magic attacks.                   |\n" +
            "   |       WHAT ARE YOU WAITING FOR?                                  |\n" +
            "   |     Choose your favorites and put them in your Private Inventory!|\n" +
            "  |                                              ____________________|_\n" +
            "  |  ___________________-------------------------                      `\\\n" +
            "  |/`--_                                                                 |\n" +
            "  ||[^]||                                            ___________________/\n" +
            "   \\===/___________________--------------------------\n" + Colors.RESET;

    public static String sword = Colors.RED + "◎■■■■】〓〓〓〓〓〓〓〓〓〓〓〓＞\n" + Colors.RESET;

    public static String win = Colors.GREEN + "    ▕▔▔╲\n" +
            "     ▏ ▕\n" + "     ▏ ▕▂▂▂▂▂\n" + "▂▂▂▂╱  ▕▂▂▂▂▂▏\n" + "▉▉▉    ▕▂▂▂▂▂▏\n" + "▉▉▉    ▕▂▂▂▂▂▏\n" +
            "▔▔▔▔╲▂▂▕▂▂▂▂▂▏\n" + Colors.RESET;

    public static String lose = Colors.BLACK + "▕▇▇▇◤▔▔▔▔▔▔▔◥▇▇▇\n" +
            "▕▇▇▇▏◥▇◣┊◢▇◤▕▇▇▇\n" +
            "▕▇▇▇▏▃▆▅▎▅▆▃▕▇▇▇\n" + "▕▇▇▇▏╱▔▕▎▔▔╲▕▇▇▇\n" +
            "▕▇▇▇◣◣▃▅▎▅▃◢◢▇▇▇\n" + "▕▇▇▇▇◣◥▅▅▅◤◢▇▇▇▇\n" +
            "▕▇▇▇▇▇◣╲▇╱◢▇▇▇▇▇\n" + "▕▇▇▇▇▇▇◣▇◢▇▇▇▇▇▇\n" + Colors.RESET;

    public static String reward = Colors.YELLOW + "           .'\\   /`.\n" + "         .'.-.`-'.-.`.\n" +
            "    ..._:   .-. .-.   :_...\n" + "  .'    '-.(¥ ) ($ ).-'    `.\n" +
            " :  _    _ _`~(_)~`_ _    _  :\n" + ":  /:   ' .-=_   _=-. `   ;\\  :\n" +
            ":   :|-.._  '     `  _..-|:   :\n" + " :   `:| |`:-:-.-:-:'| |:'   :\n" +
            "  `.   `.| | | | | | |.'   .'\n" + "    `.   `-:_| | |_:-'   .'\n" +
            "      `-._   ````    _.-'\n" + "          ``-------''" + Colors.RESET;

    public static String monster = Colors.RED + "                                               ____\n" +
            "   ___                                      .-~. /_\"-._\n" +
            "  `-._~-.                                  / /_ \"~o\\  :Y\n" +
            "      \\  \\                                / : \\~x.  ` ')\n" +
            "       ]  Y                              /  |  Y< ~-.__j\n" +
            "      /   !                        _.--~T : l  l<  /.-~\n" +
            "     /   /                 ____.--~ .   ` l /~\\ \\<|Y\n" +
            "    /   /             .-~~\"        /| .    ',-~\\ \\L|\n" +
            "   /   /             /     .^   \\ Y~Y \\.^>/l_   \"--'\n" + Colors.RESET + Colors.GREEN +
            "  /   Y           .-\"(  .  l__  j_j l_/ /~_.-~    .\n" +
            " Y    l          /    \\  )    ~~~.\" / `/\"~ / \\.__/l_\n" +
            " |     \\     _.-\"      ~-{__     l  :  l._Z~-.___.--~\n" +
            " |      ~---~           /   ~~\"---\\_  ' __[>\n" +
            " l  .                _.^   ___     _>-y~\n" +
            "  \\  \\     .      .-~   .-~   ~>--\"  /\n" +
            "   \\  ~---\"            /     ./  _.-'\n" +
            "    \"-.,_____.,_  _.--~\\     _.-~\n" +
            "                ~~     (   _}\n"  +
            "                        `. ~(\n" + Colors.RESET + Colors.PURPLE +
            "                          )  \\\n" +
            "                         /,`--'~\\--'\n" + Colors.RESET;

    public static String quit = Colors.BLUE + "                                       _ __\n" +
            "        ___                             | '  \\\n" +
            "   ___  \\ /  ___         ,'\\_           | .-. \\        /|\n" +
            "   \\ /  | |,'__ \\  ,'\\_  |   \\          | | | |      ,' |_   /|\n" +
            " _ | |  | |\\/  \\ \\ |   \\ | |\\_|    _    | |_| |   _ '-. .-',' |_   _\n" +
            "// | |  | |____| | | |\\_|| |__    //    |     | ,'_`. | | '-. .-',' `. ,'\\_\n" +
            "\\\\_| |_,' .-, _  | | |   | |\\ \\  //    .| |\\_/ | / \\ || |   | | / |\\  \\|   \\\n" +
            " `-. .-'| |/ / | | | |   | | \\ \\//     |  |    | | | || |   | | | |_\\ || |\\_|\n" +
            "   | |  | || \\_| | | |   /_\\  \\ /      | |`    | | | || |   | | | .---'| |\n" +
            "   | |  | |\\___,_\\ /_\\ _      //       | |     | \\_/ || |   | | | |  /\\| |\n" +
            "   /_\\  | |           //_____//       .||`      `._,' | |   | | \\ `-' /| |\n" +
            "        /_\\           `------'        \\ |              `.\\  | |  `._,' /_\\\n" +
            "                                       \\|                    `.\\" + Colors.RESET;

    /*
        Display the welcome information and instructions of how to play the game (e.g. how to move).
     */
    public static void welcome() {
        System.out.println(Colors.PURPLE_BG + Colors.BLACK + " Welcome To The Wizarding World! " + Colors.RESET);
        System.out.println();
        System.out.println(welcome);

        System.out.println("In this game, you are going to form a team with 3 heroes to fight 3 monsters.");
        System.out.println();

        System.out.println("The nexus is your home, where you can buy/sell things here.");
        System.out.println("You can always go back to the nexus when you feel necessary.");
        System.out.println("You can't visit all the INACCESSIBLE AREAS!");

        System.out.println();
        System.out.println("Please keep in mind of the following operations: ");
    }

    public static void showOperations() {
        System.out.println(Colors.YELLOW + "---------------------------------------------" + Colors.RESET);

        System.out.print(Colors.RED + "W/w" + Colors.RESET);
        System.out.print(": Move Up; ");
        System.out.print(Colors.RED + "A/a" + Colors.RESET);
        System.out.println(": Move Left ");

        System.out.print(Colors.RED + "S/s" + Colors.RESET);
        System.out.print(": Move Down; ");
        System.out.print(Colors.RED + "D/d" + Colors.RESET);
        System.out.println(": Move Right ");

        System.out.print(Colors.RED + "Q/q" + Colors.RESET);
        System.out.print(": Quit; ");
        System.out.print(Colors.RED + "I/i" + Colors.RESET);
        System.out.println(": Show Information ");

        System.out.print(Colors.RED + "O/o" + Colors.RESET);
        System.out.print(": Change a Weapon; ");
        System.out.print(Colors.RED + "P/p" + Colors.RESET);
        System.out.println(": Change an Armor ");

        System.out.print(Colors.RED + "U/u" + Colors.RESET);
        System.out.print(": Use a Potion; ");
        System.out.print(Colors.RED + "F/f" + Colors.RESET);
        System.out.println(": Fight (i.e. Attack) ");

        System.out.print(Colors.RED + "C/c" + Colors.RESET);
        System.out.print(": Cast a Spell; ");
        System.out.print(Colors.RED + "T/t" + Colors.RESET);
        System.out.println(": Teleport ");

        System.out.print(Colors.RED + "B/b" + Colors.RESET);
        System.out.print(": Go Back to Nexus; ");
        System.out.print(Colors.RED + "V/v" + Colors.RESET);
        System.out.println(": Visit the Market ");

        System.out.print(Colors.RED + "M/m" + Colors.RESET);
        System.out.println(": Show hero's inventory ");

        System.out.println(Colors.YELLOW + "---------------------------------------------" + Colors.RESET);
    }

    public static void spawnNewMonsters() {
        System.out.println(Colors.RED + "Eight rounds passed! Three new monsters have spawned!" + Colors.RESET);
    }

    /*
        Information about continuing or quitting the game.
     */
    public static void playOrQuit(int index) {
        switch (index) {
            case 1:
                System.out.println("Press 'Q/q' to quit, or any other key(s) to start.");
                break;
            case 2:
                System.out.println(Colors.PURPLE_BG + Colors.BLACK +" Game ends. Come back anytime! " + Colors.RESET);
                System.out.println(quit);
                break;
            case 3:
                String gameStarts = Colors.RED + " Game Starts! " + Colors.RESET + Colors.BLUE;
                String helicopter = Colors.BLUE + "▬▬▬▬▬.◙.▬▬▬▬▬" + "\n" +
                        "  ▂▂▄▄▓▄▄▂▂" + "\n" +
                        "◢◤ █▀▀████▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄◢◤ " + "\n" +
                        "█" + gameStarts + "█▀▀▀▀▀▀▀╬ " + "\n" +
                        "◥███████████████◤" + "\n" +
                        "═════╩═════╩═════" + Colors.RESET;

                System.out.println(helicopter);
                System.out.println(Colors.PURPLE_BG + Colors.BLACK +" May the odds be ever in " +
                        "your favor! " + Colors.RESET);
                System.out.println();
                break;
        }
    }

    /*
        Information about heroes (i.e. the team of heroes).
     */
    public static void heroesInfo(int index) {
        switch (index) {
            case 1:
                System.out.println("How many heroes would you like to form your team? (1/2/3)");
                break;
            case 2:
                System.out.println("Invalid input! Must be either 1/2/3!");
                break;
            case 3:
                System.out.println("Invalid input! Must be an Integer!");
                break;
            case 4:
                System.out.println("Here are all the heroes in this game: ");
                System.out.println();
                break;
            case 5:
                System.out.println(Colors.BLUE + "Warriors: " + Colors.RESET);
                break;
            case 6:
                System.out.println(Colors.BLUE + "Sorcerers: " + Colors.RESET);
                break;
            case 7:
                System.out.println(Colors.BLUE + "Paladins: " + Colors.RESET);
                break;
        }
    }

    /*
        Information when forming the hero team.
     */
    public static void formHeroTeam(int index, int memberIndex) {
        switch (index) {
            case 1:
                System.out.print("Please choose a hero for your team member ");
                System.out.print(Colors.RED + memberIndex + "" + Colors.RESET);
                System.out.println(" by entering the index at the beginning of each line (e.g. 3 or 10 or 14): ");
                break;
            case 2:
                System.out.println("Invalid input! Out of the range of the hero list! Pleas enter again: ");
                break;
            case 3:
                System.out.println("Invalid input! Must be an Integer!");
                break;
        }
    }

    /*
        About the team's information.
     */
    public static void teamInfo(int index) {
        switch (index) {
            case 1:
                System.out.println(Colors.PURPLE_BG + Colors.BLACK +" Your Team: " + Colors.RESET);
                break;
        }
    }

    /*
        Ask which action does the player want to choose from the table of all operations.
     */
    public static void askForAction(String hero) {
        System.out.println("Which action would you like to perform for " +
                Colors.RED + hero + Colors.RESET + "?");
    }

    /*
        About the game map.
     */
    public static void mapRelated(int index) {
        switch (index) {
            case 1:
                System.out.println(Colors.PURPLE_BG + Colors.BLACK + " Now you can use the KEYS introduced before " +
                        "to play the game!" + Colors.RESET);
                System.out.println();
                break;
            case 2:
                System.out.println("Please enter your action (w/a/s/d/q/i/o/p/f/c/t/b/v): ");
                break;
            case 3:
                System.out.println("Invalid input! Must be one of [w/a/s/d/q/i/o/p/f/c/t/b/v/m]!");
                System.out.println();
                break;
            case 4:
                System.out.println("Cannot go outside of the map!");
                System.out.println();
                break;
            case 5:
                System.out.println("Cannot visit the Inaccessible Cell!");
                System.out.println();
                break;
            case 6:
                System.out.println("Cannot move behind a monster without killing it!");
                System.out.println();
                break;
            case 7:
                System.out.println("Invalid action! The monster is out of your range!");
                System.out.println();
                break;
            case 8:
                System.out.println("You are not in the nexus! Cannot visit the market!");
                System.out.println();
                break;
            case 9:
                System.out.println("Cannot move to a cell that has already been taken!");
                System.out.println();
                break;
        }
    }

    public static void monsterAction() {
        System.out.println(Colors.RED_BG + Colors.BLACK + " Attention! Monster Squad Revenges! " + Colors.RESET);
        System.out.println();
    }

    /*
        Show messages related to the market.
     */
    public static void marketMessages(int index) {
        switch (index){
            case 1:
                System.out.println(market);
                break;
            case 2:
                System.out.println(Colors.BLUE + "Weapons: " + Colors.RESET);
                break;
            case 3:
                System.out.println(Colors.BLUE + "Armors: " + Colors.RESET);
                break;
            case 4:
                System.out.println(Colors.BLUE + "Potions: " + Colors.RESET);
                break;
            case 5:
                System.out.println(Colors.BLUE + "Spells: " + Colors.RESET);
                break;
            case 6:
                System.out.println(Colors.PURPLE_BG + Colors.BLACK + " Good luck dear heroes! " + Colors.RESET);
                System.out.println();
                break;
        }
    }

    /*
        Information when buying props.
     */
    public static void buy(int index) {
        switch (index) {
            case 1:
                String rejection = " Sorry! You cannot buy this prop! ";
                System.out.println(Colors.RED + rejection + Colors.RESET);
                System.out.println("Your don't have enough money or your level is not high enough!");
                System.out.println();
                break;
            case 2:
                System.out.println(Colors.RED + "Successful!" + Colors.RESET);
                System.out.println();
                break;
        }
    }

    /*
        Information about the hero's private inventory.
     */
    public static void inventory(int index, String name) {
        switch (index) {
            case 1:
                System.out.println(Colors.PURPLE_BG + Colors.BLACK + " Inventory of Hero: " + name + " " + Colors.RESET);
                System.out.println();
                break;
            case 2:
                System.out.println(Colors.PURPLE_BG + Colors.BLACK + " Private Weapon Inventory of Hero: " + name + " "
                        + Colors.RESET);
                System.out.println();
                break;
            case 3:
                System.out.println(Colors.PURPLE_BG + Colors.BLACK + " Private Spell Inventory of Hero: " + name + " "
                        + Colors.RESET);
                System.out.println();
                break;
            case 4:
                System.out.println(Colors.PURPLE_BG + Colors.BLACK + " Private Potion Inventory of Hero: " + name + " "
                        + Colors.RESET);
                System.out.println();
                break;
            case 5:
                System.out.println(Colors.PURPLE_BG + Colors.BLACK + " Private Armor Inventory of Hero: " + name + " "
                        + Colors.RESET);
                System.out.println();
                break;
        }
    }

    // About the hero's attack.
    public static void heroAttack(int index) {
        switch (index) {
            case 1:
                System.out.println("Please choose a weapon from your private weapon inventory: ");
                break;
            case 2:
                System.out.println("Invalid input! Please enter a valid weapon index!");
                break;
            case 3:
                System.out.println("Invalid input! Must be an INTEGER!");
                break;
            case 4:
                System.out.println("Ops! Monster has dodged your attack!");
                System.out.println();
                break;
            case 5:
                System.out.println("But the monster has successfully defended all your damage!");
                System.out.println();
                break;
            case 6:
                System.out.println(Colors.RED + "Sorry! Your current weapon inventory is empty!" + Colors.RESET);
//                System.out.println(Colors.RED + "So your attack will be a basic attack" + Colors.RESET);
                System.out.println();
                break;
            case 7:
                System.out.println(Colors.RED + "Successfully equip the weapon!" + Colors.RESET);
                System.out.println("The weapon is now moved from your private inventory to your hands!");
        }
    }

    // About the damage to the roles (i.e. heroes/monsters).
    public static void inflict(int index, Role r1, double damage, Role r2) {
        String marker1, marker2;

        if (r1 instanceof Hero) {
            marker1 = ((Hero) r1).heroMarker;
            marker2 = ((Monster) r2).monsterMarker;
        } else {
            marker1 = ((Monster) r1).monsterMarker;
            marker2 = ((Hero) r2).heroMarker;
        }

        switch (index) {
            case 1:
                System.out.println(Colors.RED + marker1 + " [ " + r1.name + " ] " + "has caused " +
                        damage + " damage to " + marker2 + " [ " + r2.name + " ] !" + Colors.RESET);
                System.out.println();
                break;
            case 2:
                System.out.println(Colors.RED + marker1 + " [ " + r1.name + " ] " + "tries to inflict " +
                        damage + " damage to " + marker2 + " [ " + r2.name + " ] But..." + Colors.RESET);
                System.out.println();
                break;
        }
    }

    // About the damage reduction from the armors.
    public static void armorProtection(int index, String name, Armor armor) {
        switch (index) {
            case 1:
                System.out.println(name + "'s armor has reduced " + armor.getDamageReduction() + " damage!");
                break;
            case 2:
                System.out.println(name + "'s armor has reduced all the damage!");
                break;
        }
    }

    // Messages of alive status or dead status.
    public static void aliveOrDead(int index, String info, double hp) {
        switch (index) {
            case 1:
                System.out.println(info + " now has " + (int)hp + " HP left.");
                System.out.println();
                break;
            case 2:
                System.out.println(info + " has 0 hp now!");
                System.out.println(info + " is DEAD!");
                System.out.println();
                break;
        }
    }

    // Messages when casting a spell.
    public static void castASpell(int index) {
        switch (index) {
            case 1:
                System.out.println("Please choose a spell from your private spell inventory: ");
                break;
            case 2:
                System.out.println("Invalid input! Please enter a valid spell index!");
                break;
            case 3:
                System.out.println("Invalid input! Must be an INTEGER!");
                break;
            case 4:
                System.out.println("Ops! Monster has dodged your attack!");
                System.out.println();
                break;
            case 5:
                System.out.println("But the monster has successfully defended all your damage!");
                System.out.println();
                break;
            case 6:
                System.out.println(Colors.RED + "Sorry! You don't have enough mana to cast the spell!"
                        + Colors.RESET);
                System.out.println();
                break;
            case 7:
                System.out.println(Colors.RED + "Sorry! You don't have any spells right now!" + Colors.RESET);
                System.out.println();
                break;
        }
    }

    // Messages when using a potion.
    public static void useAPotion(int index) {
        switch (index) {
            case 1:
                System.out.println(Colors.RED + "Sorry! You don't have any potions right now!" + Colors.RESET);
                System.out.println();
                break;
            case 2:
                System.out.println("Please choose a potion from your private potion inventory: ");
                break;
            case 3:
                System.out.println("Invalid input! Please enter a valid potion index!");
                break;
            case 4:
                System.out.println("Invalid input! Must be an INTEGER!");
                break;
            case 5:
                System.out.println("Successful! You have consumed the selected potion!");
                System.out.println();
                break;
        }
    }

    // Messages when changing an armor.
    public static void changeAnArmor(int index) {
        switch (index) {
            case 1:
                System.out.println(Colors.RED + "Sorry! You don't have any armors right now!" + Colors.RESET);
                System.out.println();
                break;
            case 2:
                System.out.println("Please choose an armor from your private armor inventory: ");
                break;
            case 3:
                System.out.println("Invalid input! Please enter a valid armor index!");
                break;
            case 4:
                System.out.println("Invalid input! Must be an INTEGER!");
                break;
            case 5:
                System.out.println(" ");
                break;
        }
    }

    // About the monster's attack.
    public static void monsterAction(int index, String mMarker, String hMarker, String mName, String hName) {
        switch (index) {
            case 1:
                System.out.println("Ops! " + Colors.RED + hMarker + " [ " + hName + " ] " + Colors.RESET +
                        "has dodged the attack!");
                System.out.println();
                break;
            case 2:
                System.out.println(Colors.RED + mMarker + " [ " + mName + " ] " + "now ATTACKS " +
                        hMarker + " [ " + hName + " ] !" + Colors.RESET);
                System.out.println();
                break;
            case 3:
                System.out.println(Colors.RED + mMarker + " [ " + mName + " ] " + "now MOVES FORWARD!" + Colors.RESET);
                System.out.println();
                break;
        }
    }

    // Messages when visiting the market.
    public static void visitMarket(int index, int heroIndex) {
        switch (index) {
            case 1:
                System.out.println(Colors.PURPLE_BG + Colors.BLACK + " Welcome to the market! " + Colors.RESET);
                System.out.println();
                break;
            case 2:
                System.out.println("What would you like to do for " + Colors.RED + "H" + heroIndex
                        + Colors.RESET + ": ");
                System.out.println();
                System.out.println("1. Buy     2. Sell     3. Finish and Pass");
                break;
            case 3:
                System.out.println("Invalid input! Must ba either 1/2/3!");
                break;
            case 4:
                System.out.println("Invalid input! Must be an INTEGER!");
                break;
        }
    }

    public static void chooseAnOpponent(int index) {
        switch (index) {
            case 1:
                System.out.println("Now the game will AUTOMATICALLY choose a neighboring monster " +
                        "that has the lowest HP for you!");
                System.out.println("If there is ONLY ONE neighboring monster, then it is the ONE!");
                System.out.println();
                break;
            case 2:
                System.out.println("The monster will either move one step forward or attack the hero " +
                        "(if the hero is within range).");
                System.out.println("The game will AUTOMATICALLY choose a neighboring hero " +
                        "that has the lowest HP for the monster.");
                System.out.println("If there is ONLY ONE neighboring hero, then it is the ONE!");
                System.out.println();
                break;
        }
    }

    // Messages when choosing a prop.
    public static void chooseAProp(int index) {
        switch (index) {
            case 1:
                System.out.println("Please choose a prop from the list: ");
                break;
            case 2:
                System.out.println("Invalid input! Out of the range of the props list!");
                break;
            case 3:
                System.out.println("Invalid input! Must be an Integer!");
                break;
            case 4:
                System.out.println("Please choose a prop from your private inventory: ");
                break;
            case 5:
                System.out.println("Invalid input! Out of the range of your private inventory!");
                break;
            case 6:
                System.out.println(Colors.RED + "Sorry! Your private inventory is EMPTY, thus you cannot sell anything!"
                        + Colors.RESET);
                System.out.println();
                break;
        }
    }

    // About the monsters' information.
    public static void monstersInfo(int index) {
        switch (index) {
            case 1:
                System.out.println(Colors.PURPLE_BG + Colors.BLACK +" The Monster Squad: " + Colors.RESET);
                break;
        }
    }

    // Messages when fighting.
    public static void fight(int index, String hName, String mName) {
        switch (index) {
            case 1:
                System.out.println(monster);
                System.out.println(Colors.RED + "Attention!" + Colors.RESET);
                System.out.println("You have confronted 3 monsters whose levels are EQUAL as your heroes!");
                System.out.println("Heroes, prepare to FIGHT!");
                System.out.println();
                break;
            case 2:
                System.out.println(Colors.RED + "Hero " + hName + " Fights Monster " + mName + Colors.RESET);
                System.out.println(sword);
                System.out.println();
                break;
            case 3:
                System.out.println("Hero " + hName + ", which action would you like to perform?");
                System.out.println("1. Attack  2. Case a Spell  3. Use a Potion  4. Change an Armor  5. Change a Weapon");
                break;
            case 4:
                System.out.println("Invalid input! Must be 1/2/3/4/5!");
                break;
            case 5:
                System.out.println("Invalid input! Must be an Integer!");
                break;
        }
    }

    // Messages when performing a level up action.
    public static void levelUp(String name, int newLevel) {
        System.out.println(Colors.BLUE_BG + Colors.YELLOW + " Congratulations! " + Colors.RESET);
        System.out.println("Hero " + name + " levels up!");
        System.out.println(name + " is now level " + Colors.RED + newLevel + Colors.RESET);
        System.out.println();
    }

    // Messages about the status after a fight.
    public static void processStatus(int index) {
        switch (index) {
            case 1:
                System.out.println(lose);
                System.out.println();
                System.out.println(Colors.RED + "Unfortunately, all your heroes have FAINTED! Monsters have won the game!"
                        + Colors.RESET);
                System.out.println();
                break;
            case 2:
                System.out.println(Colors.BLUE_BG + Colors.YELLOW + " Congratulations! " + Colors.RESET);
                System.out.println("The hero team has reached the monster nexus first! Hero team has won the game!");
                System.out.println();
                System.out.println(win);
                System.out.println();
                System.out.println(reward);
                System.out.println();
                break;
            case 3:
                System.out.println("All heroes and all monsters are DEAD! It is a DRAW!");
                System.out.println();
                break;
            case 4:
                System.out.println(lose);
                System.out.println();
                System.out.println(Colors.RED + "Unfortunately, the monster squad has reached the hero nexus first!" +
                        " Monsters have won the game!"
                        + Colors.RESET);
                System.out.println();
                break;
            case 5:
                System.out.println("Heroes and monsters have reached the opponents' nexus at the same time!" +
                        " It is a DRAW!");
                System.out.println();
                break;
        }
    }

    // Notifications about the rewarding procedure.
    public static void reward(int index) {
        switch (index) {
            case 1:
                System.out.println(Colors.BLUE_BG + Colors.BLACK + " Now it is REWARD time! " + Colors.RESET);
                System.out.println();
                System.out.println(reward);
                System.out.println();
                System.out.println("The heroes who won the fight have gained a lot of COINS and EXPs!");
                System.out.println("The heroes who have fainted have been REVIVED with half HP and no rewards.");
                System.out.println();
                System.out.println("Here is your team's updated statistics: ");
                System.out.println();
                break;
        }
    }

    public static void teleport(int index) {
        switch (index) {
            case 1:
                System.out.println(Colors.BLUE_BG + Colors.BLACK + " Which cell do you want to teleport? " + Colors.RESET);
                break;
            case 2:
                System.out.println("Cannot go outside of the map!");
                System.out.println();
                break;
            case 3:
                System.out.println("Invalid input! Must be an Integer!");
                break;
            case 4:
                System.out.println("Cannot teleport to an INACCESSIBLE CELL!");
                System.out.println();
                break;
            case 5:
                System.out.println("Cannot teleport to a cell of the SAME LANE!");
                System.out.println();
                break;
            case 6:
                System.out.println("Cannot teleport this cell since it has not been EXPLORED yet!");
                System.out.println();
                break;
            case 7:
                System.out.println("Please enter the index of the row that you want to teleport: ");
                break;
            case 8:
                System.out.println("Please enter the index of the column that you want to teleport: ");
                break;
            case 9:
                System.out.println(Colors.BLUE_BG + Colors.BLACK + " Teleport Successfully! " + Colors.RESET);
                System.out.println();
                break;
            case 10:
                System.out.println("Cannot teleport to this cell since it has already been taken by another hero!");
                System.out.println();
                break;
            case 11:
                System.out.println("Cannot teleport to a cell that is beyond a monster!");
                System.out.println();
                break;
        }
    }
}
