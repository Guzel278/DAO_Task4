import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class DatabaseEntityDAO implements EntityDAO{
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String JDBC_USER = "your_username";
    private static final String JDBC_PASSWORD = "your_password";

    @Override
    public Entity getById(int id) {
        Entity entity = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM entities WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    entity = mapResultSetToEntity(resultSet);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return entity;
    }

    @Override
    public List<Entity> getAll() {
        List<Entity> entities = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM entities");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                entities.add(mapResultSetToEntity(resultSet));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return entities;
    }

    @Override
    public void create(Entity entity) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO entities (name) VALUES (?)")) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public void update(Entity entity) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE entities SET name = ? WHERE id = ?")) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM entities WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private Entity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Entity entity = new Entity();
        entity.setId(resultSet.getInt("id"));
        entity.setName(resultSet.getString("name"));
        return entity;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    private void handleSQLException(SQLException e) {
        // Обработка ошибок SQL
        e.printStackTrace();
    }
}
