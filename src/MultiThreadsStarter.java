/**
 * This class serves as a starter (initiator) of all the threads that will be involved in this class.
 */
public class MultiThreadsStarter {
    public MultiThreadsStarter() {}

    /*
        Initialize and start all the threads.
     */
    public void initializeThreads() {
        Runnable playSound = new Sound();
        Runnable legends = new LegendsGame();

        Thread thread1 = new Thread(playSound); // A thread that controls SOUND playing
        Thread thread2 = new Thread(legends); // A thread that controls GAME playing

        thread1.start();
        thread2.start();
    }
}
