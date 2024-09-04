package org.example.models;

public class CanvasPlayer {
    private String nick;
    private long lastInteraction;

    public CanvasPlayer(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public long getLastInteraction() {
        return lastInteraction;
    }

    public void setLastInteraction(long lastInteraction) {
        this.lastInteraction = lastInteraction;
    }
}
