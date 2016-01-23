package org.tagpro.tasc.listeners;

public class ChatMessage {
    private final String from;
    private final String message;
    private final String to;
    private final String color;
    private final boolean mod;

    public ChatMessage(String from, String message, String to, String color, boolean mod) {
        this.from = from;
        this.message = message;
        this.to = to;
        this.color = color;
        this.mod = mod;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public String getTo() {
        return to;
    }

    public String getColor() {
        return color;
    }

    public boolean isMod() {
        return mod;
    }
}