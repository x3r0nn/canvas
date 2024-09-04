package org.example.models;

public class CanvasCords {

    private int worldX;
    private int worldY;
    private int relativeX;
    private int relativeY;

    public CanvasCords(int worldX, int worldY, int relativeX, int relativeY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.relativeX = relativeX;
        this.relativeY = relativeY;
    }

    public CanvasCords() {
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getRelativeX() {
        return relativeX;
    }

    public void setRelativeX(int relativeX) {
        this.relativeX = relativeX;
    }

    public int getRelativeY() {
        return relativeY;
    }

    public void setRelativeY(int relativeY) {
        this.relativeY = relativeY;
    }
}
