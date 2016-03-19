package Knechtcraft.HiddenTreasure.Storage;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Created by Markus on 18.03.2016.
 */
public interface BackendStore {
    void removeItem(Location location);

    void addItem(Location location, ItemStack itemToStore);

    // Reads current state of treasure locations from backend store
    // and returns it in right format.
    HashMap<Location, ItemStack> readInitialState();
}
