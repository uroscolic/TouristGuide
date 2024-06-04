package rs.raf.repositories.comment;

import rs.raf.entities.Comment;

import java.util.List;

public interface CommentRepository {

    Comment addComment(Comment comment);
    Comment findComment(Long id);
    List<Comment> allComments();
    List<Comment> allCommentsByArticleId(Long id, int page, int size);
    long countCommentsByArticleId(Long id);

}
