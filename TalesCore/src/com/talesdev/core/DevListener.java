package com.talesdev.core;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Location;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * Dev listener
 *
 * @author MoKunz
 */
public class DevListener extends PacketAdapter implements Listener {
    private TalesCore core;

    public DevListener(TalesCore core) {
        super(core, ListenerPriority.HIGHEST, PacketType.Play.Client.HELD_ITEM_SLOT);
        this.core = core;
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        /*if(event.getPacketType().equals(PacketType.Play.Client.HELD_ITEM_SLOT)){
            int slot = event.getPacket().getIntegers().read(0);
            if(slot != 0){
                event.setCancelled(true);
                thunder(event.getPlayer());
                PacketContainer slotPacket = new PacketContainer(PacketType.Play.Server.HELD_ITEM_SLOT);
                slotPacket.getIntegers().write(0,0);
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(),slotPacket);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }

    private void thunder(final Player player) {
        core.getServer().getScheduler().runTask(core, () -> {
            Location location = player.getLocation();
            Location eye = player.getEyeLocation();
            Fireball fireball = player.launchProjectile(Fireball.class, eye.getDirection().multiply(4));
            fireball.setDirection(eye.getDirection());
            fireball.setIsIncendiary(false);
            fireball.setYield(6);
        });
    }
}
