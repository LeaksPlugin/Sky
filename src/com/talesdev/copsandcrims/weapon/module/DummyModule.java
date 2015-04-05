package com.talesdev.copsandcrims.weapon.module;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.weapon.WeaponCooldownTag;
import com.talesdev.copsandcrims.weapon.bullet.BulletTask;
import com.talesdev.copsandcrims.weapon.bullet.DelayedBullet;
import com.talesdev.core.player.ClickingAction;
import com.talesdev.core.world.Sound;
import com.talesdev.core.world.SoundEffect;
import org.bukkit.event.EventHandler;
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
        if (event.getItem() == null) return;
        if (getWeapon().isWeapon(event.getItem()) &&
                ClickingAction.isRightClick(event.getAction())) {
            WeaponCooldownTag tag = new WeaponCooldownTag(3, event.getPlayer());
            if (!tag.isCooldown()) {
                DelayedBullet bullet = new DelayedBullet(event.getPlayer(), null, 4);
                bullet.setRayParameter(2000, 0.05, 4);
                bullet.setSpeed(10);
                (new BulletTask(bullet, 1)).runTaskTimer(CopsAndCrims.getPlugin(), 0, 1);
                (new Sound(SoundEffect.MOB_SKELETON_DEATH, 1, 1)).playSound(event.getPlayer(), event.getPlayer().getLocation());
                tag.attach();
            }
        }
    }
}