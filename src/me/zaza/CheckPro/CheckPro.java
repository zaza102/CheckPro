package me.zaza.CheckPro;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;

import me.zaza.CheckPro.Commands.ReloadCommand;
import me.zaza.CheckPro.Listeners.PlayerBrewListener;
import me.zaza.CheckPro.Listeners.PlayerBuildListener;
import me.zaza.CheckPro.Listeners.PlayerCraftListener;
import me.zaza.CheckPro.Listeners.PlayerDamageBlockListener;
import me.zaza.CheckPro.Listeners.PlayerEnchantListener;
import me.zaza.CheckPro.Listeners.PlayerPickUpListener;
import me.zaza.CheckPro.Listeners.PlayerPrepareUsePotionListener;
import me.zaza.CheckPro.Listeners.PlayerWearListener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CheckPro extends JavaPlugin
{	
	public static String MSGesPath = "plugins"+File.separator+"CheckPro";
	public static String MSGesFilePath;;
	
	public static YamlConfiguration MSGes;
	
	@Override
	public void onEnable()
	{
		//PLUGIN MANAGER
		PluginManager plugmag = Bukkit.getPluginManager();
		//config
		this.saveDefaultConfig();
		if(!this.getConfig().getBoolean("settings.enabled"))
		{
			plugmag.disablePlugin(this);
			return;
		}
		//Language
		if(this.getConfig().getString("settings.language").equals("en"))
		{
			//System.out.println("[CheckPro] Error wrong Value in the Config, using default config Massage File MSGes_en");
			MSGesFilePath = File.separator + "MSGes_en.yml";
			MSGes = YamlConfiguration.loadConfiguration(new File(MSGesPath + MSGesFilePath));
			//set Config Defaults
			checkConfigs(MSGes, MSGesPath,MSGesFilePath , "MSGes_en.yml");
			System.out.println("[CheckPro] Enabled MSGes_en.yml");
		}
		else
		if(this.getConfig().getString("settings.language").equals("de"))
		{
			MSGesFilePath = File.separator + "MSGes_de.yml";
			MSGes = YamlConfiguration.loadConfiguration(new File(MSGesPath + MSGesFilePath));
			//set Config Defaults
			checkConfigs(MSGes, MSGesPath,MSGesFilePath , "MSGes_de.yml");
			System.out.println("[CheckPro] Enabled MSGes_de.yml");
		}		
		else
		{
			System.out.println("[CheckPro] Error wrong Value in the Config, using default config Message File MSGes_en");
			MSGesFilePath = File.separator + "MSGes_en.yml";
			MSGes = YamlConfiguration.loadConfiguration(new File(MSGesPath + MSGesFilePath));
			//set Config Defaults
			checkConfigs(MSGes, MSGesPath,MSGesFilePath , "MSGes_en.yml");
			System.out.println("ReEnabled_EN");
		}
		//EVENTS
		if(this.getConfig().getBoolean("settings.CheckUserCrafting"))
		{
			plugmag.registerEvents(new PlayerCraftListener(), this);
		}
		plugmag.registerEvents(new PlayerWearListener(this), this);
		plugmag.registerEvents(new PlayerPickUpListener(this), this);
		if(this.getConfig().getBoolean("settings.CheckUserBuilding"))
		{	
			plugmag.registerEvents(new PlayerBuildListener(), this);
			plugmag.registerEvents(new PlayerDamageBlockListener(), this);
		}
		if(this.getConfig().getBoolean("settings.CheckUserEnchanting"))
		{
			plugmag.registerEvents(new PlayerEnchantListener(this), this);
		}
		if(this.getConfig().getBoolean("settings.CheckUserUsePotions"))
		{
			plugmag.registerEvents(new PlayerPrepareUsePotionListener(), this);
		}
		if(this.getConfig().getBoolean("settings.CheckUserBrewing"))
		{
			plugmag.registerEvents(new PlayerBrewListener(this), this);
		}
		//COMMANDS
		this.getCommand("checkpro").setExecutor(new ReloadCommand(this));

	}
    
    public void checkConfigs(YamlConfiguration config, String path, String FilePath, String nameWextension)
    {    	    	
    	File file = new File(path + FilePath);
    	if(file.exists())
    	{
    		try {
				config.load(file);
			} catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			} catch (IOException e) 
			{
				e.printStackTrace();
			} catch (InvalidConfigurationException e) 
			{
				e.printStackTrace();
			}
    		return;
    	}
    	File dir = new File(path);
    	dir.mkdir();
		Reader defConfigStream = null;
		config.options().copyDefaults(true);
		defConfigStream = new InputStreamReader(this.getResource(nameWextension));
		if (defConfigStream != null) 
		{
		    FileOutputStream out = null;
		    try {
				out = new FileOutputStream(file);
			} catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
		    try 
		    {
		    	char[] buffer = new char[0];
		    	out.write(defConfigStream.read(buffer));
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
		    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
		    config.setDefaults(defConfig);
		    try 
		    {
				out.close();
				defConfigStream.close();
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
        try 
        {
			config.save(file);
		} 
        catch (IOException e) 
		{
			this.getLogger().log(Level.WARNING, "Could not save "+ file.getName() + " in folder" + file.getAbsolutePath());
		}
        try {
        	config.load(file);
			System.out.println("[CheckPro] "+ file.getName() + " loaded!");
		} catch (FileNotFoundException e) 
		{
			this.getLogger().log(Level.WARNING, "Could not found "+ nameWextension + " File");
		} catch (IOException e) 
		{
			this.getLogger().log(Level.WARNING, "IOException by "+ file.getName() + " File");
		} catch (InvalidConfigurationException e) 
		{
			this.getLogger().log(Level.WARNING, "Could not config "+ file.getName() + " File");
		}
    }
}
