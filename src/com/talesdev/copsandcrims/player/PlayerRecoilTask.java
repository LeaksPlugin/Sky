package com.talesdev.copsandcrims.player;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.ServerCvCPlayer;

/**
 * Player recoil processing task
 */
public class PlayerRecoilTask implements Runnable {
    private CopsAndCrims plugin;

    public PlayerRecoilTask(CopsAndCrims plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        ServerCvCPlayer cvCPlayer = plugin.getServerCvCPlayer();
        for (CvCPlayer player : cvCPlayer.getAllPlayers()) {
            for (String weaponName : plugin.getWeaponFactory().getAllWeaponName()) {
                plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        player.getPlayerRecoil().removeRecoil(weaponName, player.getPlayerRecoil().getWeaponRecoil(weaponName).getRegen());
                    }
                });
            }
        }
    }
}
