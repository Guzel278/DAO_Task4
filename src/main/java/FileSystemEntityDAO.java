import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class FileSystemEntityDAO implements EntityDAO{
    private static final String FILE_PATH = "entities.txt";

    @Override
    public Entity getById(int id) {
        List<Entity> entities = readFromFile();
        for (Entity entity : entities) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public List<Entity> getAll() {
        return readFromFile();
    }

    @Override
    public void create(Entity entity) {
        List<Entity> entities = readFromFile();
        int newId = entities.isEmpty() ? 1 : entities.get(entities.size() - 1).getId() + 1;
        entity.setId(newId);
        entities.add(entity);
        writeToFile(entities);
    }

    @Override
    public void update(Entity entity) {
        List<Entity> entities = readFromFile();
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId() == entity.getId()) {
                entities.set(i, entity);
                writeToFile(entities);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        List<Entity> entities = readFromFile();
        entities.removeIf(entity -> entity.getId() == id);
        writeToFile(entities);
    }

    private List<Entity> readFromFile() {
        List<Entity> entities = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                entities.add(parseEntityFromString(line));
            }
        } catch (IOException e) {
            handleIOException(e);
        }
        return entities;
    }

    private void writeToFile(List<Entity> entities) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Entity entity : entities) {
                writer.write(formatEntityToString(entity));
                writer.newLine();
            }
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    private Entity parseEntityFromString(String line) {
        String[] parts = line.split(",");
        Entity entity = new Entity();
        entity.setId(Integer.parseInt(parts[0]));
        entity.setName(parts[1]);
        return entity;
    }

    private String formatEntityToString(Entity entity) {
        return entity.getId() + "," + entity.getName();
    }

    private void handleIOException(IOException e) {
        // Обработка ошибок ввода-вывода
        e.printStackTrace();
    }
}
