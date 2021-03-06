package com.sagaciouszed.bukkit.samplelocalchat;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

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

    /**
     * When the player quits remove them from the location cache
     * 
     * @param event
     */
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.locationsStore.remove(event.getPlayer().getName());
    }

    /**
     * When the player teleports update their position
     */
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        plugin.locationsStore.put(event.getPlayer().getName(), event.getTo());
    }

    /**
     * Go through the list of recipients and determine if they are closer than
     * the distanceSquared for local chat.
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
                    && playerLocation.getWorld().getUID().equals(recipientLocation.getWorld().getUID())
                    && playerLocation.distanceSquared(recipientLocation) > distanceSquared) {
                iterator.remove();
            }
        }
    }
}
