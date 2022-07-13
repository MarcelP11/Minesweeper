package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import minesweeper.UserInterface;
import minesweeper.core.Field;
import minesweeper.core.Mine;
import minesweeper.core.Tile;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    /** Playing field. */
    private Field field;
    
    /** Input reader. */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Reads line of text from the reader.
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
     * @param field field of mines and clues
     */
    @Override
    public void newGameStarted(Field field) {
        this.field = field;
        do {
            update();
            processInput();
            throw new UnsupportedOperationException("Resolve the game state - winning or loosing condition.");
        } while(true);
    }
    
    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        //throw new UnsupportedOperationException("Method update not yet implemented");
        String columnIndex="  ";
        for (int i = 0; i < field.getColumnCount(); i++) {
            columnIndex+=i+" ";
        }
        System.out.println(columnIndex);
        char c= 6+64;
        for (int i = 0; i < field.getRowCount(); i++) {
            String rowValues=c+" ";
            for (int j = 0; j < field.getColumnCount(); j++) {
                (if field.getTile(i,j).getState() == Tile.State.OPEN && field.getTile(i,j)= instanceof Mine ){
                    rowValues+="X ";
                }
                }

            }

        }



    
    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        throw new UnsupportedOperationException("Method processInput not yet implemented");
    }
}
