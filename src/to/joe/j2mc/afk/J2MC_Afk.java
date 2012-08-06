package to.joe.j2mc.afk;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;

public class J2MC_Afk extends JavaPlugin implements Listener {

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

    private final Set<String> names = Collections.newSetFromMap(new HashMap<String, Boolean>());

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        this.names.add(event.getPlayer().getName());
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        this.names.add(event.getPlayer().getName());
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new AfkCheck(), 6000, 6000);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            return;
        }
        this.names.add(event.getPlayer().getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.names.add(event.getPlayer().getName());
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        this.names.add(event.getPlayer().getName());
    }

    @EventHandler
    public void onSprint(PlayerToggleSprintEvent event) {
        this.names.add(event.getPlayer().getName());
    }
}
