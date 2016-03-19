package Knechtcraft.HiddenTreasure.Storage;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Created by Markus on 18.03.2016.
 */
public class TreasureStore {
    private Map<Location, ItemStack> treasureLocations;
    private BackendStore backendStore;

    public TreasureStore(BackendStore backendStore) {
        this.backendStore = backendStore;
        treasureLocations = backendStore.readInitialState();
    }

    public ItemStack removeItem(Location location) {
        ItemStack storedItem = treasureLocations.remove(location);

        if (storedItem != null) {
            backendStore.removeItem(location);
        }

        return storedItem;
    }

    public boolean locationContainsItem(Location location) {
        return treasureLocations.containsKey(location);
    }

    public void addItem(Location location, ItemStack itemToStore) {
        treasureLocations.put(location, itemToStore);
        backendStore.addItem(location, itemToStore);
    }

    public int getStoreSize() {
        return treasureLocations.size();
    }
}
