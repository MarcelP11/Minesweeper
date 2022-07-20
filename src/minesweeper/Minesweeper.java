package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;

/**
 * Main application class.
 */
public class Minesweeper {
    /**
     * User interface.
     */
    private UserInterface userInterface;
    private long startMillis = System.currentTimeMillis();


    private BestTimes bestTimes = new BestTimes();
    private static Minesweeper instance;

    public static Minesweeper getInstance() {
        if (instance == null) {
            new Minesweeper();
        }
        return instance;
    }

    /**
     * Constructor.
     */
    private Minesweeper() {
        instance = this;  //singleton
        userInterface = new ConsoleUI();
        System.out.println("Hello " + System.getProperty("user.name"));  //hodnota user.name do uvodzoviek
        Field field = new Field(5, 5, 3);
        userInterface.newGameStarted(field);
        bestTimes.addPlayerTime(System.getProperty("user.name"),getPlayingSeconds());  //prida hraca s menom a sekundami
        System.out.println(bestTimes.toString());  //vypise tabulku
    }

    public int getPlayingSeconds() {
        return ((int) (System.currentTimeMillis() - startMillis) / 1000);
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        new Minesweeper();
    }

}
