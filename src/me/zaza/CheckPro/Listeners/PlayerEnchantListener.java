package me.zaza.CheckPro.Listeners;

import me.zaza.CheckPro.CheckPro;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerEnchantListener implements Listener
{
	private final String denyMessage = CheckPro.MSGes.getString("MSG.EnchantingDenyMessage");
	private final String DenyMessage = CheckPro.MSGes.getString("MSG.EnchantDenyMessage");
	private final String AnvilDenyMessage = CheckPro.MSGes.getString("MSG.RepairingDenyMessage");
	
	private boolean ThreadIsRunning = false;
	
	private CheckPro checkpro;
	
	public PlayerEnchantListener(CheckPro checkpro)
	{
		this.checkpro = checkpro;
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerEnchanting(final PrepareItemEnchantEvent e)
	{
		if(!ThreadIsRunning)
		{
			final Player p = e.getEnchanter();
			final Inventory inv = e.getInventory();
			if(inv instanceof EnchantingInventory)
			{
				if(!p.hasPermission("CheckPro.allow.*") 
						&& !p.isOp() 
						&& !p.hasPermission("CheckPro.allow.enchant." + e.getItem().getType()))
				{
					e.setCancelled(true);
					final ItemStack item = inv.getItem(0);
					ItemMeta itemMeta = inv.getItem(0).getItemMeta();
					item.setItemMeta(itemMeta);
					this.ThreadIsRunning = true;
					checkpro.getServer().getScheduler().scheduleSyncDelayedTask(checkpro, new Runnable() 
					{
						@Override
						public void run() 
						{
							p.getInventory().addItem(item);
							inv.setItem(0, new ItemStack(Material.AIR));
							p.sendMessage("§3[CheckPro]" + "§4 " + denyMessage);
							ThreadIsRunning = false;
						}
					}, 5L);
					
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerWantEnchanting(PlayerInteractEvent e)
	{
		Player p = (Player) e.getPlayer();
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR))
		{
			if(e.getClickedBlock().getType().equals(Material.ENCHANTMENT_TABLE))
			{
				if(!p.hasPermission("CheckPro.allow.*") && !p.isOp() && !p.hasPermission("CheckPro.allow.enchanting"))
				{
					e.setCancelled(true);
					p.sendMessage("§3[CheckPro]" + "§4 " + DenyMessage);
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerWantRepairing(PlayerInteractEvent e)
	{
		Player p = (Player) e.getPlayer();
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR))
		{
			if(e.getClickedBlock().getType().equals(Material.ANVIL))
			{
				if(!p.hasPermission("CheckPro.allow.*") && !p.isOp() && !p.hasPermission("CheckPro.allow.repairing"))
				{
					e.setCancelled(true);
					p.sendMessage("§3[CheckPro]" + "§4 " + AnvilDenyMessage);
				}
			}
		}
	}
	
}