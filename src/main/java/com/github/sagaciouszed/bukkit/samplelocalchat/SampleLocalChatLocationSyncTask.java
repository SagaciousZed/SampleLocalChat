package com.github.sagaciouszed.bukkit.samplelocalchat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        final Map<String, ImmutableLocation> playerLocationMap = new HashMap<String, ImmutableLocation>();
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            playerLocationMap.put(player.getName(), new ImmutableLocation(player.getLocation()));
        }
        plugin.tempLocations = Collections.unmodifiableMap(playerLocationMap);
    }

}
