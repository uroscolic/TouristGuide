package rs.raf.services;

import rs.raf.entities.Comment;
import rs.raf.repositories.comment.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {

    @Inject
    private CommentRepository commentRepository;

    public CommentService() {
        System.out.println("Creating CommentService");
    }
    public Comment addComment(Comment comment) {
        return this.commentRepository.addComment(comment);
    }
    public List<Comment> allComments() {
        return this.commentRepository.allComments();
    }
    public Comment findComment(Long id) {
        return this.commentRepository.findComment(id);
    }

    public List<Comment> allCommentsByArticleId(Long id, int page, int size) {
        return this.commentRepository.allCommentsByArticleId(id, page, size);
    }
    public long countCommentsByArticleId(Long id) {
        return this.commentRepository.countCommentsByArticleId(id);
    }
}
