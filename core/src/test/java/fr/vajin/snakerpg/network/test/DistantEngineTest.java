package fr.vajin.snakerpg.network.test;

import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.network.DistantEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DistantEngineTest {

    @Test
    void newEntityTest() {

        DistantEngine engine = new DistantEngine();

        NotificationCounter counter = new NotificationCounter();
        engine.addGameEngineObserver(counter);

        Assertions.assertTrue(engine.getEntities().isEmpty());

        engine.beginChange();

        engine.getEntity(0);

        engine.endChange();

        Assertions.assertEquals(1, counter.getNewEntityNotificationCount());
    }

    public static DistantEngine initTestDistantEngine() {
        DistantEngine engine = new DistantEngine();

        engine.beginChange();

        engine.getEntity(0);

        engine.endChange();

        return engine;
    }

    @Test
    void removedEntityTest() {
        DistantEngine distantEngine = initTestDistantEngine();

        NotificationCounter counter = new NotificationCounter();

        distantEngine.addGameEngineObserver(counter);

        distantEngine.getEntity(0).registerObserver(counter);

        distantEngine.beginChange();
        distantEngine.endChange();

        Assertions.assertEquals(1, counter.getRemovedEntityNotificationCount());
        Assertions.assertEquals(1, counter.getDestroyedNotificationCount());
        Assertions.assertTrue(distantEngine.getEntities().isEmpty());
    }

}
