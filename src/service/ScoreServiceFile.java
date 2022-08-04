package service;

import entity.Score;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreServiceFile implements ScoreService {
    private static final String FILE = "score.bin";
    //vytvorime si zoznam Score s ktorym budeme pracovat
    private List<Score> scores = new ArrayList<>();

    @Override
    public void addScore(Score score) {
        scores = load();
        scores.add(score);
        save(scores);
    }

    @Override
    public List<Score> getBestScores(String game) {
        scores=load();
        return scores.stream()
                        .filter(s ->s.getGame().equals(game))
                        .sorted((s1,s2) -> -Integer.compare(s1.getPoints(),s2.getPoints()))
                        .limit(5)
                        .collect(Collectors.toList())
                        ;    //vraciame prud dat pre filtraciu, usporiadanie , a obmedzenie na 5 skore //lambda hovori o tom
    }                                                            //prud upravime tak ze v nim ostanu iba polozky ktore vyhovuju lambda vyrazu,
    //skrateny zapis funkcie ktora nie je pomenovana
    //vrati true iba tam kde je hra rovna parametru game

    @Override
    public void reset() {
        scores = new ArrayList<>();
        save(scores);
    }

    private List<Score> load() {
        try (var is = new ObjectInputStream(new FileInputStream(FILE))) { //ak je podciarknute cervenou tak chce vynimku teda blok catch
            return (List<Score>) is.readObject();
        } catch (IOException |
                 ClassNotFoundException e) { //pri prejdeni na metodu sa zobrazia aj vynimky ktore mozu vyskocit
            throw new GameStudioException(e);
        }
    }

    private void save(List<Score> scores2save) {
        try (var os = new ObjectOutputStream(new FileOutputStream(FILE))) { //ak je podciarknute cervenou tak chce vynimku teda blok catch
            os.writeObject(scores2save);
        } catch (IOException e) {
            throw new GameStudioException(e);
        }
    }
}
