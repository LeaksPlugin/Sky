package com.talesdev.copsandcrims.weapon.module;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.weapon.WeaponCooldownTag;
import com.talesdev.copsandcrims.weapon.bullet.Bullet;
import com.talesdev.copsandcrims.weapon.bullet.BulletTask;
import com.talesdev.copsandcrims.weapon.bullet.DelayedBullet;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Dummy module
 */
public class DummyModule extends WeaponModule {
    public DummyModule() {
        super("Dummy");
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (getWeapon().isWeapon(event.getItem()) &&
                (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                )) {
            WeaponCooldownTag tag = new WeaponCooldownTag(30, event.getPlayer());
            if (!tag.isCooldown()) {
                Bullet bullet = new DelayedBullet(event.getPlayer(), null, 4);
                bullet.setRayParameter(2000, 0.05, 4);
                (new BulletTask(bullet, 3)).runTaskTimer(CopsAndCrims.getPlugin(), 0, 1);
                tag.attach();
            }
        }
    }
}
