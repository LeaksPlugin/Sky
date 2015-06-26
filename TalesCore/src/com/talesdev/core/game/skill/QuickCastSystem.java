package com.talesdev.core.game.skill;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.talesdev.core.TalesCore;
import com.talesdev.core.world.NearbyEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Quick cast
 *
 * @author MoKunz
 */
public class QuickCastSystem extends PacketAdapter implements Listener {
    private TalesCore core;
    private int mainSlot = 0;

    public QuickCastSystem(TalesCore core) {
        super(core, ListenerPriority.HIGHEST, PacketType.Play.Client.HELD_ITEM_SLOT);
        this.core = core;
    }

    public void setMainSlot(int slot) {
        if (slot >= 0 && slot <= 8) this.mainSlot = slot;
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        if (event.getPacketType().equals(PacketType.Play.Client.HELD_ITEM_SLOT)) {
            int slot = event.getPacket().getIntegers().read(0);
            if (slot != 0) {
                event.setCancelled(true);
                if (slot != 1) {
                    castSkill(event.getPlayer());
                }
                sendChangeSlot(event.getPlayer(), mainSlot);
            }
        }
    }

    private void castSkill(Player player) {
        core.getServer().getScheduler().runTask(core, () -> {
            NearbyEntity<LivingEntity> nearbyEntity = new NearbyEntity<>(player.getLocation(), LivingEntity.class);
            List<LivingEntity> entities = nearbyEntity.findInRadius(6);
            entities.stream().filter(entity -> !entity.equals(player)).forEach(entity -> {
                entity.getWorld().strikeLightningEffect(entity.getLocation());
                entity.damage(10);
            });
        });
    }

    public void sendChangeSlot(Player player, int slot) {
        int targetSlot = slot;
        if (slot < 0 || slot > 8) {
            targetSlot = this.mainSlot;
        }
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.HELD_ITEM_SLOT);
        packet.getIntegers().write(0, targetSlot);
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
