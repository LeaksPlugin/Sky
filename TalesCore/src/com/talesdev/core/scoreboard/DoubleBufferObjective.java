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
        //System.out.println("DoubleBufferObjective constructed");
        //System.out.println("primary var : " + primary.getName());
        //System.out.println("secondary var : " + secondary.getName());
        //System.out.println("Now using " + primary.getName() + " for writing");
    }

    public Objective getObjective() {
        return primary;
    }

    public void startWriting() {
        //System.out.println("Now starting writing in " + primary.getName() + " buffer");
        writing = true;
        //System.out.println("Debugging info (Current objective buffer) : " + objectiveName);
    }

    public void writeTitle(String titleName) {
        if (!writing) {
            throw new IllegalStateException("You must call startWriting before make change in scoreboard!");
        }
        //System.out.println("Writing title \"" + titleName + "\" to " + getObjective().getName() + " buffer");
        getObjective().setDisplayName(titleName);
    }

    public void write(int line, String message) {
        if (!writing) {
            throw new IllegalStateException("You must call startWriting before make change in scoreboard!");
        }
        //System.out.println("Writing \"" + message + "\" (line : " + line + ") to " + getObjective().getName() + " buffer");
        getObjective().getScore(message).setScore(line);
    }

    public void endWriting() {
        //System.out.println("Sending " + getObjective().getName() + "buffer to player");
        getObjective().setDisplaySlot(getDisplaySlot());
        // writing in MoKunz-primary
        // obj primary = MoKunz-primary , obj secondary = MoKunz-secondary;
        swapBuffer();
        // writing in MoKunz-secondary
        // obj primary = MoKunz-secondary , obj secondary = MoKunz-primary;
        primary.unregister();
        if (writingPos.equals(Pos.PRIMARY)) {
            primary = scoreboard.registerNewObjective(uName + "", "dummy");
        } else if (writingPos.equals(Pos.SECONDARY)) {
            primary = scoreboard.registerNewObjective(uName + "-", "dummy");
        }
        //System.out.println("Buffer swapped");
        //System.out.println("primary var : " + primary.getName());
        //System.out.println("secondary var : " + secondary.getName());
        //System.out.println("Now using " + primary.getName() + " for writing");
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
