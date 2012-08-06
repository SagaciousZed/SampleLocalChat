package com.github.sagaciouszed.bukkit.samplelocalchat;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/*
 * This is a sample event listener
 */
public class SampleLocalChatListener implements Listener {

    private final SampleLocalChat plugin;
    private final double distanceSquared;

    /*
     * This listener needs to know about the plugin which it came from
     */
    public SampleLocalChatListener(SampleLocalChat plugin, double distanceSquared) {
        // Register the listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        this.plugin = plugin;
        this.distanceSquared = distanceSquared;
    }
    
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.locationsStore.remove(event.getPlayer().getName());
    }

    /**
     * Go through the list of recipients and determine if they are closer than
     * the local distanceSquared
     * if not remove them
     * 
     * @param event
     */
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        final boolean enabled = plugin.enabled;
        final ConcurrentHashMap<String, Location> locations = plugin.locationsStore;
        if (!enabled || locations == null)
            return;

        final Iterator<Player> iterator = event.getRecipients().iterator();
        final Location playerLocation = locations.get(event.getPlayer().getName());

        while (iterator.hasNext()) {
            final Player recipient = iterator.next();
            final Location recipientLocation = locations.get(recipient.getName());
            if (recipientLocation != null
                    && playerLocation != null
                    && playerLocation.getWorld().getName().equals(recipientLocation.getWorld().getName())
                    && playerLocation.distanceSquared(recipientLocation) > distanceSquared) {
                iterator.remove();
            }
        }
    }
}
