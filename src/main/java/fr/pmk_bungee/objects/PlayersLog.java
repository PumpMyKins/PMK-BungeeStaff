package fr.pmk_bungee.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import fr.pmk_bungee.MainBungeeStaff;
import fr.pmk_bungee.MySQL;
import fr.pmk_bungee.utils.Converter;

public class PlayersLog {

	private List<Ban> banList;
	private List<Mute> muteList;
	private List<Warn> warnList;
	private List<Kick> kickList;
	private List<BungeePlayer> playerList;

	public PlayersLog() throws SQLException {

		this.banList = new ArrayList<Ban>();
		this.muteList = new ArrayList<Mute>();
		this.warnList = new ArrayList<Warn>();
		this.kickList = new ArrayList<Kick>();
		this.playerList = new ArrayList<BungeePlayer>();

		MySQL mySQL = MainBungeeStaff.getMySQL();

		ResultSet rsPlayer = mySQL.getResult("SELECT * FROM `Players`");

		if(rsPlayer.first()) {
			do {

				BungeePlayer bp = new BungeePlayer();
				bp.setFirstCome((Date) rsPlayer.getTimestamp("firstCome"));
				bp.setIp(rsPlayer.getString("ip"));
				bp.setLastConnection((Date) rsPlayer.getTimestamp("lastCome"));
				bp.setUsername(rsPlayer.getString("username"));
				bp.setUuid(UUID.fromString(rsPlayer.getString("uuid")));

				this.playerList.add(bp);
			} while(rsPlayer.next());
		}
		
		ResultSet rsBan = mySQL.getResult("SELECT * FROM `BungeeBan`");
		
		if(rsBan.first()) {
			do {

				Ban b = new Ban();
				b.setBanId(rsBan.getInt("banId"));
				b.setBanAt((Date) rsBan.getTimestamp("banAt"));
				b.setBanBy(UUID.fromString(rsBan.getString("banBy")));
				b.setBanDuration(rsBan.getInt("banDuration"));
				b.setBanIp(rsBan.getString("banIp"));
				b.setBannedPlayer(UUID.fromString(rsBan.getString("bannedPlayer")));

				this.banList.add(b);
			} while(rsBan.next());
		}
		
		ResultSet rsMute = mySQL.getResult("SELECT * FROM `BungeeMutes`");

		if(rsMute.first()) {
			do {

				Mute m = new Mute();
				m.setMuteId(rsMute.getInt("muteId"));
				m.setMuteAt((Date) rsMute.getTimestamp("muteAt"));
				m.setMuteBy(UUID.fromString(rsMute.getString("muteBy")));
				m.setMuteDuration(rsMute.getInt("muteDuration"));
				m.setMutePlayer(UUID.fromString(rsMute.getString("mutePlayer")));
				m.setMuteReason(rsMute.getString("muteReason"));

				this.muteList.add(m);
			} while(rsMute.next());
		}
		
		ResultSet rsWarn = mySQL.getResult("SELECT * FROM `BungeeWarn`");
		
		if(rsWarn.first()) {
			do {

				Warn w = new Warn();
				w.setWarnId(rsWarn.getInt("warnId"));
				w.setWarnBy(UUID.fromString(rsWarn.getString("warnBy")));
				w.setWarnDate((Date) rsWarn.getTimestamp("warnDate"));
				w.setWarnPlayer(UUID.fromString(rsWarn.getString("warnPlayer")));
				w.setWarnReason(rsWarn.getString("warnReason"));

				this.warnList.add(w);
			}while(rsWarn.next());
		}
		
		ResultSet rsKick = mySQL.getResult("SELECT * FROM `BungeeKicks`");
		
		if(rsKick.first()) {
			do {

				Kick k = new Kick();
				k.setKickId(rsKick.getInt("kickId"));
				k.setKickBy(UUID.fromString(rsKick.getString("kickBy")));
				k.setKickDate((Date) rsKick.getTimestamp("kickDate"));
				k.setKickPlayer(UUID.fromString(rsKick.getString("kickPlayer")));
				k.setKickReason(rsKick.getString("kickReason"));

				this.kickList.add(k);
			} while(rsKick.next());
		}
	}

	public PlayersLog refreshAll() throws SQLException {

		return new PlayersLog();
	}

	public List<Ban> getBanList() {
		return this.banList;
	}

	public void setBanList(List<Ban> banList) {
		this.banList = banList;
	}

	public List<Mute> getMuteList() {
		return this.muteList;
	}

	public void setMuteList(List<Mute> muteList) {
		this.muteList = muteList;
	}

	public List<Warn> getWarnList() {
		return this.warnList;
	}

	public void setWarnList(List<Warn> warnList) {
		this.warnList = warnList;
	}

	public List<Kick> getKickList() {
		return this.kickList;
	}

	public void setKickList(List<Kick> kickList) {
		this.kickList = kickList;
	}

	public List<BungeePlayer> getPlayerList() {
		return this.playerList;
	}

	public void setPlayerList(List<BungeePlayer> playerList) {
		this.playerList = playerList;
	}


	public void addPlayer(BungeePlayer p) {

		this.playerList.add(p);
		dbPlayerAdd(p);
	}
	public void dbPlayerAdd(BungeePlayer bp) {

		try {
			MainBungeeStaff.getMySQL().update("INSERT INTO `Players`(`username`, `uuid`, `ip`, `firstCome`, `lastCome`) VALUES ('"
					+bp.getUsername()
					+"','"
					+bp.getUniqueId()
					+"','"
					+bp.getIp()
					+"','"
					+Converter.dateConv(bp.getFirstCome())
					+"','"
					+Converter.dateConv(bp.getLastConnection())
					+"')");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	public void dbLastCome(BungeePlayer bp) {
		
		try {
			int i = 0;
			for(i = 0; i < this.playerList.size(); i++) {
				
				if(this.playerList.get(i).equals(bp)) {
					
					break;
				}
			}
			this.playerList.remove(i);
			bp.setLastConnection(new Date());
			this.playerList.add(bp);
			
			MainBungeeStaff.getMySQL().update("UPDATE `Players` SET `lastCome`="+Converter.dateConv(new Date())+" WHERE `uuid` = '"+bp.getUniqueId()+"'");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public BungeePlayer getPlayer(UUID uuid) {

		for(BungeePlayer bp  :this.playerList) {

			if(bp.getUniqueId().equals(uuid))
				return bp;
		}
		return null;
	}

	public BungeePlayer getPlayer(String username) {

		for(BungeePlayer bp : this.playerList) {

			if(bp.getUsername().equalsIgnoreCase(username))
				return bp;
		}
		return null;
	}

	public void addBan(BungeePlayer bannedPlayer, String reason, BungeePlayer banBy, int banDuration) {

		List<Ban> banList = this.banList;
		Ban b = new Ban();
		b.setBanAt(new Date());
		b.setBanBy(banBy.getUniqueId());
		b.setBanDuration(banDuration);
		b.setBanIp(bannedPlayer.getIp());
		b.setBannedPlayer(bannedPlayer.getUniqueId());
		b.setBanReason(reason);
		banList.add(b);

		dbBanAdd(b);

		this.setBanList(banList);
	}

	public void addBan(BungeePlayer bannedPlayer, String reason) { //Console only, perma ban && no ProxiedPlayer

		if(!isBan(bannedPlayer)) {
			List<Ban> banList = this.banList;
			Ban b = new Ban();
			b.setBanAt(new Date());
			b.setBanDuration(31557600);
			b.setBanIp(bannedPlayer.getIp());
			b.setBannedPlayer(bannedPlayer.getUniqueId());
			b.setBanReason(reason);
			b.setBanBy(UUID.fromString("console"));
			banList.add(b);

			dbBanAdd(b);

			this.setBanList(banList);
		}
	}

	public void dbBanAdd(Ban b) {

		try {
			MainBungeeStaff.getMySQL().update("INSERT INTO `BungeeBan`(`banAt`, `banDuration`, `banBy`, `bannedPlayer`, `banIp`, `banReason`) VALUES ('"
					+Converter.dateConv(b.getBanAt())
					+"','"
					+b.getBanDuration()
					+"','"
					+b.getBanBy()
					+"','"
					+b.getBannedPlayer()
					+"','"
					+b.getBanIp()
					+"','"
					+b.getBanReason()
					+"')");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void unBan(BungeePlayer bannedPlayer) {

		if(isBan(bannedPlayer)) {

			for(Ban b : this.banList) {

				if(b.getBannedPlayer().equals(bannedPlayer.getUniqueId())) {

					Date at = b.getBanAt();
					Date today = new Date();
					if(at.getTime()+b.getBanDuration() > today.getTime()) {

						int newDuration = (int) (today.getTime()-b.getBanAt().getTime());
						b.setBanDuration(newDuration);

						dbUnBan(b.getBanId(), newDuration);
					}
				}
			}
		}
	}

	public void dbUnBan(int banId, int newBanDuration) {

		try {
			MainBungeeStaff.getMySQL().update("UPDATE `BungeeBan` SET `banDuration`="+newBanDuration+" WHERE `banId`="+banId);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public boolean isBan(BungeePlayer player) {

		for(Ban b : this.banList) {

			if(b.getBannedPlayer().equals(player.getUniqueId())) {

				Date at = b.getBanAt();
				Date today = new Date();
				if(at.getTime()+b.getBanDuration() > today.getTime()) {

					return true;
				}
			}
		}
		return false;
	}

	public Ban getPlayerCurrentBan(BungeePlayer player) {

		if(isBan(player)) {

			for(Ban b : this.banList) {

				if(b.getBannedPlayer().equals(player.getUniqueId())) {

					Date at = b.getBanAt();
					Date today = new Date();
					if(at.getTime()+b.getBanDuration() > today.getTime()) {

						return b;
					}
				}
			}
		}
		return null;
	}

	public List<Ban> getPlayerBans(BungeePlayer player) {

		List<Ban> lb = new ArrayList<Ban>();
		for(Ban b : this.banList) {

			if(b.getBannedPlayer().equals(player.getUniqueId())) {

				lb.add(b);
			}
		}

		return lb;
	}

	public List<Ban> getPlayerBannedBy(BungeePlayer player) {

		List<Ban> lb = new ArrayList<Ban>();
		for(Ban b : this.banList) {

			if(b.getBanBy().equals(player.getUniqueId())) {

				lb.add(b);
			}
		}

		return lb;
	}

	public void addMute(BungeePlayer mutedPlayer, String reason, BungeePlayer muteBy, int muteDuration) {

		List<Mute> muteList = this.muteList;
		Mute m = new Mute();
		m.setMuteAt(new Date());
		m.setMuteBy(muteBy.getUniqueId());
		m.setMuteDuration(muteDuration);
		m.setMutePlayer(mutedPlayer.getUniqueId());
		m.setMuteReason(reason);
		muteList.add(m);

		dbMuteAdd(m);

		this.setMuteList(muteList);
	}

	public void addMute(BungeePlayer mutedPlayer, String reason) { //Console only, perma mute && no ProxiedPlayer

		if(!isMute(mutedPlayer)) {
			List<Mute> muteList = this.muteList;
			Mute m = new Mute();
			m.setMuteAt(new Date());
			m.setMuteDuration(31557600);
			m.setMutePlayer(mutedPlayer.getUniqueId());
			m.setMuteBy(UUID.fromString("console"));
			m.setMuteReason(reason);
			muteList.add(m);

			dbMuteAdd(m);

			this.setMuteList(muteList);
		}
	}

	public void dbMuteAdd(Mute m) {

		try {
			MainBungeeStaff.getMySQL().update("INSERT INTO `BungeeMutes`(`muteAt`, `muteDuration`, `muteBy`, `mutePlayer`, `muteReason`) VALUES ('"
					+Converter.dateConv(m.getMuteAt())
					+"','"
					+m.getMuteDuration()
					+"','"
					+m.getMuteBy()
					+"','"
					+m.getMutePlayer()
					+"','"
					+m.getMuteReason()
					+"')");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void unMute(BungeePlayer player) {

		if(isMute(player)) {

			for(Mute m : this.getMuteList()) {

				if(m.getMutePlayer().equals(player.getUniqueId())) {

					Date at = m.getMuteAt();
					Date today = new Date();
					if(at.getTime()+m.getMuteDuration() > today.getTime()) {

						int newDuration = (int) (today.getTime()-m.getMuteAt().getTime());
						m.setMuteDuration(newDuration);

						dbUnMute(m.getMuteId(), newDuration);
					}
				}
			}
		}
	}

	public void dbUnMute(int muteId, int newDuration) {

		try {
			MainBungeeStaff.getMySQL().update("UPDATE `BungeeMutes` SET `muteDuration`="+newDuration+" WHERE `muteId`="+muteId);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public boolean isMute(BungeePlayer player) {

		for(Mute m : this.getMuteList()) {

			if(m.getMutePlayer().equals(player.getUniqueId())) {

				Date at = m.getMuteAt();
				Date today = new Date();
				if(at.getTime()+m.getMuteDuration() > today.getTime()) {

					return true;
				}
			}
		}
		return false;
	}

	public List<Mute> getPlayerMute(BungeePlayer player){

		List<Mute> lm = new ArrayList<Mute>();
		for(Mute m : this.muteList) {

			if(m.getMutePlayer().equals(player.getUniqueId())) {
				
				lm.add(m);
			}
		}

		return lm;
	}

	public Mute getPlayerCurrentMute(BungeePlayer player) {

		for(Mute m : this.muteList) {

			if(m.getMutePlayer().equals(player.getUniqueId())) {

				Date at = m.getMuteAt();
				Date today = new Date();
				if(at.getTime()+m.getMuteDuration() > today.getTime()) {

					return m;
				}
			}
		}
		return null;
	}

	public List<Mute> getPlayerMuteBy(BungeePlayer player){

		List<Mute> lm = new ArrayList<Mute>();
		for(Mute m : this.muteList) {

			if(m.getMuteBy().equals(player.getUniqueId())) {

				lm.add(m);
			}
		}

		return lm;
	}

	public void addWarn(BungeePlayer warnPlayer, String reason, BungeePlayer warnBy) {


		List<Warn> warnList = this.warnList;
		Warn w = new Warn();
		w.setWarnDate(new Date());
		w.setWarnBy(warnBy.getUniqueId());
		w.setWarnPlayer(warnPlayer.getUniqueId());
		w.setWarnReason(reason);
		warnList.add(w);

		dbWarnAdd(w);

		this.setWarnList(warnList);
	}

	public void addWarn(BungeePlayer warnPlayer, String reason) {

		List<Warn> warnList = this.warnList;
		Warn w = new Warn();
		w.setWarnDate(new Date());
		w.setWarnBy(UUID.fromString("console"));
		w.setWarnPlayer(warnPlayer.getUniqueId());
		w.setWarnReason(reason);

		warnList.add(w);

		dbWarnAdd(w);

		this.setWarnList(warnList);
	}

	public void dbWarnAdd(Warn w) {

		try {
			MainBungeeStaff.getMySQL().update("INSERT INTO `BungeeWarn`(`warnBy`, `warnDate`, `warnReason`, `warnPlayer`) VALUES ('"
					+w.getWarnBy()
					+"','"
					+Converter.dateConv(w.getWarnDate())
					+"','"
					+w.getWarnReason()
					+"','"
					+w.getWarnPlayer()
					+"')");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void dbWarnDelete(int id) {

		try {
			MainBungeeStaff.getMySQL().update("DELETE FROM `BungeeWarn` WHERE `warnId`="+id);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public List<Warn> getPlayerWarn(BungeePlayer player){

		List<Warn> listwarn = new ArrayList<Warn>();
		for(Warn w : this.warnList) {

			if(w.getWarnPlayer().equals(player.getUniqueId())) {

				listwarn.add(w);
			}
		}
		return listwarn;
	}

	public List<Warn> getWarnBy(BungeePlayer player) {

		List<Warn> listwarn = new ArrayList<Warn>();
		for(Warn w : this.warnList) {

			if(w.getWarnBy().equals(player.getUniqueId())) {

				listwarn.add(w);
			}
		}
		return listwarn;
	}

	public void addKick(BungeePlayer kickPlayer, String reason, BungeePlayer kickBy) {

		List<Kick> listkick = new ArrayList<Kick>();
		Kick k = new Kick();
		k.setKickBy(kickBy.getUniqueId());
		k.setKickDate(new Date());
		k.setKickPlayer(kickPlayer.getUniqueId());
		k.setKickReason(reason);

		listkick.add(k);

		dbAddKick(k);

		this.setKickList(listkick);
	}

	public void addKick(BungeePlayer kickPlayer, String reason) {

		List<Kick> listkick = new ArrayList<Kick>();
		Kick k = new Kick();
		k.setKickBy(UUID.fromString("console"));
		k.setKickDate(new Date());
		k.setKickPlayer(kickPlayer.getUniqueId());
		k.setKickReason(reason);

		listkick.add(k);

		dbAddKick(k);

		this.setKickList(listkick);
	}

	public void dbAddKick(Kick k) {

		try {
			MainBungeeStaff.getMySQL().update("INSERT INTO `BungeeKicks`(`kickBy`, `kickDate`, `kickReason`, `kickPlayer`) VALUES ('"
					+k.getKickBy()
					+"','"
					+Converter.dateConv(k.getKickDate())
					+"','"
					+k.getKickReason()
					+"','"
					+k.getKickPlayer()
					+"')");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public List<Kick> getPlayerKick(BungeePlayer player) {

		List<Kick> listkick = new ArrayList<Kick>();
		for(Kick k : this.kickList) {

			if(k.getKickPlayer().equals(player.getUniqueId())) {

				listkick.add(k);
			}
		}

		return listkick;
	}

	public List<Kick> getPlayerKickBy(BungeePlayer player) {

		List<Kick> listkick = new ArrayList<Kick>();
		for(Kick k : this.kickList) {

			if(k.getKickBy().equals(player.getUniqueId())) {

				listkick.add(k);
			}
		}

		return listkick;
	}

	public List<Ban> getCurrentBan() {

		List<Ban> listban = new ArrayList<Ban>();
		for(Ban b : this.banList) {

			if(b.getBanAt().getTime()+b.getBanDuration()> new Date().getTime()) {

				listban.add(b);
			}
		}

		return listban;
	}

	public List<Mute> getCurrentMute() {

		List<Mute> listmute = new ArrayList<Mute>();
		for(Mute m : this.muteList) {

			if(m.getMuteAt().getTime()+m.getMuteDuration() > new Date().getTime()) {

				listmute.add(m);
			}
		}

		return listmute;
	}
}