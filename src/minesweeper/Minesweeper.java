package minesweeper;

import minesweeper.consoleui.ConsoleUI;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Main application class.
 */
public class Minesweeper {
    /**
     * User interface.
     */



    private static Minesweeper instance;



    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    private String readLine() {  //metoda pre vstup od pouzivatela
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    /*
    public Settings getSetting() {
        return setting;
    }

    public void setSetting(Settings setting) {
        this.setting = setting;
        this.setting.save();
    }
*/
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
//pridany nejaky hraci na kontrolu ci porovnava

        //setLevel();  //spusti sa metoda pre nastavenie levelu od pouzivatela, presunute do consoleUI
        //setting.save();  //musi sa ulozit stav
        final UserInterface userInterface = new ConsoleUI();
        //System.out.println("Hello " + System.getProperty("user.name"));  //hodnota user.name do uvodzoviek
        userInterface.play();
       // if (field.getState() == GameState.SOLVED) {
        //    bestTimes.addPlayerTime(System.getProperty("user.name"), getPlayingSeconds());  //prida hraca s menom a sekundami
        //}

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
