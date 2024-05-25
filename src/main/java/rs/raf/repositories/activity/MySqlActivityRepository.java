package rs.raf.repositories.activity;

import rs.raf.entities.Activity;
import rs.raf.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MySqlActivityRepository extends MySqlAbstractRepository implements ActivityRepository{
    @Override
    public Activity addActivity(Activity activity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        System.out.println("Activity: " + activity);
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};


            preparedStatement = connection.prepareStatement("INSERT INTO activities (name) VALUES(?)", generatedColumns);
            preparedStatement.setString(1, activity.getName());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();


            if (resultSet.next()) {
                activity.setId(resultSet.getLong(1));
            }

            for (Long articleId : activity.getArticles().stream().distinct().collect(Collectors.toList())) {

                preparedStatement = connection.prepareStatement("SELECT * FROM articles WHERE id = ?");
                preparedStatement.setLong(1, articleId);
                resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) {
                    preparedStatement = connection.prepareStatement("INSERT INTO articles_activities (article_id, activity_id) VALUES(?, ?)");
                    preparedStatement.setLong(1, articleId);
                    preparedStatement.setLong(2, activity.getId());
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

        return activity;
    }

    @Override
    public Activity findActivity(String name) {
        Activity activity = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM activities WHERE name = ?");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                List<Long> articles = new ArrayList<>();
                long id = resultSet.getLong("id");
                preparedStatement = connection.prepareStatement("SELECT article_id FROM articles_activities WHERE activity_id = ?");
                preparedStatement.setLong(1, id);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    articles.add(resultSet.getLong("article_id"));
                }
                activity = new Activity(id, name, articles);
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

        return activity;
    }

    @Override
    public List<Activity> allActivities() {
        List<Activity> activities = new ArrayList<>();

        System.out.println("All activities in MySqlActivityRepository");
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Statement innerStatement = null;
        ResultSet innerResultSet = null;

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from activities");

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");

                innerStatement = connection.createStatement();
                innerResultSet = innerStatement.executeQuery("select article_id from articles_activities where activity_id = " + id);
                List<Long> articles = new ArrayList<>();
                while (innerResultSet.next()) {
                    articles.add(innerResultSet.getLong("article_id"));
                }
                activities.add(new Activity(id, name, articles));

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

        return activities;
    }
}
