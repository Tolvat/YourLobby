package pl.tolvat.bukkit.yourlobby;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class YourLobby extends JavaPlugin {
	
	/** Release date of YourLobby. It's simple to understand it. */
	private static int RELEASE_DATE = 1607201301;
	
	/** Versions - better not to change that. */
	private static String BUKKIT_VERSION = "git-Bukkit-1.5.2-R1.0-27-gdc25312-b2814jnks (MC: 1.6.2)";
	private static String PLUGIN_VERSION = "1.6.2";
	
	/** Logger instance */
	private Logger logger = Logger.getLogger("Minecraft");
	
	/** PluginManager instance */
	private PluginManager pm;
	
	/** Configuration */
	private FileConfiguration config;
	
	/**
	 * This function is executed when plugin is starting.
	 */
	public void onEnable()
	{
		if(!Bukkit.getVersion().equals(BUKKIT_VERSION))
		{
			log("WARNING", "YourLobby may not compatibile with your Craftbukkit version!");
		}
		
		pm = getServer().getPluginManager();
		
		config = getConfig();
		
		pm.registerEvents(new JoinEvent(this), this);
		
		getCommand("lobby").setExecutor(new LobbyCommand(this));
		
		log("INFO", "Plugin has been started.");
	}
	
	/**
	 * This function is executed when plugin is stopping.
	 */
	public void onDisable()
	{
		log("INFO", "Plugin has been stopped.");
	}
	
	/**
	 * You can log something to console using this function.
	 * @param String prefix
	 * @param String message
	 */
	public void log(String prefix, String message)
	{
		logger.info("[" + prefix + "] [YourLobby for Craftbukkit " + PLUGIN_VERSION + "]: " + message);
	}
	
	/**
	 * Returns FileConfiguration instance.
	 * @return 
	 * @return FileConfiguration config
	 */
	public FileConfiguration getConfiguration()
	{
		return config;
	}
	
	/**
	 * Returns plugin version
	 * @return String plugin version
	 */
	public String getVersion() {
		return PLUGIN_VERSION;
	}
	
}
