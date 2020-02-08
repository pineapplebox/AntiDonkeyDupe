public class AntiDupe extends JavaPlugin {
    @Override
    public void onEnable() {
        ProtocolLibrary.getProtocolManager().addPacketListener(
            new PacketAdapter(this, PacketType.CPacketInput) {
            @Override
              
                public void onTime(final Player p) {
                    Bukkit.getScheduler().runTaskLater(Core.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        if(p.isInsideVehicle()) {
                            if (p.getVehicle() != null) {
                                public void onPacketReceiving(PacketEvent event) {

                                    if (event.getPacketType() == PacketType.CPacketInput) {
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
