package com.savior67.zonewars.enums;

public enum EnumStatus
{
	zombieOwned("Captured by Zombies", 0),
	witchOwned("Captured by Witches", 1),
	villagerOwned("Captured by Villagers", 2),
	neutral("Neutral", 3);
	
	private String info;
	private int index;
	
	private EnumStatus(String s, int ind)
	{
		info = s;
		index = ind;
	}
	
	public String getInfo() 
	{
		return info;
	}
	
	public static EnumStatus getFromIndex(int i) {
		if(i == 0)
			return zombieOwned;
		else if(i == 1)
			return witchOwned;
		else if(i == 2)
			return villagerOwned;
		else
			return neutral;
	}
	
}