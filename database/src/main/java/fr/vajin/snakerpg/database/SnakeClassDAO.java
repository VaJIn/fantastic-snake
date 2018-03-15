package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.SnakeClassEntity;

import java.util.Collection;
import java.util.Optional;

public interface SnakeClassDAO {

    Optional<SnakeClassEntity> getSnakeClassById(int snakeClassId);

    Collection<SnakeClassEntity> getAllSnakeClasses();
}
