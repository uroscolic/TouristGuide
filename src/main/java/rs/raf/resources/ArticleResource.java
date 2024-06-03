package rs.raf.resources;

import rs.raf.entities.Article;
import rs.raf.services.ArticleService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/articles")
public class ArticleResource {

    @Inject
    private ArticleService articleService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allArticles() {
        System.out.println("All articles");
        return Response.ok(this.articleService.allArticles("All")).build();
    }
    @GET
    @Path("/most-recent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostRecentArticles() {
        return Response.ok(this.articleService.allArticles("mostRecent")).build();
    }
    @GET
    @Path("/most-read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostReadArticles() {
        return Response.ok(this.articleService.allArticles("mostRead")).build();
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
    public Response allArticlesByDestinationName(@PathParam("name") String name) {
        return Response.ok(this.articleService.allArticlesByDestinationName(name)).build();
    }
    @GET
    @Path("/activity/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allArticlesByActivityId(@PathParam("id") Long id) {
        return Response.ok(this.articleService.allArticlesByActivityId(id)).build();
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
