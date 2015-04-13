package com.talesdev.core.scoreboard;

import com.talesdev.core.text.InvisibleCharacter;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A class represent an wrapped objective
 *
 * @author MoKunz
 */
public class WrappedSidebarObjective {
    private String title = "";
    private String name;
    private Map<Integer, String> scoreMap = new HashMap<>();
    private int maxLine = 15;

    public WrappedSidebarObjective(String name) {
        this(name, 15);
    }

    public WrappedSidebarObjective(String name, int maxLine) {
        this.name = name;
        this.maxLine = maxLine;
    }

    public void setLine(int line, String message) {
        getScoreMap().put(line, message);
    }

    public void removeLine(int line) {
        getScoreMap().remove(line);
    }

    public String getLine(int line) {
        return getScoreMap().get(line);
    }

    public Collection<String> getLines() {
        return getScoreMap().values();
    }

    public void swapLine(int firstLine, int secondLine) {
        if (!getScoreMap().containsKey(firstLine) || !getScoreMap().containsKey(secondLine)) return;
        String first = getScoreMap().get(firstLine);
        String second = getScoreMap().get(secondLine);
        getScoreMap().put(firstLine, second);
        getScoreMap().put(secondLine, first);
    }

    public int lineOf(String message) {
        for (Map.Entry<Integer, String> scoreEntry : getScoreMap().entrySet()) {
            if (scoreEntry.getValue().equalsIgnoreCase(message)) {
                return scoreEntry.getKey();
            }
        }
        return -1;
    }

    public void applyTo(Objective objective) {
        objective.setDisplayName(getTitle());
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for (Map.Entry<Integer, String> scoreEntry : prePassMap(getScoreMap()).entrySet()) {
            objective.getScore(scoreEntry.getValue()).setScore(scoreEntry.getKey());
        }
    }

    public void applyTo(Scoreboard scoreboard) {
        Objective objective = scoreboard.registerNewObjective(getName(), "dummy");
        objective.setDisplayName(getTitle());
        applyTo(objective);
    }

    public void reset(Objective objective) {
        objective.unregister();
    }

    public void reset(Scoreboard scoreboard) {
        scoreboard.getObjective(getName()).unregister();
    }

    private Map<Integer, String> prePassMap(Map<Integer, String> map) {
        Map<Integer, String> prePassedMap = new HashMap<>();
        if (map.size() > 0) {
            for (int i = 0; i <= getMaxLine(); i++) {
                if (map.get(i) != null) {
                    prePassedMap.put(i, ChatColor.RESET + new InvisibleCharacter(i).asString() + map.get(i));
                } else {
                    prePassedMap.put(i, ChatColor.RESET + new InvisibleCharacter(i).asString());
                }
            }
        }
        return prePassedMap;
    }

    public int totalLines() {
        return getScoreMap().size();
    }

    public Map<Integer, String> getScoreMap() {
        return scoreMap;
    }

    public int getMaxLine() {
        return maxLine;
    }

    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
