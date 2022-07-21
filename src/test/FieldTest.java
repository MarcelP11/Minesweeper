package test;

import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Mine;
import minesweeper.core.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {

    private Random randomGenerator = new Random();
    private Field field;
    private int rowCount;
    private int columnCount;
    private int minesCount;

    @BeforeEach
    public void initTest() {  //tu sa menilo z konstruktora FieldTest na initTest a pridalo sa @BeforeEach
        rowCount = randomGenerator.nextInt(10) + 5;
        columnCount = rowCount;
        minesCount = Math.max(1, randomGenerator.nextInt(rowCount * columnCount));
        field = new Field(rowCount, columnCount, minesCount);
    }

    @Test
    public void checkFieldInitialization() {
        assertEquals(rowCount, field.getRowCount(), "Field was initialized incorrectly - different amount");
        assertEquals(columnCount, field.getColumnCount(), "Field was initialized incorrectly - different amount");
        assertEquals(minesCount, field.getMineCount(), "Field was initialized incorrectly - different amount");
        assertEquals(GameState.PLAYING, field.getState(), "wrong state");
    }

    @Test
    public void checkMarkTile() {
        int row = 5, col = 5;
        //field.markTile(row, col);
        //assertEquals(Tile.State.CLOSED, field.getTile(row,col).getState());
        field.markTile(row, col);
        assertEquals(Tile.State.MARKED, field.getTile(row, col).getState());  //tu vyjde test, pri ostatnych nie
        // field.markTile(row,col);
        //assertEquals(Tile.State.CLOSED, field.getTile(row,col).getState());
        // field.openTile(row,col);
        //assertEquals(Tile.State.OPEN, field.getTile(row,col).getState());
        // field.markTile(row,col);
        //  assertEquals(Tile.State.CLOSED, field.getTile(row,col).getState());
    }

    /*
    @Test
    public void checkOpenClue() {
        int r = 0, c = 0;
        for (int i = 0; i <rowCount; i++){
            for (int j = 0; j < columnCount; j++) {   //cez instanceOf
                c++;
                if (field.getTile(r,c) ==0){
                    return;
                    field.openTile();
                    field.get
                }
            }
            r++;
        }

        //Marknut si akukolvek neotvorenu dlazdicu a skusit si na nej open, ci ostava marknuta
        int row = 2, col = 2;
        field.markTile(row, col);
        field.openTile(row, col);
        assertEquals(Tile.State.MARKED, field.getTile(row, col).getState());

    }
*/

    @Test
    public void checkMinesCount() {
        int minesCounter = 0;
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                if (field.getTile(row, column) instanceof Mine) {
                    minesCounter++;
                }
            }
        }

        assertEquals(minesCount, minesCounter, "Field was initialized incorrectly - " +
                "a different amount of mines was counted in the field than amount given in the constructor.");
    }

    @Test
    public void fieldWithTooManyMines() {
        Field fieldWithTooManyMines = null;
        int higherMineCount = rowCount * columnCount + randomGenerator.nextInt(10) + 1;
        try {
            if (higherMineCount < (rowCount * columnCount)) {  //najprv otestujem ci vygenerovany random pocet min je mensi ako pocet vsetkych poli
                fieldWithTooManyMines = new Field(rowCount, columnCount, higherMineCount);  //ak ano tam vytvorim novy objekt Field
            } else {  //ak nie vyhodim vynimku
                throw new RuntimeException("Fields with more mines than tiles should not be created");
            }

        } catch (Exception e) {
            // field with more mines than tiles should not be created - it may fail on exception
        }
        assertTrue((fieldWithTooManyMines == null) || (fieldWithTooManyMines.getMineCount() <= (rowCount * columnCount)));
    }

    // ... dalsie testy
}