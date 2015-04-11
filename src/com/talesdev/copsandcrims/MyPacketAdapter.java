package com.talesdev.copsandcrims;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Packet listener
 *
 * @author MoKunz
 */
public class MyPacketAdapter extends PacketAdapter {
    public MyPacketAdapter(Plugin plugin) {
        super(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.SET_SLOT);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if (event.getPacketType().equals(PacketType.Play.Server.SET_SLOT)) {
            PacketContainer packet = event.getPacket();
            ItemStack itemStack = packet.getItemModifier().read(0);
        }
    }

    private void removeEnchantments(ItemStack stack) {
        if (stack == null)
            return;
        Object[] copy = stack.getEnchantments().keySet().toArray();
        for (Object enchantment : copy) {
            stack.removeEnchantment((Enchantment) enchantment);
        }
    }
}
