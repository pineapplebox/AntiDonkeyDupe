package com.christ.AntiDonkeyDupe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiDonkeyDupe extends JavaPlugin {
    
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
            public void onPacketReceiving(PacketEvent event) {
                Player p = event.getPlayer();
                if(p.isInsideVehicle()) {
                    if (event.getPacketType() == PacketType.Play.Client.CPacketInput) {
                        event.setCancelled(true);
                    }
                }
            }
        });
    }
}
