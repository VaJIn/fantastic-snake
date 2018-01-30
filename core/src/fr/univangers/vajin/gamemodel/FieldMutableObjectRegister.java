package fr.univangers.vajin.gamemodel;


import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

/**
 * Register for all mutable object present on the map from the start, such as tree, rock, etc...
 */
public interface FieldMutableObjectRegister {

    /**
     * Search for the a mutable object in the register
     *
     * @param key the key of the object
     * @return an new instance of the mutableObject matching the key if it exists, else null.
     */
    @Nullable
    Entity getMutableObject(@NotNull String key);
}
