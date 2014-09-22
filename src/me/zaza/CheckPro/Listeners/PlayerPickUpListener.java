package me.zaza.CheckPro.Listeners;


import me.zaza.CheckPro.CheckPro;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickUpListener implements Listener
{
	CheckPro checkpro;
	public PlayerPickUpListener(CheckPro checkPro)
	{
		this.checkpro = checkPro;
	}

	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerPickupItem(PlayerPickupItemEvent e)
	{
		final Player p = e.getPlayer();
		checkpro.getServer().getScheduler().scheduleSyncDelayedTask(checkpro, new Runnable()
		{
			@Override
			public void run() 
			{
				Bukkit.getPluginManager().callEvent(new PlayerItemHeldEvent(p, p.getInventory().firstEmpty(), p.getInventory().getHeldItemSlot()));
			}
		}, 2L);
		
	}

}
