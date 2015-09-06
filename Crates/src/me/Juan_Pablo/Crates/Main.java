package me.Juan_Pablo.Crates;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin implements Listener {
		
	Map<UUID, Integer> crateUsesMap;
	Random random;
	
	String[] prizes = {"Material:Diamond", "Material:Gold", "Rank:Test"};
	
	public void onEnable() {
		this.crateUsesMap = new HashMap<UUID, Integer>();
		this.random = new Random();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (!(sender instanceof Player)){
			return true;
		}
		
		Player p = (Player) sender;
		if (command.getName().equals("test")) {
			activateCrate(p);
		}
		
		return false;
	}
	
	void activateCrate(Player p) {
		if (this.crateUsesMap.get(p.getUniqueId()) == null) { this.crateUsesMap.put(p.getUniqueId(), 0); }
		this.crateUsesMap.put(p.getUniqueId(), this.crateUsesMap.get(p.getUniqueId()) + 1);
		
		String prize = choosePrize();
		String[] prizeIndex = prize.split("\\:");
		
		if(prize.contains("Rank")) {
			Bukkit.broadcastMessage("The User " + p.getDisplayName() + " Won The Rank " + prizeIndex[1] + "!");
		} else if (prize.contains("Material")) {
			Bukkit.broadcastMessage("The User " + p.getDisplayName() + " Won The Item " + prizeIndex[1] + "!");
			p.getInventory().addItem(new ItemStack(Material.valueOf(prizeIndex[1].toUpperCase())));
		}
		
	}
	
	String choosePrize(){ return prizes[this.random.nextInt(prizes.length)]; }
		
	Material[] items = {Material.DIAMOND, Material.IRON_INGOT, Material.REDSTONE};
	
	void startInventory(final Inventory inv, final Player p) {
		final Sound sound = Sound.ORB_PICKUP;
		
		new BukkitRunnable() {
			public void run() {
				
				for (int x = 0;x<inv.getSize(); x++) {
					inv.setItem(x, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5));
				}
				
				ItemStack is = new ItemStack(items[random.nextInt(items.length)]);
				
			}
		}.runTaskLater(this, 20L);
	
	}
}
