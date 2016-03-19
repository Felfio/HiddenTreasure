package Knechtcraft.HiddenTreasure.Listeners;

import Knechtcraft.HiddenTreasure.Storage.DatabaseStore;
import Knechtcraft.HiddenTreasure.Storage.TreasureStore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Markus on 18.03.2016.
 */

public class TreasureListener implements Listener {
    private TreasureStore store = new TreasureStore(new DatabaseStore());

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block clickedBlock = event.getClickedBlock();

        // Trigger only if block is clicked, not entity.
        if (clickedBlock == null) {
            return;
        }

        Location clickedBlockLocation = clickedBlock.getLocation();
        Player player = event.getPlayer();
        ItemStack itemInHand = event.getItem();

        // Checks if player wants to add or remove treasure.
        switch (getPlayerInteractResult(event)) {
            case ADD_TREASURE:
                addTreasure(player, clickedBlockLocation, itemInHand);
                break;
            case REMOVE_TREASURE:
                removeTreasure(player, clickedBlockLocation);
                break;
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Location blockLocation = event.getBlock().getLocation();
        ItemStack itemInBlock = store.removeItem(blockLocation);

        if (itemInBlock != null) {
            blockLocation.getWorld().dropItem(blockLocation, itemInBlock);
            event.getPlayer().sendMessage(String.format("Currently %d items in hidden.", store.getStoreSize()));
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        List<Block> affectedBlocks = event.blockList();

        for (Block block : affectedBlocks) {
            Location blockLocation = block.getLocation();
            ItemStack itemInBlock = store.removeItem(blockLocation);

            if (itemInBlock != null) {
                blockLocation.getWorld().dropItem(blockLocation, itemInBlock);
            }
        }
    }

    private TreasureInteractResult getPlayerInteractResult(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = event.getItem();
        boolean playerIsSneaking = player.isSneaking();

        if (playerIsSneaking && itemInHand != null && event.getHand() == EquipmentSlot.HAND) {
            return TreasureInteractResult.ADD_TREASURE;
        } else if (playerIsSneaking && itemInHand == null && event.getHand() == EquipmentSlot.HAND) {
            return TreasureInteractResult.REMOVE_TREASURE;
        }

        return TreasureInteractResult.NONE;
    }

    private void addTreasure(Player player, Location location, ItemStack itemToStore) {
        if (store.locationContainsItem(location)) {
            player.sendMessage("There is already an item hided in here.");
            return;
        }

        store.addItem(location, itemToStore);
        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));

        player.sendMessage("You placed the item in this block!");
        player.sendMessage(String.format("Currently %d items in hidden.", store.getStoreSize()));
    }

    private void removeTreasure(Player player, Location location) {
        ItemStack storedItem = store.removeItem(location);
        player.getInventory().setItemInMainHand(storedItem);

        player.sendMessage(String.format("Currently %d items in hidden.", store.getStoreSize()));
    }
}