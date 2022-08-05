package entity;

import java.util.Date;
import java.util.Formatter;

public class Comment {
    private String game;
    private String username;
    private String comment;
    private Date commentedOn;

    public Comment(String game, String username, String comment, Date commentedOn) {
        this.game = game;
        this.username = username;
        this.comment = comment;
        this.commentedOn = commentedOn;
    }

    @Override
    public String toString() {
        Formatter f = new Formatter();
        f.format("%s   %s    %s\n", username, comment,commentedOn);
        return f.toString();
//        return "Comment{" +
//                "game='" + game + '\'' +
//                ", username='" + username + '\'' +
//                ", comment='" + comment + '\'' +
//                ", commentedOn=" + commentedOn +
//                '}';
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentedOn() {
        return commentedOn;
    }

    public void setCommentedOn(Date commentedOn) {
        this.commentedOn = commentedOn;
    }
}
