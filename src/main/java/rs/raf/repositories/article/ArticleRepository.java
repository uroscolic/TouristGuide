package rs.raf.repositories.article;

import rs.raf.entities.Article;

import java.util.List;

public interface ArticleRepository {

    Article addArticle(Article article);
    Article findArticle(Long id);
    List<Article> allArticles();
    List<Article> allArticlesByDestinationName(String name);
    String removeArticle(Article article);
    Article updateArticle(Article article);
    int incrementNumberOfVisits(Article article);

}
