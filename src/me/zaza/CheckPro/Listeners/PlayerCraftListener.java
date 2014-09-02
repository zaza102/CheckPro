package me.zaza.CheckPro.Listeners;


import java.util.List;

import me.zaza.CheckPro.CheckPro;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

public class PlayerCraftListener implements Listener
{
	
	private String CraftingDenyMessage = CheckPro.MSGes.getString("MSG.CraftingDenyMessage");
	@EventHandler
	public void onPlayerCraft(PrepareItemCraftEvent e)
	{
		List<HumanEntity> viewers = e.getViewers();
		if(viewers == null || viewers.size() == 0)
		{
			return;
		}
		Player p = (Player) e.getViewers().get(0);
		if(p.isOp() != true && (!p.hasPermission("CheckPro.allow.craft.*") && !p.hasPermission("CheckPro.allow.craft." + e.getRecipe().getResult().getType())))
		{
			e.getInventory().setResult(null);
			p.sendMessage("§3[CheckPro]" + "§4 " + CraftingDenyMessage);
		}
	}
	@EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
	public void onCraft(CraftItemEvent event) {
		Player player = (Player)event.getWhoClicked();
		if (!player.isOp() && (!player.hasPermission("CheckPro.allow.craft.*") && !player.hasPermission("CheckPro.allow.craft." + event.getCurrentItem().getType()))) {
			event.setResult(null);
			event.setCancelled(true);
			player.sendMessage("§3[CheckPro]" + "§4 " + CraftingDenyMessage);
		}
	}
}