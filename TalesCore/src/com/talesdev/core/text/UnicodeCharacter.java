package com.talesdev.core.text;

/**
 * Represent an unicode character
 *
 * @author MoKunz
 */
public class UnicodeCharacter {
    private int unicodeNumber;

    public UnicodeCharacter(int unicodeNumber) {
        this.unicodeNumber = unicodeNumber;
    }

    public UnicodeCharacter(String hexCode) {
        String regex = "([a-fA-F0-9]{4})";
        if (hexCode.matches(regex)) {
            this.unicodeNumber = Integer.parseInt(hexCode.toUpperCase(), 16);
        }
    }

    @Override
    public String toString() {
        return Character.toString((char) unicodeNumber);
    }
}
