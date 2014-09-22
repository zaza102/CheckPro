package me.zaza.CheckPro.Listeners;

import java.util.List;

import me.zaza.CheckPro.CheckPro;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerBrewListener implements Listener
{
	private String BrewingIngredientDenyMessage = CheckPro.MSGes.getString("MSG.BrewingIngredientDenyMessage");
	private String denyMessage = CheckPro.MSGes.getString("MSG.BrewingDenyMessage");
	
	private CheckPro checkpro;
	
	public PlayerBrewListener(CheckPro checkpro)
	{
		this.checkpro = checkpro;
	}
	
	
	@EventHandler
	public void onPlayerBrew(BrewEvent e)
	{
		List<HumanEntity> players = e.getContents().getViewers();
		if(players == null || players.size() == 0)
		{
			return;
		}
		Player p = (Player) players.get(0);
		BrewerInventory con = e.getContents();
		
		if(!p.isOp() && !p.hasPermission("CheckPro.allow.*") && !p.hasPermission("CheckPro.allow.brewing." + con.getIngredient().getType()))
		{
			p.sendMessage("§3[CheckPro]" + "§4 " + BrewingIngredientDenyMessage);
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerWantBrewing(PlayerInteractEvent e)
	{
		Player p = (Player) e.getPlayer();
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR))
		{
			if(e.getClickedBlock().getType().equals(Material.BREWING_STAND) 
					|| e.getClickedBlock().getType().equals(Material.BREWING_STAND_ITEM))
			{
				if(!p.hasPermission("CheckPro.allow.*") && !p.isOp() && !p.hasPermission("CheckPro.allow.brewing"))
				{
					e.setCancelled(true);
					p.sendMessage("§3[CheckPro]" + "§4 " + denyMessage);
				}
				

			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerSetIngredient(InventoryClickEvent e)
	{
		final Player p = (Player) e.getWhoClicked();
		final Inventory inv = e.getInventory();
		if(inv instanceof BrewerInventory)
		{	
			checkpro.getServer().getScheduler().scheduleSyncDelayedTask(checkpro, new Runnable() {
				
				@Override
				public void run() 
				{
					try
					{
						if(inv.getItem(3).getType() != null)
						{
							if(!p.isOp() && !p.hasPermission("CheckPro.allow.*") && !p.hasPermission("CheckPro.allow.brewing." + inv.getItem(3).getType()))
							{
								p.sendMessage("§3[CheckPro]" + "§4 " + BrewingIngredientDenyMessage);
								p.getInventory().addItem(new ItemStack(inv.getItem(3).getType()));
								inv.setItem(3, new ItemStack(Material.AIR));
							}
						}
					}catch(Exception e)
					{
					}
				}
			},1L);
		}
	}
}

