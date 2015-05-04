package com.talesdev.core.arena;

/**
 * Multi kill word
 *
 * @author MoKunz
 */
public enum MultiKill {
    SINGLE(1, ""),
    DOUBLE(2, "Double"),
    TRIPLE(3, "Triple"),
    QUADRA(4, "Quadra"),
    PENTA(5, "Penta"),
    HEXA(6, "Hexa"),
    LEGENDARY(7, "Legendary");
    private int kills;
    private String word;

    MultiKill(int kills, String word) {
        this.kills = kills;
        this.word = word;
    }

    public static MultiKill get(int kills) {
        switch (kills) {
            case 1:
                return SINGLE;
            case 2:
                return DOUBLE;
            case 3:
                return TRIPLE;
            case 4:
                return QUADRA;
            case 5:
                return PENTA;
            case 6:
                return HEXA;
            case 7:
                return LEGENDARY;
            default:
                if (kills < 1) {
                    return SINGLE;
                } else if (kills > 7) {
                    return LEGENDARY;
                } else {
                    return SINGLE;
                }
        }
    }

    public String getWord() {
        return word;
    }

    public int getRequired() {
        return kills;
    }
}
