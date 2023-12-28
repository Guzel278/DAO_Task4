import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseEntityDAOTest {

    private EntityDAO entityDAO = new DatabaseEntityDAO();

    @Test
    void testCRUDOperations() {
        // Create
        Entity entityToCreate = new Entity();
        entityToCreate.setName("TestEntity");
        entityDAO.create(entityToCreate);

        // Read
        List<Entity> entities = entityDAO.getAll();
        assertEquals(1, entities.size());
        Entity createdEntity = entities.get(0);

        // Update
        createdEntity.setName("UpdatedEntity");
        entityDAO.update(createdEntity);

        Entity updatedEntity = entityDAO.getById(createdEntity.getId());
        assertEquals("UpdatedEntity", updatedEntity.getName());

        // Delete
        entityDAO.delete(updatedEntity.getId());
        List<Entity> remainingEntities = entityDAO.getAll();
        assertTrue(remainingEntities.isEmpty());
    }

}
