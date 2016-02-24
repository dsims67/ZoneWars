package com.savior67.zonewars.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.savior67.zonewars.enums.EnumChannels;
import com.savior67.zonewars.enums.EnumRaces;
import com.savior67.zonewars.race.RaceBase;

public class LoginListener implements Listener{
	
	@EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		EnumRaces race = RaceBase.findLeastPopulated();
		//Default player to team chat channel
		EnumChannels.switchChannel(player.getDisplayName(), EnumChannels.tc);
		//Add player to the team with the least players
		race.addPlayer(player.getDisplayName());
		if(race.getName() == "Zombie") {
			player.sendMessage(ChatColor.GREEN + "You've joined the Zombie Horde!"+ChatColor.DARK_GREEN+" Braainnnzzzz");
			RaceBase.sendChatToRace(race, ChatColor.GREEN+player.getDisplayName()+ChatColor.DARK_GREEN+" has joined the Zombie cause");
		}
		else if(race.getName() == "Witch") {
			player.sendMessage(ChatColor.LIGHT_PURPLE + "You've joined the Witch Cult!"+ChatColor.DARK_PURPLE+" Mwuhahahaha");
			RaceBase.sendChatToRace(race, ChatColor.LIGHT_PURPLE+player.getDisplayName()+ChatColor.DARK_PURPLE+" has joined the Witch cause");
		}
		else if(race.getName() == "Villager") {
			player.sendMessage(ChatColor.YELLOW + "You've joined the Villager Militia!"+ChatColor.GOLD+" Run Away!!!");
			RaceBase.sendChatToRace(race, ChatColor.YELLOW+player.getDisplayName()+ChatColor.GOLD+" has joined the Villager cause");
		}
		player.sendMessage(ChatColor.BLUE+"Now Chatting in 'Team Chat', Switch with /ac");

		event.setJoinMessage(null);
	}
	
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
    	Player player = event.getPlayer();
		EnumRaces playerRace = RaceBase.getRaceOf(player.getDisplayName());
		//Remove player from team on quit.
		playerRace.removePlayer(player.getDisplayName());
		EnumChannels.removeChatter(player.getDisplayName());
		
    }

}