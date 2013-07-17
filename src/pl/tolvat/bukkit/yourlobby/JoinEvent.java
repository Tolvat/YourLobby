package pl.tolvat.bukkit.yourlobby;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
	private YourLobby yl;
	
	public JoinEvent(YourLobby yl)
	{
		this.yl = yl;
	}

	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		
		if(yl.getConfiguration().getInt("lobbies.count") > 0)
		{
			for(String pName : yl.getConfiguration().getStringList("lobbies.players"))
			{
				if(pName == player.getName())
				{
					yl.log("DEV", "..");
				}
			}
		}
	}
}
