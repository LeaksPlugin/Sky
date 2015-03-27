package com.talesdev.copsandcrims.weapon.bullet;

import org.bukkit.Location;

/**
 * Bullet particle
 * @author MoKunz
 */
public interface BulletParticle {
    /**
     * Create particle in each iteration round
     * @param bullet A bullet
     * @param location A location
     * @param iterationCount An iteration count
     * @param distance Distance between each iteration
     */
    public void createParticle(Bullet bullet,Location location,int iterationCount,double distance);

    /**
     * Create particle when bullet hit something
     * @param bulletHitResult A result of bullet hitting
     */
    public void createHitParticle(BulletHitResult bulletHitResult);
}
