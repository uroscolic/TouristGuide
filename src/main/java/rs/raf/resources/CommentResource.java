package rs.raf.resources;

import rs.raf.entities.Comment;
import rs.raf.services.CommentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/comments")
public class CommentResource {

    @Inject
    private CommentService commentService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allComments() {
        return Response.ok(this.commentService.allComments()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findComment(@PathParam("id") Long id) {
        return Response.ok(this.commentService.findComment(id)).build();
    }

    @GET
    @Path("/article/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allCommentsByArticleId(@PathParam("id") Long id, @QueryParam("page") @DefaultValue("1") int page, @QueryParam("size") @DefaultValue("10") int size){
        List<Comment> comments = this.commentService.allCommentsByArticleId(id, page, size);
        long count = this.commentService.countCommentsByArticleId(id);
        Map<String, Object> response = new HashMap<>();
        response.put("comments", comments);
        response.put("totalComments", count);
        response.put("currentPage", page);
        response.put("totalPages", (int) Math.ceil((double) count / size));
        return Response.ok(response).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Comment comment) {
        return Response.ok(this.commentService.addComment(comment)).build();
    }


}
