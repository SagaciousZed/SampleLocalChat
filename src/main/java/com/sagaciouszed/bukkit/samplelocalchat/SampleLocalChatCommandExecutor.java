package com.sagaciouszed.bukkit.samplelocalchat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/*
 * This is a sample CommandExectuor
 */
public class SampleLocalChatCommandExecutor implements CommandExecutor {
    private final SampleLocalChat plugin;

    /*
     * This command executor needs to know about its plugin from which it came from
     */
    public SampleLocalChatCommandExecutor(SampleLocalChat plugin) {
        this.plugin = plugin;
    }

    /*
     * On command set the sample message
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            plugin.enabled = Boolean.parseBoolean(args[0]);
            plugin.getServer().broadcastMessage(String.format("Local Chat Only: %s", plugin.enabled));
            return true;
        } else {
            return false;
        }
    }

}
