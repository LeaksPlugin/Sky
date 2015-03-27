package com.talesdev.copsandcrims;

import com.talesdev.core.entity.BoundingBox;
import com.talesdev.core.player.UUIDTask;
import com.talesdev.core.system.Timer;
import com.talesdev.core.world.NearbyEntity;
import com.talesdev.core.world.RayTrace;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Main plugin command executor
 *
 * @author MoKunz
 */
public class CopsAndCrimsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("getuuid")) {
                if (args.length > 1) {
                    Bukkit.getScheduler().runTaskAsynchronously(CopsAndCrims.getPlugin(CopsAndCrims.class),
                            new UUIDTask(CopsAndCrims.getPlugin(CopsAndCrims.class), args[1], sender)
                    );
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("raytrace")) {
                if (sender instanceof Player) {
                    // player
                    Player p = (Player) sender;
                    // ray tracing engine
                    RayTrace rayTrace = new RayTrace(p.getEyeLocation());
                    Location currentLocation = p.getEyeLocation();
                    Vector currentVector = currentLocation.toVector();
                    World world = p.getWorld();
                    // entity scanner
                    NearbyEntity<LivingEntity> nearbyEntity = new NearbyEntity<>(p.getEyeLocation(), LivingEntity.class);
                    List<LivingEntity> lastEntity = null;
                    // timer for profiling
                    Timer timer = new Timer();
                    timer.start();
                    // start shooting ray
                    for (int i = 0; i < 2000; i++) {
                        // shoot
                        currentVector = rayTrace.iterate(0.05);
                        currentLocation = currentVector.toLocation(world);
                        // check if hit box
                        if (currentLocation.getBlock().getType().isSolid()) {
                            world.playEffect(currentLocation.getBlock().getLocation(), Effect.STEP_SOUND, currentLocation.getBlock().getType());
                            //p.sendMessage(ChatColor.GREEN + "Ray distance : " + i*0.1);
                            break;
                        }
                        // set location of scanner
                        nearbyEntity.setLocation(currentLocation);
                        // find
                        LivingEntity entity = nearbyEntity.findNearestInRadius(3, true);
                        if(entity != null){
                            if (entity.getName().equals(p.getName())) continue;
                            BoundingBox box = new BoundingBox(entity);
                            if (box.isInside(currentVector)) {
                                entity.damage(4);
                                break;
                            }
                        }
                    }
                    timer.record();
                    System.out.println("Time used : " + timer.getDeltaMS());
                    //sender.sendMessage(ChatColor.GREEN + "Output printed to console! Time used : " + new DecimalFormat("#.####").format(timer.getDeltaMS()));
                }
                return true;
            }
        }
        return false;
    }
}
