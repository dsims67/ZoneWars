package com.savior67.zonewars.enums;

import java.util.ArrayList;
import java.util.List;

import com.savior67.zonewars.ZoneWars;

public enum EnumChannels 
{
	ac("ac","All Chat"),
	tc("tc", "Team Chat"),
	sc("sc", "Squad Chat");

	
	private String name;
	private String fullName;
	
	private EnumChannels(String n, String s)
	{
		name = n;
		fullName = s;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public String getFullName() 
	{
		return fullName;
	}
	
	//Returns true if player is chatting in provided channel
	public static boolean isChattingIn(String pName, EnumChannels ch) {
			if(ZoneWars.chatters.get(pName) == ch)
				return true;
			else
				return false;
	}
	
	public static EnumChannels getChannelFor(String pName) {
		return ZoneWars.chatters.get(pName);
	}
	
	public static void switchChannel(String pName, EnumChannels ch) {
		ZoneWars.chatters.put(pName, ch);
	}
	
	public static void removeChatter(String pName) {
		ZoneWars.chatters.remove(pName);
	}
	
}
