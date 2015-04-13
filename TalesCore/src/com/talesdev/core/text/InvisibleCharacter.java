package com.talesdev.core.text;

/**
 * Represent an invisible unicode character
 *
 * @author MoKunz
 */
public class InvisibleCharacter {
    private int start = 57344;
    private int id;

    public InvisibleCharacter(int id) {
        if (id < 0) {
            this.id = start;
        } else {
            this.id = start + id;
        }
    }

    public InvisibleCharacter() {
        this.id = start;
    }

    public String asString() {
        String character = "";
        character += (char) id;
        return character;
    }
}
