package com.sagaciouszed.bukkit.samplelocalchat;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SampleLocalChatLocationSyncTask implements Runnable {

    private final SampleLocalChat plugin;

    public SampleLocalChatLocationSyncTask(SampleLocalChat plugin) {
        this.plugin = plugin;
    }

    /**
     * Go through every player, and store their location
     */
    public void run() {
        final Map<String, Location> playerLocationMap = plugin.locationsStore;
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            playerLocationMap.put(player.getName(), player.getLocation());
        }
    }

}
