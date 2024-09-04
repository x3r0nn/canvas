package org.example;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;
import org.example.models.Canvas;
import org.example.models.CanvasBlock;
import org.example.models.CanvasPlayer;
import org.example.utils.UtilityConfig;

import java.util.*;

public class CanvasManager {

    private int size_x;
    private int size_y;
    private int playerCooldown;
    private List<Material> blocks;
    private Material defaultBlock;

    private HashMap<String, Canvas> canvasHashMap;
    private HashMap<String, CanvasPlayer> playerHashMap;


    private UtilityConfig config;
    private UtilityConfig dataConfig;
    private Main plugin;

    public CanvasManager() {
        plugin = Main.getInstance();
        canvasHashMap = new HashMap<>();
        playerHashMap = new HashMap<>();
        loadData();
    }

    private void loadData() {
        blocks = new ArrayList<>();

        config = new UtilityConfig("canvas_config");
        config.setup(true);
        size_x = config.get().getInt("default_x");
        size_y = config.get().getInt("default_y");
        playerCooldown = config.get().getInt("cooldown");
        defaultBlock = Material.matchMaterial(config.get().getString("default_block"));
        if(defaultBlock == null) {Bukkit.getConsoleSender().sendMessage("Invalid default block in canvas config"); return;}
        List<String> strings = config.get().getStringList("items");
        for(String str: strings) {
            Material material = Material.matchMaterial(str);
            if(material == null) { Bukkit.getConsoleSender().sendMessage("Invalid block in canvas config named:" + str); continue;}
            blocks.add(material);
        }

        dataConfig = new UtilityConfig("canvas_data");
        dataConfig.setup(true);

        dataConfig.get().getKeys(false).forEach(s -> {


            String locationString = dataConfig.get().getString(s+".location");
            String worldName = locationString.split("world=CraftWorld\\{name=")[1].split("}")[0];
            String[] parts = locationString.split(",");

            double xl = Double.parseDouble(parts[1].split("=")[1]);
            double yl = Double.parseDouble(parts[2].split("=")[1]);
            double zl = Double.parseDouble(parts[3].split("=")[1].split("}")[0]);

            // Get the world object
            World world = Bukkit.getWorld(worldName);

            int x = dataConfig.get().getInt(s+".x");
            int y = dataConfig.get().getInt(s+".y");
            Location location = new Location(world, xl, yl, zl);
            boolean canEdit = dataConfig.get().getBoolean(s+".edit");
            Canvas c = new Canvas(x,y,location,s,canEdit);
            canvasHashMap.put(s,c);
            for(int i = 0;i <x;i++) {
                for(int j = 0;j < y; j ++) {
                    Location l = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
                    l.add(i,0,j);
                    CanvasBlock canvasBlock = new CanvasBlock(l,location.getBlock().getType());
                    c.addCanvasBlock(canvasBlock);
                }
            }
        });

    }



    public void makeCanvas(String name,Location location, int x, int y) {
        Canvas canvas = new Canvas(x,y,location,name,true);
        canvasHashMap.put(name,canvas);
        //dataConfig.get().set("", name);
        dataConfig.get().set(name+".location",location.toString());
        dataConfig.get().set(name+".x",x);
        dataConfig.get().set(name+".y",y);
        dataConfig.get().set(name+".edit",canvas.isCanEdit());
        dataConfig.save();
    }

    public Canvas getCanvas(String name) {
        return canvasHashMap.get(name);
    }

    public Canvas getCanvas(Location location) {
        for(Canvas c : canvasHashMap.values()) {
            for(CanvasBlock block: c.getBlocks()) {
                if(block.getLocation().equals(location)) {
                    return c;
                }
            }
        }
        return null;
    }

    public CanvasBlock getCanvasBlockByLocation(Location location) {
        for(Canvas c : canvasHashMap.values()) {
            for(CanvasBlock block: c.getBlocks()) {
                if(block.getLocation().equals(location)) {
                    return block;
                }
            }
        }
        return null;
    }

    public CanvasPlayer getCanvasPlayer(String name) {
        if(playerHashMap.get(name) == null) {createCanvasPlayer(name);}
        return playerHashMap.get(name);
    }

    public void removeCanvas(Player player,String name) {
        Canvas c = getCanvas(name);
        if(c == null) {return;}
        for(CanvasBlock cb: c.getBlocks()) {
            cb.getLocation().getBlock().setType(Material.AIR);
        }
        player.sendMessage("Canvas " + name + " úspěšně smazán");
        dataConfig.get().set(name,null);
        canvasHashMap.remove(name);
        dataConfig.save();
    }

    public Set<String> getCanvases () {
        return canvasHashMap.keySet();
    }

    public void resetCanvas (Player player,String name) {
        Canvas c = getCanvas(name);
        if(c == null) {return;}
        for(CanvasBlock canvasBlock: canvasHashMap.get(name).getBlocks()) {
            canvasBlock.getLocation().getBlock().setType(getDefaultBlock());
        }
        player.sendMessage("Canvas " + name + " úspěšně resetován");
    }

    public void enableCanvas(Player player,String name) {
        Canvas c = getCanvas(name);
        if(c == null) {return;}
        c.setCanEdit(true);
        dataConfig.get().set(name+".edit",c.isCanEdit());
        dataConfig.save();
        player.sendMessage("Canvas " + name + " úspěšně aktivován");
    }

    public void disableCanvas(Player player,String name) {
        Canvas c = getCanvas(name);
        if(c == null) {return;}
        c.setCanEdit(false);
        dataConfig.get().set(name+".edit",c.isCanEdit());
        dataConfig.save();
        player.sendMessage("Canvas " + name + " úspěšně deaktivován");
    }

    public void createCanvasPlayer(String player) {
        playerHashMap.put(player, new CanvasPlayer(player));
    }

    public int getSize_x() {
        return size_x;
    }

    public int getSize_y() {
        return size_y;
    }

    public int getPlayerCooldown() {
        return playerCooldown;
    }

    public Material getDefaultBlock() {
        return defaultBlock;
    }

    public List<Material> getBlocks() {
        return blocks;
    }
}
