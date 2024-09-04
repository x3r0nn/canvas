package org.example;

import org.bukkit.plugin.java.JavaPlugin;
import org.example.inv.InventoryManager;

public class Main extends JavaPlugin {

    private static Main instance;
    private CanvasCommands canvasCommands;
    private CanvasController canvasController;
    private CanvasManager canvasManager;
    private CanvasListener canvasListener;
    private InventoryManager inventoryManager;

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
        instance = this;
        inventoryManager = new InventoryManager(this);
        inventoryManager.init();
        this.canvasManager = new CanvasManager();
        this.canvasController = new CanvasController();
        this.canvasCommands = new CanvasCommands();
        this.getCommand("canvas").setExecutor(canvasCommands);
        this.canvasListener = new CanvasListener();
        this.getServer().getPluginManager().registerEvents(canvasListener, this);


    }

    public static Main getInstance() {
        return instance;
    }

    public CanvasCommands getCommands() {
        return canvasCommands;
    }

    public InventoryManager getInventoryManager() {return inventoryManager;}

    public CanvasController getController() {
        return canvasController;
    }

    public CanvasManager getManager() {
        return canvasManager;
    }
}