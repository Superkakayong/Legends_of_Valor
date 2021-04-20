import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class manages all the common properties of all types of heroes.
 * It inherits from the Role class, and implements the Fight interface.
 * It is the super class for all specific hero classes.
 */
public class Hero extends Role implements Fight{
    protected double mana;
    protected double strength;
    protected double dexterity;
    protected double agility;
    protected double dodgeChance;
    protected double money;
    protected int exp;
    protected int expBonus; // For accumulating the EXP bonus for every successful fight
    protected int startingCol; // Starting column position of the hero
    protected String heroMarker; // The marker of the hero (e.g. H1/H2/H3)

    // All kinds of props that the hero owns
    protected ArrayList<Prop> props;
    protected ArrayList<Weapon> weapons;
    protected ArrayList<Armor> armors;
    protected ArrayList<Potion> potions;
    protected ArrayList<Spell> spells;

    protected LevelUpBehavior levelUpBehavior; // Use of the Strategy Pattern!!!
    protected Weapon equippedWeapon; // Record the hero's current equipped weapon
    protected Armor equippedArmor; // Record the hero's current equipped armor

    public Hero(String name, int level, double mana, int strength, int dexterity, int agility, double money, int exp) {
        super(name, level);

        this.mana = mana;
        this.strength = strength;
        this.dexterity = dexterity;
        this.agility = agility;
        this.setDodgeChance();
        this.money = money;
        this.exp = exp;

        expBonus = 0;
        startingCol = 0;

        props = new ArrayList<>();
        weapons = new ArrayList<>();
        armors = new ArrayList<>();
        potions = new ArrayList<>();
        spells = new ArrayList<>();

        equippedWeapon = null;
        equippedArmor = null;
    }

    public double getMoney() {
        return money;
    }

    public void setDodgeChance() {
        dodgeChance = this.agility * 0.001;
    }

    public void setExpBonus(int bonus) {
        this.expBonus += bonus;
        this.exp += bonus;
    }

    public void setStartingCol(int startingCol) {
        this.startingCol = startingCol;
    }

    /*
        Check if a hero has dead.
    */
    public boolean hasDead() {
        return hp <= 0;
    }

    /*
        Check if a hero can level up.
        Please be noticed that the maximum level for a hero is level 10.
     */
    public boolean checkLevelUp() {
        return levelUpBehavior.canLevelUp() && level <= 9;
    }

    /*
        Level up the hero when (s)he is qualified to do so.
     */
    public void performLevelUp() {
        if (!checkLevelUp()) { return; }

        NotificationCenter.levelUp(name, level + 1);
        levelUpBehavior.levelUp();

        // When levels up, also update the hero's HP and mana
        // (i.e. a generic update for all kinds of heroes when they level up)
        setHp();
        mana *= 1.1;
    }

    /*
        A hero can buy props.
     */
    public boolean buy(Prop prop) {
        if (!prop.isAffordable(this)) {
            NotificationCenter.buy(1);
            return false; // Failed to buy the prop
        }

        NotificationCenter.buy(2);

        money -= prop.cost;
        props.add(prop);

        // Also add the prop to its specific type 0f prop ArrayList
        if (prop instanceof Weapon) { weapons.add((Weapon) prop); }
        else if (prop instanceof Armor) { armors.add((Armor) prop); }
        else if (prop instanceof Potion) { potions.add((Potion) prop); }
        else if (prop instanceof Spell) { spells.add((Spell) prop); }

        return true; // Successfully bought the prop
    }

    /*
        A hero can print her/his private inventory of all owned props.
     */
    public void printInventory() {
        NotificationCenter.inventory(1, name);

        String splitLine = Colors.YELLOW_BG + Colors.BLACK +
                "--><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><--" +
                Colors.RESET + "\n";

        System.out.println("Name                  Price    Required Level    Prop Type");
        System.out.print(splitLine);

        for (int i = 0; i < props.size(); ++i) {
            // Print all the props owned by the hero
            String name = (i + 1) + ". " + props.get(i).name;
            int price = props.get(i).cost;
            int level = props.get(i).requiredLevel;
            String type = "";

            if (props.get(i) instanceof Weapon) { type = Colors.BLUE + "Weapon" + Colors.RESET; }
            else if (props.get(i) instanceof Armor) { type = Colors.BLACK + "Armor" + Colors.RESET; }
            else if (props.get(i) instanceof Potion) { type = Colors.WHITE + "Potion" + Colors.RESET; }
            else if (props.get(i) instanceof IceSpell) { type = Colors.CYAN + "Ice Spell" + Colors.RESET; }
            else if (props.get(i) instanceof FireSpell) { type = Colors.RED + "Fire Spell" + Colors.RESET; }
            else if (props.get(i) instanceof LightningSpell) { type = Colors.GREEN + "Lightning Spell" + Colors.RESET; }

            System.out.printf("%-22s %-14d %-12d %-8s", name, price, level, type);
            System.out.println();
        }
        System.out.println(splitLine);
    }

    /*
        A hero can sell props
     */
    public void sell(int propIndex) {
        Prop temp = props.get(propIndex - 1);

        NotificationCenter.buy(2);

        money += temp.cost * 0.5; // Sell the prop for half price
        props.remove(propIndex - 1);

        if (temp instanceof Weapon) { weapons.remove(temp); }
        else if (temp instanceof Armor) { armors.remove(temp); }
        else if (temp instanceof Potion) { potions.remove(temp); }
        else if (temp instanceof Spell) { spells.remove(temp); }
    }

    /*
        Override the attack() method in the Fight interface.
     */
    @Override
    public void attack() {}

    /*
        Overload the attack() method above.
     */
    public void attack(Monster monster, Map map) {
        // Get the monster's information
        double monsterDodgeChance = monster.dodgeChance;
        double strength = this.strength;

        Cell[][] m = map.getMap();

        // Koulou cells buff the strength of any hero who is inside them by 10%
        if (m[row][col] instanceof KoulouCell) { strength *= 1.1; }

        // Calculate the total damage that the hero can inflict to the monster
//        equippedWeapon = chooseWeapon();
        double totalDamage = equippedWeapon == null ? strength * 0.5 : equippedWeapon.damage(strength);

        // Determine if the monster can dodge this attack
        double rand = Math.random();

        if (rand <= monsterDodgeChance) {
            // The monster has dodged the attack
            NotificationCenter.inflict(2, this, totalDamage, monster);
            NotificationCenter.heroAttack(4);
            return;
        }

        // The monster failed to dodged the attack
        monster.defenseStat -= totalDamage;

        // Show "who caused how much damage to whom"
        NotificationCenter.inflict(1, this, totalDamage, monster);

        // Check and process the status of the monster after being inflicted
        processMonsterStatus(monster);
    }

    /*
        A hero can choose weapons from her/his private inventory.
     */
    public Weapon chooseWeapon() {
        if (weapons.isEmpty()) {
            // If the hero has no weapons in her/his weapon inventory, return null
            NotificationCenter.heroAttack(6);
            return null;
        }

        printWeaponInventory();
        NotificationCenter.heroAttack(1);

        Scanner sc = new Scanner(System.in);
        int choice; // Get the hero's choice of which weapon to choose

        while (true) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();

                if (choice < 1 || choice > weapons.size()) {
                    // Cannot choose a weapon that is out of the range of the weapons list
                    NotificationCenter.heroAttack(2);
                } else {
                    props.remove(weapons.get(choice - 1));
                    return weapons.remove(choice - 1);
                }
            } else {
                // Illegal input => not an INTEGER
                sc.next();
                NotificationCenter.heroAttack(3);
            }
        }
    }

    /*
        A hero can print her/his private weapon inventory.
     */
    public void printWeaponInventory() {
        String splitLine = Colors.YELLOW_BG + Colors.BLACK +
                "--><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><--" +
                Colors.RESET + "\n";

        NotificationCenter.inventory(2, name);
        System.out.println("Name                  Base Damage");
        System.out.print(splitLine);

        int index = 1;

        for (int i = 0; i < props.size(); ++i) {
            // Print all the weapons
            if (props.get(i) instanceof Weapon) {
                String name = index + ". " + props.get(i).name;
                int damage = ((Weapon) props.get(i)).getBaseDamage();
                ++index;

                System.out.printf("%-24s %-5d", name, damage);
                System.out.println();
            }
        }
        System.out.println(splitLine);
    }

    /*
        After a round of fight, check the monster's status.
     */
    public void processMonsterStatus(Monster monster) {
        String monsterName = monster.name;

        if (monster.defenseStat >= 0)  {
            // The monster has blocked all the damage
            NotificationCenter.castASpell(5);
        } else {
            // The monster failed to block all the damage
            monster.hp += monster.defenseStat; // Notice: now monster.defenseStat < 0
            monster.defenseStat = 0; // Reset monster.defenseStat to 0 (no negative defense)
            String info = "Monster " + monsterName;

            if (monster.hp > 0){
                // The monster is still alive
                NotificationCenter.aliveOrDead(1, info, monster.hp);
            } else {
                // The monster is DEAD ^
                NotificationCenter.aliveOrDead(2, info, 0);
            }
        }
    }

    /*
        A hero can cast a spell to the monster.
     */
    public boolean castASpell(Monster monster, Map map) {
        // Get the monster's information
        double monsterDodgeChance = monster.dodgeChance;
        double dexterity = this.dexterity;

        Spell s = chooseSpell(); // Pick a spell in the hero's spell inventory

        Cell[][] m = map.getMap();

        // Bush cells increase the dexterity of any hero who is inside them by 10%.
        if (m[row][col] instanceof BushCell) { dexterity *= 1.1; }

        // If failed to choose a spell (may due to the spell inventory is empty), return false
        if (s == null) { return false; }

        if (mana < s.getManaCost()) {
            // Not enough mana to cast the spell
            NotificationCenter.castASpell(6);
            return false;
        } else { mana -= s.getManaCost(); }

        // Calculate the total damage that the hero can inflict to the monster
        double totalDamage = s.damage(dexterity);

        // Determine if the monster can dodge this attack
        double rand = Math.random();

        if (rand <= monsterDodgeChance) {
            // The monster dodged the attack
            NotificationCenter.inflict(2, this, totalDamage, monster);
            NotificationCenter.castASpell(4);
            return true;
        }

        // The monster failed to dodged the attack
        monster.defenseStat -= totalDamage;

        // Deteriorate the level of a specific skill of the monster by 10%
        if (s instanceof IceSpell) { monster.damage *= 0.9; }
        else if (s instanceof FireSpell) { monster.defenseStat *= 0.9; }
        else if (s instanceof LightningSpell) { monster.dodgeChance *= 0.9; }

        // Show "who caused how much damage to whom"
        NotificationCenter.inflict(1, this, totalDamage, monster);

        // Check and process the status of the monster after being inflicted
        processMonsterStatus(monster);

        return true;
    }

    /*
        A hero can choose spells in her/his spell inventory.
     */
    public Spell chooseSpell() {
        if (spells.isEmpty()) {
            // The spell inventory is empty
            NotificationCenter.castASpell(7);
            return null;
        }

        printSpellInventory();
        NotificationCenter.castASpell(1);

        Scanner sc = new Scanner(System.in);
        int choice; // Get the spell that the hero wants to choose

        // Input validation checking
        while (true) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();

                if (choice < 1 || choice > spells.size()) {
                    NotificationCenter.castASpell(2);
                } else {
                    return spells.get(choice - 1);
                }
            } else {
                sc.next();
                NotificationCenter.castASpell(3);
            }
        }
    }

    /*
        A hero can print her/his spell inventory.
     */
    public void printSpellInventory() {
        String splitLine = Colors.YELLOW_BG + Colors.BLACK +
                "--><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><--" +
                Colors.RESET + "\n";

        NotificationCenter.inventory(3, name);
        System.out.println("Name                  Base Damage     Mana Cost");
        System.out.print(splitLine);

        int index = 1;

        for (int i = 0; i < props.size(); ++i) {
            // Print all the spells
            if (props.get(i) instanceof Spell) {
                String name = index + ". " + props.get(i).name;
                int damage = ((Spell) props.get(i)).getBaseDamage();
                int mana = ((Spell) props.get(i)).getManaCost();
                ++index;

                System.out.printf("%-24s %-15d %-5d", name, damage, mana);
                System.out.println();
            }
        }
        System.out.println(splitLine);
    }

    /*
        A hero can use a potion.
     */
    public boolean useAPotion() {
        Potion p = choosePotion();

        if (p == null) { return false; }

        // Increase the specific skill of the hero
        if (p.getAffectedAttr().charAt(0) == 'S') { strength += p.getIncreasedAttr(); }
        else if (p.getAffectedAttr().charAt(0) == 'D') { dexterity += p.getIncreasedAttr(); }
        else if (p.getAffectedAttr().charAt(0) == 'A') { agility += p.getIncreasedAttr(); }

        NotificationCenter.useAPotion(5);

        // After consumed, remove it from both potions and props (potions are single-use)
        potions.remove(p);
        props.remove(p);

        return true;
    }

    /*
        A hero can choose potions in her/his spell inventory.
     */
    public Potion choosePotion() {
        if (potions.isEmpty()) {
            NotificationCenter.useAPotion(1);
            return null;
        }

        printPotionInventory();
        NotificationCenter.useAPotion(2);

        Scanner sc = new Scanner(System.in);
        int choice; // Get the hero's choice of potion

        // Input validation checking
        while (true) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();

                if (choice < 1 || choice > potions.size()) {
                    NotificationCenter.useAPotion(3);
                } else {
                    return potions.get(choice - 1);
                }
            } else {
                sc.next();
                NotificationCenter.useAPotion(4);
            }
        }
    }

    /*
        A hero can print her/his potion inventory.
     */
    public void printPotionInventory() {
        String splitLine = Colors.YELLOW_BG + Colors.BLACK +
                "--><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><--" +
                Colors.RESET + "\n";

        NotificationCenter.inventory(4, name);
        System.out.println("Name                  Increased Amount    Affected Attr");
        System.out.print(splitLine);

        int index = 1;

        for (int i = 0; i < props.size(); ++i) {
            // Print all the potions
            if (props.get(i) instanceof Potion) {
                String name = index + ". " + props.get(i).name;
                int amount = ((Potion) props.get(i)).getIncreasedAttr();
                String affectedAttr = ((Potion) props.get(i)).getAffectedAttr();
                ++index;

                System.out.printf("%-28s %-14d %-18s", name, amount, affectedAttr);
                System.out.println();
            }
        }
        System.out.println(splitLine);
    }

    /*
        A hero can change an armor.
     */
    public boolean changeAnArmor() {
        Armor temp = chooseArmor();

        if (temp == null) { return false; }

        if (equippedArmor != null) {
            // If the hero is currently wearing an armor, add it to props and armors first.
            // This is to simulate a "change armors" effect
            props.add(equippedArmor);
            armors.add(equippedArmor);
        }

        equippedArmor = temp; // Substitute temp for the current wearing armor (i.e. equippedArmor)

        return true;
    }

    /*
        A hero can choose armors in her/his spell inventory.
     */
    public Armor chooseArmor() {
        if (armors.isEmpty()) {
            NotificationCenter.changeAnArmor(1);
            return null;
        }

        printArmorInventory();
        NotificationCenter.changeAnArmor(2);

        Scanner sc = new Scanner(System.in);
        int choice; // Get the hero's choice of armor

        // Input validation checking
        while (true) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();

                if (choice < 1 || choice > armors.size()) {
                    NotificationCenter.changeAnArmor(3);
                } else {
                    props.remove(armors.get(choice - 1));
                    return armors.remove(choice - 1);
                }
            } else {
                sc.next();
                NotificationCenter.changeAnArmor(4);
            }
        }
    }

    /*
        A hero can print her/his armor inventory.
     */
    public void printArmorInventory() {
        String splitLine = Colors.YELLOW_BG + Colors.BLACK +
                "--><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><-><--" +
                Colors.RESET + "\n";

        NotificationCenter.inventory(5, name);
        System.out.println("Name                    Damage Reduction");
        System.out.print(splitLine);

        int index = 1;

        for (int i = 0; i < props.size(); ++i) {
            // Print all the armors
            if (props.get(i) instanceof Armor) {
                String name = index + ". " + props.get(i).name;
                int reduction = ((Armor) props.get(i)).getDamageReduction();
                ++index;

                System.out.printf("%-28s %-5d", name, reduction);
                System.out.println();
            }
        }
        System.out.println(splitLine);
    }

    /*
        A hero can change an weapon.
     */
    public boolean changeAWeapon() {
        Weapon temp = chooseWeapon();

        if (temp == null) { return false; }

        if (equippedWeapon != null) {
            // If the hero is currently using a weapon, add it to props and weapons first.
            // This is to simulate a "change weapons" effect
            props.add(equippedWeapon);
            weapons.add(equippedWeapon);
        }

        equippedWeapon = temp; // Substitute temp for the current using weapon (i.e. equippedWeapon)

        return true;
    }

    /*
        A hero can go back to her/his nexus at any time.
     */
    public void backToNexus(Map m) {
        // Set the left marker of the original cell to be "  "
        m.getMap()[row][col].leftMarker = "  ";
        m.getMap()[row][col].setMiddle();

        // Update the new row and column values for the hero
        setRow(m.getSize() - 1);
        setCol(startingCol);

        // Set the left marker of the new cell to be the current hero's marker
        m.getMap()[row][col].leftMarker = heroMarker;
        m.getMap()[row][col].setMiddle();
    }

    /*
        The hero can choose to teleport to another cell.
     */
    public boolean teleport(Map m, ArrayList<Monster> monsters) {
        int newRow = -1, newCol = -1; // The row and column that the hero wants to teleport to
        boolean validIndexes = false;
        Scanner sc = new Scanner(System.in);

        NotificationCenter.teleport(1);

        while (!validIndexes) {
            while (true) {
                // Get a valid row index to teleport
                NotificationCenter.teleport(7);

                if (sc.hasNextInt()) {
                    newRow = sc.nextInt();

                    if (newRow < 0 || newRow >= m.getSize()) {
                        NotificationCenter.teleport(2);
                    } else { break; }
                } else {
                    sc.next();
                    NotificationCenter.teleport(3);
                }
            }

            while (true) {
                // Get a valid column index to teleport
                NotificationCenter.teleport(8);

                if (sc.hasNextInt()) {
                    newCol = sc.nextInt();

                    if (newCol < 0 || newCol >= m.getSize()) {
                        NotificationCenter.teleport(2);
                    } else { validIndexes = true; break; } // Successfully got a valid row and column indexes
                } else {
                    sc.next();
                    NotificationCenter.teleport(3);
                }
            }
        }

        if (isInaccessibleCell(m, newRow, newCol)) {
            // The desired cell is an Inaccessible Cell
            NotificationCenter.teleport(4);
        } else if (isSameLane(newCol)) {
            // The desired cell is within the same lane of the current cell
            NotificationCenter.teleport(5);
        } else if (!isValidCell(m, newRow, newCol)) {
            // The desired cell has not been explored yet
            NotificationCenter.teleport(6);
        } else if (isTaken(m, newRow, newCol)) {
            // The desired cell has been taken by another hero
            NotificationCenter.teleport(10);
        } else if (isValidCell(m, newRow, newCol) && isBehindMonsters(m, newRow, newCol, monsters)) {
            // The desired cell has been explored, but it is behind a monster of the same lane
            NotificationCenter.teleport(11);
        } else {
            // A valid cell to teleport
            NotificationCenter.teleport(9);

            // Set the left marker of the original cell to be "  "
            m.getMap()[row][col].leftMarker = "  ";
            m.getMap()[row][col].setMiddle();

            // Since the hero is leaving this cell, the cell should be set to "no heroes" status
            m.getMap()[row][col].setHasHeroes(false);

            // Update the new row and column values for the hero
            setRow(newRow);
            setCol(newCol);

            // Set the left marker of the new cell to be the current hero's marker
            m.getMap()[row][col].leftMarker = heroMarker;
            m.getMap()[row][col].setMiddle();

            // Since the hero is in the new cell, the cell should be set to "has heroes" status
            m.getMap()[row][col].setHasHeroes(true);

            return true;
        }

        return false;
    }

    /*
        Check if a cell is an Inaccessible Cell.
     */
    private boolean isInaccessibleCell(Map m, int newRow, int newCol) {
        return m.getMap()[newRow][newCol] instanceof InaccessibleCell;
    }

    /*
        Check if a new cell is in the same lane of the current cell.
     */
    private boolean isSameLane(int newCol) {
        return Math.abs(newCol - col) <= 1;
    }

    /*
        Check if a cell belongs to the explored cells in that lane.
     */
    private boolean isValidCell(Map m, int newRow, int newCol) {
        int lane = calculateLane(m, newRow, newCol);

        return newRow >= m.getMaxExploredLevels()[lane];
    }

    /*
        Check if a cell has already been taken by another hero.
     */
    private boolean isTaken(Map map, int newRow, int newCol) {
        return !map.getMap()[newRow][newCol].leftMarker.equals("  ");
    }

    /*
        Calculate the lane that a cell belongs to
     */
    private int calculateLane(Map map, int newRow, int newCol) {
        int lane;

        // Calculate the corresponding lane of the teleport cell
        if (newCol <= 1) { lane = 0; }
        else if (map.getSize() - newCol <= 2) { lane = 2; }
        else { lane = 1; }

        return lane;
    }

    /*
        Check if a cell is behind any monsters in that lane.
     */
    private boolean isBehindMonsters(Map map, int newRow, int newCol, ArrayList<Monster> monsters) {
        int lane = calculateLane(map, newRow, newCol);

        for (Monster m : monsters) {
            int monsterLane = calculateLane(map, m.row, m.col);

            if (lane == monsterLane) {
                // Find a monster that is in the same lane as the cell is
                if (newRow < m.row) { return true; } // The cell is beyond the monster
            }
        }

        return false;
    }
}