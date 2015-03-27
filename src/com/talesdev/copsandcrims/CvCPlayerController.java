package com.talesdev.copsandcrims;

/**
 * Interface for hooking with player in arena
 * @author MoKunz
 */
public interface CvCPlayerController {
    public void onPlayerAdded(CvCPlayer player);
    public void onPlayerRemoved(CvCPlayer player);
}
