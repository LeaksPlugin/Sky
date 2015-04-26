package com.talesdev.core;

import com.talesdev.core.player.CorePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent all CorePlayers on current server
 *
 * @author MoKunz
 */
public class ServerCorePlayer implements Listener {
    private List<CorePlayer> corePlayerList;
    private TalesCore core;

    public ServerCorePlayer(TalesCore core) {
        this.core = core;
        this.core.getServer().getPluginManager().registerEvents(this, this.core);
        this.corePlayerList = new ArrayList<>();
        // check up dir
        File playerDir = TalesCore.dataLocation("players");
        if (!playerDir.exists()) {
            if (!playerDir.mkdirs()) {
                getCore().getLogger().warning("Failed to create new players folder");
            }
        }
    }

    public TalesCore getCore() {
        return core;
    }

    public CorePlayer getCorePlayer(Player player) {
        for (CorePlayer corePlayer : corePlayerList) {
            if (corePlayer.getPlayer().equals(player)) {
                return corePlayer;
            }
        }
        return null;
    }

    public boolean containsPlayer(Player player) {
        for (CorePlayer corePlayer : corePlayerList) {
            if (corePlayer.getPlayer().equals(player)) {
                return true;
            }
        }
        return false;
    }

    public List<CorePlayer> getCorePlayerList() {
        return new ArrayList<>(corePlayerList);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        if (!containsPlayer(event.getPlayer())) {
            corePlayerList.add(new CorePlayer(event.getPlayer(), core));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        playerQuit(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onKick(PlayerKickEvent event) {
        if (!event.isCancelled()) {
            playerQuit(event.getPlayer());
        }
    }

    private void playerQuit(Player player) {
        if (containsPlayer(player)) {
            CorePlayer corePlayer = getCorePlayer(player);
            corePlayer.destroy();
            corePlayerList.remove(corePlayer);
        } else {
            getCore().getLogger().warning("Someone have bypassed PlayerJoinEvent!");
        }
    }

    public void destroy() {
        corePlayerList.forEach(CorePlayer::destroy);
        corePlayerList.clear();
    }
}
