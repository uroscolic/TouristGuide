package rs.raf.repositories.destination;

import rs.raf.entities.Destination;
import rs.raf.entities.User;
import rs.raf.entities.UserType;
import rs.raf.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlDestinationRepository extends MySqlAbstractRepository implements DestinationRepository{
    @Override
    public Destination addDestination(Destination destination) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        System.out.println("Destination: " + destination);
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};


            preparedStatement = connection.prepareStatement("INSERT INTO destinations (name, description) VALUES(?, ?)", generatedColumns);
            preparedStatement.setString(1, destination.getName());
            preparedStatement.setString(2, destination.getDescription());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                destination.setId(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return destination;
    }

    @Override
    public Destination findDestination(String name) {
        Destination destination = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM destinations where name = ?");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Long id = resultSet.getLong("id");
                String description = resultSet.getString("description");
                destination = new Destination(id, name, description);
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

        return destination;
    }

    @Override
    public List<Destination> allDestinations() {
        List<Destination> destinations = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from destinations");

            while (resultSet.next()) {
                destinations.add(new Destination(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return destinations;
    }

    @Override
    public String removeDestination(Destination destination) {

        String name = destination.getName();
        Long id = destination.getId();

        try (
                Connection connection = this.newConnection();
                PreparedStatement deleteStatement = connection.prepareStatement("delete from destinations where name = ?");
                PreparedStatement selectStatement = connection.prepareStatement("select * from articles where destination_id = ?")
        ){
            selectStatement.setLong(1, id);

            try(ResultSet resultSet = selectStatement.executeQuery())
            {
                if(resultSet.next())
                    return "Destination is used in articles therefore it can't be removed.";

            }
            deleteStatement.setString(1, name);
            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error while removing destination";
        }
        return "Destination removed";
    }

    @Override
    public Destination updateDestination(Destination destination) {
        Long id = destination.getId();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE destinations SET name = ?, description = ? WHERE id = ?");
            preparedStatement.setString(1, destination.getName());
            preparedStatement.setString(2, destination.getDescription());
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return destination;
    }

    @Override
    public Destination findDestination(Long id) {
        Destination destination = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM destinations where id = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                destination = new Destination(id, name, description);
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

        return destination;
    }
}
