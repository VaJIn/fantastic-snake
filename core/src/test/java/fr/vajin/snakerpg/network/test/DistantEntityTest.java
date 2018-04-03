package fr.vajin.snakerpg.network.test;

import fr.univangers.vajin.engine.utilities.Position;
import fr.univangers.vajin.network.DistantEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DistantEntityTest {

    private static final String TEST_RESOURCE_KEY = "RSRC_KEY_TEST";

    @Test
    void failModificationTest() {

        DistantEntity entity = new DistantEntity(0);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> entity.setTile(1, new Position(1, 1), TEST_RESOURCE_KEY));
    }


    @Test
    void addTileTest() {
        DistantEntity entity = new DistantEntity(0);

        NotificationCounter notificationCounter = new NotificationCounter();

        entity.registerObserver(notificationCounter);

        entity.beginUpdate();

        entity.setTile(0, new Position(0, 0), TEST_RESOURCE_KEY);

        entity.endUpdate();

        Assertions.assertEquals(1, notificationCounter.getSpriteChangeNotificationCount());
        Assertions.assertEquals(1, notificationCounter.getChangeAtPositionNotificationCount());
    }


    /**
     * Create a distant entity with one tile of id 0, on position (0,0), with resource key TEST_RESOURCE_KEY
     *
     * @return
     */
    public static DistantEntity initDistantEntity() {
        DistantEntity entity = new DistantEntity(0);

        entity.beginUpdate();

        entity.setTile(0, new Position(0, 0), TEST_RESOURCE_KEY);

        entity.endUpdate();

        return entity;
    }


    @Test
    void updateTileTest() {

        DistantEntity entity = initDistantEntity();

        NotificationCounter counter = new NotificationCounter();

        entity.registerObserver(counter);

        entity.beginUpdate();
        entity.setTile(0, new Position(0, 0), TEST_RESOURCE_KEY + "_UPDATE");
        entity.endUpdate();

        Assertions.assertEquals(1, counter.getSpriteChangeNotificationCount());
    }

    @Test
    void updateTilePositionChangeTest() {
        DistantEntity entity = initDistantEntity();

        NotificationCounter counter = new NotificationCounter();
        entity.registerObserver(counter);

        entity.beginUpdate();
        entity.setTile(0, new Position(0, 1), TEST_RESOURCE_KEY);
        entity.endUpdate();

        Assertions.assertEquals(2, counter.getSpriteChangeNotificationCount());

        Assertions.assertTrue(entity.getEntityTilesInfosIterator().hasNext());

        counter.reset();

        entity.beginUpdate();
        entity.setTile(0, new Position(1, 1), TEST_RESOURCE_KEY + "_UPDATE");
        entity.endUpdate();

        Assertions.assertNotEquals(0, counter.getChangeAtPositionNotificationCount());
        Assertions.assertEquals(2, counter.getSpriteChangeNotificationCount());
    }

    @Test
    void testRemovedTile() {
        DistantEntity entity = initDistantEntity();

        NotificationCounter counter = new NotificationCounter();

        entity.registerObserver(counter);

        entity.beginUpdate();
        entity.endUpdate(); //Non updated tile are considered removed.

        Assertions.assertEquals(1, counter.getSpriteChangeNotificationCount());
        Assertions.assertEquals(1, counter.getChangeAtPositionNotificationCount());
    }
}
