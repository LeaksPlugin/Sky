package com.talesdev.copsandcrims;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class for testing algorithm
 *
 * @author MoKunz
 */
public class Test {
    public static void main(String[] args) {
        List<String> team = new ArrayList<>();
        team.add("CT");
        team.add("T");
        List<String> players = new ArrayList<>();
        players.add("MoKunz");
        players.add("Astraea");
        players.add("zeus136");
        players.add("NeoTerminate");
        int pos = 0;
        int max = team.size() - 1;
        System.out.println("Max : " + max);
        for (String player : players) {
            if (pos > max) {
                System.out.println("Reset pos to 0");
                pos = 0;
            }
            System.out.println("Current pos : " + pos);
            System.out.println(player + " : " + team.get(pos));
            pos++;
        }
        pos = 0;
    }
}