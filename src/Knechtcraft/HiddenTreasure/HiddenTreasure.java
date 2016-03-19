package Knechtcraft.HiddenTreasure;

import Knechtcraft.HiddenTreasure.Listeners.TreasureListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Markus on 18.03.2016.
 */
public class HiddenTreasure extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Initializing HiddenTreasure...");
        getServer().getPluginManager().registerEvents(new TreasureListener(), this);
    }

    @Override
    public void onDisable() {
        //Fired when the server stops and disables all plugins
    }
}
