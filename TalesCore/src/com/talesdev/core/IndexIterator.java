package com.talesdev.core;

/**
 * Index iterator
 *
 * @author MoKunz
 */
public class IndexIterator {
    private int pos;
    private int size;

    public IndexIterator(int size) {
        this.pos = 0;
        this.size = size;
    }

    public int next() {
        int maxIndex = size - 1;
        pos++;
        if (pos > maxIndex) {
            pos = 0;
        }
        return pos;
    }

    public void reset() {
        pos = 0;
    }
}
