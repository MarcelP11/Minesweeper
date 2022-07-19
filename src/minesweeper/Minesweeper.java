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
    private long startMillis;



    private static Minesweeper instance;

    /**
     * Constructor.
     */
    private Minesweeper() {
        startMillis=System.currentTimeMillis();
        userInterface = new ConsoleUI();
        System.out.println("Hello " + System.getProperty("user.name"));  //hodnota user.name do uvodzoviek
        Field field = new Field(5, 5, 3);
        userInterface.newGameStarted(field);

    }

    public int getPlayingSeconds(){
        return ((int)(System.currentTimeMillis()-startMillis)/1000);
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        new Minesweeper();
    }
    public static Minesweeper getInstance() {
        return instance;
    }
}
