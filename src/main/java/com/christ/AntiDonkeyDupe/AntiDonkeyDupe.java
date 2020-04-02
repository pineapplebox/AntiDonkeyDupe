package com.christ.AntiDonkeyDupe;

import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketAdapter;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerListener


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;

public class AntiDonkeyDupe extends JavaPlugin {
    @Override
    public void onEnable() {
        this.useProtocolLib();
        System.out.println("[AntiDonkeyDupe] Plugin successfully enabled.");
    }
    public void onDisable() {
        ProtocolLibrary.getProtocolManager().removePacketListeners((Plugin)this);
    }
    private void useProtocolLib() {
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter(this, new PacketType[] { PacketType.Play.Client.STEER_VEHICLE }) {
            public void onPacketReceiving(final PacketEvent event) {
                final PacketType packetType = event.getPacketType();
                if (packetType.equals((Object)PacketType.Play.Client.STEER_VEHICLE)) {
                    Player p = event.getPlayer();
                    if(p.isInsideVehicle()) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        });
    }
    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        Boolean isSneaking = player.isSneaking();
        if(isSneaking) {
            if(player.isInsideVehicle()) {
		        player.eject();
		        return;
	        }
        }
    }
}
