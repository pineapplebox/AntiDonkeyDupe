package com.christ.AntiDonkeyDupe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiDupe extends JavaPlugin implements Listener {
    
    @Override
	public void onLoad() {
		plugin = this;

		getLogger().info("[AntiDonkeyDupe] Successfully Enabled");
	}
    
    @Override
    public void onEnable() {
        ProtocolLibrary.getProtocolManager().addPacketListener(
            new PacketAdapter(this, PacketType.Play.Client.CPacketInput) {
            @Override
              
                public void onTime(final Player p) {
                    Bukkit.getScheduler().runTaskLater(Core.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        if(p.isInsideVehicle()) {
                            if (p.getVehicle() != null) {
                                public void onPacketReceiving(PacketEvent event) {
                                    if (event.getPacketType() == PacketType.Play.Client.CPacketInput) {
                                        event.setCancelled(true);
                                    }
                                }
                            }
                        }
                    }
                    },20L);
                }
            
            });
    }
    
}
