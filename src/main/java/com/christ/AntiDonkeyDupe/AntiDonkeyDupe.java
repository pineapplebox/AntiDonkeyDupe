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
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;

public class AntiDonkeyDupe extends JavaPlugin {
    @Override
    public void onEnable() {
        this.setupProtocolLibrary();
        System.out.println("[Multi-Patch] Plugin successfully enabled.");
    }
    public void onDisable() {
        ProtocolLibrary.getProtocolManager().removePacketListeners((Plugin)this);
    }
    private void setupProtocolLibrary() {
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter(this, new PacketType[] { PacketType.Play.Client.TAB_COMPLETE }) {
            public void onPacketReceiving(final PacketEvent p) {
                final PacketType packetType = p.getPacketType();
                if (packetType.equals((Object)PacketType.Play.Client.TAB_COMPLETE)) {
                    final PacketContainer packetContainer = p.getPacket();
                    final String msg = ((String)packetContainer.getSpecificModifier((Class<?>)String.class).read(0)).toLowerCase();
                    if (msg.equals("/")) {
                        p.setCancelled(true);
                        return;
                    }
                }
            }
        });
        
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
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPortalCreate(final PortalCreateEvent e) {
        ArrayList<BlockState> b = (ArrayList<BlockState>) e.getBlocks();
        World w = e.getWorld();
        int height = b.get(0).getLocation().getBlockY();
        if (height >= 128 && w.getName().endsWith("_nether")) {
                e.setCancelled(true);
        }
    }
}
