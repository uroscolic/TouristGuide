package rs.raf.repositories.article;

import rs.raf.entities.Article;

import java.util.List;

public interface ArticleRepository {

    public Article addArticle(Article article);
    public Article findArticle(Integer id);
    public List<Article> allArticles();
}
