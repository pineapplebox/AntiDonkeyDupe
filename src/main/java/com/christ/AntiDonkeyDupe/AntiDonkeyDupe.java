public class AntiDupe extends JavaPlugin {
    
    @Override
    public void onLoad() {
	    plugin = this;

	    getLogger().info("[AntiDonkeyDupe] Successfully Enabled");
    }
	
    private ProtocolManager protocolManager;
  
    @Override
    public void onEnable() {
    	protocolManager = ProtocolLibrary.getProtocolManager();
    }
	
    public void onTime(final Player p) {
            Bukkit.getScheduler().runTaskLater(Core.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if(p.isInsideVehicle()) {
    			    protocolManager.addPacketListener(
      		    	    new PacketAdapter(this, ListenerPriority.NORMAL, 
              			    PacketType.CPacketInput) {
        			    @Override
        			    public void onPacketSending(PacketEvent event) {
            				    if (event.getPacketType() == 
                    			    PacketType.CPacketInput) {
                				    event.setCancelled(true);
            				    }
        			    }
    				    });
                    }
                }
            },20L);
    }
}
