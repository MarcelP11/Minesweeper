package service;

import entity.Comment;
import entity.Score;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CommentServiceFile implements CommentService{
    private static final String FILE = "comments.bin";
    private List<Comment> comments = new ArrayList<>();

    @Override
    public void addComment(Comment comment) {
        comments = load();
        comments.add(comment);
        save(comments);
    }

    @Override
    public List<Comment> getComments(String game) {
        comments=load();
        return comments.stream()
                .filter(s ->s.getGame().equals(game))
                .sorted((s1,s2) -> -s1.getCommentedOn().compareTo(s2.getCommentedOn()))
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public void reset() {
        comments=new ArrayList<>();
        save(comments);
    }

    private List<Comment> load(){
        try (var is = new ObjectInputStream(new FileInputStream(FILE))) {
            return (List<Comment>) is.readObject();
        } catch (IOException |
                 ClassNotFoundException e) {
            throw new GameStudioException(e);
        }
    }

    private void save(List<Comment> comments2save){
        try (var os = new ObjectOutputStream(new FileOutputStream(FILE))) {
            os.writeObject(comments2save);
        } catch (IOException e) {
            throw new GameStudioException(e);
        }
    }
}
