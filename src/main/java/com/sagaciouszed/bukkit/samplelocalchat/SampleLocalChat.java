package com.sagaciouszed.bukkit.samplelocalchat;

import java.util.Collections;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * This is the main class of the sample plug-in
 */
public class SampleLocalChat extends JavaPlugin {

    // There variables are volatile because they will be read from another thread.
    volatile boolean enabled;
    volatile Map<String, Location> locationsStore = Collections.emptyMap();

    /*
     * This is called when your plug-in is enabled
     */
    @Override
    public void onEnable() {
        // save the default configuration file
        saveDefaultConfig();

        enabled = getConfig().getBoolean("localchat.enabled");

        // set the command executor for setLocalChat
        this.getCommand("setLocalChat").setExecutor(new SampleLocalChatCommandExecutor(this));

        // Create the Listener
        new SampleLocalChatListener(this, getConfig().getDouble("localchat.distance"));

        // Schedule the repeating sync task
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new SampleLocalChatLocationSyncTask(this), 0, 20);
    }

    @Override
    public void onDisable() {
        locationsStore.clear();
        getServer().getScheduler().cancelTasks(this);
    }

}
