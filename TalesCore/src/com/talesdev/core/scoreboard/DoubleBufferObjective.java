package com.talesdev.core.scoreboard;

import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Double Buffer primary
 *
 * @author MoKunz
 */
@Deprecated
public class DoubleBufferObjective {
    private Objective primary;
    private Objective secondary;
    private String name;
    private String uName = "sidebar-obj";
    private Scoreboard scoreboard;
    private boolean writing = false;
    private Pos writingPos = Pos.PRIMARY;
    private DisplaySlot displaySlot = DisplaySlot.SIDEBAR;

    public DoubleBufferObjective(String name, Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
        this.name = name;
        this.primary = scoreboard.registerNewObjective(uName + "", "dummy");
        this.secondary = scoreboard.registerNewObjective(uName + "-", "dummy");
        this.writingPos = Pos.PRIMARY;
    }

    public Objective getObjective() {
        return primary;
    }

    public void startWriting() {
        writing = true;
    }

    public void writeTitle(String titleName) {
        if (!writing) {
            throw new IllegalStateException("You must call startWriting before make change in scoreboard!");
        }
        getObjective().setDisplayName(titleName);
    }

    public void write(int line, String message) {
        if (!writing) {
            throw new IllegalStateException("You must call startWriting before make change in scoreboard!");
        }
        getObjective().getScore(message).setScore(line);
    }

    public void endWriting() {
        getObjective().setDisplaySlot(getDisplaySlot());
        swapBuffer();
        primary.unregister();
        if (writingPos.equals(Pos.PRIMARY)) {
            primary = scoreboard.registerNewObjective(uName + "", "dummy");
        } else if (writingPos.equals(Pos.SECONDARY)) {
            primary = scoreboard.registerNewObjective(uName + "-", "dummy");
        }
        writing = false;
    }

    public void swapBuffer() {
        Objective temp = primary;
        primary = secondary;
        secondary = temp;
        if (writingPos.equals(Pos.PRIMARY)) {
            this.writingPos = Pos.SECONDARY;
        } else if (writingPos.equals(Pos.SECONDARY)) {
            this.writingPos = Pos.PRIMARY;
        }
    }

    public Pos getWritingPos() {
        return this.writingPos;
    }

    public void reset() {
        if (writing) {
            throw new IllegalStateException("You can't reset primary while writing!");
        }
        secondary.unregister();
        primary.unregister();
        this.primary = scoreboard.registerNewObjective(uName, "dummy");
        this.secondary = scoreboard.registerNewObjective(uName + "-", "dummy");
        this.writingPos = Pos.PRIMARY;
    }

    public DisplaySlot getDisplaySlot() {
        return displaySlot;
    }

    public void setDisplaySlot(DisplaySlot displaySlot) {
        this.displaySlot = displaySlot;
    }

    enum Pos {
        PRIMARY, SECONDARY
    }
}
