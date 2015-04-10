package com.talesdev.copsandcrims.weapon.module;

import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.core.player.LastDamageCause;
import com.talesdev.core.player.LastPlayerDamage;
import com.talesdev.core.text.FormattedMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Death message module
 *
 * @author MoKunz
 */
public class DeathMessageModule extends WeaponModule {
    private String deathMessage = "";
    private String headShotMessage = "(Headshot)";

    public DeathMessageModule(String deathMessage) {
        super("DeathMessage");
        setDeathMessage(deathMessage);
    }

    public DeathMessageModule() {
        this("{entity} was killed by {killer} using {weapon} {headshot}");
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LastPlayerDamage lastPlayerDamage = new LastPlayerDamage(event.getEntity(), getPlugin());
        LastDamageCause lastDamageCause = lastPlayerDamage.getLastDamage();
        if (lastDamageCause != null) {
            getPlugin().getServer().broadcastMessage(getFormattedDeathMessage(event, lastDamageCause));
        }
    }

    public String getFormattedDeathMessage(EntityDeathEvent event, LastDamageCause lastDamageCause) {
        FormattedMessage message = new FormattedMessage(getDeathMessage());
        message.addPattern("entity", event.getEntity().getName());
        message.addPattern("killer", lastDamageCause.getEntity().getName());
        message.addPattern("weapon", lastDamageCause.getAttachment("Weapon", Weapon.class).getName());
        message.addPattern("headshot", ((Boolean) lastDamageCause.getAttachment("HeadShot")) ? "(Headshot)" : "");
        return message.getMessage();
    }

    public String getDeathMessage() {
        return deathMessage;
    }

    public void setDeathMessage(String deathMessage) {
        this.deathMessage = deathMessage;
    }

    public String getHeadShotMessage() {
        return headShotMessage;
    }

    public void setHeadShotMessage(String headShotMessage) {
        this.headShotMessage = headShotMessage;
    }
}
