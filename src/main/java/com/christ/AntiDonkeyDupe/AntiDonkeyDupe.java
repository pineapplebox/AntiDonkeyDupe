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
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.*;
import org.bukkit.event.world.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

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
        ProtocolLibrary.getProtocolManager().removePacketListeners((Plugin)this);
        System.out.println("[AntiDonkeyDupe] Plugin successfully disabled.");
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
                                    getLogger().info(ChatColor.RED + "Prevented packet entity dismount from player: " + p.getName());
                                    return;
                                }
                            }, 2L);
                        }, 1L);
                    }
                }
            }
        });
    }
    
    public void onContact(final EntityPortalEvent event) {
        final World world = event.getTo().getWorld();
        if (world.getName().endsWith("_end")) {
            if (event.getEntity() instanceof Mule || event.getEntity() instanceof Donkey || event.getEntity() instanceof Llama || event.getEntity() instanceof Zombie) {
                final Entity entity = (Entity) event.getEntity();
                double health = ((LivingEntity) entity).getHealth();
                if(health <= 2) {
                    event.setCancelled(true);
                }
            }
            return;
        } else if (world.getName().endsWith("_nether")) {
            if (event.getEntity() instanceof Mule || event.getEntity() instanceof Donkey || event.getEntity() instanceof Llama || event.getEntity() instanceof Horse) {
                final Entity entity = (Entity) event.getEntity();
                if(entity.isEmpty() != true) {
                    entity.eject();
                }
            }
        }
    }
}
