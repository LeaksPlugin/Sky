package com.talesdev.core.scoreboard;

import com.talesdev.core.text.InvisibleCharacter;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

/**
 * A class represent an wrapped simpleScoreboard
 *
 * @author MoKunz
 */
public class WrappedScoreboard {
    private String title = "";
    private int maxLine;
    private Scoreboard scoreboard;
    private SimpleScoreboard simpleScoreboard;

    public WrappedScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
        simpleScoreboard = new SimpleScoreboard(this.title, this.scoreboard);
    }

    public void setLine(int line, String message) {
        simpleScoreboard.add(message, line);
    }

    public void setBlankLine(int... lines) {
        for (int line : lines) {
            simpleScoreboard.add(new InvisibleCharacter(line).asString(), line);
        }
    }

    public void update() {
        simpleScoreboard.update();
    }

    public void reset() {
        simpleScoreboard.reset();
        scoreboard.clearSlot(DisplaySlot.SIDEBAR);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
        simpleScoreboard.setTitle(this.title);
    }

    public int getMaxLine() {
        return maxLine;
    }

    public void setMaxLine(int maxLine) {
        reset();
        this.maxLine = maxLine;
    }
}
