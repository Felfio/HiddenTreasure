package Knechtcraft.HiddenTreasure.Storage;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Created by Markus on 18.03.2016.
 */
public class DatabaseStore implements BackendStore{
    public void removeItem(Location location){

    }

    public void addItem(Location location, ItemStack itemToStore){

    }

    @Override
    public HashMap<Location, ItemStack> readInitialState() {
        return new HashMap<>();
    }
}
