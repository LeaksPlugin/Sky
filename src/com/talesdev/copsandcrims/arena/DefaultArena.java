package com.talesdev.copsandcrims.arena;

/**
 * Default Arena for testing
 * @author MoKunz
 */
public class DefaultArena {
    public static CvCArena instance;
    public static CvCArena getInstance(){
        if(instance == null) instance = new CvCArena("DefaultArena", new CvCArenaController("Defusal") {
            @Override
            public void startArena() {
                System.out.println("Arena starting...");
            }

            @Override
            public void endArena() {
                System.out.println("Arena ended!");
            }
        });
        return instance;
    }
}
