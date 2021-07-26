<h1 align = "center">Legends_of_Valor </h1>

---

> # Background
>
> This is a terminal-based and **Harry Potter Style** RPG game focusing on **Java**, **object-oriented design**, **design patterns** and **teamwork**. There are **46** classes in total, which renders the whole project of high **scalability** and **extendability!** 
>
> Project Developers: **Dayong Wu** and **Xiongxin Zeng**. 

---

## Examples of Gaming Experience

<img src="https://github.com/Superkakayong/Trivia/blob/master/Project_Images/Legends/Legends.jpeg" alt="image-20210210200440573" style="zoom:90%; float: left" />

<img src="https://github.com/Superkakayong/Trivia/blob/master/Project_Images/Legends/Map.jpeg" alt="image-20210210200440573" style="zoom:90%; float: left" />

## General workflow of the game

1. After running the project, the **sound** will automatically play.
2. Ask the players if they want to start or quit the game.
3. If the players choose to play, the game will display welcome messages and relevant instructions.
4. Let the players select heroes for every hero headcount in the team.
5. Enter the game map. The players can use **w/a/s/d/q/i/o/p/u/f/c/t/b/v/m** to perform corresponding operations.
6. The hero team can buy/sell props in her/his nexus (i.e. market).
7. The restricted cells are not allowed to be visited by the hero team.
8. Other types of cells can buff specific attributes of the heroes.
9. The hero team will fight the monster squad when they confront. A fight contains multiple rounds.
10. After a round, if the game if not finished, the dead heroes will respawn in their initial nexuses.
11. The game will end if anyone (a hero or a monster) has reached the nexus of her/his opponent nexus, or all heroes are dead.
12. After every 8 rounds, there will be 3 new monsters spawn in the monster nexus.

---

## Classes of the project (Recommended Viewing Order)

### 1. GameEntrance.java

- This class serves as the entrance of the entire project.
- Only this class has the main() method.

### 2. MultiThreadsStarter.java

- This class serves as a starter (initiator) of all the threads that will be involved in this class.

### 3. NotificationCenter.java

- This class serves as a center that stores all the messages during the game.
- So we don't need to write a lot of similar print statements in other classes!

### 4. Colors.java

- This class stores all the colors that will be used in this project.

### 5. Trade.java

- This interface guarantees the ability of a class that implements it can be traded (i.e. can be bought and sold).

###  6. Props.Java

- This class represents all props in the game.
- It is the super class of all kinds of props.
- It inherits from the **Trade** interface to guarantee that a prop can be bought and sold.

### 7. CauseDamage.java

- This interface serves as a contract to the user that if a class implements it, the class should be able to inflict other "classes" (i.e. players).

###  8. Weapon.java

- This class represents the weapon, which is a type of props.
- Since a weapon can be used to inflict the enemies, this class also implements the CauseDamage interface.

###  9. Armor.java

* This class manages the properties of an armor.
 * It also extends from the class Prop since an armor is a prop.

###  10. Potion.java

- This class represents the potion, which is a type of props.

###  11. Spell.java

* This class represents the potion, which is a type of props.
 * Since a spell can be used to inflict the enemies, this class also implements the CauseDamage interface.

###  12. FireSpell.java

- This class stands for the fire spell, which is a specific type of spells.

###  13. IceSpell.java

- This class stands for the ice spell, which is a specific type of spells.

###  14. LightningSpell.java

- This class stands for the lightning spell, which is a specific type of spells.

### 15. Role.java

- This class represents a role in the RPG games.
- In this game, both Hero and Monster have inherited from this class.

### 16. Fight.java

 * This interface serves as a contract to the user that if a class implements it, the class should be able to fight.
 * In this project, both the Hero class and Monster class have implemented this interface.

### 17. Monster.java

* This class manages all the common properties of all types of monsters.
 * It inherits from the Role class, and implements the Fight interface.
 * It is the super class for all specific monster classes.

### 18. Dragon.java

* This class stands for a dragon monster.
 * It inherits from the monster class.

### 19. Exoskeleton.java

*  This class stands for a exoskeleton monster.
 *  It inherits from the monster class.

### 20. Spirit.java

* This class stands for a spirit monster.
 * It inherits from the monster class.

### 21. MonsterList.java

* This class stores all the monsters in the game.

### 22. Hero.java

 * This class manages all the common properties of all types of heroes.
 * It inherits from the Role class, and implements the Fight interface.
 * It is the super class for all specific hero classes.

### 23. Paladin.java

* This class stands for the hero Paladin, which is a hero type.

### 24. Sorcerer.java

* This class stands for the hero Sorcerer, which is a hero type.

### 25. Warrior.java

* This class stands for the hero Warrior, which is a hero type.

### 26. HeroList.java

- This class stores all the heroes in this game.

### 27. HeroTeam.java

- This class manages the operations of a team of heroes during the game.

### 28. LevelUpBehavior.java

* Since the algorithms for the level up behavior of different types of heroes vary, I have implemented the Strategy Pattern to facilitate the level up operations for heroes.

### 29. PaladinLevelUp.java

 * This class holds the algorithms for the level up behavior of hero Paladin.
 * Implementation of the Strategy Pattern!

### 30. SorcererLevelUp.java

* This class holds the algorithms for the level up behavior of hero Sorcerer.
 * Implementation of the Strategy Pattern!

### 31. WarriorLevelUp.java

* This class holds the algorithms for the level up behavior of hero Warrior.
 * Implementation of the Strategy Pattern!

### 32. Map.java

- This class represents the game map.

### 33. Cell.java

- This class represents a map cell of the map.

### 34. BushCell.java

- This class represents a bush cell, which belongs to a specific type of map cells.

 * Therefore, it inherits from the Cell class.

### 35. CaveCell.java

* This class represents a cave cell, which belongs to a specific type of map cells.
 * Therefore, it inherits from the Cell class.

### 36. InaccessibleCell.java

- This class represents an inaccessible cell, which belongs to a specific type of map cells.

 * Therefore, it inherits from the Cell class.

### 37. NexusCell.java

 * This class represents a nexus cell, which belongs to a specific type of map cells.
 * Therefore, it inherits from the Cell class.

### 38. HeroNexusCell.java

- This class represents a hero nexus cell, which belongs to a specific type of nexus cells.
 * Therefore, it inherits from the NexusCell class.

### 39. MonsterNexusCell.java

- This class represents a monster nexus cell, which belongs to a specific type of nexus cells.
 - Therefore, it inherits from the NexusCell class.

### 40. PlainCell.java

- This class represents a plain cell, which belongs to a specific type of map cells.
- Therefore, it inherits from the Cell class.

### 41. KoulouCell.java

- This class represents a koulou cell, which belongs to a specific type of map cells.
- Therefore, it inherits from the Cell class.

### 42. Market.java

- This class stores all the props in the market of the game.

### 43. Game.java

- This class is the top super class for all types of games.

### 44. RPGGame.java

 * This class is the super class of all RPG games.
 * It also inherits from the Game class, which is the top super class of all games.

### 45. LegendsGame.java

* This is the main class of the project!
 * It controls all the game logics.
 * It is a role playing game, therefore it inherits from the RPGGame class.
 * It also implements the Runnable interface since this class is a **[thread]** in the project.
 * We also have another **[thread]** in the project called **Sound.java**.
 * There are also "**MORE THAN ENOUGH**" comments in this class to help you understand every piece of the logics!
 * In fact, you will find very detailed comments in every class of the project!

### 46. Sound.java

- This class contains the functions of playing music when playing the game.
 * It implements the Runnable interface since this class is a **[thread]** in the project.
 * We also have another **[thread]** in the project called **LegendsGame.java**.

---

## Instructions on how to compile and run the program via Mac Terminal

1. Create a new folder on your MacBook and copy-paste all the 46 classes mentioned above to that folder.
2. Go to [Sound File (Google Drive)](https://drive.google.com/file/d/1TLhkv_ooiUfgXfN-TFR_48D0A_z_GIRt/view?usp=sharing) or [Sound File (Baidu Netdisk)](https://pan.baidu.com/s/1VKMIHvAZfkyZW97EWFUgHQ) (password: **kt0n**) to download the sound file, and put it into the **same folder** as mentioned in **Step 1**.
3. Open Terminal and type "cd " (notice that there is a  **whitespace** after "cd" !).
4. Then **drag the folder** to the terminal so that Mac can automatically complete the directory of that folder for you.
5. Press "Enter". Now you are inside the newly created folder.
6. Type "javac GameEntrance.java" in the terminal.
7. Type "java GameEntrance" in the terminal.
8. Now you should be able to play the game through Mac Terminal.
9. Please note that you should have a **JDK** installed in you MacBook with version at least **1.8**. 
10. Since I am using **[ANSI escape code/sequence]** to display colors, there are chances that **Windows-based** machines can fail to render the colors. However, if you are using a Unix-based machine, things will work just fine ^.

---

## Instructions on how to compile and run the program in the IDE Console

1. Create a new JAVA project in IntelliJ IDEA CE.
2. Copy-paste all the 46 classes mentioned above to the **/src** folder.
3. Go to [Sound File (Google Drive)](https://drive.google.com/file/d/1TLhkv_ooiUfgXfN-TFR_48D0A_z_GIRt/view?usp=sharing) or [Sound File (Baidu Netdisk)](https://pan.baidu.com/s/1VKMIHvAZfkyZW97EWFUgHQ) (password: **kt0n**) to download the sound file, and put it into the **root folder** of the project mentioned in **Step 1** (i.e. put the sound file **1 layer** **outside** the **/src** folder).
4. Click the "Run" button or press Control+R to run the project.
5. Please note that you should have a **JDK** installed in you MacBook with version at least **1.8**. 
6. Since I am using **[ANSI escape code/sequence]** to display colors, there are chances that **Windows-based** machines can fail to render the colors. However, if you are using a Unix-based machine, things will work just fine ^.

---

## * Highlights of the Project

### 1. Colorful and lively terminal (console) experience

- We have implemented [ANSI escape code/sequence] to display a lot of messages and words during the game.

### 2. Tons of ASCII Arts

- We have implemented a lot of ASCII arts during the game to make it more graphic and vivid.

### 3. Use of Design Pattern

- Since We found that the algorithms for the level up behavior of different types of heroes vary, We have implemented the **Strategy Pattern** to facilitate the level up operations for heroes.

### 4. Sound

- Since this is a **Harry Potter style** game, we decided to embed one of the most famous **theme songs** of **Harry Potter** as the background music of our project, namely **Hedwig's Theme!!!**
- The sound will automatically play once you run the project (as long as the sound file is placed correctly), and it will self-loop again once it reaches its end.

### 5. Proper code format

- Proper indentations, **"MORE THAN ENOUGH"** comments, etc.

---

## Things Worth Noting

### 1. Rules about endding the game

- If anyone touches her/his opponent's nexus, the game ends.
- If all heroes are dead, the game ends.
- If any hero chooses to quit, the game ends.

### 2. Rules about heroes statistics after each fight

- After a fight, if the hero survives, (s)he will regain 10% of their HP and 10% of her/his mana right.
- After a fight,  if the hero survives, (s)he will  receive **100 * hero_level**  money bonus and **5 * hero_level** EXP bonus.
- After a fight, if not all heroes are dead, the HP and mana of all **DEAD** heroes will respawn in their corresponding nexuses and be reset to **half** of their initial values, but they will receive no money bonus or EXP bonus. This is done right after a hero is dead.

### 3. Rules about Level Up

- The heroes can level up if they have accumulated **10 * hero_level** EXP bonus.
- Therefore, a hero can level up if (s)he can survive for **two** complete fights (because **5 * hero_level** EXP bonus for one successful fight).
- The **MAXIMUM LEVEL** of any hero is **10**. Therefore, once a hero reaches level 10, (s)he cannot level up anymore.

### 4. Rules about the Buffs of Different Types of Cells

- The printing result of the heroes' stats will **not** change regardless of which types of cell are the heroes currently in (i.e. the **printTeamMembers()** method will display the **same result** no matter the team members are in **Plain Cells** or **Bush Cells**).
- However, the buff is functioning **implicitly**, in other words, when the hero is fighting a monster, her/his corresponding stats will be enhanced. It is just that it would not show when you print the hero's stats.

### 5. About Who Fights First

- Typically, it is the hero who gets the first turn to fight if a monster is within range.
- However, once the monster is within range to fight and the hero does not choose to fight (e.g. move to right instead), the monster will have the opportunity to attack the hero.

### 6. Additional Sound Files

- Besides **Hedwig's Theme**, we also provide several other sound options for you!
- They are all in [Additional Sound Files (Google Drive)](https://drive.google.com/drive/folders/1--bvDKvoDczm2OHPh_hT-hl0Fc7iKNlC?usp=sharing) or [Additional Sound Files (Baidu Netdisk)](https://pan.baidu.com/s/1fMfmfCwCBE-4uYLBskZ0JA) (password: icfk). Feel free to download this folder!
- Once you have downloaded this folder, pick a song and **rename** it as "**Sound.wav**".
- Then put it into the aforementioned directory (it depends on whether you are running the project via an **IDE** or the **Mac Terminal**).
- **Enjoy The Rest~**

---

