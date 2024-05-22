package rs.raf.repositories.comment;

import rs.raf.entities.Comment;
import rs.raf.repositories.MySqlAbstractRepository;

import java.util.List;

public class MySqlCommentRespository extends MySqlAbstractRepository implements CommentRepository{
    @Override
    public Comment addComment(Comment comment) {
        return null;
    }

    @Override
    public Comment findComment(Integer id) {
        return null;
    }

    @Override
    public List<Comment> allComments() {
        return null;
    }
}
