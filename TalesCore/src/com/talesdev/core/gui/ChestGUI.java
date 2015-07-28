package com.talesdev.core.gui;

import com.talesdev.core.player.ClickingAction;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Chest inventory based GUI
 *
 * @author MoKunz
 */
public class ChestGUI implements GUI {
    private static final Server server = Bukkit.getServer();
    private Player owner;
    private String name;
    private int size = 54;
    private List<ItemNode> nodeList = new ArrayList<>();

    public ChestGUI(int size, String name) {
        this.size = size;
        this.name = name;
    }

    public ChestGUI(int size, String name, Player owner) {
        this.size = size;
        this.name = name;
        this.owner = owner;
    }

    public ChestGUI newState(Player player) {
        ChestGUI gui = new ChestGUI(size, name, player);
        for (ItemNode node : nodeList) {
            gui.addNode(node.getX(), node.getY(), node.getItem(), node.getAction());
        }
        return gui;
    }

    @Override
    public void open(Player player) {
        Inventory inventory = server.createInventory(player, size, name);
        for (ItemNode node : nodeList) {
            int index = new InventoryIndex(node.getX(), node.getY()).get();
            if (index <= inventory.getSize()) {
                inventory.setItem(index, node.getItem());
            }
        }
        player.openInventory(inventory);
    }

    @Override
    public void action(InventoryClickEvent event) {
        InventoryIndex index = InventoryIndex.fromSlot(event.getSlot());
        ItemNode node = getNode(index.getX(), index.getY());
        if (node != null) {
            node.getAction().trigger(Bukkit.getPlayer(event.getWhoClicked().getName()), node, ClickingAction.LEFT_CLICK);
        }
    }

    public ItemNode addNode(int x, int y, ItemStack item, NodeTrigger action) {
        // check
        if (getNode(x, y) == null) {
            // create
            ItemNode node = new ItemNode(this, x, y, item, action);
            nodeList.add(node);
            return node;
        }
        return getNode(x, y);
    }

    public ItemNode addNode(int x, int y, ItemStack item) {
        return addNode(x, y, item, (player, node, action1) -> {
        });
    }

    public void setNode(int x, int y, ItemStack item, NodeTrigger action) {
        ItemNode node = getNode(x, y);
        if (node != null) {
            removeNode(node);
            addNode(x, y, item, action);
        } else {
            addNode(x, y, item, action);
        }
    }

    public void setNode(int x, int y, ItemStack item) {
        setNode(x, y, item, (player, node, action1) -> {
        });
    }

    public ItemNode getNode(int x, int y) {
        for (ItemNode node : nodeList) {
            if (node.getX() == x && node.getY() == y) {
                return node;
            }
        }
        return null;
    }

    public boolean containsNode(int x, int y) {
        return getNode(x, y) != null;
    }

    public void removeNode(ItemNode node) {
        nodeList.remove(nodeList.indexOf(node));
    }

    public void clear() {
        nodeList.clear();
    }

    @Override
    public void close(Player player) {
        player.closeInventory();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }
}
