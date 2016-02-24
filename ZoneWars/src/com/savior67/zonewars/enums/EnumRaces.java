package com.savior67.zonewars.enums;

import org.bukkit.entity.Player;

import com.savior67.zonewars.race.RaceBase;
import com.savior67.zonewars.race.Villager;
import com.savior67.zonewars.race.Witch;
import com.savior67.zonewars.race.Zombie;

public enum EnumRaces 
{
	zombie("Zombie", 0),
	witch("Witch", 1),
	villager("Villager", 2);

	
	private String name;
	private int index;
	
	private EnumRaces(String n, int i)
	{
		name = n;
		index = i;
	}
	
	
	public String getName() 
	{
		return name;
	}
	
	public static EnumRaces getFromIndex(int i) {
		if(i==0)
			return EnumRaces.zombie;
		else if(i==1)
			return EnumRaces.witch;
		else if(i==2)
			return EnumRaces.villager;
		else
			return null;
	}
	
	public String getPrefix() {
		if(name == "Villager")
			return Villager.getChatPrefix();
		else if(name == "Witch")
			return Witch.getChatPrefix();
		else if(name =="Zombie")
			return Zombie.getChatPrefix();
		else
			return "";
	}
	
	public EnumRaces getRaceOf(String pName) {
		return RaceBase.getRaceOf(pName);
	}
	
	//Remove player from team
	public void removePlayer(String pName) {
		if(name == "Villager")
			Villager.removePlayer(pName);
		else if(name == "Witch")
			Witch.removePlayer(pName);
		else if(name =="Zombie")
			Zombie.removePlayer(pName);
	}
	
	public void addPlayer(String pName) {
		if(name == "Villager")
			Villager.addPlayer(pName);
		else if(name == "Witch")
			Witch.addPlayer(pName);
		else if(name =="Zombie")
			Zombie.addPlayer(pName);
	}
}
