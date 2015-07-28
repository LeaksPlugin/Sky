package com.talesdev.core.gui;

import com.talesdev.core.player.ClickingAction;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Represent an item node in the GUI
 *
 * @author MoKunz
 */
public class ItemNode {
    private int x = 0, y = 0;
    private ItemStack item;
    private NodeTrigger action;
    private ChestGUI gui;

    public ItemNode(ChestGUI gui, int x, int y, ItemStack item, NodeTrigger action) {
        this.gui = gui;
        this.x = x;
        this.y = y;
        this.item = item;
        this.action = action;
    }

    public ItemNode(ChestGUI gui, int x, int y, ItemStack item) {
        this(gui, x, y, item, (player, node, action1) -> {
        });
    }

    public void trigger(Player player, ClickingAction clickingAction) {
        action.trigger(player, this, clickingAction);
    }

    public ItemStack getItem() {
        return item;
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

    public NodeTrigger getAction() {
        return action;
    }

    public void setAction(NodeTrigger action) {
        this.action = action;
    }

    public ChestGUI getGUI() {
        return gui;
    }
}
