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
        return Response.ok(this.articleService.allArticles()).build();
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
}
