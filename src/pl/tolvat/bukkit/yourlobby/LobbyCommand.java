package pl.tolvat.bukkit.yourlobby;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCommand implements CommandExecutor {
	private YourLobby yl;
	
	public LobbyCommand(YourLobby yl)
	{
		this.yl = yl;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		int arguments = args.length;
		Player player = Bukkit.getServer().getPlayer(sender.getName());
		
		if(label.equalsIgnoreCase("lobby"))
		{
			if(arguments == 0)
			{
				help(player);
			}
			else if(arguments == 1)
			{
				String sublabel = args[0];
				
				if(sublabel.equalsIgnoreCase("info"))
				{
					info(player);
				}
				else if(sublabel.equalsIgnoreCase("create"))
				{
					player.sendMessage(ChatColor.RED + "--- Correct usage: /lobby create <name> <max players> --- ");
				}
			}
			else if(arguments == 3)
			{
				String sublabel = args[0];
				
				if(sublabel.equalsIgnoreCase("create"))
				{
					String name = args[1]; // name of the lobby.
					int maxPlayers = 0;
					
					if(args[2] != null)
					{
						maxPlayers = Integer.parseInt(args[2]);
					}
					
					if(name != null)
					{
						if(yl.getConfig().get("lobbies." + name + ".name") != null)
						{
							player.sendMessage(ChatColor.DARK_AQUA + "Lobby with that name already exists!");
						}
						else
						{
							yl.getConfig().set("lobbies." + name + ".name", name);
							yl.getConfig().set("lobbies." + name + ".owner", player.getName());
							yl.getConfig().set("lobbies." + name + ".maxplayers", maxPlayers);
							yl.getConfig().set("lobbies." + name + ".spawnX", player.getLocation().getX());
							yl.getConfig().set("lobbies." + name + ".spawnY", player.getLocation().getY());
							yl.getConfig().set("lobbies." + name + ".spawnZ", player.getLocation().getZ());
							yl.getConfig().set("lobbies." + name + ".players", Arrays.asList("none"));
						
							yl.saveConfig();
						
							player.sendMessage(ChatColor.DARK_AQUA + "Lobby has been created.");
						}
					}
					else
					{
						player.sendMessage(ChatColor.RED + "--- Correct usage: /lobby create <name> <max players> --- ");
					}
				}
			}
			else if(arguments == 2)
			{
				String sublabel = args[0]; // name of the lobby.
				
				if(sublabel.equalsIgnoreCase("join"))
				{
					String name = args[1];
					
					if(name != null)
					{
						List<String> players = yl.getConfig().getStringList("lobbies." + name + ".players");
						
						if(players.contains(player.getName()))
						{
							player.sendMessage(ChatColor.RED + "You have already joined this lobby.");
						}
						else
						{
							players.add(player.getName());
						}
						
						yl.getConfig().set("lobbies." + name + ".players", players);
						int x = yl.getConfig().getInt("lobbies." + name + ".spawnX");
						int y = yl.getConfig().getInt("lobbies." + name + ".spawnY");
						int z = yl.getConfig().getInt("lobbies." + name + ".spawnZ");
						
						yl.saveConfig();
						
						player.teleport(new Location(player.getWorld(), x, y, z));
						player.sendMessage(ChatColor.DARK_AQUA + "Teleported to lobby spawn.");
					}
					else
					{
						player.sendMessage(ChatColor.RED + "--- Correct usage: /lobby join <name> --- ");
					}
				}
				else if(sublabel.equalsIgnoreCase("delete"))
				{
					String name = args[1];
					
					if(yl.getConfig().get("lobbies." + name + ".owner").equals(player.getName()))
					{
						yl.getConfig().set("lobbies." + name, null);
						yl.saveConfig();
						
						player.sendMessage(ChatColor.DARK_AQUA + "Lobby has been deleted.");
					}
				}
				else if(sublabel.equalsIgnoreCase("kickall"))
				{
					String name = args[1];
					
					if(yl.getConfig().get("lobbies." + name + ".owner").equals(player.getName()))
					{
						yl.getConfig().set("lobbies." + name + ".players", null);
						yl.saveConfig();
						
						player.sendMessage(ChatColor.DARK_AQUA + "Lobby has been edited.");
					}
				}
			}
		}
		
		return false;
	}

	/**
	 * Shows YourLobby commands.
	 * @param Player sender
	 */
	public void help(Player sender)
	{
		sender.sendMessage(ChatColor.GOLD + "=== YourLobby Commands ===");
		sender.sendMessage(ChatColor.DARK_AQUA + "/lobby create <name> <max players>" + ChatColor.BLACK + " : " + ChatColor.AQUA + "create new lobby with specified name and player limit (0 if unlimited)");
		sender.sendMessage(ChatColor.DARK_AQUA + "/lobby join <name>" + ChatColor.BLACK + " : " + ChatColor.AQUA + "join lobby with specified name");
		sender.sendMessage(ChatColor.DARK_AQUA + "/lobby delete <name>" + ChatColor.BLACK + " : " + ChatColor.AQUA + "delete lobby with specified name");
	}
	
	/**
	 * Shows YourLobby informations.
	 * @param Player sender
	 */
	public void info(Player sender)
	{
		sender.sendMessage(ChatColor.GOLD + "=== YourLobby Informations ===");
		sender.sendMessage(ChatColor.DARK_AQUA + "YourLobby Author" + ChatColor.BLACK + " : " + ChatColor.AQUA + "Tolvat/shooly");
		sender.sendMessage(ChatColor.DARK_AQUA + "YourLobby Version (for Craftbukkit)" + ChatColor.BLACK + " : " + ChatColor.AQUA + yl.getVersion());
		sender.sendMessage(ChatColor.DARK_AQUA + "Available lobbies" + ChatColor.BLACK + " : " + ChatColor.AQUA + yl.getConfiguration().getInt("lobbies.count"));
	}
}
