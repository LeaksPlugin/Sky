package com.talesdev.copsandcrims.arena.data;

/**
 * Enum represent status of arena
 */
public enum ArenaStatus {
    // waiting for player
    WAITING,
    // counting down before starting
    COUNTDOWN,
    // running
    RUNNING,
    // ended and arena is cleaning itself
    END
}