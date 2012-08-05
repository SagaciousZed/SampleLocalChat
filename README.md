SampleBukkitPlugin
======

This is a sample plug-in for the Bukkit API. It requires a CraftBukkit server.
Bukkit can be found at [http://bukkit.org](http://bukkit.org) 

The goal of this sample plug-in is to demonstrate the work necessary for thread safety. This
plugin has a main class named SampleLocalChat, one CommandExecutor named
SampleLocalChatCommandExectuor, one Listener named SampleLocalChatListener,
one Runnable SampleLocalChatLocationSyncTask, and one utility class ImmutableLocation.

This plug-in provides the ability for a login message to be sent to players
connecting. A player with the "samplelocalchat.set" permission can set if local chat is enforced.
Ops by default have this permission.

Commands
----------

This plugin has one command: setLocalChat takes a argument which is a boolean string.
true will set it to true, all other strings will set set enabled to false. This change
is only temporary, and will returned to configured value when disabled and enabled.

Configuration
-------------

There are two configuration options: localchat.enabled and localchat.localrage.

* localchat.enabled is the default value on startup. 
* localchat.localrange is the distance squared that chat can be read.

Compilation
-----------

This plugin has a Maven 3 pom.xml and uses Maven to compile. Dependencies are 
therefore managed by Maven. You should be able to build it with Maven by running

    mvn package

a jar will be generated in the target folder. For those unfa1milliar with Maven
it is a build system, see http://maven.apache.org/ for more information.
