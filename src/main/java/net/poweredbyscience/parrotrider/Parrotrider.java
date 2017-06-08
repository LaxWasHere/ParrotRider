package net.poweredbyscience.parrotrider;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
                ev.getRightClicked().addPassenger(ev.getPlayer());
            }
        }
    }

    @EventHandler
    public void onFire(EntityShootBowEvent ev) {
        if (ev.getEntity() instanceof Player) {
            Player p = (Player) ev.getEntity();
            if (p.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.OXYGEN) == 5 && p.hasPermission("parrot.rider.bow")) {
                Entity proj = ev.getProjectile();
                Entity parrot = proj.getWorld().spawnEntity(proj.getLocation(), EntityType.valueOf("PARROT"));
                parrot.setVelocity(proj.getVelocity());
                parrot.addPassenger(ev.getEntity());
                proj.remove();
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent ev) {
        if (ev.getMessage().equalsIgnoreCase("/parrotbow") && ev.getPlayer().hasPermission("parrot.rider.bow.command")) {
            ItemStack bow = new ItemStack(Material.BOW, 1);
            bow.addUnsafeEnchantment(Enchantment.OXYGEN, 5);
            ItemMeta im = bow.getItemMeta();
            im.setDisplayName(ChatColor.GREEN+"Parrot Bow");
            bow.setItemMeta(im);
            ev.getPlayer().getInventory().addItem(bow);
        }
    }
}
