/**
 * This class is the top super class for all types of games.
 */
public abstract class Game {
    public Game() {}

    /*
        A game can be played.
     */
    public abstract void play();

    /*
        A game can be quited.
     */
    public abstract void quit();
}
