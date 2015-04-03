package com.talesdev.copsandcrims.weapon.module;

import com.talesdev.core.player.ClickingAction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Weapon shooting module
 *
 * @author MoKunz
 */
public class ShootingModule extends WeaponModule {
    private FiringMode firingMode;
    private ClickingAction shooting;
    private ClickingAction reload;

    public ShootingModule() {
        super("Shooting");
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (ClickingAction.isRightClick(event.getAction())) {
            // TODO : shooting stuff
        }
    }
}
