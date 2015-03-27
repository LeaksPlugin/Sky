package com.talesdev.copsandcrims.weapon.bullet;

/**
 * Bullet listener
 *
 * @author MoKunz
 */
public interface BulletListener{
    /**
     * Called when bullet is constructed
     * @param bullet A bullet
     */
    public void bulletCreated(Bullet bullet);

    /**
     * Called before bullet firing start
     * @param bullet A bullet
     */
    public void prepareFiring(Bullet bullet);

    /**
     * Called when bullet is fired
     * @param bullet A bullet
     * @param iterationCount An iteration round
     * @param distance A distance
     */
    public void bulletFire(Bullet bullet,int iterationCount,double distance);

    /**
     * Called when bullet hits object
     * @param bulletHitResult A result of bullet hitting
     */
    public void bulletHitObject(BulletHitResult bulletHitResult);

    /**
     * Called when bullet completed its iteration in each round
     * @param bullet A bullet
     * @param iterationCount An iteration round
     * @param distance A distance
     */
    public void afterBulletFire(Bullet bullet,int iterationCount,double distance);

    /**
     * Called when bullet firing is stopped
     * @param bullet A bullet
     */
    public void finishFiring(Bullet bullet);
}
