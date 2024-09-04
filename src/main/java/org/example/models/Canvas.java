package org.example.models;


import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Canvas {

    private List<CanvasBlock> blocks;
    private int x;
    private int y;
    private Location location;
    private String name;
    private boolean canEdit;

    public Canvas(int x, int y, Location location, String name, boolean canEdit) {
        this.blocks = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.location = location;
        this.name = name;
        this.canEdit = canEdit;
    }

    public List<CanvasBlock> getBlocks() {
        return blocks;
    }

    public void addCanvasBlock(CanvasBlock c) {
        this.blocks.add(c);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
}
