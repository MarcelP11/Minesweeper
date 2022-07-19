package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.Minesweeper;
import minesweeper.UserInterface;
import minesweeper.core.*;

import static minesweeper.core.Tile.State.OPEN;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    /**
     * Playing field.
     */
    private Field field;
    private String format = "%2s";

    /**
     * Input reader.
     */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Reads line of text from the reader.
     *
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Starts the game.
     *
     * @param field field of mines and clues
     */
    @Override
    public void newGameStarted(Field field) {
        this.field = field;
        this.format = "%" + (1 + String.valueOf(field.getColumnCount()).length()) + "s";

        do {
            update();
            processInput();
            // throw new UnsupportedOperationException("Resolve the game state - winning or loosing condition.");
            if (field.getState() == GameState.SOLVED) {  //ak hrac vyhral hru vypise sa text o vyhre a skonci sa hra
                System.out.println("You win!!!");
                System.exit(0);
            } else if (field.getState() == GameState.FAILED) {  //ak hrac prehral vypise sa text a skonci sa hra
                System.out.println("You jumped on mine!!! You lose.");
                System.exit(0);
            }
        } while (true);
    }

    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        //throw new UnsupportedOperationException("Method update not yet implemented");
        /*
        //moj zapis

        String columnIndex="  ";
        for (int i = 0; i < field.getColumnCount(); i++) {
            columnIndex+=i+" ";
        }
        System.out.println(columnIndex);
        char c= 65;
        for (int i = 0; i < field.getRowCount(); i++) {
            String rowValues=c+" ";
            for (int j = 0; j < field.getColumnCount(); j++) {
                Tile t = field.getTile(i, j);

                if(t.getState() == Tile.State.OPEN) {
                  rowValues += t;
                } else if (t.getState()==Tile.State.MARKED){
                    rowValues+="M ";
                } else if (t.getState()==Tile.State.CLOSED){
                    rowValues+="- ";
                }
            }
            System.out.printf("%s%n",rowValues);
            c++;

         */
        System.out.printf(format, "");
        for (int c = 0; c < field.getColumnCount(); c++) {
            System.out.printf(format, c);
        }
        System.out.println();

        //vypis riadky so zvislo osou na zaciatku
        for (int r = 0; r < field.getRowCount(); r++) {
            System.out.printf(format, (char) (r + 65));
            for (int c = 0; c < field.getColumnCount(); c++) {
                System.out.printf(format, field.getTile(r, c));
            }
            System.out.println();

        }
    }


    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        // throw new UnsupportedOperationException("Method processInput not yet implemented");
        System.out.println("Game instructions: X - game exit, MA1 - mark tile in row A and column 1, OB4 - open tile in row B and column 4");  //vypis informacie pre pouzivatela ako hrat
        String input = readLine();  //nacitanie vstupu od pouzivatela do premennej line
        try {
            handleInput(input);   //zavolam funkciu ktora sa skusa a v ktorej je novy objekt vynimky pri if kde by mala byt chyba
        } catch (WrongFormatException e) {  //ak sa vygeneruje chyba tak ju sem zachytime a v tomto bloku sa vypise na obrazovku obsah spravy chyby
            System.out.println(e.getMessage());;
            processInput(); //ak vyskoci chyba tak chceme nanovo spracovat vstup
        }
    }

    void handleInput(String input) throws WrongFormatException{   //throws WrongFromatException znamena ze sa moze vyskytnut chyba ciza tato klasa
            //Pattern pre OPEN
            Pattern patternOpen = Pattern.compile("^[o]{1}([A-E]{1})([0-4]{1})", Pattern.CASE_INSENSITIVE);
            //triedu Pattern trebalo naimportovat
            Matcher matcherOpen = patternOpen.matcher(input);
            if (matcherOpen.matches()) {
                int row = matcherOpen.group(1).charAt(0) - 'a';
                int column = Integer.parseInt(matcherOpen.group(2));
                field.openTile(row, column);  //zavola sa metoda na otvorenie miny v ktorej je podmienka ak sa natrafi na minu tak hra spadne
            }

            //Pattern pre MARK
            Pattern patternMark = Pattern.compile("^[m]{1}([A-E]{1})([0-4]{1})", Pattern.CASE_INSENSITIVE);
            //triedu Pattern trebalo naimportovat
            Matcher matcherMark = patternMark.matcher(input);
            if (matcherMark.matches()) {
                int row = matcherMark.group(1).charAt(0) - 'a';
                int column = Integer.parseInt(matcherMark.group(2));
                field.markTile(row, column);   //zavola sa metoda na marknutie miny v ktorej je podmienka ak je oznacena sa odznaci a naopak
            }

            //Pattern pre EXIT
            Pattern patternExit = Pattern.compile("[x]{1}", Pattern.CASE_INSENSITIVE);
            //triedu Pattern trebalo naimportovat
            Matcher matcherExit = patternExit.matcher(input);
            if (matcherExit.matches()) {
                System.exit(0);    //prikaz na ukoncenie programu
            }

            //Pattern pre wrong
           // Pattern patternWrong = Pattern.compile("([x]{2,}|[^x,m,o]{1,})|([^m,o]{1}[^A-E]{1,}[^0-4]{1,})", Pattern.CASE_INSENSITIVE);  //matchuje pri zadani ma1
            //Matcher matcherWrong = patternWrong.matcher(input);
            //if (matcherWrong.matches())
                if (!(matcherOpen.matches() || matcherMark.matches() || matcherExit.matches())) {
                    throw new WrongFormatException("Zly vystup");
                    // System.out.println("Wrong input. Try again.");  //ak zada zle vypise sa do konzoly informacia pre uzivatela
                    //readLine();  //znova sa spusti funkcia na citanie vstupu od uzivatela - netreba
                    // }
                }
    }
}

