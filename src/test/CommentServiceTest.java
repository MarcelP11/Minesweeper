package test;

import entity.Comment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import service.CommentService;
import service.CommentServiceFile;
import service.CommentServiceJDBC;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class CommentServiceTest {
    private CommentService commentService=new CommentServiceJDBC();
//    private CommentService commentService=new CommentServiceFile();

    @Test
    public void testReset(){
        commentService.addComment(new Comment("Minesweeper", "Peter", "first comment", new Date()));
        commentService.reset();
        assertEquals(0, commentService.getComments("Minesweeper").size());
    }

    @Test
    public void testAddComment(){
        commentService.reset();
        var date = new Date();
        commentService.addComment(new Comment("Minesweeper","Peter","first comment", date));
        var comments = commentService.getComments("Minesweeper");
        assertEquals(1, comments.size());

        assertEquals("Minesweeper",comments.get(0).getGame());
        assertEquals("Peter",comments.get(0).getUsername());
        assertEquals("first comment",comments.get(0).getComment());
        assertEquals(date,comments.get(0).getCommentedOn());
    }

    @Test
    public void testGetComments(){
        commentService.reset();
        var date1 = new Date();
        //Thread.sleep(1000);
        var date2 = new Date();
       // Thread.sleep(1000);
        var date3 = new Date();
       // Thread.sleep(1000);
        var date4 = new Date();
       // Thread.sleep(1000);
        commentService.addComment(new Comment("Minesweeper","Peter", "first comment", date1));
        commentService.addComment(new Comment("Minesweeper","Jozef", "second comment", date2));
        commentService.addComment(new Comment("Tiles","Anna", "third comment", date3));
        commentService.addComment(new Comment("Minesweeper","Petra", "fourth comment", date4));

        var comments = commentService.getComments("Minesweeper");

        assertEquals(3,comments.size());

        assertEquals("Minesweeper", comments.get(0).getGame());
        assertEquals("Peter", comments.get(0).getUsername());
        assertEquals("first comment", comments.get(0).getComment());
        assertEquals(date1, comments.get(0).getCommentedOn());

        assertEquals("Minesweeper", comments.get(1).getGame());
        assertEquals("Jozef", comments.get(1).getUsername());
        assertEquals("second comment", comments.get(1).getComment());
        assertEquals(date2, comments.get(1).getCommentedOn());

        assertEquals("Minesweeper", comments.get(2).getGame());
        assertEquals("Petra", comments.get(2).getUsername());
        assertEquals("fourth comment", comments.get(2).getComment());
        assertEquals(date4, comments.get(2).getCommentedOn());
    }
}
