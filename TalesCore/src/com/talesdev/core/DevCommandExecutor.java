package com.talesdev.core;

import com.talesdev.core.entity.PlayerView;
import com.talesdev.core.world.RayTrace;
import com.talesdev.core.world.particle.ParticleEffect;
import com.talesdev.core.world.raytrace.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author MoKunz
 */
public class DevCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("dev")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("view")) {
                    if (sender instanceof Player) {
                        PlayerView view = new PlayerView(((Player) sender));
                        List<Entity> entityList = view.entities();
                        sender.sendMessage(ChatColor.GREEN + "Entity list");
                        for (Entity entity : entityList) {
                            sender.sendMessage("  " + ChatColor.BLUE + entity.getName() + ChatColor.GREEN + " at" + ChatColor.BLUE + entity.getLocation().toVector().toString());
                        }
                    } else {
                        sender.sendMessage(errorMessage("Only player can use this command!"));
                    }
                } else if (args[0].equalsIgnoreCase("testgui")) {
                    if (sender instanceof Player) {

                    }
                } else if (args[0].equalsIgnoreCase("trace")) {
                    Player player = null;
                    if (sender instanceof Player) {
                        player = ((Player) sender);
                    } else {
                        if (args.length > 1) {
                            if (Bukkit.getPlayer(args[1]) != null) {
                                player = Bukkit.getPlayer(args[1]);
                            } else {
                                System.out.println("Player " + args[1] + " not found!");
                                return true;
                            }
                        } else {
                            System.out.println("Missing player args!");
                            return true;
                        }
                    }
                    final Player finalPlayer = player;
                    long start = System.currentTimeMillis();
                    SortedMap<Entity, AABB> entities = new TreeMap<>((e1, e2) -> {
                        double d1 = finalPlayer.getEyeLocation().distanceSquared(e1.getLocation());
                        double d2 = finalPlayer.getEyeLocation().distanceSquared(e2.getLocation());
                        return (int) (d1 - d2);
                    });
                    Ray3D ray3D = new Ray3D(player.getEyeLocation());
                    Vec3D vec3D = null;
                    Entity foundEntity = null;
                    boolean hasLineOfSight = false;
                    DecimalFormat format = new DecimalFormat(".##");
                    for (Entity entity : player.getNearbyEntities(100, 100, 100))
                        entities.put(entity, new AABB(entity));
                    for (Map.Entry<Entity, AABB> entry : entities.entrySet()) {
                        vec3D = entry.getValue().intersectsRay(ray3D, 0, 200);
                        if (vec3D != null) {
                            foundEntity = entry.getKey();
                            hasLineOfSight = finalPlayer.hasLineOfSight(foundEntity);
                            break;
                        }
                    }
                    Set<Material> trans = new HashSet<>();
                    for (Material m : Material.values()) {
                        if (!m.isSolid()) trans.add(m);
                    }
                    Block block = finalPlayer.getTargetBlock(trans, 200);
                    sender.sendMessage(ChatColor.GREEN + "Intersection : " + ((vec3D != null && hasLineOfSight) ? (vec3D.toString() + " , " + foundEntity.toString()) : "none"));
                    if (foundEntity != null && hasLineOfSight) {
                        sender.sendMessage(ChatColor.GREEN + "Distance : " + format.format(finalPlayer.getEyeLocation().distance(foundEntity.getLocation())));
                        if (foundEntity instanceof Damageable) ((Damageable) foundEntity).damage(10);
                    }
                    sender.sendMessage(ChatColor.GREEN + "Block : " + block.getType().toString() + " - " + block.getLocation().toString());
                } else {
                    if (args[0].equalsIgnoreCase("newtrace")) {
                        Player player = null;
                        if (sender instanceof Player) {
                            player = ((Player) sender);
                        } else {
                            if (args.length > 1) {
                                if (Bukkit.getPlayer(args[1]) != null) {
                                    player = Bukkit.getPlayer(args[1]);
                                } else {
                                    System.out.println("Player " + args[1] + " not found!");
                                    return true;
                                }
                            } else {
                                System.out.println("Missing player args!");
                                return true;
                            }
                        }
                        final Player finalPlayer = player;
                        TraceEngine engine = new TraceEngine(finalPlayer);
                        TraceResult result = engine.trace(200);
                        // play bullet trail
                        RayTrace rayTrace = new RayTrace(finalPlayer.getEyeLocation());
                        org.bukkit.util.Vector trail;
                        while ((trail = rayTrace.iterate(1)).distanceSquared(finalPlayer.getEyeLocation().toVector()) < finalPlayer.getEyeLocation().toVector().distanceSquared(result.getHit())) {
                            ParticleEffect.FLAME.display(0.01F, 0.01F, 0.01F, 0.001F, 1, trail.toLocation(finalPlayer.getWorld()), 128);
                        }
                        if (result.found()) {
                            Bukkit.getScheduler().runTaskLater(TalesCore.getPlugin(), () -> {
                                Entity entity = result.getObject().entity();
                                if (entity instanceof Damageable) {
                                    ((Damageable) entity).damage(10);
                                }
                                Block block = result.getObject().block();
                                if (block != null) {
                                    block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType());
                                }
                            }, 5);
                        }
                        //sender.sendMessage(ChatColor.GREEN + result.toString());
                    } else {
                        sender.sendMessage(errorMessage("Sub command not found!"));
                    }
                }
            } else {
                sender.sendMessage(errorMessage("Too few arguments"));
            }
            return true;
        } else {
            return false;
        }
    }

    private String errorMessage(String msg) {
        return ChatColor.RED + "Error : " + msg;
    }
}
