package com.talesdev.copsandcrims.weapon;

import com.talesdev.copsandcrims.weapon.bullet.Bullet;
import com.talesdev.copsandcrims.weapon.bullet.BulletHitResult;
import com.talesdev.copsandcrims.weapon.bullet.BulletListener;

/**
 * Test
 */
public class DefaultBulletListener implements BulletListener {
    @Override
    public void bulletCreated(Bullet bullet) {

    }

    @Override
    public void prepareFiring(Bullet bullet) {

    }

    @Override
    public void bulletFire(Bullet bullet, int iterationCount, double distance) {

    }

    @Override
    public void bulletHitObject(BulletHitResult bulletHitResult) {

    }

    @Override
    public void afterBulletFire(Bullet bullet, int iterationCount, double distance) {

    }

    @Override
    public void finishFiring(Bullet bullet) {

    }
}
