package com.lexis.module;

public enum Category {
    COMBAT("Savaş"),
    MOVEMENT("Hareket"),
    PLAYER("Oyuncu"),
    RENDER("Görsel"),
    MISC("Diğer");

    public final String name;
    Category(String name) { this.name = name; }
}