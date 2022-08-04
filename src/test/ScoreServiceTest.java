package test;

import entity.Score;
import org.junit.jupiter.api.Test;
import service.ScoreService;
import service.ScoreServiceFile;
import service.ScoreServiceJDBC;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class ScoreServiceTest {
//private ScoreService scoreService=new ScoreServiceJDBC();  //vytvorime novu instanciu objektu scoreserviceJDBC
    private ScoreService scoreService=new ScoreServiceFile();

    @Test
    public void testReset(){

        scoreService.addScore(new Score("Minesweeper","Peter", 10, new Date())); //pridame jeden zaznam pre test ci metoda reset maze zaznamy
        scoreService.reset();
        assertEquals(0, scoreService.getBestScores("Minesweeper").size());  //porovnavame pocet zaznamov po resete, ocakavana hodnota 0,
        //dalej ziskame dlzku Listu ktory vrati metoda getBestScores
    }

    @Test
    public void testAddScore(){
        scoreService.reset();   //najprv vyresetujeme celu tabulku pred pridanim
        var date = new Date();  //vytovrime datum aby sme ho potom mohli pouzit v teste
        scoreService.addScore(new Score("Minesweeper","Peter", 10, date));  //pridame zaznam

        var scores = scoreService.getBestScores("Minesweeper");   //do premennej ulozime zoznam zaznamov
        //prve co porovnavame je pocet zaznamov
        assertEquals(1,scores.size());

        //dalsie co porovnavame  je co sme tam vlozili
        assertEquals("Minesweeper",scores.get(0).getGame());
        assertEquals("Peter",scores.get(0).getUsername());
        assertEquals(10,scores.get(0).getPoints());
        assertEquals(date,scores.get(0).getPlayedOn());
    }

    @Test
    public void testGetBestScores() {
        scoreService.reset();
        var date = new Date();
        scoreService.addScore(new Score("minesweeper", "Peto", 140, date));
        scoreService.addScore(new Score("minesweeper", "Katka", 150, date));
        scoreService.addScore(new Score("tiles", "Zuzka", 290, date));
        scoreService.addScore(new Score("minesweeper", "Jergus", 100, date));

        var scores = scoreService.getBestScores("minesweeper");
//testujeme ci je spravny pocet zaznamov

        assertEquals(3, scores.size());
//nasledne ci su tam spravne zaznamy
        assertEquals("minesweeper", scores.get(0).getGame());
        assertEquals("Katka", scores.get(0).getUsername());
        assertEquals(150, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedOn());

        assertEquals("minesweeper", scores.get(1).getGame());
        assertEquals("Peto", scores.get(1).getUsername());
        assertEquals(140, scores.get(1).getPoints());
        assertEquals(date, scores.get(1).getPlayedOn());

        assertEquals("minesweeper", scores.get(2).getGame());
        assertEquals("Jergus", scores.get(2).getUsername());
        assertEquals(100, scores.get(2).getPoints());
        assertEquals(date, scores.get(2).getPlayedOn());
    }

}
