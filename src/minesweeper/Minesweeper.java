package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;

/**
 * Main application class.
 */
public class Minesweeper {
    /** User interface. */
    private ConsoleUI UserInterface;
 
    /**
     * Constructor.
     */
    private Minesweeper() {
        UserInterface = new ConsoleUI();
        
        Field field = new Field(5, 5, 10);
        UserInterface.newGameStarted(field);
    }

    /**
     * Main method.
     * @param args arguments
     */
    public static void main(String[] args) {
        new Minesweeper();
        System.out.println("Hello "+ System.getProperty("user.name"));  //hodnota user.name do uvodzoviek
        Field novePole = new Field(5,3,3);
    }


}
