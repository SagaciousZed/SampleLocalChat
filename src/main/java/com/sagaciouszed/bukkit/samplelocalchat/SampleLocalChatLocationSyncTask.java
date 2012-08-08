package com.sagaciouszed.bukkit.samplelocalchat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SampleLocalChatLocationSyncTask implements Runnable {

    private final SampleLocalChat plugin;

    public SampleLocalChatLocationSyncTask(SampleLocalChat plugin) {
        this.plugin = plugin;
    }

    /**
     * Go through every player, and store the important parts of their location
     */
    public void run() {
        final Map<String, Location> playerLocationMap= new HashMap<String, Location>();
        final Player[] players = plugin.getServer().getOnlinePlayers();
        for (Player player : players) {
            playerLocationMap.put(player.getName(), player.getLocation());
        }
        plugin.locationsStore = Collections.unmodifiableMap(playerLocationMap);
    }

}
