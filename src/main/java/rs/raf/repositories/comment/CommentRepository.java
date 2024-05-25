package rs.raf.repositories.comment;

import rs.raf.entities.Comment;

import java.util.List;

public interface CommentRepository {

    public Comment addComment(Comment comment);
    public Comment findComment(Long id);
    public List<Comment> allComments();
}
