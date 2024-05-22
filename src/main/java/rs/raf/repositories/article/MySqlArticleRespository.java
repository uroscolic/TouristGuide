package rs.raf.repositories.article;

import rs.raf.entities.Article;
import rs.raf.repositories.MySqlAbstractRepository;

import java.util.List;

public class MySqlArticleRespository extends MySqlAbstractRepository implements ArticleRepository{
    @Override
    public Article addArticle(Article article) {
        return null;
    }

    @Override
    public Article findArticle(Integer id) {
        return null;
    }

    @Override
    public List<Article> allArticles() {
        return null;
    }
}
