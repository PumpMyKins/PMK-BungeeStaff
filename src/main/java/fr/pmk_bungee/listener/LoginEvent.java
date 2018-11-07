package fr.pmk_bungee.listener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.object.Player;
import fr.pmk_bungee.utils.PlayerSituation;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginEvent implements Listener {

	@EventHandler
	public void onLogin(net.md_5.bungee.api.event.LoginEvent e) {

		String playername = e.getConnection().getName();
		try {
			ResultSet rs = Main.getMySQL().getResult("SELECT * FROM MinecraftPlayer WHERE username = '" + e.getConnection().getName()+ "'"); 
			if(!rs.next()) {

				Player player = new Player();
				player.setFirstCome(new Timestamp(System.currentTimeMillis()));
				player.setUsername(playername);
				player.setUuid(e.getConnection().getUniqueId().toString());

				Main.getMySQL().update("INSERT INTO MinecraftPlayer(username, uuid, ip, firstCome) VALUES ('" 
						+  player.getUsername()
						+ "', '" 
						+ player.getUuid()
						+ "', '" 
						+ e.getConnection().getAddress().getAddress()
						+ "', '" 
						+ player.getFirstCome()
						+ "')");
			}
		} catch (SQLException | NullPointerException error) {
			error.printStackTrace();
		}
		PlayerSituation situation = new PlayerSituation(playername);
		if(situation.isBanned()) {

			if(!situation.unban()) {
				e.setCancelled(true);
				//TODO kick_message
			}
		}
	}
}
