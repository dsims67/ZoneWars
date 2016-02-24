package com.savior67.zonewars.race;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.savior67.zonewars.enums.EnumRaces;

public abstract class RaceBase {
	
	public abstract String getName();
	
	public abstract EnumRaces getEnum();
	
	public static EnumRaces getRaceOf(String pName) {
		if(Villager.isPlayer(pName))
			return EnumRaces.villager;
		else if(Zombie.isPlayer(pName))
			return EnumRaces.zombie;
		else if(Witch.isPlayer(pName))
			return EnumRaces.witch;
		else
			return null;
		
	}
	
	//returns the team(race) with the least amount of players
	public static EnumRaces findLeastPopulated() {
		int least = Villager.playerCount();
		EnumRaces currRace = EnumRaces.villager;
		
		if(Witch.playerCount() <= least) {
			least = Witch.playerCount();
			currRace = EnumRaces.witch;
		}
		
		if(Zombie.playerCount() <= least) {
			least = Zombie.playerCount();
			currRace = EnumRaces.zombie;
		}
		
		return currRace;	
	}
	
    public static void sendChatToRace(EnumRaces race, String msg) {
    	for(Player p: Bukkit.getServer().getOnlinePlayers()) {
    		if(RaceBase.getRaceOf(p.getDisplayName()) == race)
    			p.sendMessage(msg);
    	}
    }

}
