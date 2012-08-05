package com.github.sagaciouszzzz.bukkit.samplelocalchat;

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
        ConcurrentHashMap<String, ImmutableLocation> concurrentHashMap = new ConcurrentHashMap<String, ImmutableLocation>();
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            concurrentHashMap.put(player.getName(), new ImmutableLocation(player.getLocation()));
        }
        plugin.tempLocations.set(concurrentHashMap);
    }

}
