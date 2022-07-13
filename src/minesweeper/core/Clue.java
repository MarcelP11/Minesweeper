package minesweeper.core;

/**
 * Clue tile.
 */
public class Clue extends Tile {
    private final int value;

    /**
     * Constructor.
     *
     * @param value value of the clue
     */
    public Clue(int value) {

        this.value = value;
    }

    /**
     * Value of the clue.
     */
    public int getValue() {   //pridanie gettera - uloha 1
        return value;
    }

    /* dokoncit
    @Override
    public String toString() {
        return String.valueOf();
    }

     */
}
