package com.christ.AntiDonkeyDupe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiDonkeyDupe extends JavaPlugin implements Listener {
    
    @Override
	public void onLoad() {
		plugin = this;

		getLogger().info("[AntiDonkeyDupe] Successfully Enabled");
	}

    @Override
    public void onEnable() {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public void onTime(final Player p) {
        Bukkit.getScheduler().runTaskLater(Core.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(p.isInsideVehicle()) {
                    if (p.getVehicle() != null) {
                        protocolManager.addPacketListener(
                            new PacketAdapter(this, ListenerPriority.NORMAL, 
                            PacketType.Play.Client.CPacketInput) {
                            @Override
                                private void onPacketReceiving(PacketEvent event) {
                                    if (event.getPacketType() == 
                                    PacketType.Play.Client.CPacketInput) {
                                        event.setCancelled(true);
                                    }
                            }
                            break onPacketReceiving;
                        });
                    }
                }
            }
        },20L);
    }
}
