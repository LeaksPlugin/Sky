package com.talesdev.core.gui;

/**
 * Inv Index
 *
 * @author MoKunz
 */
public class InventoryIndex {
    private int x, y;

    public InventoryIndex(int x, int y) {
        this.x = (x > 8 ? 8 : x);
        this.y = (y > 5 ? 5 : y);
    }

    public static InventoryIndex fromSlot(int slot) {
        return new InventoryIndex(slot % 9, slot / 9);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int get() {
        return (y * 9) + x;
    }
}
