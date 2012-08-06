package com.github.sagaciouszed.bukkit.samplelocalchat;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.bukkit.plugin.java.JavaPlugin;

/*
 * This is the main class of the sample plug-in
 */
public class SampleLocalChat extends JavaPlugin {

    volatile boolean enabled;
    volatile Map<String, ImmutableLocation> tempLocations;

    /*
     * This is called when your plug-in is enabled
     */
    @Override
    public void onEnable() {
        // save the default configuration file
        saveDefaultConfig();

        tempLocations = Collections.emptyMap();
        enabled = getConfig().getBoolean("localchat.enabled");

        // set the command executor for setLocalChat
        this.getCommand("setLocalChat").setExecutor(new SampleLocalChatCommandExecutor(this));

        // Create the Listener
        new SampleLocalChatListener(this, getConfig().getDouble("localchat.distance"));

        // Schedule the repeating sync task
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new SampleLocalChatLocationSyncTask(this), 0, 1);
    }

    @Override
    public void onDisable() {
        tempLocations = null;
        getServer().getScheduler().cancelTasks(this);
    }

}
