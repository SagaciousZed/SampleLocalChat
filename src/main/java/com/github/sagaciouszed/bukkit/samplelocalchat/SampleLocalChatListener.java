package com.github.sagaciouszed.bukkit.samplelocalchat;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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
     * Go through the list of recipients and determine if they are closer than
     * the local distanceSquared
     * if not remove them
     * 
     * @param event
     */
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        final AtomicBoolean enabled = plugin.enabled;
        final AtomicReference<ConcurrentHashMap<String, ImmutableLocation>> tempLocations = plugin.tempLocations;
        if (enabled == null
                || tempLocations == null
                || enabled.get())
            return;
        
        final ConcurrentHashMap<String, ImmutableLocation> locations = tempLocations.get();
        if (locations == null)
            return;

        final Iterator<Player> iterator = event.getRecipients().iterator();
        final ImmutableLocation playerLocation = locations.get(event.getPlayer().getName());

        while (iterator.hasNext()) {
            final Player recipient = iterator.next();
            final ImmutableLocation recipientLocation = locations.get(recipient.getName());
            if (recipientLocation != null
                    && playerLocation != null
                    && playerLocation.distanceSquared(recipientLocation) > distanceSquared) {
                iterator.remove();
            }
        }
    }
}
