package org.example;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.example.inv.SmartInventory;
import org.example.models.Canvas;
import org.example.models.CanvasBlock;
import org.example.models.CanvasCords;
import org.example.models.CanvasPlayer;

import java.lang.foreign.PaddingLayout;
import java.util.List;

public class CanvasController {
    private Main plugin;
    public CanvasController() {
        plugin = Main.getInstance();
    }

    public void createCanvas(String sender, String name) {
        createCanvas(sender,name, String.valueOf(plugin.getManager().getSize_x()),String.valueOf(plugin.getManager().getSize_y()));
    }
    public void createCanvas(String sender,String name, String x, String y) {
        if(!Bukkit.getPlayer(sender).isOp()) {return;} //todo connect to perms
        //if(!Bukkit.getPlayer(sender).hasPermission("canvas.create")) {return;}
        if(plugin.getManager().getCanvas(name) != null) {Bukkit.getPlayer(sender).sendMessage("Canvas s tímto jménem už existuje!"); return;}
        Location location;
        int x_cord,y_cord;
        try {
            x_cord = Integer.parseInt(x);
            y_cord = Integer.parseInt(y);
            location = Bukkit.getPlayer(sender).getLocation();
        }
        catch (Exception e) {
            Bukkit.getPlayer(sender).sendMessage("Špatně zadaný parametr příkazu");
            return;
        }

        plugin.getManager().makeCanvas(name,location,x_cord,y_cord);
        placeCanvas(sender,name,location, x_cord, y_cord);
        Bukkit.getPlayer(sender).sendMessage("Canvas " + name + " byl úspěšně vytvořen");
    }

    public void placeCanvas(String player,String canvasName,Location location, int x, int y) {
        Canvas canvas = plugin.getManager().getCanvas(canvasName);
        for(int i = 0;i <x;i++) {
            for(int j = 0;j < y; j ++) {
                Location l = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
                l.add(i,0,j);
                l.getBlock().setType(plugin.getManager().getDefaultBlock());
                CanvasBlock canvasBlock = new CanvasBlock(l,plugin.getManager().getDefaultBlock());
                canvas.addCanvasBlock(canvasBlock);
            }
        }

    }

    public void replaceBlock(Player player, Location location) {
        CanvasPlayer canvasPlayer = plugin.getManager().getCanvasPlayer(player.getName());
        if(canvasPlayer.getLastInteraction() + (plugin.getManager().getPlayerCooldown()*1000L) > System.currentTimeMillis()) {
            long timeLeft = (canvasPlayer.getLastInteraction()+(plugin.getManager().getPlayerCooldown()*1000L)) - System.currentTimeMillis();
            timeLeft /= 1000;
            player.sendMessage("§cJe příliš brzy, počkej ještě "+ timeLeft+"s !");
            return;
        }
        Canvas canvas = plugin.getManager().getCanvas(location);
        if(!canvas.isCanEdit()) {player.sendMessage("§cTento canvas již nemůžeš upravovat!"); return;}
        Material m = player.getInventory().getItemInMainHand().getType();
        CanvasBlock cb = plugin.getManager().getCanvasBlockByLocation(location);

        if(!plugin.getManager().getBlocks().contains(m)) {open(player,location.getBlock()).open(player); return;}
        location.getBlock().setType(m);

        canvasPlayer.setLastInteraction(System.currentTimeMillis());
        cb.setLastPlayerInteracted(player.getName());

    }

    public SmartInventory open(Player player, Block block) {
        SmartInventory smartInventory = SmartInventory.builder()
                .closeable(true)
                .id("materialGui")
                .provider(new BlockGUI(block))
                .size(6, 9) // Size of the inventory (6 rows, 9 columns)
                .title("Material GUI")
                .manager(Main.getInstance().getInventoryManager())
                .build();
        return smartInventory;
    }
}
