package org.example.models;

import org.bukkit.Location;
import org.bukkit.Material;

public class CanvasBlock {
    private Location location;
    private String lastPlayerInteracted;
    private Material material;

    public CanvasBlock(Location location, Material material) {
        this.location = location;
        this.material = material;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLastPlayerInteracted() {
        return lastPlayerInteracted;
    }

    public void setLastPlayerInteracted(String lastPlayerInteracted) {
        this.lastPlayerInteracted = lastPlayerInteracted;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
