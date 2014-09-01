package me.zaza.CheckPro.Listeners;


import me.zaza.CheckPro.CheckPro;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerWearListener implements Listener
{	
	CheckPro checkPro;
	public PlayerWearListener(CheckPro checkPro)
	{
		this.checkPro = checkPro;
	}

	//Universal
	//private String UniversalDenyMessage = "§3[CheckPro]" + "§4 " + MSGes.getString("MSG.UniversalDenyMessage");
	
	//Armor Positionen
	private final int ARMOR_HELMET_POSITION = 3;
	private final int ARMOR_CHESTPLATE_POSITION = 2;
	private final int ARMOR_LEGGINGS_POSITION = 1;
	private final int ARMOR_BOOTS_POSITION = 0;
	
	//Werkzeuge Nachrichten
 	private String AxeDenyMessage = "§3[CheckPro]" + "§4 " + CheckPro.MSGes.getString("MSG.AxeDenyMessage");
	private String PickaxeDenyMessage = "§3[CheckPro]" + "§4 " + CheckPro.MSGes.getString("MSG.PickaxeDenyMessage");
	private String HoeDenyMessage = "§3[CheckPro]" + "§4 " +  CheckPro.MSGes.getString("MSG.HoeDenyMessage");
	private String ShovelDenyMessage = "§3[CheckPro]" + "§4 " + CheckPro.MSGes.getString("MSG.ShovelDenyMessage"); 
	
	//Rüstung Nachrichten
	private String HelmetDenyMessage = "§3[CheckPro]" + "§4 " + CheckPro.MSGes.getString("MSG.HelmetDenyMessage");
	private String ChestplateDenyMessage = "§3[CheckPro]" + "§4 " + CheckPro.MSGes.getString("MSG.ChestplateDenyMessage");
	private String LeggingsDenyMessage = "§3[CheckPro]" + "§4 " + CheckPro.MSGes.getString("MSG.LeggingsDenyMessage");
	private String BootsDenyMessage = "§3[CheckPro]" + "§4 " + CheckPro.MSGes.getString("MSG.BootsDenyMessage");
	
	//Rüstung Permissions
	private String LeatherPermission = "CheckPro.allow.armor.leather";
	private String IronPermission = "CheckPro.allow.armor.iron";
	private String GoldPermission = "CheckPro.allow.armor.gold";
	private String DiamondPermission = "CheckPro.allow.armor.diamond";
	private String ChainmailPermission = "CheckPro.allow.armor.chainmail";
	
	//War Tools Message
	private String SwordDenyMessage = "§3[CheckPro]" + "§4 " + CheckPro.MSGes.getString("MSG.SwordDenyMessage");
	private String BowDenyMessage = "§3[CheckPro]" + "§4 " + CheckPro.MSGes.getString("MSG.BowDenyMessage");
	private String ArrowDenyMessage = "§3[CheckPro]" + "§4 " + CheckPro.MSGes.getString("MSG.ArrowDenyMessage");
	
	//War Tools Permissions
	private String WoodPermissionSword = "CheckPro.allow.wartool.wood";
	private String StonePermissionSword = "CheckPro.allow.wartool.stone";
	private String IronPermissionSword = "CheckPro.allow.wartool.iron";
	private String GoldPermissionSword = "CheckPro.allow.wartool.gold";
	private String DiamondPermissionSword = "CheckPro.allow.wartool.diamond";
	private String BowPermission = "CheckPro.allow.wartool.bow";
	private String ArrowPermission = "CheckPro.allow.wartool.arrow";
	
	//Werkzeug Permission
	private String WoodPermission = "CheckPro.allow.tool.wood";
	private String StonePermission = "CheckPro.allow.tool.stone";
	private String IronToolPermission = "CheckPro.allow.tool.iron";
	private String GoldToolPermission = "CheckPro.allow.tool.gold";
	private String DiamondToolPermission = "CheckPro.allow.tool.diamond";
	
	//Sonstige Werkzeuge Nachrichten
	private String AngelDenyMessage = "§3[CheckPro]" + "§4 " + CheckPro.MSGes.getString("MSG.FisgingrodDenyMessage");
	private String KompassDenyMessage = "§3[CheckPro]" + "§4 " + CheckPro.MSGes.getString("MSG.CompassDenyMessage");
	private String FeuerzeugDenyMessage = "§3[CheckPro]" + "§4 " + CheckPro.MSGes.getString("MSG.Flint_and_StealDenyMessage");
	private String SchereDenyMessage = "§3[CheckPro]" + "§4 " + CheckPro.MSGes.getString("MSG.ScissorsDenyMessage");
	private String NamensschildDenyMessage = "§3[CheckPro]" + "§4 " + CheckPro.MSGes.getString("MSG.NametagDenyMessage");
	
	//Sonstige Werkzeuge Permission
	private String KompassPermission = "CheckPro.allow.tool.compass";
	private String AngelPermission = "CheckPro.allow.tool.fishingrod";
	private String FeuerzeugPermission = "CheckPro.allow.tool.flintandsteal";
	private String ScherePermission = "CheckPro.allow.tool.shears";
	private String NamensschildPermission = "CheckPro.allow.tool.nametag";
	
	@EventHandler
	public void onPlayerWear(InventoryCloseEvent e)
	{
		if(!checkPro.getConfig().getBoolean("settings.CheckUserWearing"))
		{
			return;
		}
		Player p = (Player) e.getPlayer();
		if(p.isOp() || p.hasPermission("CheckPro.*") || p.hasPermission("CheckPro.allow.*"))
		{
			return;
		}
		ItemStack[] armor = p.getInventory().getArmorContents();
		if(armor.length == 0 || armor == null)
		{
			return;
		}
		checkHelmet(p, armor);
		checkChestPlate(p, armor);
		checkLeggings(p, armor);
		checkBoots(p, armor);	
	}
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerSwitchItem(PlayerItemHeldEvent e)
	{
		if(!checkPro.getConfig().getBoolean("settings.CheckUserUsing"))
		{
			return;
		}
		Player p = (Player) e.getPlayer();
		try
		{
		if(p.getInventory().getItem(e.getNewSlot()).equals(null) 
		|| p.getInventory().getItem(e.getNewSlot()).equals(Material.AIR))
		{
			return;
		}
		}catch(Exception Ex)
		{
			return;
		}
		ItemStack tool = p.getInventory().getItem(e.getNewSlot());
		if(tool.getType().isBlock())
		{
			return;
		}
		checkAxeInHand(p, tool, e);
		checkPickaxeInHand(p, tool, e);
		checkHoeInHand(p, tool, e);
		checkShovelInHand(p, tool, e);
		//OTHER TOOLS
		checkOtherToolsInHand(p, tool, e);
		//WAR TOOLS
		checkSwordInHand(p, tool, e);
		checkBowOrArrowInHand(p, tool, e);
		
	}
//	@EventHandler(priority=EventPriority.HIGH)
//	public void onPlayerUse(PlayerInteractEvent e)
//	{
//		//String materialEigenschaften = null;
//		Player p = (Player) e.getPlayer();
//		ItemStack tool = p.getItemInHand();
//		if(tool.equals(null) || tool.equals(Material.AIR))
//		{
//			e.setCancelled(true);
//			return;
//		}
//		if(tool.getType().isBlock())
//		{
//			e.setCancelled(true);
//			return;
//		}
//		if(e.getAction().equals(Action.LEFT_CLICK_BLOCK) 
//		|| e.getAction().equals(Action.LEFT_CLICK_AIR) 
//		|| e.getAction().equals(Action.RIGHT_CLICK_AIR) 
//		|| e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
//		|| e.getAction().equals(Action.PHYSICAL))
//		{
//			
//		//MAIN TOOLS
//		checkAxeInHand(p, tool, e);
//		checkPickaxeInHand(p, tool, e);
//		checkHoeInHand(p, tool, e);
//		checkShovelInHand(p, tool, e);
//		//OTHER TOOLS
//		checkOtherToolsInHand(p, tool, e);
//		//WAR TOOLS
//		checkSwordInHand(p, tool, e);
//		checkBowOrArrowInHand(p, tool, e);
//			
//			
//			
//			Material[] mat = Material.values();			
//			for(Material ma : mat)
//			{
//				if(tool.getType().equals(ma))
//				{
//
//					
//					
//					
//				}
//			}
//		}
//	}
	
//WAR TOOLS
	private void checkSwordInHand(Player p, ItemStack ItemInHand, PlayerItemHeldEvent e)
	{	
		if(!checkItems(ItemInHand,
				p,
				Material.WOOD_SWORD,
				WoodPermissionSword,
				SwordDenyMessage, e))
		{
			if(!checkItems(ItemInHand,
					p,
					Material.STONE_SWORD,
					StonePermissionSword,
					SwordDenyMessage, e))
			{
				if(!checkItems(ItemInHand,
						p,
						Material.IRON_SWORD,
						IronPermissionSword,
						SwordDenyMessage, e))
				{
					if(!checkItems(ItemInHand,
							p,
							Material.GOLD_SWORD,
							GoldPermissionSword,
							SwordDenyMessage, e))
					{
						if(!checkItems(ItemInHand,
								p,
								Material.DIAMOND_SWORD,
								DiamondPermissionSword,
								SwordDenyMessage, e))
						{
							
						}
						
					}
				}
			}
		}
	}
	
	private void checkBowOrArrowInHand(final Player p, final ItemStack ItemInHand, PlayerItemHeldEvent e)
	{
		if(ItemInHand.getType() == Material.BOW)
		{
			ItemStack bow = new ItemStack(Material.BOW);
			if(!p.hasPermission(BowPermission))
			{
				int firstEmpty = p.getInventory().firstEmpty();
				final int itemSlot = e.getNewSlot();

				p.getInventory().setItem(itemSlot, new ItemStack(Material.AIR));

				if(firstEmpty == -1)
				{
					Location loc = p.getLocation();
					p.getWorld().dropItem(loc, bow);
				}
				else
				{
					p.getInventory().setItem(firstEmpty, bow);
				}
				p.sendMessage(BowDenyMessage);
			}
		}
		else
		if(ItemInHand.getType() == Material.ARROW)
		{
			ItemStack arrow = new ItemStack(Material.ARROW);
			if(!p.hasPermission(ArrowPermission))
			{
				int firstEmpty = p.getInventory().firstEmpty();
				int itemSlot = e.getNewSlot();
				
				arrow.setAmount(ItemInHand.getAmount());
				
				p.getInventory().setItem(itemSlot, new ItemStack(Material.AIR));
				if(firstEmpty == -1)
				{
					Location loc = p.getLocation();
					p.getWorld().dropItem(loc, arrow);
				}
				else
				{
					p.getInventory().setItem(firstEmpty, arrow);
				}
				p.sendMessage(ArrowDenyMessage);
			}
		}
	}
//TOOLS
	private void checkAxeInHand(Player p, ItemStack ItemInHand, PlayerItemHeldEvent e)
	{		
		if(!checkItems(ItemInHand,
				p,
				Material.WOOD_AXE,
				WoodPermission + ".axe",
				AxeDenyMessage, e))
		{
			if(!checkItems(ItemInHand,
					p,
					Material.STONE_AXE,
					StonePermission + ".axe",
					AxeDenyMessage, e))
			{
				if(!checkItems(ItemInHand,
						p,
						Material.IRON_AXE,
						IronToolPermission + ".axe",
						AxeDenyMessage, e))
				{
					if(!checkItems(ItemInHand,
							p,
							Material.GOLD_AXE,
							GoldToolPermission + ".axe",
							AxeDenyMessage, e))
					{
						if(!checkItems(ItemInHand,
								p,
								Material.DIAMOND_AXE,
								DiamondToolPermission + ".axe",
								AxeDenyMessage, e))
						{
							
						}
						
					}
				}
			}
		}
	}
	
	private void checkPickaxeInHand(Player p, ItemStack ItemInHand, PlayerItemHeldEvent e)
	{		
		if(!checkItems(ItemInHand,
				p,
				Material.WOOD_PICKAXE,
				WoodPermission + ".pickaxe",
				PickaxeDenyMessage, e))
		{
			if(!checkItems(ItemInHand,
					p,
					Material.STONE_PICKAXE,
					StonePermission + ".pickaxe",
					PickaxeDenyMessage, e))
			{
				if(!checkItems(ItemInHand,
						p,
						Material.IRON_PICKAXE,
						IronToolPermission + ".pickaxe",
						PickaxeDenyMessage, e))
				{
					if(!checkItems(ItemInHand,
							p,
							Material.GOLD_PICKAXE,
							GoldToolPermission + ".pickaxe",
							PickaxeDenyMessage, e))
					{
						if(!checkItems(ItemInHand,
								p,
								Material.DIAMOND_PICKAXE,
								DiamondToolPermission + ".pickaxe",
								PickaxeDenyMessage, e))
						{
							
						}
						
					}
				}
			}
		}
	}

	
	private boolean checkItems(final ItemStack ItemInHand, final Player p, Material newItem, String Permission, String DenyMessage, PlayerItemHeldEvent e)
	{
		if(ItemInHand.getType() == newItem)
		{
			ItemStack hoe = new ItemStack(newItem);
			if(!p.hasPermission(Permission))
			{
				int firstEmpty = p.getInventory().firstEmpty();
				final int ItemHolder = e.getNewSlot();
				
				
				p.getInventory().setItem(ItemHolder, new ItemStack(Material.AIR));

				
				if(firstEmpty == -1)
				{
					Location loc = p.getLocation();
					p.getWorld().dropItem(loc, hoe);
				}
				else
				{
					p.getInventory().setItem(firstEmpty, hoe);
				}
				p.sendMessage(DenyMessage);
			}
			return true;
		}
		else
			return false;
	}
	
	
	private void checkHoeInHand(Player p, ItemStack ItemInHand, PlayerItemHeldEvent e)
	{
		if(!checkItems(ItemInHand,
				p,
				Material.WOOD_HOE,
				WoodPermission + ".hoe",
				HoeDenyMessage, e))
		{
			if(!checkItems(ItemInHand,
					p,
					Material.STONE_HOE,
					StonePermission + ".hoe",
					HoeDenyMessage, e))
			{
				if(!checkItems(ItemInHand,
						p,
						Material.IRON_HOE,
						IronToolPermission + ".hoe",
						HoeDenyMessage, e))
				{
					if(!checkItems(ItemInHand,
							p,
							Material.GOLD_HOE,
							GoldToolPermission + ".hoe",
							HoeDenyMessage, e))
					{
						if(!checkItems(ItemInHand,
								p,
								Material.DIAMOND_HOE,
								DiamondToolPermission + ".hoe",
								HoeDenyMessage, e))
						{
							
						}
					}
				}
			}
		}
	}
	
	private void checkShovelInHand(Player p, ItemStack ItemInHand, PlayerItemHeldEvent e)
	{		
		if(!checkItems(ItemInHand,
				p,
				Material.WOOD_SPADE,
				WoodPermission + ".shovel",
				ShovelDenyMessage, e))
		{
			if(!checkItems(ItemInHand,
					p,
					Material.STONE_SPADE,
					StonePermission + ".shovel",
					ShovelDenyMessage, e))
			{
				if(!checkItems(ItemInHand,
						p,
						Material.IRON_SPADE,
						IronToolPermission + ".shovel",
						ShovelDenyMessage, e))
				{
					if(!checkItems(ItemInHand,
							p,
							Material.GOLD_SPADE,
							GoldToolPermission + ".shovel",
							ShovelDenyMessage, e))
					{
						if(!checkItems(ItemInHand,
								p,
								Material.DIAMOND_SPADE,
								DiamondToolPermission + ".shovel",
								ShovelDenyMessage, e))
						{
							return;
						}
						
					}
				}
			}
		}
	}
	
	private void checkOtherToolsInHand(Player p, ItemStack ItemInHand, PlayerItemHeldEvent e)
	{
		if(!checkItems(ItemInHand,
				p,
				Material.COMPASS,
				KompassPermission,
				KompassDenyMessage, e))
		{
			if(!checkItems(ItemInHand,
					p,
					Material.FISHING_ROD,
					AngelPermission,
					AngelDenyMessage, e))
			{
				if(!checkItems(ItemInHand,
						p,
						Material.SHEARS,
						ScherePermission,
						SchereDenyMessage, e))
				{
					if(!checkItems(ItemInHand,
							p,
							Material.NAME_TAG,
							NamensschildPermission,
							NamensschildDenyMessage, e))
					{
						if(!checkItems(ItemInHand,
								p,
								Material.FLINT_AND_STEEL,
								FeuerzeugPermission,
								FeuerzeugDenyMessage, e))
						{
							return;
						}
						
					}
				}
			}
		}
	}
	
//RÜSTUNG
	private boolean checkArmor(Player p, ItemStack[] armor, int position, Material newItem, String permission, String denyMessage)
	{
		ItemStack armorHelmet = null;
		if(armor[position].getType() == newItem)
		{
			armorHelmet = new ItemStack(newItem);
			if(!p.hasPermission(permission))
			{
				int firstempty = p.getInventory().firstEmpty();
				if(position == 3)
				{
					p.getInventory().setHelmet(new ItemStack(Material.AIR));
				}
				else
				if(position == 2)
				{
					p.getInventory().setChestplate(new ItemStack(Material.AIR));
				}
				else
				if(position == 1)
				{
					p.getInventory().setLeggings(new ItemStack(Material.AIR));
				}
				else
				{
					p.getInventory().setBoots(new ItemStack(Material.AIR));
				}
				if(firstempty != -1)
				{
					p.getInventory().addItem(armorHelmet);
				}
				else
				{
					p.getWorld().dropItem(p.getLocation(), armorHelmet);
				}
				p.sendMessage(denyMessage);
			}
			return true;
		}
		else
			return false;
	}
	
	private void checkHelmet(Player p, ItemStack[] armor)
	{
		if(!checkArmor(
				p,
				armor,
				ARMOR_HELMET_POSITION,
				Material.LEATHER_HELMET,
				LeatherPermission + ".helmet",
				HelmetDenyMessage))
		{
			if(!checkArmor(
					p,
					armor,
					ARMOR_HELMET_POSITION,
					Material.IRON_HELMET,
					IronPermission + ".helmet",
					HelmetDenyMessage))
			{
				if(!checkArmor(
						p,
						armor,
						ARMOR_HELMET_POSITION,
						Material.GOLD_HELMET,
						GoldPermission + ".helmet",
						HelmetDenyMessage))
				{
					if(!checkArmor(
							p,
							armor,
							ARMOR_HELMET_POSITION,
							Material.DIAMOND_HELMET,
							DiamondPermission + ".helmet",
							HelmetDenyMessage))
					{
						if(!checkArmor(
								p,
								armor,
								ARMOR_HELMET_POSITION,
								Material.CHAINMAIL_HELMET,
								ChainmailPermission + ".helmet",
								HelmetDenyMessage))
						{
							return;
						}
						
					}
				}
			}
		}
	}
	
	private void checkChestPlate(Player p, ItemStack[] armor)
	{
		if(!checkArmor(
				p,
				armor,
				ARMOR_CHESTPLATE_POSITION,
				Material.LEATHER_CHESTPLATE,
				LeatherPermission + ".chestplate",
				ChestplateDenyMessage))
		{
			if(!checkArmor(
					p,
					armor,
					ARMOR_CHESTPLATE_POSITION,
					Material.IRON_CHESTPLATE,
					IronPermission + ".chestplate",
					ChestplateDenyMessage))
			{
				if(!checkArmor(
						p,
						armor,
						ARMOR_CHESTPLATE_POSITION,
						Material.GOLD_CHESTPLATE,
						GoldPermission + ".chestplate",
						ChestplateDenyMessage))
				{
					if(!checkArmor(
							p,
							armor,
							ARMOR_CHESTPLATE_POSITION,
							Material.DIAMOND_CHESTPLATE,
							DiamondPermission + ".chestplate",
							ChestplateDenyMessage))
					{
						if(!checkArmor(
								p,
								armor,
								ARMOR_CHESTPLATE_POSITION,
								Material.CHAINMAIL_CHESTPLATE,
								ChainmailPermission + ".chestplate",
								ChestplateDenyMessage))
						{
							return;
						}
						
					}
				}
			}
		}
	}
	
	private void checkLeggings(Player p, ItemStack[] armor)
	{
		if(!checkArmor(
				p,
				armor,
				ARMOR_LEGGINGS_POSITION,
				Material.LEATHER_LEGGINGS,
				LeatherPermission + ".leggings",
				LeggingsDenyMessage))
		{
			if(!checkArmor(
					p,
					armor,
					ARMOR_LEGGINGS_POSITION,
					Material.IRON_LEGGINGS,
					IronPermission + ".leggings",
					LeggingsDenyMessage))
			{
				if(!checkArmor(
						p,
						armor,
						ARMOR_LEGGINGS_POSITION,
						Material.GOLD_LEGGINGS,
						GoldPermission + ".leggings",
						LeggingsDenyMessage))
				{
					if(!checkArmor(
							p,
							armor,
							ARMOR_LEGGINGS_POSITION,
							Material.DIAMOND_LEGGINGS,
							DiamondPermission + ".leggings",
							LeggingsDenyMessage))
					{
						if(!checkArmor(
								p,
								armor,
								ARMOR_LEGGINGS_POSITION,
								Material.CHAINMAIL_LEGGINGS,
								ChainmailPermission + ".leggings",
								LeggingsDenyMessage))
						{
							return;
						}
						
					}
				}
			}
		}
	}
	
	private void checkBoots(Player p, ItemStack[] armor)
	{
		if(!checkArmor(
				p,
				armor,
				ARMOR_BOOTS_POSITION,
				Material.LEATHER_BOOTS,
				LeatherPermission + ".boots",
				BootsDenyMessage))
		{
			if(!checkArmor(
					p,
					armor,
					ARMOR_BOOTS_POSITION,
					Material.IRON_BOOTS,
					IronPermission + ".boots",
					BootsDenyMessage))
			{
				if(!checkArmor(
						p,
						armor,
						ARMOR_BOOTS_POSITION,
						Material.GOLD_BOOTS,
						GoldPermission + ".boots",
						BootsDenyMessage))
				{
					if(!checkArmor(
							p,
							armor,
							ARMOR_BOOTS_POSITION,
							Material.DIAMOND_BOOTS,
							DiamondPermission + ".boots",
							BootsDenyMessage))
					{
						if(!checkArmor(
								p,
								armor,
								ARMOR_BOOTS_POSITION,
								Material.CHAINMAIL_BOOTS,
								ChainmailPermission + ".boots",
								BootsDenyMessage))
						{
							return;
						}
						
					}
				}
			}
		}
	}
}