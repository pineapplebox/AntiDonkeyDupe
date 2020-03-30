package com.christ.AntiDonkeyDupe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiDupe extends JavaPlugin {
    
    @Override
	public void onLoad() {
		plugin = this;

		getLogger().info("[AntiDonkeyDupe] Successfully Enabled");
	}
    
    @Override
    public void onEnable() {
        ProtocolLibrary.getProtocolManager().addPacketListener(
            new PacketAdapter(this, PacketType.CPacketInput) {
            @Override
                public void onPacketReceiving(PacketEvent event) {
                    Player p = event.getPlayer();
                        Bukkit.getScheduler().runTaskLater(Core.getInstance(), new Runnable() {
                        @Override
                            if(p.isInsideVehicle()) {
                                if (event.getPacketType() == PacketType.CPacketInput) {
                                    event.setCancelled(true);
                                }
                            }
                        },20L);
                    }
                }
            });
    }
}
