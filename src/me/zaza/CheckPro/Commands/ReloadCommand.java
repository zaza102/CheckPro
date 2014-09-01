package me.zaza.CheckPro.Commands;

import me.zaza.CheckPro.CheckPro;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class ReloadCommand implements CommandExecutor
{
	CheckPro checkpro;
	
	public ReloadCommand(CheckPro check)
	{
		this.checkpro = check;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			if(p.hasPermission("CheckPro.*") || p.hasPermission("CheckPro.Reload"))
			{
				if(cmd.getName().equalsIgnoreCase("checkpro")|| args.length == 2 || args[1].equalsIgnoreCase("reload"))
				{
					PluginManager pluginManager = Bukkit.getPluginManager();
					checkpro.reloadConfig();
					pluginManager.disablePlugin(checkpro);
					pluginManager.enablePlugin(checkpro);
				}
			}
		}
		else
		{	
			if(cmd.getName().equalsIgnoreCase("checkpro")|| args.length == 2 || args[1].equalsIgnoreCase("reload"))
			{
				PluginManager pluginManager = Bukkit.getPluginManager();
				checkpro.reloadConfig();
				pluginManager.disablePlugin(checkpro);
				pluginManager.enablePlugin(checkpro);
			}
		}
		return true;
	}
}
