package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entity.Comment;
import entity.Rating;
import entity.Score;
import minesweeper.Minesweeper;
import minesweeper.UserInterface;
import minesweeper.core.*;
import service.*;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    /**
     * Playing field.
     */
    private Field field;
    private String format = "%2s";

    /*
    name of player
     */
    private String userName = "";

    /**
     * Input reader.
     */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Reads line of text from the reader.
     *
     * @return line as a string
     */

    private Settings setting;
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
        int gameScore = 0;
        this.field = field;
        processInputUsername();
//     System.out.println("Please enter your name:");
//        userName = readLine();
        System.out.println("Select difficult level: 1 - BEGINNER, 2 - INTERMEDIATE, 3 - EXPERT");
        String level = readLine();
        if (level != null && !level.equals("")) {
            try {
                int intLevel = Integer.parseInt(level);
                Settings s = switch (intLevel) {
                    case 2 -> Settings.INTERMEDIATE;
                    case 3 -> Settings.EXPERT;
                    default -> Settings.BEGINNER;
                };
                this.setting = s;
                this.setting.save();
                this.field = new Field(s.getRowCount(), s.getColumnCount(), s.getMineCount());
            } catch (NumberFormatException e) {
                //empty naschval
            }
        }
        this.format = "%" + (1 + String.valueOf(field.getColumnCount()).length()) + "s";


        do {
            update();
            processInput();
            var fieldState = this.field.getState();
            // throw new UnsupportedOperationException("Resolve the game state - winning or loosing condition.");
            if (fieldState == GameState.SOLVED) {  //ak hrac vyhral hru vypise sa text o vyhre a skonci sa hra
                gameScore = this.field.getScore();
                System.out.println(userName + " ,you win!!! Your score is: " + gameScore + ".");
                break;
            } else if (fieldState == GameState.FAILED) {  //ak hrac prehral vypise sa text a skonci sa hra
                System.out.println(userName + ", you jumped on mine!!! You lose. Your score is " + gameScore + ".");
                break;
            }
        } while (true);
        Score player_score = new Score("Minesweeper", userName, gameScore, new Date());  //vytvorenie noveho objektu score
        ScoreService scoreService = new ScoreServiceJDBC();  //vytvorenie noveho objektu ScoreServiceJDBC
        scoreService.addScore(player_score); //pridanie casu
        var scores = scoreService.getBestScores("Minesweeper");
        System.out.printf("\nTable of best players:\n%s\n\n", scores);  //vypis skore
        processInputComment();
//        System.out.println("Please write your comment:");
//        var userComment = readLine();
//        Comment player_comment = new Comment("Minesweeper", userName, userComment, new Date());
//        CommentService commentService = new CommentServiceJDBC();
//        commentService.addComment(player_comment);
//        var comments = commentService.getComments("Minesweeper");
//        System.out.println(comments);
        processInputRate();
//        System.out.println("Please rate the game from 1 to 5:");
//        var userRating = Integer.parseInt((readLine()));
//        Rating player_rating = new Rating("Minesweeper", userName, userRating, new Date());
//        RatingService ratingService = new RatingServiceJDBC();
//        ratingService.setRating(player_rating);
//        var averageRating = ratingService.getAverageRating("Minesweeper");
//        System.out.println("Thank you for your rating. Average rating of game Minesweeper is: " + averageRating);


        System.exit(0);
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
        System.out.printf("Cas hrania: %d s%n", field.getPlayTimeInSeconds());
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
        } catch (
                WrongFormatException e) {  //ak sa vygeneruje chyba tak ju sem zachytime a v tomto bloku sa vypise na obrazovku obsah spravy chyby
            System.out.println(e.getMessage());
            processInput(); //ak vyskoci chyba tak chceme nanovo spracovat vstup
        }
    }

    void handleInput(String input) throws WrongFormatException {   //throws WrongFromatException znamena ze sa moze vyskytnut chyba ciza tato klasa
        //Pattern pre OPEN
        Pattern patternOpen = Pattern.compile("^[o]{1}([A-I]{1})([0-9]{1})", Pattern.CASE_INSENSITIVE);
        //triedu Pattern trebalo naimportovat
        Matcher matcherOpen = patternOpen.matcher(input);
        if (matcherOpen.matches()) {
            int row = matcherOpen.group(1).charAt(0) - 'a';
            int column = Integer.parseInt(matcherOpen.group(2));
            field.openTile(row, column);  //zavola sa metoda na otvorenie miny v ktorej je podmienka ak sa natrafi na minu tak hra spadne
        }

        //Pattern pre MARK
        Pattern patternMark = Pattern.compile("^[m]{1}([A-I]{1})([0-9]{1})", Pattern.CASE_INSENSITIVE);
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

    public void handleInputComment(String inputComment) throws WrongFormatException{
        Pattern pattern = Pattern.compile(".{1,64}");
        Matcher matcher = pattern.matcher(inputComment);
        if (matcher.matches()){
            Comment player_comment = new Comment("Minesweeper", userName, inputComment, new Date());
            CommentService commentService = new CommentServiceJDBC();
            commentService.addComment(player_comment);
            var comments = commentService.getComments("Minesweeper");
            System.out.println(comments);
        }else{
            throw new WrongFormatException("Wrong input. Please insert at least one character. Maximum length of comment is 64 characters.");
        }
    }

    private void processInputComment() {
        // throw new UnsupportedOperationException("Method processInput not yet implemented");
        System.out.println("Please write your comment:");
        var userComment = readLine();
        try {
            handleInputComment(userComment);   //zavolam funkciu ktora sa skusa a v ktorej je novy objekt vynimky pri if kde by mala byt chyba
        } catch (
                WrongFormatException e) {  //ak sa vygeneruje chyba tak ju sem zachytime a v tomto bloku sa vypise na obrazovku obsah spravy chyby
            System.out.println(e.getMessage());
            processInputComment(); //ak vyskoci chyba tak chceme nanovo spracovat vstup
        }
    }

    public void handleInputUsername(String inputUsername) throws WrongFormatException{
        Pattern pattern2 = Pattern.compile(".{1,64}");
        Matcher matcher2 = pattern2.matcher(inputUsername);
        if (matcher2.matches()) {
            userName=inputUsername;
        }else{
            throw new WrongFormatException("Wrong input. Please insert at least one character. Maximum length of username is 64 characters.");
        }
    }

    private void processInputUsername() {
        // throw new UnsupportedOperationException("Method processInput not yet implemented");
        System.out.println("Please enter your name:");
        String usernameToCheck = readLine();
        try {
            handleInputUsername(usernameToCheck);   //zavolam funkciu ktora sa skusa a v ktorej je novy objekt vynimky pri if kde by mala byt chyba
        } catch (
                WrongFormatException e) {  //ak sa vygeneruje chyba tak ju sem zachytime a v tomto bloku sa vypise na obrazovku obsah spravy chyby
            System.out.println(e.getMessage());
            processInputUsername(); //ak vyskoci chyba tak chceme nanovo spracovat vstup
        }
    }

    public void handleInputRate(String inputRate) throws WrongFormatException{
        Pattern pattern = Pattern.compile("[1,2,3,4,5]{1}");
        Matcher matcher = pattern.matcher(inputRate);
        if (matcher.matches()){
            Rating player_rating = new Rating("Minesweeper", userName, Integer.parseInt(inputRate), new Date());
            RatingService ratingService = new RatingServiceJDBC();
            ratingService.setRating(player_rating);
            var averageRating = ratingService.getAverageRating("Minesweeper");
            System.out.println("Thank you for your rating. Average rating of game Minesweeper is: " + averageRating);
        }else{
            throw new WrongFormatException("Wrong input. Please rate from 1 to 5.");
        }
    }

    private void processInputRate() {
        // throw new UnsupportedOperationException("Method processInput not yet implemented");
        System.out.println("Please rate the game from 1 to 5:");
        var userRating = readLine();

        try {
            handleInputRate(userRating);   //zavolam funkciu ktora sa skusa a v ktorej je novy objekt vynimky pri if kde by mala byt chyba
        } catch (
                WrongFormatException e) {  //ak sa vygeneruje chyba tak ju sem zachytime a v tomto bloku sa vypise na obrazovku obsah spravy chyby
            System.out.println(e.getMessage());
            processInputRate(); //ak vyskoci chyba tak chceme nanovo spracovat vstup
        }
    }

    @Override
    public void play(){
         setting=Settings.load();//premenna setting bude mat navratovu hodnotu metody load v triede Settings


        //setLevel();  //spusti sa metoda pre nastavenie levelu od pouzivatela, presunute do consoleUI
        //setting.save();  //musi sa ulozit stav

        Field field = new Field(setting.getRowCount(), setting.getColumnCount(), setting.getMineCount());  //nastavime hodnoty pola podla toho co je v nastaveni
        newGameStarted(field);
    }
/*
    public void setLevel(){//nastavenie urovne zo vstupu
        System.out.println("Zvol si uroven: 1 - BEGINNER, 2 - INTERMEDIATE, 3 - EXPERT");
        String inputLevel = readLine();
        switch (inputLevel){
            case "1": setting= Settings.BEGINNER;  //ak je jednotka tak sa nastavi BEGINNER atd
                break;
            case "2": setting=Settings.INTERMEDIATE;
                break;
            case "3": setting=Settings.EXPERT;
                break;
            default: setting=getSetting();  //ak sa nezhoduje vstup ani s jednym tak ostane aktualna hodnota
        }
        this.setting = setting;
        this.setting.save();

    }
    */

}

