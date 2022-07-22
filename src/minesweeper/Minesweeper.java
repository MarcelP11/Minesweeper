package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;
import minesweeper.core.GameState;

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
    private UserInterface userInterface;
    private long startMillis = System.currentTimeMillis();


    private BestTimes bestTimes = new BestTimes();
    private static Minesweeper instance;

    private Settings setting;

    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    private String readLine() {  //metoda pre vstup od pouzivatela
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }
    public Settings getSetting() {
        return setting;
    }

    public void setSetting(Settings setting) {
        this.setting = setting;
    }

    public static Minesweeper getInstance() {
        if (instance == null) {
            new Minesweeper();
        }
        return instance;
    }

public void setLevel(){//nastavenie urovne zo vstupu
    System.out.println("Zvol si uroven: 1 - BEGINNER, 2 - INTERMEDIATE, 3 - EXPERT");
    String inputLevel = readLine();
        switch (inputLevel){
            case "1": setting=Settings.BEGINNER;  //ak je jednotka tak sa nastavi BEGINNER atd
                    break;
            case "2": setting=Settings.INTERMEDIATE;
                break;
            case "3": setting=Settings.EXPERT;
                break;
            default: setting=getSetting();  //ak sa nezhoduje vstup ani s jednym tak ostane aktualna hodnota
        }

    }
    /**
     * Constructor.
     */
    private Minesweeper() {
        instance = this;  //singleton
//pridany nejaky hraci na kontrolu ci porovnava
        bestTimes.addPlayerTime("jano", 10);
        bestTimes.addPlayerTime("peter", 1000000000);
        setting = Settings.load();  //premenna setting bude mat navratovu hodnotu metody load v triede Settings
        setLevel();  //spusti sa metoda pre nastavenie levelu od pouzivatela
        userInterface = new ConsoleUI();
        System.out.println("Hello " + System.getProperty("user.name"));  //hodnota user.name do uvodzoviek
        Field field = new Field(setting.getRowCount(), setting.getColumnCount(), setting.getMineCount());  //nastavime hodnoty pola podla toho co je v nastaveni
        userInterface.newGameStarted(field);
        if (field.getState() == GameState.SOLVED) {
            bestTimes.addPlayerTime(System.getProperty("user.name"), getPlayingSeconds());  //prida hraca s menom a sekundami
        }
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
