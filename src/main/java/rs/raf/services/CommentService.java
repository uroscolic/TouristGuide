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

}
