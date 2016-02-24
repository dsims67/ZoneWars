package com.savior67.zonewars;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.savior67.zonewars.capture.CaptureBar;
import com.savior67.zonewars.capture.CapturePoint;
import com.savior67.zonewars.commands.CommandHandler;
import com.savior67.zonewars.enums.EnumChannels;
import com.savior67.zonewars.enums.EnumStatus;
import com.savior67.zonewars.listeners.ChatListener;
import com.savior67.zonewars.listeners.EntityDamageListener;
import com.savior67.zonewars.listeners.LoginListener;
import com.savior67.zonewars.race.RaceBase;

public class ZoneWars extends JavaPlugin implements Listener{
	public static FileConfiguration config;
	
	private static Plugin plugin;
	public static List<String> villagerPlayers = new ArrayList<String>();
	public static List<String> witchPlayers = new ArrayList<String>();
	public static List<String> zombiePlayers = new ArrayList<String>();
	public static HashMap<String, EnumChannels> chatters = new HashMap<String, EnumChannels>();
	public static List<CapturePoint> cps = new ArrayList<CapturePoint>();




	
	
	/* 3 Races - Zombie - Witch - Villager
	 * Each has initial base, capture points spread out over map
	 * tickhandler checks players within certain radius of point
	 * if point is neutral or not owned by that player's race then capture will begin
	 * EnderDragon bar will decrease, point will return to neutral, then increase
	 * Races able to teleport on owned points
	 * Item in all players inventory with warp book, changes to add/subtract captured points realtime
	 * When capturing is finished, BLAST FIREWORKS
	 */
	
	public void onEnable() {
		this.saveDefaultConfig(); //saves copy of default config if it doesn't exist
		getLogger().info("ZoneWars has been enabled!");
		PluginManager manager = getServer().getPluginManager();
		manager.registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new LoginListener(), this);
		getServer().getPluginManager().registerEvents(new ChatListener(), this);
		getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
		
		plugin = getServer().getPluginManager().getPlugin("ZoneWars");
		config = this.getConfig();
		loadPoints();
		
		
		//On tick listener
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@SuppressWarnings("deprecation")
			
			@Override
			public void run()
			{
				//IF PLAYER IS WITHIN CAPTURE RANGE OF THE POINT
				//DISPLAY CAPTURE BAR FOR THEM (store the capture progress in CapturePoint)
				//Regens over time, rate increases with more players on point
				
				//BAR LISTENER
				for(int i=0;i<cps.size();i++) {
					CapturePoint cp = cps.get(i);
					for(Player player:Bukkit.getOnlinePlayers()) {
						//if the player standing on point is friendly
						if(cp.isWithinRangeOf(player))  {
							//times 2 because enderdragon has 200 health
							CaptureBar.removeBar(player);
							CaptureBar.displayTextBar(cp.getTitle(), player, cp.getHealth()*2);//, cp.getHealth());
							cp.captureListener();
						}
						
						//if the player is an invader
//						if(cp.isWithinRangeOf(player) && !RaceBase.getRaceOf(player.getDisplayName()).getName().equalsIgnoreCase(cp.getOwner())) {
//							//CaptureBar.displayTextBar(cp.getTitle(), player, cp.getHealth()*2);
//							cp.captureListener();
//						}
					}
				}
				
				//CAPTURE POINT LISTENER
				
				
				
//				HeadsUpDisplay.displayTextBarForAll(getCurrentMessage());
//				if(getRemainingTime().getSeconds() == 1) 
//				{
//					nextMessage();
//					timeLeft = DateUtils.addSeconds(new Date(), interval);
//				}
				
			}
		}, 1, 1);
		//end of on tick listener
	}
	
	@Override
	public void onDisable() {
		getLogger().info("ZoneWars has been disabled!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
			return CommandHandler.process(sender, cmd, args);

	}
	
	public static final Plugin getInstance() {
		return Bukkit.getServer().getPluginManager().getPlugin("ZoneWars");
	}
	
	//loads existing points from the config
	private void loadPoints() {
		
	    Map<String, Object> pointNames = config.getConfigurationSection("CapturePoints").getValues(false);//config.getStringList("CapturePoints");
	    for(Entry<String, Object> set: pointNames.entrySet()) {
	    	String n = set.getKey().toString();
	    	getLogger().info(config.getString("CapturePoints."+n+".location"));
	    	Location loc = str2loc(config.getString("CapturePoints."+n+".location"));

	    	EnumStatus state = EnumStatus.valueOf(config.getString("CapturePoints."+n+".state"));
	    	cps.add(new CapturePoint(n, loc, state));
	    }
	}
	
	//converts a string to a location
	public Location str2loc(String str){
		String loc[]=str.split("\\,");
		World w = Bukkit.getWorld(loc[0]);
		Double x = Double.parseDouble(loc[1]);
		Double y = Double.parseDouble(loc[2]);
		Double z = Double.parseDouble(loc[3]);
		float yaw = Float.parseFloat(loc[4]);
		float pitch = Float.parseFloat(loc[5]);
		Location location = new Location(w, x, y, z, yaw, pitch);
		return location;
	}

	public static void savecfg() {
		try {
			config.save(plugin.getDataFolder() + File.separator + "config.yml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void delay(int seconds) {
		//1000 is one second
	    try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
