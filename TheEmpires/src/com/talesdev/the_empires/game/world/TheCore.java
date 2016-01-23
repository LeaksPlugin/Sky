package com.talesdev.the_empires.game.world;

/**
 * @author MoKunz
 */
public interface TheCore extends GameUnit {
    double health();

    void damage(double damage, double penetration);

    void magicDamage(double damage, double magicPen);

    void rawDamage(double damage);
}
