package rs.raf.repositories.article;

import rs.raf.entities.Activity;
import rs.raf.entities.Article;
import rs.raf.entities.Comment;
import rs.raf.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MySqlArticleRepository extends MySqlAbstractRepository implements ArticleRepository{
    @Override
    public Article addArticle(Article article) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        System.out.println("Article: " + article);
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};


            preparedStatement = connection.prepareStatement("INSERT INTO articles (destination_id, title, text, date, number_of_visits, author) VALUES(?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setLong(1, article.getDestinationId());
            preparedStatement.setString(2, article.getTitle());
            preparedStatement.setString(3, article.getText());
            preparedStatement.setString(4, article.getDate());
            preparedStatement.setInt(5, article.getNumberOfVisits());
            preparedStatement.setString(6, article.getAuthor());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();


            if (resultSet.next()) {
                article.setId(resultSet.getLong(1));
            }

            for (Long activityId : article.getActivities().stream().distinct().collect(Collectors.toList())) {
                preparedStatement = connection.prepareStatement("SELECT * FROM activities WHERE id = ?");
                preparedStatement.setLong(1, activityId);
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) {
                    preparedStatement = connection.prepareStatement("INSERT INTO articles_activities (article_id, activity_id) VALUES(?, ?)");
                    preparedStatement.setLong(1, article.getId());
                    preparedStatement.setLong(2, activityId);
                    preparedStatement.executeUpdate();
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return article;
    }

    @Override
    public Article findArticle(Long id) {
        Article article = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM articles WHERE id = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                List<Long> activities = new ArrayList<>();

                Long destinationId = resultSet.getLong("destination_id");
                String title = resultSet.getString("title");
                String text = resultSet.getString("text");
                String date = resultSet.getString("date");
                int numberOfVisits = resultSet.getInt("number_of_visits");
                String author = resultSet.getString("author");

                preparedStatement = connection.prepareStatement("SELECT activity_id FROM articles_activities WHERE article_id = ?");
                preparedStatement.setLong(1, id);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    activities.add(resultSet.getLong("activity_id"));
                }

                article = new Article(id, destinationId, activities, title, text, date, numberOfVisits, author);

            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return article;
    }

    @Override
    public List<Article> allArticles() {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Statement innerStatement = null;
        ResultSet innerResultSet = null;

        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from articles ORDER BY date DESC");
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long destinationId = resultSet.getLong("destination_id");
                String title = resultSet.getString("title");
                String text = resultSet.getString("text");
                String date = resultSet.getString("date");
                int numberOfVisits = resultSet.getInt("number_of_visits");
                String author = resultSet.getString("author");

                innerStatement = connection.createStatement();
                innerResultSet = innerStatement.executeQuery("SELECT activity_id FROM articles_activities WHERE article_id = " + id);
                List<Long> activities = new ArrayList<>();
                while (innerResultSet.next()) {
                    activities.add(innerResultSet.getLong("activity_id"));
                }

                articles.add(new Article(id, destinationId, activities, title, text, date, numberOfVisits, author));

                this.closeResultSet(innerResultSet);
                this.closeStatement(innerStatement);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeResultSet(resultSet);
            this.closeStatement(statement);
            this.closeConnection(connection);
        }

        return articles;
    }
}
