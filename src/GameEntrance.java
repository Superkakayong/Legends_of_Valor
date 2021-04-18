/**
 * This class serves as the entrance of the entire project.
 * Only this class has the main() method, and it is very concise!
 */
public class GameEntrance {
    public static void main(String[] args) {
        Runnable playSound = new Sound();
        Runnable legends = new LegendsGame();

        Thread thread1 = new Thread(playSound);
        Thread thread2 = new Thread(legends);

        thread1.start();
        thread2.start();
    }
}
