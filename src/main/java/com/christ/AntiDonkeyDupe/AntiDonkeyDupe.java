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
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.EntityType;

import net.minecraft.server.v1_12_R1.PacketPlayInSteerVehicle;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;

public class AntiDonkeyDupe extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        this.useProtocolLib();
        System.out.println("[AntiDonkeyDupe] Plugin successfully enabled.");
    }
    public void onDisable() {
        System.out.println("[AntiDonkeyDupe] Plugin successfully disabled.");
        ProtocolLibrary.getProtocolManager().removePacketListeners((Plugin)this);
    }
    private void useProtocolLib() {
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter(this, new PacketType[] { PacketType.Play.Client.STEER_VEHICLE }) {
            public void onPacketReceiving(final PacketEvent event) {
                if(event.getPacketType().equals(PacketType.Play.Client.STEER_VEHICLE)){
                    Player p = event.getPlayer();
                    Entity vehicle = p.getVehicle();
                    PacketPlayInSteerVehicle ppisv = (PacketPlayInSteerVehicle) event.getPacket().getHandle();
                    boolean shift = ppisv.d();
                    event.setCancelled(true);
                    if (shift == true){
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            vehicle.eject();
                            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                if(p.isSneaking()) {
                                    return;
                                } else {
                                    vehicle.addPassenger(p);
                                }
                            }, 3L);
                        }, 2L);
                    }
                }
            }
        });
    }
}
