package com.sagaciouszed.bukkit.samplelocalchat;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * This is the main class of the sample plug-in
 */
public class SampleLocalChat extends JavaPlugin {

    // There variables are volatile because they will be read from another
    // thread.
    volatile boolean enabled;
    final ConcurrentHashMap<String, Location> locationsStore = new ConcurrentHashMap<String, Location>();

    /*
     * Called when plugin is enabled.
     */
    @Override
    public void onEnable() {
        // save the default configuration file
        saveDefaultConfig();

        // Check config to see if the plugin should restrict to local chat
        // immediately.
        enabled = getConfig().getBoolean("localchat.enabled");

        // set the command executor for setLocalChat
        this.getCommand("setLocalChat").setExecutor(new SampleLocalChatCommandExecutor(this));

        // Create the Listener
        new SampleLocalChatListener(this, getConfig().getDouble("localchat.distance"));

        // Schedule the Location polling task every other second.
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new SampleLocalChatLocationSyncTask(this), 0, 40);
    }

    /**
     * Called when the plugin is disabled.
     * Clears locationStore.
     */
    @Override
    public void onDisable() {
        locationsStore.clear();
        getServer().getScheduler().cancelTasks(this);
    }

}
