package com.talesdev.copsandcrims.arena.game;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.talesdev.copsandcrims.arena.data.*;
import com.talesdev.copsandcrims.arena.system.ArenaJoinLeave;
import com.talesdev.copsandcrims.arena.system.CountdownSystem;
import com.talesdev.copsandcrims.arena.system.CvCArenaController;
import com.talesdev.copsandcrims.event.EntityDamageByWeaponEvent;
import com.talesdev.copsandcrims.guns.Glock18;
import com.talesdev.copsandcrims.guns.Knife;
import com.talesdev.copsandcrims.guns.USP;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.weapon.RandomWeapon;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.WeaponSlot;
import com.talesdev.copsandcrims.weapon.module.ShootingModule;
import com.talesdev.core.player.LastDamageCause;
import com.talesdev.core.player.LastPlayerDamage;
import com.talesdev.core.text.AlignedMessage;
import com.talesdev.core.world.LocationString;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

/**
 * Defusal arena controller
 *
 * @author MoKunz
 */
public class TDMArenaController extends CvCArenaController implements ArenaJoinLeave, CountdownSystem {

    private int maxPlayers = 8;
    private GameScoreboard gameScoreboard;
    private Team terroristTeam;
    private Location terroristSpawn;
    private int terroristKills = 0;
    private Scoreboard arenaScoreboard;
    private Team counterTerroristTeam;
    private int counterTerroristKills = 0;
    private Location counterTerroristSpawn;
    private GameTicking gameTicking;

    public TDMArenaController() {
        super("TDM");
    }

    @Override
    public void arenaLoaded() {
        String lobbyLocation = getConfig().getString("Lobby_Location");
        if (lobbyLocation != null) {
            Location location = LocationString.fromString(lobbyLocation).getLocation();
            setLobbyLocation(location);
        }
        String endLocation = getConfig().getString("End_Location");
        if (endLocation != null) {
            Location location = LocationString.fromString(endLocation).getLocation();
            setEndLocation(location);
        }
        String terroristSpawn = getConfig().getString("Terrorist_Spawn");
        if (terroristSpawn != null) {
            Location location = LocationString.fromString(terroristSpawn).getLocation();
            setTerroristSpawn(location);
        }
        String counterTerroristSpawn = getConfig().getString("Counter_Terrorist_Spawn");
        if (counterTerroristSpawn != null) {
            Location location = LocationString.fromString(counterTerroristSpawn).getLocation();
            setCounterTerroristSpawn(location);
        }
        setMaxPlayers(getConfig().getInt("Max_Players", getMaxPlayers()));
        gameScoreboard = new GameScoreboard(getArena(), ChatColor.RED + "CopsAndCrims - TDM");
        arenaScoreboard = getPlugin().getServer().getScoreboardManager().getNewScoreboard();
        terroristTeam = arenaScoreboard.registerNewTeam("Terrorist");
        setUpTeam(terroristTeam);
        counterTerroristTeam = arenaScoreboard.registerNewTeam("CounterTerrorist");
        setUpTeam(counterTerroristTeam);
        gameTicking = new GameTicking(this);
    }

    private void setUpTeam(Team team) {
        team.setCanSeeFriendlyInvisibles(false);
        team.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);
        team.setAllowFriendlyFire(false);
    }

    @Override
    public CvCArenaController createController() {
        return new TDMArenaController();
    }

    @Override
    public void save() {
        if (getLobbyLocation() != null)
            getConfig().set("Lobby_Location", new LocationString(getLobbyLocation()).toString());
        if (getEndLocation() != null)
            getConfig().set("End_Location", new LocationString(getEndLocation()).toString());
        if (getTerroristSpawn() != null)
            getConfig().set("Terrorist_Spawn", new LocationString(getTerroristSpawn()).toString());
        if (getCounterTerroristSpawn() != null)
            getConfig().set("Counter_Terrorist_Spawn", new LocationString(getCounterTerroristSpawn()).toString());
        getConfig().set("Max_Players", getMaxPlayers());
    }

    @Override
    public void joinArena(CvCPlayer player) {
        joinArena(player, true);
    }

    @Override
    public void leaveArena(CvCPlayer cPlayer, boolean showMessage, boolean removePlayer) {
        if (cPlayer.getArenaData().getPlayingArena() != null) {
            if (cPlayer.getArenaData().getPlayingArena().getArenaName().equals(getArena().getArenaName())) {
                if (getArena().getStatus().equals(ArenaStatus.WAITING)) {
                    getArena().getPlayers().forEach(gameScoreboard::updateLobby);
                }
                cPlayer.getSidebarObjective().reset();
                if (removePlayer) getArena().removePlayer(cPlayer);
                destroyTeam(cPlayer);
                removeFromTeam(cPlayer.getPlayer());
                cPlayer.getPlayer().getInventory().clear();
                cPlayer.getPlayer().setHealth(cPlayer.getPlayer().getMaxHealth());
                cPlayer.getPlayer().setFoodLevel(20);
                cPlayer.getArenaData().setStatus(PlayerArenaStatus.NOT_PLAYING);
                if (showMessage) cPlayer.getPlayer().sendMessage(ChatColor.GREEN + "You left arena");
                cPlayer.getPlayer().teleport(getEndLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                cPlayer.getPlayer().setGameMode(cPlayer.getArenaData().getLastGameMode());
                cPlayer.getArenaData().setPlayingArena(null);
                if (showMessage) getArena().broadcastMessage(cPlayer.getPlayerName() + " left the arena!");
                if (removePlayer) cPlayer.clearArenaData();
            }
        }
    }

    private void removeFromTeam(Player player) {
        if (terroristTeam.hasPlayer(player)) {
            terroristTeam.removePlayer(player);
        }
        if (counterTerroristTeam.hasPlayer(player)) {
            counterTerroristTeam.removePlayer(player);
        }
    }

    @Override
    public void joinArena(CvCPlayer cPlayer, boolean showMessage) {
        if (!(getArena().getStatus().equals(ArenaStatus.RUNNING) || getArena().getStatus().equals(ArenaStatus.END))) {
            if (getMaxPlayers() - getArena().getTotalPlayers() > 0) {
                if (!getArena().hasPlayer(cPlayer)) {
                    getArena().addPlayer(cPlayer);
                    initTeam(cPlayer);
                    cPlayer.getPlayer().getInventory().clear();
                    cPlayer.getPlayer().setHealth(cPlayer.getPlayer().getMaxHealth());
                    cPlayer.getPlayer().setFoodLevel(20);
                    cPlayer.getArenaData().setLastGameMode(cPlayer.getPlayer().getGameMode());
                    cPlayer.getArenaData().setStatus(PlayerArenaStatus.IN_LOBBY);
                    cPlayer.getArenaData().setPlayingArena(getArena());
                    cPlayer.getPlayer().setGameMode(GameMode.SURVIVAL);
                    cPlayer.getPlayer().teleport(getLobbyLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    if (showMessage)
                        getArena().broadcastMessage(ChatColor.BLUE + cPlayer.getPlayerName() + ChatColor.YELLOW +
                                        " joined! (" + getArena().getTotalPlayers() + "/" + getMaxPlayers() + ")"
                        );
                    gameScoreboard.firstJoinApply(cPlayer);
                    getArena().getPlayers().forEach(gameScoreboard::updateLobby);
                    if (getArena().getTotalPlayers() >= Math.round(getMaxPlayers() / 2F)) {
                        startCountdown();
                    }
                } else {
                    cPlayer.getPlayer().sendMessage(ChatColor.GREEN + "You're already in this arena!");
                }
            } else {
                cPlayer.getPlayer().sendMessage(ChatColor.RED + getArena().getArenaName() + " has already full!");
            }
        } else {
            cPlayer.getPlayer().sendMessage(ChatColor.RED + "You can't join that arena!");
        }
    }


    @Override
    public void leaveArena(CvCPlayer player) {
        leaveArena(player, true);
    }

    @Override
    public void leaveArena(CvCPlayer cPlayer, boolean showMessage) {
        leaveArena(cPlayer, showMessage, true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        CvCPlayer cPlayer = getPlugin().getServerCvCPlayer().getPlayer(event.getPlayer());
        if (getArena().hasPlayer(cPlayer)) {
            leaveArena(cPlayer);
        }
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    @Override
    public void startArena() {
        if (getArena().getStatus().equals(ArenaStatus.RUNNING) || getArena().getStatus().equals(ArenaStatus.END)) {
            return;
        }
        // setup team
        TeamedPlayer teamPlayer = new TeamedPlayer(terroristTeam, counterTerroristTeam);
        List<Player> playerList = new ArrayList<>();
        getArena().getPlayers().forEach(player1 -> playerList.add(player1.getPlayer()));
        teamPlayer.team(playerList);
        getArena().getPlayers().forEach(this::fillPlayerTeam);
        tpToTeamSpawn();
        getArena().setStatus(ArenaStatus.RUNNING);
        for (CvCPlayer player : getArena().getPlayers()) {
            player.getArenaData().setStatus(PlayerArenaStatus.PLAYING);
            gameScoreboard.gameStartApply(player);
        }
        getArena().getBukkitPlayer().forEach(this::giveWeapon);
        getArena().broadcastMessage("The game is now started!");
        gameTicking.runTaskTimer(getPlugin(), 0, 20);
    }

    public void initTeam(CvCPlayer cPlayer) {
        Team t = cPlayer.getPlayerScoreboard().registerNewTeam(terroristTeam.getName());
        setUpTeam(t);
        terroristTeam.getPlayers().forEach(t::addPlayer);
        Team ct = cPlayer.getPlayerScoreboard().registerNewTeam(counterTerroristTeam.getName());
        setUpTeam(ct);
        counterTerroristTeam.getPlayers().forEach(ct::addPlayer);
    }

    public void fillPlayerTeam(CvCPlayer cPlayer) {
        for (OfflinePlayer player : getCounterTerroristTeam().getPlayers()) {
            cPlayer.getPlayerScoreboard().getTeam(getCounterTerroristTeam().getName()).addPlayer(player);
        }
        for (OfflinePlayer player : getTerroristTeam().getPlayers()) {
            cPlayer.getPlayerScoreboard().getTeam(getTerroristTeam().getName()).addPlayer(player);
        }
    }

    public Team getPlayerTeam(CvCPlayer player, Team team) {
        return player.getPlayerScoreboard().getTeam(team.getName());
    }

    public void addPlayerToTeam(CvCPlayer player, Team team) {
        for (CvCPlayer cPlayer : getAllPlayers()) {
            getPlayerTeam(cPlayer, team).addPlayer(player.getPlayer());
        }
    }

    public void removePlayerFromTeam(CvCPlayer player, Team team) {
        for (CvCPlayer cPlayer : getAllPlayers()) {
            getPlayerTeam(cPlayer, team).removePlayer(player.getPlayer());
        }
    }

    public void destroyTeam(CvCPlayer cPlayer) {
        Team t = cPlayer.getPlayerScoreboard().getTeam(terroristTeam.getName());
        Team ct = cPlayer.getPlayerScoreboard().getTeam(counterTerroristTeam.getName());
        t.getPlayers().forEach(t::removePlayer);
        ct.getPlayers().forEach(ct::removePlayer);
        t.unregister();
        ct.unregister();
    }


    public void tpToTeamSpawn() {
        getArena().getBukkitPlayer().forEach(this::tpToTeamSpawn);
    }

    @EventHandler
    public void onTeamDamage(EntityDamageByWeaponEvent event) {
        if (getArenaStatus().equals(ArenaStatus.END)) {
            event.setCancelled(true);
        }
        if (event.getEntity() instanceof Player) {
            Player player = ((Player) event.getEntity());
            // find team
            if (counterTerroristTeam.hasPlayer(player)) {
                if (counterTerroristTeam.hasPlayer(event.getPlayer())) {
                    event.setCancelled(true);
                }
            } else if (terroristTeam.hasPlayer(player)) {
                if (terroristTeam.hasPlayer(event.getPlayer())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        CvCPlayer sender = getPlugin().getServerCvCPlayer().getPlayer(event.getPlayer());
        if (getArena().hasPlayer(sender)) {
            try {
                for (Iterator<Player> it = event.getRecipients().iterator(); it.hasNext(); ) {
                    Player player = it.next();
                    CvCPlayer cvCPlayer = getPlugin().getServerCvCPlayer().getPlayer(player);
                    if (!getArena().hasPlayer(cvCPlayer)) {
                        it.remove();
                    }
                }
            } catch (UnsupportedOperationException e) {
                getPlugin().getLogger().log(Level.WARNING, "Unable to modify chat recipients!");
                e.printStackTrace();
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = ((Player) event.getDamager());
            CvCPlayer cPlayer = getPlugin().getServerCvCPlayer().getPlayer(player);
            if (getArena().hasPlayer(cPlayer)) {
                if (!getArena().getStatus().equals(ArenaStatus.RUNNING)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (getArena().getBukkitPlayer().contains(event.getPlayer())) {
            CvCPlayer cPlayer = getPlugin().getServerCvCPlayer().getPlayer(event.getPlayer());
            if (terroristTeam.hasPlayer(event.getPlayer())) {
                event.setRespawnLocation(getTerroristSpawn());
            } else if (counterTerroristTeam.hasPlayer(event.getPlayer())) {
                event.setRespawnLocation(getCounterTerroristSpawn());
            }
            if (cPlayer.getArenaData().isSpectator()) {
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
                cPlayer.getArenaData().setSpectator(false);
                RespawnTask task = new RespawnTask(this, cPlayer);
                task.runTaskTimer(getPlugin(), 0, 20);
            }
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        CvCPlayer cPlayer = getPlugin().getServerCvCPlayer().getPlayer(Bukkit.getPlayer(event.getEntity().getName()));
        if (getArena().hasPlayer(cPlayer)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        CvCPlayer cPlayer = getPlugin().getServerCvCPlayer().getPlayer(event.getPlayer());
        if (getArena().hasPlayer(cPlayer)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (getArena().getBukkitPlayer().contains(event.getEntity())) {
            // spectator
            CvCPlayer cPlayer = getPlugin().getServerCvCPlayer().getPlayer(event.getEntity());
            cPlayer.getArenaData().setSpectator(true);
            cPlayer.getArenaData().addDeath();
            event.getDrops().clear();
            // add kill to killer
            LastPlayerDamage lastDamage = new LastPlayerDamage(event.getEntity(), getPlugin());
            if (lastDamage.getLastDamage() != null) {
                LastDamageCause lastDamageCause = lastDamage.getLastDamage();
                if (lastDamageCause.getDamageCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) &&
                        lastDamageCause.getAttachment("Weapon", Weapon.class) != null
                        ) {
                    if (lastDamageCause.getEntity() instanceof Player) {
                        Player lastDamager = ((Player) lastDamageCause.getEntity());
                        if (getPlugin().getServerCvCPlayer().getPlayer(lastDamager) != null) {
                            CvCPlayer cvCPlayer = getPlugin().getServerCvCPlayer().getPlayer(lastDamager);
                            cvCPlayer.getArenaData().addKill();
                            if (getCounterTerroristTeam().hasPlayer(lastDamager)) {
                                addCounterTerroristKill();
                            } else if (getTerroristTeam().hasPlayer(lastDamager)) {
                                addTerroristKill();
                            }
                        }
                    }
                }
                lastDamageCause.clearDamager();
                fillAssist(lastDamageCause);
            }
            getArena().getPlayers().forEach(gameScoreboard::updateStats);
            getArena().getPlayers().forEach(player -> gameScoreboard.updateTeamStats(player, terroristKills, counterTerroristKills));
            if (getArenaStatus().equals(ArenaStatus.RUNNING)) {
                checkTeamStats();
            }
            // force respawn
            PacketContainer packetContainer = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Client.CLIENT_COMMAND);
            packetContainer.getClientCommands().write(0, EnumWrappers.ClientCommand.PERFORM_RESPAWN);
            try {
                ProtocolLibrary.getProtocolManager().recieveClientPacket(event.getEntity(), packetContainer);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            // give item
            getPlugin().getServer().getScheduler().runTaskLater(getPlugin(), () -> giveWeapon(event.getEntity()), 1);
        }
    }

    public void fillAssist(LastDamageCause lastDamageCause) {
        Set<Entity> assistSet = lastDamageCause.getAssist();
        if (assistSet != null) {
            for (Entity entity : assistSet) {
                if (entity instanceof Player) {
                    Player player = ((Player) entity);
                    if (getPlugin().getServerCvCPlayer().getPlayer(player) != null) {
                        CvCPlayer cPlayer = getPlugin().getServerCvCPlayer().getPlayer(player);
                        cPlayer.getArenaData().addAssist();
                    }
                }
            }
        }
    }

    public void giveWeapon(Player player) {
        RandomWeapon randomWeapon = new RandomWeapon(getPlugin().getWeaponFactory());
        Weapon firstWeapon = randomWeapon.randomWeapon();
        int firstSlot = 0;
        Weapon secondWeapon = null;
        int secondSlot = 1;
        Weapon melee = getPlugin().getWeaponFactory().getWeapon(Knife.class);
        int meleeSlot = 2;
        if (WeaponSlot.getSlot(firstWeapon).equals(WeaponSlot.PRIMARY)) {
            if (getCounterTerroristTeam().hasPlayer(player)) {
                secondWeapon = getPlugin().getWeaponFactory().getWeapon(USP.class);
            } else if (getTerroristTeam().hasPlayer(player)) {
                secondWeapon = getPlugin().getWeaponFactory().getWeapon(Glock18.class);
            }
        } else {
            firstSlot = 1;
        }
        CvCPlayer cPlayer = getPlugin().getServerCvCPlayer().getPlayer(player);
        fillBullet(cPlayer, firstWeapon);
        player.getInventory().setItem(firstSlot, getPlugin().getWeaponFactory().createWeaponItem(firstWeapon.getClass()));
        if (secondWeapon != null) {
            player.getInventory().setItem(secondSlot, getPlugin().getWeaponFactory().createWeaponItem(secondWeapon.getClass()));
            fillBullet(cPlayer, secondWeapon);
        }
        player.getInventory().setItem(meleeSlot, getPlugin().getWeaponFactory().createWeaponItem(melee.getClass()));
    }

    public void fillBullet(CvCPlayer cPlayer, Weapon weapon) {
        if (weapon.containsModule(ShootingModule.class)) {
            cPlayer.getPlayerBullet().getBullet(weapon.getName()).setBulletCount(
                    weapon.getModule(ShootingModule.class).getMaxBullet()
            );
        }
    }

    public void tpToTeamSpawn(Player player) {
        if (terroristTeam.hasPlayer(player)) {
            if (getTerroristSpawn() != null)
                player.teleport(getTerroristSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        } else if (counterTerroristTeam.hasPlayer(player)) {
            if (getCounterTerroristSpawn() != null)
                player.teleport(getCounterTerroristSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        } else {
            getPlugin().getLogger().log(Level.WARNING, "Found player with no team in " + getArena().getArenaName());
        }
    }

    @Override
    public void endArena() {
        if (!getArena().getStatus().equals(ArenaStatus.RUNNING)) {
            return;
        }
        if (getCounterTerroristKills() > getTerroristKills()) {
            broadcastWinner(ChatColor.RED + "Counter Terrorist");
        } else if (getTerroristKills() > getCounterTerroristKills()) {
            broadcastWinner(ChatColor.BLUE + "Terrorist");
        } else {
            broadcastWinner(ChatColor.GREEN + "Draw");
        }
        getArena().setStatus(ArenaStatus.END);
        getArena().getPlayers().forEach(gameScoreboard::gameEndApply);
        getArena().broadcastMessage("The game is now ended!");
        List<CvCPlayer> newPlayerList = new ArrayList<>(getArena().getPlayers());
        getPlugin().getServer().getScheduler().runTaskLater(getPlugin(), new Runnable() {
            @Override
            public void run() {
                getArena().setStatus(ArenaStatus.WAITING);
                for (CvCPlayer player : newPlayerList) {
                    leaveArena(player, false, false);
                    player.clearArenaData();
                    getArena().removePlayer(player);
                }
                clearData();
            }
        }, 100);
    }

    @Override
    public Location getLobbyLocation() {
        return getArena().getLobbyLocation();
    }

    @Override
    public void setLobbyLocation(Location location) {
        getArena().setLobbyLocation(location);
    }

    @Override
    public Location getEndLocation() {
        return getArena().getEndLocation();
    }

    @Override
    public void setEndLocation(Location location) {
        getArena().setEndLocation(location);
    }

    @Override
    public CmdResult receiveCommand(CommandSender sender, String[] args) {
        boolean isPlayer = false;
        Player player = null;
        if (sender instanceof Player) {
            isPlayer = true;
            player = ((Player) sender);
        }
        if (args[0].equalsIgnoreCase("setlobby") || args[0].equalsIgnoreCase("setend")) {
            if (isPlayer) {
                if (args[0].equalsIgnoreCase("setlobby")) {
                    setLobbyLocation(player.getLocation());
                    player.sendMessage(ChatColor.GREEN + "Lobby location of " +
                                    ChatColor.BLUE + getArena().getArenaName() +
                                    ChatColor.GREEN + " set!"
                    );
                } else if (args[0].equalsIgnoreCase("setend")) {
                    setEndLocation(player.getLocation());
                    player.sendMessage(ChatColor.GREEN + "End location of " +
                                    ChatColor.BLUE + getArena().getArenaName() +
                                    ChatColor.GREEN + " set!"
                    );
                }
                getArena().save();
            } else {
                if (args.length > 1) {
                    Player refPlayer = getPlugin().getServer().getPlayer(args[1]);
                    if (refPlayer != null) {
                        if (args[0].equalsIgnoreCase("setlobby")) {
                            setLobbyLocation(refPlayer.getLocation());
                            sender.sendMessage(ChatColor.GREEN + "Lobby location of " +
                                            ChatColor.BLUE + getArena().getArenaName() +
                                            ChatColor.GREEN + " set!"
                            );
                        } else if (args[0].equalsIgnoreCase("setend")) {
                            setEndLocation(refPlayer.getLocation());
                            sender.sendMessage(ChatColor.GREEN + "End location of " +
                                            ChatColor.BLUE + getArena().getArenaName() +
                                            ChatColor.GREEN + " set!"
                            );
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Error : player " + args[1] + " not found!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Error : please provide a player name when running in console mode!");
                }
                getArena().save();
            }
        } else if (args[0].equalsIgnoreCase("setspawn")) {
            if (sender instanceof Player) {
                if (args.length > 2) {
                    if (args[1].equalsIgnoreCase("ct")) {
                        setCounterTerroristSpawn(((Player) sender).getLocation());
                    } else if (args[1].equalsIgnoreCase("t")) {
                        setTerroristSpawn(((Player) sender).getLocation());
                    } else {
                        sender.sendMessage(ChatColor.RED + "Error : unknown team!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Error : too few arguments!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Only player can use this command!");
            }

        } else if (args[0].equalsIgnoreCase("setmaxplayers")) {
            if (args.length > 1) {
                int maxPlayers = 8;
                try {
                    maxPlayers = Integer.parseInt(args[1]);
                    setMaxPlayers(maxPlayers);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Error : Invalid number format!");
                    e.printStackTrace();
                }
            }
        } else if (args[0].equalsIgnoreCase("forcestart")) {
            startCountdown();
        } else if (args[0].equalsIgnoreCase("forceend")) {
            endArena();
        }
        return new CmdResult(null, true);
    }

    @Override
    public void startCountdown() {
        getArena().setStatus(ArenaStatus.COUNTDOWN);
        CountdownTask task = new CountdownTask(gameScoreboard);
        task.runTaskTimer(getPlugin(), 0, 20);
    }

    public void checkTeamStats() {
        if (getTerroristKills() >= 50 || getCounterTerroristKills() >= 50) {
            endArena();
        }
    }

    public void broadcastWinner(String winnerName) {
        getArena().broadcastMessage(new AlignedMessage("").center(), false);
        getArena().broadcastMessage(new AlignedMessage("").center(), false);
        getArena().broadcastMessage(new AlignedMessage(ChatColor.BOLD + "CopsAndCrims - TDM").center(), false);
        getArena().broadcastMessage(new AlignedMessage("").center(), false);
        getArena().broadcastMessage(new AlignedMessage(
                ChatColor.YELLOW + "Winner" + ChatColor.GRAY + " - " + winnerName
        ).center(), false);
        getArena().broadcastMessage(new AlignedMessage("").center(), false);
        getArena().broadcastMessage(new AlignedMessage("").center(), false);
        getArena().broadcastMessage(new AlignedMessage("").center(), false);
    }


    @Override
    public void countdownFinished() {
        startArena();
    }

    public void clearData() {
        gameScoreboard = new GameScoreboard(getArena(), ChatColor.RED + "CopsAndCrims - TDM");
        arenaScoreboard = getPlugin().getServer().getScoreboardManager().getNewScoreboard();
        terroristTeam = arenaScoreboard.registerNewTeam("Terrorist");
        setUpTeam(terroristTeam);
        counterTerroristTeam = arenaScoreboard.registerNewTeam("CounterTerrorist");
        setUpTeam(counterTerroristTeam);
        gameTicking = new GameTicking(this);
        counterTerroristKills = 0;
        terroristKills = 0;
    }

    public GameScoreboard getGameScoreboard() {
        return gameScoreboard;
    }

    public Location getTerroristSpawn() {
        return terroristSpawn;
    }

    public void setTerroristSpawn(Location terroristSpawn) {
        this.terroristSpawn = terroristSpawn;
    }

    public Location getCounterTerroristSpawn() {
        return counterTerroristSpawn;
    }

    public void setCounterTerroristSpawn(Location counterTerroristSpawn) {
        this.counterTerroristSpawn = counterTerroristSpawn;
    }

    public int getCounterTerroristKills() {
        return counterTerroristKills;
    }

    public void addCounterTerroristKill() {
        counterTerroristKills++;
    }

    public int getTerroristKills() {
        return terroristKills;
    }

    public void addTerroristKill() {
        terroristKills++;
    }

    public Team getTerroristTeam() {
        return terroristTeam;
    }

    public Team getCounterTerroristTeam() {
        return counterTerroristTeam;
    }

    public List<CvCPlayer> getAllPlayers() {
        return getArena().getPlayers();
    }
}
