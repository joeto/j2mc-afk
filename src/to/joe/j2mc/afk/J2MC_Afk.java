package to.joe.j2mc.afk;

import java.util.HashSet;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class J2MC_Afk extends JavaPlugin implements Listener {

    private final HashSet<String> names = new HashSet<String>();

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new AfkCheck(), 6000, 6000);
    }

    private class AfkCheck implements Runnable {

        @Override
        public void run() {
            for (final Player player : J2MC_Afk.this.getServer().getOnlinePlayers()) {
                if ((player != null) && !J2MC_Afk.this.names.contains(player.getName())) {
                    player.kickPlayer("Kicked for idling");
                }
            }
            J2MC_Afk.this.names.clear();
        }

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        this.names.add(event.getPlayer().getName());
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        this.names.add(event.getPlayer().getName());
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        this.names.add(event.getPlayer().getName());
    }
}
