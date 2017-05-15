package net.poweredbyscience.parrotrider;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Parrotrider extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent ev) {
        if (ev.getPlayer().getInventory().getItemInMainHand().getType() == Material.SADDLE && ev.getPlayer().hasPermission("parrot.rider")) {
            if (ev.getRightClicked().getType() == EntityType.valueOf("PARROT")) {
                ev.getRightClicked().setPassenger(ev.getPlayer());
            }
        }
    }
}
