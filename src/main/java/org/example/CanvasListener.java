package org.example;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class CanvasListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(e.getHand() == EquipmentSlot.OFF_HAND) return;
        if(e.getClickedBlock() == null) {return;}
        Location location = e.getClickedBlock().getLocation();
        if(Main.getInstance().getManager().getCanvasBlockByLocation(location) == null) {return;}
        e.setCancelled(true);
        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) { return;}

        Main.getInstance().getController().replaceBlock(e.getPlayer(), e.getClickedBlock().getLocation());

    }

}
