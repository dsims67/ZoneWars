package com.savior67.zonewars.race;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.savior67.zonewars.ZoneWars;
import com.savior67.zonewars.enums.EnumRaces;

public class Zombie extends RaceBase {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getChatPrefix() {
		return(ChatColor.DARK_GREEN+"<Zombie>"+ChatColor.GREEN);
	}

	@Override
	public EnumRaces getEnum() {
		return EnumRaces.zombie;
	}

	public static boolean isPlayer(String pName) {
		return(ZoneWars.zombiePlayers.contains(pName));
	}
	
	public static void removePlayer(String pName) {
		ZoneWars.zombiePlayers.remove(pName);
	}
	
	public static void addPlayer(String pName) {
		ZoneWars.zombiePlayers.add(pName);
	}
	
	public static int playerCount() {
		return ZoneWars.zombiePlayers.size();
	}

}
