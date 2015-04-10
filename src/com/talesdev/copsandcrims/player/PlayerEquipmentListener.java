package com.talesdev.copsandcrims.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.talesdev.copsandcrims.CopsAndCrims;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Listening to EntityEquipPacket
 *
 * @author MoKunz
 */
public class PlayerEquipmentListener extends PacketAdapter {
    public PlayerEquipmentListener(Plugin plugin) {
        super(plugin, PacketType.Play.Server.ENTITY_EQUIPMENT);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        // get packet container and entity id
        PacketContainer packetContainer = event.getPacket();
        int entityID = packetContainer.getIntegers().read(0);
        // get the receiver
        CvCPlayer cPlayer = CopsAndCrims.getPlugin().getServerCvCPlayer().getPlayer(event.getPlayer());
        // player is not null
        if (cPlayer != null) {
            if (cPlayer.getPlayer().getEntityId() == entityID) {
                return;
            }
            // is it helmet ?
            if (packetContainer.getIntegers().readSafely(1) == 4) {
                // get item
                ItemStack itemStack = packetContainer.getItemModifier().read(0);
                // prevent npe
                if (itemStack != null) {
                    // is it pumpkin block ? if it is , then cancel it
                    if (itemStack.getType().equals(Material.PUMPKIN)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
