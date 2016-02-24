package com.savior67.zonewars.race;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.savior67.zonewars.ZoneWars;
import com.savior67.zonewars.enums.EnumRaces;

public class Witch extends RaceBase{

	@Override
	public String getName() {
		return "Witch";
	}

	@Override
	public EnumRaces getEnum() {
		return EnumRaces.witch;
	}

	public static String getChatPrefix() {
		return(ChatColor.DARK_PURPLE+"<Witch>"+ChatColor.LIGHT_PURPLE);
	}
	
	public static boolean isPlayer(String pName) {
		return(ZoneWars.witchPlayers.contains(pName));
	}
	
	public static void removePlayer(String pName) {
		ZoneWars.witchPlayers.remove(pName);
	}
	
	public static void addPlayer(String pName) {
		ZoneWars.witchPlayers.add(pName);
	}
	
	public static int playerCount() {
		return ZoneWars.witchPlayers.size();
	}

}
