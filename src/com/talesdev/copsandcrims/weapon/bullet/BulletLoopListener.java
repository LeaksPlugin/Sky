package com.talesdev.copsandcrims.weapon.bullet;

/**
 * Bullet loop listener
 * @author MoKunz
 */
public interface BulletLoopListener {
    /**
     * Called when bullet loop is started
     * @param bullet A bullet
     */
    public void loopStarted(Bullet bullet);

    /**
     * Called when bullet iteration passed each round
     * @param bullet A bullet
     * @param iterationCount Iteration count
     */
    public void eachLoopFinished(Bullet bullet,int iterationCount);

    /**
     * Called when bullet loop is canceled by something
     * @param bullet A bullet
     * @param iterationCount Iteration count before cancel
     */
    public void loopCanceled(Bullet bullet,int iterationCount);

    /**
     * Called when bullet loop is ended
     * @param bullet A bullet
     */
    public void loopEnded(Bullet bullet);
}
