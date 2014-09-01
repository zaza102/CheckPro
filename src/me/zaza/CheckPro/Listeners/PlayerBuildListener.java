package me.zaza.CheckPro.Listeners;


import me.zaza.CheckPro.CheckPro;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerBuildListener implements Listener
{
	//darf Bauen
	private String PlayerDenyBuildingMessage = "§3[CheckPro]" + "§4 " + CheckPro.MSGes.getString("MSG.PlayerDenyBuildingMessage");
	private String PlayerBuildingPermission = "CheckPro.allow.building";
	
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerBuild(BlockPlaceEvent e)
	{
		Player p = e.getPlayer();
		if(p.isOp() || p.hasPermission(PlayerBuildingPermission))
		{
			return;
		}
		if(p.getInventory().getItemInHand().getType().isBlock())
		{
			Block block = e.getBlockPlaced();
			ItemStack blockStacks = e.getItemInHand();
			checkBuilding(p, block, blockStacks);
		}		
	}
	
	private void checkBuilding(Player p, Block block, ItemStack blockStacks)
	{
			block.setType(Material.AIR);
			p.getInventory().addItem(blockStacks);
			p.sendMessage(PlayerDenyBuildingMessage);
	}
}
