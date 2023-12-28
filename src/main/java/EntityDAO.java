import java.util.List;

public interface EntityDAO {
    Entity getById(int id);
    List<Entity> getAll();
    void create(Entity entity);
    void update(Entity entity);
    void delete(int id);
}
