package rs.raf.resources;

import rs.raf.entities.Article;
import rs.raf.services.ArticleService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/articles")
public class ArticleResource {

    @Inject
    private ArticleService articleService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allArticles(@QueryParam("page") @DefaultValue("1") int page, @QueryParam("size") @DefaultValue("10") int size) {
        System.out.println("All articles");
        List<Article> articles = this.articleService.allArticles("All", page, size);
        long count = this.articleService.countArticles("All");
        Map<String, Object> response = new HashMap<>();
        response.put("articles", articles);
        response.put("totalArticles", count);
        response.put("currentPage", page);
        response.put("totalPages", (int) Math.ceil((double) count / size));
        return Response.ok(response).build();
    }
    @GET
    @Path("/most-recent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostRecentArticles(@QueryParam("page") @DefaultValue("1") int page, @QueryParam("size") @DefaultValue("10") int size) {
        List<Article> articles = this.articleService.allArticles("mostRecent", page, size);
        long count = this.articleService.countArticles("mostRecent");
        if(count > 10)
            count = 10;
        Map<String, Object> response = new HashMap<>();
        response.put("articles", articles);
        response.put("totalArticles", count);
        response.put("currentPage", page);
        response.put("totalPages", (int) Math.ceil((double) count / size));
        return Response.ok(response).build();
    }
    @GET
    @Path("/most-read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostReadArticles(@QueryParam("page") @DefaultValue("1") int page, @QueryParam("size") @DefaultValue("10") int size) {
        List<Article> articles = this.articleService.allArticles("mostRead", page, size);
        long count = this.articleService.countArticles("mostRead");
        if(count > 10)
            count = 10;
        Map<String, Object> response = new HashMap<>();
        response.put("articles", articles);
        response.put("totalArticles", count);
        response.put("currentPage", page);
        response.put("totalPages", (int) Math.ceil((double) count / size));
        return Response.ok(response).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid Article article) {
        return Response.ok(this.articleService.addArticle(article)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") Long id) {
        return Response.ok(this.articleService.findArticle(id)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("id") Long id) {
        Article article = this.articleService.findArticle(id);
        if (article == null)
            return Response.status(422, "Unprocessable Entity").entity("Article with this id does not exist.").build();
        return Response.ok(this.articleService.removeArticle(article)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid Article article) {
        return Response.ok(this.articleService.updateArticle(article)).build();
    }

    @GET
    @Path("/destination/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allArticlesByDestinationName(@PathParam("name") String name, @QueryParam("page") @DefaultValue("1") int page, @QueryParam("size") @DefaultValue("10") int size){
        List<Article> articles = this.articleService.allArticlesByDestinationName(name, page, size);
        long count = this.articleService.countArticlesByDestinationName(name);
        Map<String, Object> response = new HashMap<>();
        response.put("articles", articles);
        response.put("totalArticles", count);
        response.put("currentPage", page);
        response.put("totalPages", (int) Math.ceil((double) count / size));
        return Response.ok(response).build();

    }
    @GET
    @Path("/activity/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allArticlesByActivityId(@PathParam("id") Long id, @QueryParam("page") @DefaultValue("1") int page, @QueryParam("size") @DefaultValue("10") int size){
        List<Article> articles = this.articleService.allArticlesByActivityId(id, page, size);
        long count = this.articleService.countArticlesByActivityId(id);
        Map<String, Object> response = new HashMap<>();
        response.put("articles", articles);
        response.put("totalArticles", count);
        response.put("currentPage", page);
        response.put("totalPages", (int) Math.ceil((double) count / size));
        return Response.ok(response).build();
    }

    @PUT
    @Path("/visit/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response incrementNumberOfVisits(@PathParam("id") Long id) {
        Article article = this.articleService.findArticle(id);
        if (article == null)
            return Response.status(422, "Unprocessable Entity").entity("Article with this id does not exist.").build();
        return Response.ok(this.articleService.incrementNumberOfVisits(article)).build();
    }

}
