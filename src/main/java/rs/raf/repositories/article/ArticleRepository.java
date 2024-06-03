package rs.raf.repositories.article;

import rs.raf.entities.Article;

import java.util.List;

public interface ArticleRepository {

    Article addArticle(Article article);
    Article findArticle(Long id);
    List<Article> allArticles(String filter);
    List<Article> allArticlesByDestinationName(String name);
    String removeArticle(Article article);
    Article updateArticle(Article article);
    List<Article> allArticlesByActivityId(Long id);
    int incrementNumberOfVisits(Article article);

}
