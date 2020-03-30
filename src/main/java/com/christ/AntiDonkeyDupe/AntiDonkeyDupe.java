package com.christ.AntiDonkeyDupe;

import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketAdapter;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.entity;

public class AntiDonkeyDupe extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("[AntiDonkeyDupe] Successfully Enabled");
    }
    public void onDisable() {
        ProtocolLibrary.getProtocolManager().removePacketListeners((Plugin)this);
    }
    private void protocolPacketListener() {
        ProtocolLibrary.getProtocolManager().addPacketListener(
          (PacketListener)new PacketAdapter(this, new PacketType[] { PacketType.Play.Client.CPacketInput }) { public void onPacketReceiving(final PacketEvent event) {
                if (event.getPacketType().equals((Object)PacketType.Play.Client.CPacketInput)) {
                    Player p = event.getPlayer();
                    if(p.isInsideVehicle()) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        });
    }
}
