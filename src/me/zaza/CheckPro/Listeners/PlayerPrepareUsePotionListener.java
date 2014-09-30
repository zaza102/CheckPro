package me.zaza.CheckPro.Listeners;

import me.zaza.CheckPro.CheckPro;
import net.minecraft.server.v1_7_R3.Material;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class PlayerPrepareUsePotionListener implements Listener
{
	private String denyMessage = CheckPro.MSGes.getString("MSG.PotionUseDenyMessage");
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerSwitchPotion(PlayerItemHeldEvent e)
	{
		PotionType[] bottle = PotionType.values();
		Player p = e.getPlayer();
		int New = e.getNewSlot();
		ItemStack newItem;
		try
		{
			if(!p.getInventory().getItem(New).equals(Material.AIR))
			{
				newItem = p.getInventory().getItem(New);		
			}
			else
			{
				return;
			}
		}catch(Exception ex)
		{
			return;
		}
		for(int i = 0; i < bottle.length; i++)
		{
			Potion pot = new Potion(bottle[i]);
			try
			{
				if(Potion.fromItemStack(newItem).getType().toString().equals(pot.getType().toString()))	
				{
					
					onPlayerSwitchItem(p, Potion.fromItemStack(newItem), New);
					return;
				}
			}catch(Exception ex)
			{
				return;
			}
		}
	}
	
	private void onPlayerSwitchItem(Player p, Potion newItem, int New)
	{
		
		if(!p.isOp() &&!p.hasPermission("CheckPro.allow.potion.*") && !p.hasPermission("CheckPro.allow.*") && !p.hasPermission("CheckPro.allow.potion." + newItem.getType().toString()))
		{
			int newSlot = p.getInventory().firstEmpty();
			if(newSlot == -1)
			{
				p.getWorld().dropItem(p.getLocation(), newItem.toItemStack(1));
				p.getInventory().clear(New);
			}
			else
			{
				p.getInventory().setItem(newSlot, newItem.toItemStack(1));
				p.getInventory().clear(New);
			}
			p.sendMessage("§3[CheckPro]" + "§4 " + denyMessage);
		}
	}
}
