package com.savior67.zonewars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


import org.bukkit.event.player.PlayerAchievementAwardedEvent;

import com.savior67.zonewars.enums.EnumChannels;
import com.savior67.zonewars.enums.EnumRaces;
import com.savior67.zonewars.race.RaceBase;

//Limit chat to player's race
public class ChatListener implements Listener {

	@EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
    	if(event.getPlayer() instanceof Player) {
    		Player p = event.getPlayer();
    		EnumRaces playerRace = RaceBase.getRaceOf(p.getDisplayName());
    		String name = p.getDisplayName();
    		String prefix = playerRace.getPrefix();
    		if(EnumChannels.isChattingIn(name, EnumChannels.tc)) {
        		String msg = prefix+name+ChatColor.GRAY+": "+event.getMessage();
    			RaceBase.sendChatToRace(playerRace, msg);
    		}
    		else if(EnumChannels.isChattingIn(name, EnumChannels.ac)) {
        		String msg = prefix+name+ChatColor.WHITE+": "+event.getMessage();
    			Bukkit.getServer().broadcastMessage(msg);
    		}
    		else  {
        		String msg = prefix+name+ChatColor.AQUA+": "+event.getMessage();
    			Bukkit.getServer().broadcastMessage(msg); //REPLACE THIS WITH SQUAD CHAT LATER
    		}
    		
    		event.setCancelled(true);
    	}
    	
    }
	
	//Stop those annoying achievement messages
	@EventHandler
	public void onAchievementAwarded(PlayerAchievementAwardedEvent event) {
		event.setCancelled(true);
	}
    
}
