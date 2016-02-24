package com.savior67.zonewars.race;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.savior67.zonewars.ZoneWars;
import com.savior67.zonewars.enums.EnumRaces;

public class Villager extends RaceBase {

	@Override
	public String getName() {
		return "Villager";
	}

	@Override
	public EnumRaces getEnum() {
		return EnumRaces.villager;
	}

	public static String getChatPrefix() {
		return(ChatColor.GOLD+"<Villager>"+ChatColor.YELLOW);
	}

	public static boolean isPlayer(String pName) {
		return(ZoneWars.villagerPlayers.contains(pName));
	}
	
	public static void removePlayer(String pName) {
		ZoneWars.villagerPlayers.remove(pName);
	}
	
	public static void addPlayer(String pName) {
		ZoneWars.villagerPlayers.add(pName);
	}
	
	public static int playerCount() {
		return ZoneWars.villagerPlayers.size();
	}


}
