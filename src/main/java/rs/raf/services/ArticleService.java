package rs.raf.services;

import rs.raf.entities.Article;
import rs.raf.repositories.article.ArticleRepository;

import javax.inject.Inject;
import java.util.List;

public class ArticleService {

    @Inject
    private ArticleRepository articleRepository;

    public ArticleService() {
        System.out.println("Creating ArticleService");
    }

    public Article addArticle(Article article) {
        return this.articleRepository.addArticle(article);
    }
    public Article findArticle(Long id) {
        return this.articleRepository.findArticle(id);
    }
    public List<Article> allArticles(String filter, int page, int size) {
        return this.articleRepository.allArticles(filter, page, size);
    }
    public long countArticles(String filter) {
        return this.articleRepository.countArticles(filter);
    }
    public List<Article> allArticlesByDestinationName(String name, int page, int size) {
        return this.articleRepository.allArticlesByDestinationName(name, page, size);
    }
    public String removeArticle(Article article) {
        return this.articleRepository.removeArticle(article);
    }
    public Article updateArticle(Article article) {
        return this.articleRepository.updateArticle(article);
    }
    public int incrementNumberOfVisits(Article article) {
        return this.articleRepository.incrementNumberOfVisits(article);
    }
    public long countArticlesByActivityId(Long id) {
        return this.articleRepository.countArticlesByActivityId(id);
    }
    public long countArticlesByDestinationName(String name) {
        return this.articleRepository.countArticlesByDestinationName(name);
    }
    public List<Article> allArticlesByActivityId(Long id, int page, int size) {
        return this.articleRepository.allArticlesByActivityId(id, page, size);
    }
}
