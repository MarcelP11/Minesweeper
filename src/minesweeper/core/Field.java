package minesweeper.core;

import java.util.Random;

/**
 * Field represents playing field and game logic.
 */
public class Field {


    /**
     * Playing field tiles.
     */
    private final Tile[][] tiles;

    /**
     * Field row count. Rows are indexed from 0 to (rowCount - 1).
     */
    private final int rowCount;

    /**
     * Column count. Columns are indexed from 0 to (columnCount - 1).
     */
    private final int columnCount;

    /**
     * Mine count.
     */
    private final int mineCount;

    /**
     * Game state.
     */
    private GameState state = GameState.PLAYING;

    /**
     * Constructor.
     *
     * @param rowCount    row count
     * @param columnCount column count
     * @param mineCount   mine count
     */
    public Field(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
        tiles = new Tile[rowCount][columnCount];

        //generate the field content
        generate();  //funkcia sa generuje priamo v konstruktore

//vypis
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] instanceof Mine) {  //ak je na danej dlazdici Mina tak sa vypise x
                    System.out.print("x ");
                } else if (tiles[i][j] instanceof Clue) {
                    System.out.print(countAdjacentMines(i, j) + " ");  //ak je na danej dlazdici Cle tak sa vypise cislo ktore vrati metoda countAdjacentMines
                }

            }
            System.out.println();
        }
    }

    //metoda vrati dlazdicu podla zadaneho riadku a stlpca - uloha 3
    public Tile getTile(int row, int column) {
        Tile tile = tiles[row][column];
        return tile;
    }

    /**
     * Opens tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void openTile(int row, int column) {
        Tile tile = tiles[row][column];
        if (tile.getState() == Tile.State.CLOSED) {
            tile.setState(Tile.State.OPEN);
            if (tile instanceof Mine) {
                setState(GameState.FAILED);
                return;
            }

            if (isSolved()) {
                setState(GameState.SOLVED);
                return;
            }
        }
    }

    /**
     * Marks tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void markTile(int row, int column) {
        //throw new UnsupportedOperationException("Method markTile not yet implemented");
        Tile tile = tiles[row][column];
        if (tile.getState() == Tile.State.CLOSED) {
            tile.setState(Tile.State.MARKED);
        } else if (tile.getState() == Tile.State.CLOSED) {
            tile.setState(Tile.State.CLOSED);
        }
    }

    /**
     * Generates playing field.
     */
    private void generate() {
        //throw new UnsupportedOperationException("Method generate not yet implemented");
        System.out.println("kontrolny vypis");
        Random randomNumber = new Random();  //novy objekt triedy Random

        // naplnime ich minami
        for (int i = 0; i < mineCount; ) {   //ak sme v rovnakej triede tak nemusime pouzivat getter ale rovno privatnu premennu
            int rowRnd = randomNumber.nextInt(getRowCount());  //vytvorenie random riadku
            int columnRnd = randomNumber.nextInt(getColumnCount());  //vytvorenie random stlpca
            if (tiles[rowRnd][columnRnd] == null) {  //ak je na danom poli hodnota null co je pri vytovreni pola tak vytvori na danej pozicii objekt typu Mine
                tiles[rowRnd][columnRnd] = new Mine();
                i++;  //az po vykonani zvysi pocitadlo aby ak je na danej pozicii mina tak sa vykona slucka bez pridania
            }
        }

        //doplnime zvysne polia ktore su null, typmi Clue a parameter je vratena hodnota metody countAdjacent Mines
        for (int i = 0; i < rowCount; i++) {  //
            for (int j = 0; j < columnCount; j++) {
                if (tiles[i][j] == null) {
                    tiles[i][j] = new Clue(countAdjacentMines(i, j));
                }
            }
        }
        System.out.println();
    }

    private int getNumberOf() {
        int countNumber = 0;
        for (int i = 0; i < rowCount; i++) {  //
            for (int j = 0; j < columnCount; j++) {
                if (tiles[i][j].getState() == Tile.State.OPEN) {
                    countNumber++;
                }
            }
        }
        return countNumber;
    }

    /**
     * Returns true if game is solved, false otherwise.
     *
     * @return true if game is solved, false otherwise
     */
    private boolean isSolved() {
        //throw new UnsupportedOperationException("Method isSolved not yet implemented");
        boolean solveStatus = false;
        if ((rowCount * columnCount) - getNumberOf() == mineCount) {
            solveStatus = true;
        }
        return solveStatus;
    }

    /**
     * Returns number of adjacent mines for a tile at specified position in the field.
     *
     * @param row    row number.
     * @param column column number.
     * @return number of adjacent mines.
     */
    private int countAdjacentMines(int row, int column) {
        int count = 0;
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            int actRow = row + rowOffset;
            if (actRow >= 0 && actRow < getRowCount()) {
                for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                    int actColumn = column + columnOffset;
                    if (actColumn >= 0 && actColumn < getColumnCount()) {
                        if (tiles[actRow][actColumn] instanceof Mine) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }


    public int getRowCount() {  //getter pre RowCount
        return rowCount;
    }


    public int getColumnCount() {  //getter pre ColumnCount
        return columnCount;
    }


    public int getMineCount() {  //getter pre MineCount
        return mineCount;
    }


    public GameState getState() { //getter pre State  - uloha 2
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
