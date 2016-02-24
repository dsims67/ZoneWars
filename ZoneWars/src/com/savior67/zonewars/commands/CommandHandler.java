package com.savior67.zonewars.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.savior67.zonewars.ZoneWars;
import com.savior67.zonewars.capture.CapturePoint;
import com.savior67.zonewars.enums.EnumChannels;

public class CommandHandler {
	
	public static boolean process(CommandSender sender, Command cmd, String[] args) {
		//CHAT CHANNEL COMMANDS
		if(cmd.getName().equalsIgnoreCase("ac")) {
			if(EnumChannels.getChannelFor(sender.getName()) != EnumChannels.ac) {
				EnumChannels.switchChannel(sender.getName(), EnumChannels.ac);
				sender.sendMessage(ChatColor.BLUE+"Now Chatting in 'All Chat'");
			}
			else {
				sender.sendMessage(ChatColor.RED+"You are already in this channel");
			}
		}
		else if(cmd.getName().equalsIgnoreCase("tc")) {
			if(EnumChannels.getChannelFor(sender.getName()) != EnumChannels.tc) {
				EnumChannels.switchChannel(sender.getName(), EnumChannels.tc);
				sender.sendMessage(ChatColor.BLUE+"Now Chatting in 'Team Chat'");
			}
			else {
				sender.sendMessage(ChatColor.RED+"You are already in this channel");
			}
		}
		else if(cmd.getName().equalsIgnoreCase("sc")) {
			if(EnumChannels.getChannelFor(sender.getName()) != EnumChannels.sc) {
				EnumChannels.switchChannel(sender.getName(), EnumChannels.sc);
				sender.sendMessage(ChatColor.BLUE+"Now Chatting in 'Squad Chat'");
			}
			else {
				sender.sendMessage(ChatColor.RED+"You are already in this channel");
			}
		}
		else if(cmd.getName().equalsIgnoreCase("listpoints")) {
			sender.sendMessage(ChatColor.BLUE+"Existing Capture Points Include:  ");
			if(!ZoneWars.cps.isEmpty()) {
				for(CapturePoint cp: ZoneWars.cps) {
					sender.sendMessage(ChatColor.AQUA+cp.getName()+": "+cp.getStatus().toString());
				}
			}
			else {
				sender.sendMessage(ChatColor.AQUA+"There are none.");
			}
		}
		else if(cmd.getName().equalsIgnoreCase("createpoint") && args.length>=1 && sender.getName().equalsIgnoreCase("savior67")) {
			CapturePoint.create((Player)sender, args[0]);
			sender.sendMessage(ChatColor.BLUE+"Successfully Created A Capture Point Called "+args[0]);
		}
		else {
			sender.sendMessage(ChatColor.RED+"That is not a chat channel, use /ac or /tc");
		}
		
		return true;
	}

}
