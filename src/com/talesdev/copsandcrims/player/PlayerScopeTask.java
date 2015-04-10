package com.talesdev.copsandcrims.player;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.ServerCvCPlayer;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Player Scope task
 *
 * @author MoKunz
 */
@Deprecated
public class PlayerScopeTask implements Runnable {
    private CopsAndCrims plugin;

    public PlayerScopeTask(CopsAndCrims plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        ServerCvCPlayer cvCPlayer = plugin.getServerCvCPlayer();
        for (CvCPlayer player : cvCPlayer.getAllPlayers()) {
            if (player.isScoping()) {
                player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 10, true, false));
            } else {
                player.getPlayer().removePotionEffect(PotionEffectType.SLOW);
            }
        }
    }
}
