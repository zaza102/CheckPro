package me.zaza.CheckPro.Listeners;

import me.zaza.CheckPro.CheckPro;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

public class PlayerDamageBlockListener implements Listener
{
	private String PlayerDenyBreakingMessage = "§3[CheckPro]" + "§4 " + CheckPro.MSGes.getString("MSG.PlayerDenyBreakingMessage");
	private String PlayerBreakingPermission = "CheckPro.allow.breaking";
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerDamageBlock(BlockDamageEvent e)
	{
		Player p = e.getPlayer();
		if(p.isOp() || p.hasPermission(PlayerBreakingPermission))
		{
			e.setCancelled(true);
			return;
		}
		if(p.getInventory().getItemInHand().getType().isBlock())
		{
			Block block = e.getBlock();
			checkDamaging(p, block, e);
		}		
	}

	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerBreakBlock(BlockBreakEvent e)
	{
		Player p = e.getPlayer();
		if(p.isOp() || p.hasPermission(PlayerBreakingPermission))
		{
			e.setCancelled(true);
			return;
		}
		if(p.getInventory().getItemInHand().getType().isBlock())
		{
			Block block = e.getBlock();
			checkBreaking(p, block, e);
		}	
		
	}
	
	private void checkBreaking(Player p, Block block, BlockBreakEvent e)
	{
		e.setCancelled(true);
		p.sendMessage(PlayerDenyBreakingMessage);
	}
	
	private void checkDamaging(Player p, Block block, BlockDamageEvent e)
	{
		e.setCancelled(true);
		p.sendMessage(PlayerDenyBreakingMessage);
	}
}
