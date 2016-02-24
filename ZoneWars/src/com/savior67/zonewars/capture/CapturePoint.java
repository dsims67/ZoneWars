package com.savior67.zonewars.capture;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.savior67.zonewars.ZoneWars;
import com.savior67.zonewars.enums.EnumRaces;
import com.savior67.zonewars.enums.EnumStatus;
import com.savior67.zonewars.race.RaceBase;

public class CapturePoint {
	
	private static String name;
	private static Location location;
	private static EnumStatus currentStatus;
	private static World world;
	private static int health;
	
	//number of friendly players on point
	private static int friendlies;
	//number of hostiles on point
	private static int hostiles = 1;
	
	private static long lastHealthTick = 0;
	

	
	
	
	//for loading points from config
	public CapturePoint(String n, Location l, EnumStatus s) {
		name = n;
		world = Bukkit.getServer().getWorld("world");
		location = l;
		currentStatus = s;
	}
	
	
	//Creates a capture point at players location with name n
	public static void create(Player p, String n) {
		//Creates the item
//		ItemStack beacon = new ItemStack(Material.BEACON);
//		ItemMeta im = beacon.getItemMeta();
//		im.setDisplayName(name);
//		beacon.setItemMeta(im);
		
		health = 1;
		Location pLoc = p.getLocation();
		double x = pLoc.getX();
		double y = pLoc.getY()-1; 
		double z = pLoc.getZ();
		name = n;
		world = Bukkit.getServer().getWorld("world");
		location = new Location(world, x, y, z);
		Block b = location.getBlock();
		b.setType(Material.BEACON);
		
		//Create ironblock base under beacon
		y-=1;
		x-=1;
		z-=1;
		//Creates a square of iron blocks
		for(int posX=0; posX<3; posX++) {
			for(int posZ=0; posZ<3; posZ++) {
				Block ironblock = new Location(p.getWorld(), x+posX, y, z+posZ).getBlock();
				ironblock.setType(Material.IRON_BLOCK);
			}
		}
		
		//Initialize to neutral status
		setStatus(EnumStatus.neutral);
		
		//Add to cps
		ZoneWars.cps.add(new CapturePoint(name, location, currentStatus));
		
		//Add to config
		String path = "CapturePoints."+name;
		String strLoc = location.getWorld().getName()+","+location.getX()+","+location.getY()+","+location.getZ()+","+location.getYaw()+","+location.getPitch();
		ZoneWars.config.addDefault(path+".location", strLoc);
		ZoneWars.config.addDefault(path+".state", currentStatus.toString());
		ZoneWars.config.options().copyDefaults(true);
		ZoneWars.savecfg();
		
	}
	
	//if player is within 2 blocks of point in any direction return true
	public static boolean isWithinRangeOf(Player p) {
		if(location.distance(p.getLocation()) < 3)
			return true;
		else
			return false;
	}
	
	
	
	public static EnumStatus getStatus() {
		return currentStatus;
	}
	
	public static String getName() {
		return name;
	}
	
	public static String getTitle() {
		String owner;
		if(currentStatus == EnumStatus.villagerOwned)
			owner = "&6Villagers";
		else if(currentStatus == EnumStatus.witchOwned)
			owner = "&5Witches";
		else if(currentStatus == EnumStatus.zombieOwned)
			owner = "&2Zombies";
		else
			owner = "&fNobody";
		return("&a"+name+" - &cCaptured By "+owner);
	}
	
	//returns the percentage that the point has been captured
	public static int getHealth() {
		return health;
	}
	
	public static void setHealth(int i) {
		if(i < 1)
			health = 1;
		else if(i > 100)
			health = 100;
		else
			health = i;
	}
	
	public static String getOwner() {
		String owner;
		if(currentStatus == EnumStatus.villagerOwned)
			owner = "Villager";
		else if(currentStatus == EnumStatus.witchOwned)
			owner = "Witch";
		else if(currentStatus == EnumStatus.zombieOwned)
			owner = "Zombie";
		else
			owner = "Neutral";
		return owner;
	}
	
	//handles point capturing, for use in tickhandler in ZoneWars
	public static void captureListener() {
		//THIS NEEDS TO BE CHANGED LATER
		int[] capturers = getCapturers();
		//race is the race with the most units on point, 0 zombie, 1 witch, 2 villager
		int race = 0;
		if(capturers[1] > capturers[race])
			race = 1;
		else if(capturers[2] > capturers[race])
			race = 2;
		else if(capturers[race] == 0)
			return; //nobody on point
		
		long currentTime = Bukkit.getServer().getWorld("world").getTime();
		
		int captureRate = 5-(capturers[race]*1); //what was I thinking when I wrote this.
		if(captureRate<5)
			captureRate = 5;
		
		if(currentTime%captureRate==0 && lastHealthTick!=currentTime && currentStatus != EnumStatus.neutral 
				&& !EnumRaces.getFromIndex(race).getName().equalsIgnoreCase(getOwner())) {
			setHealth(getHealth()-1);
			lastHealthTick = currentTime;
		}
		else if(currentTime%captureRate==0 && lastHealthTick!=currentTime && currentStatus == EnumStatus.neutral) {
			setHealth(getHealth()+1);
			lastHealthTick = currentTime;
		}
		
		if(getHealth()<=1 && currentStatus != EnumStatus.neutral) {
			setStatus(EnumStatus.neutral);
		}
		else if(getHealth()>=100 && currentStatus == EnumStatus.neutral) {
			setStatus(EnumStatus.getFromIndex(race));
		}
	}
	
	//would it be better to keep track as they enter a point?
	private static int[] getCapturers() {
		//0 zombie, 1 witch, 2 villager
		int[] playersOnPoint = {0, 0, 0};
		for(Player player:Bukkit.getOnlinePlayers()) 
		{
			if(isWithinRangeOf(player)) {
				EnumRaces pRace = RaceBase.getRaceOf(player.getDisplayName());
				if(pRace.getName() == "Zombie")
					playersOnPoint[0]+=1;
				else if(pRace.getName() == "Witch")
					playersOnPoint[1]+=1;
				else if(pRace.getName() == "Villager")
					playersOnPoint[2]+=1;
			}
		}
		return playersOnPoint;
	}
	
	//changes wool color depending on current status
	public static void setStatus(EnumStatus s) {
		currentStatus = s;
		Byte color;
		
		double x = location.getX()-2;
		double y = location.getY();
		double z = location.getZ()-2;
		
		if(s == EnumStatus.villagerOwned)
			color = DyeColor.YELLOW.getData();
		else if(s == EnumStatus.witchOwned)
			color = DyeColor.MAGENTA.getData();
		else if(s == EnumStatus.zombieOwned)
			color = DyeColor.LIME.getData();
		else
			color = DyeColor.WHITE.getData();
			
		for(int posX=0; posX<5; posX++) {
			for(int posZ=0; posZ<5; posZ++) {
				Block block = new Location(world, x+posX, y, z+posZ).getBlock();
				if(block.getType() == Material.BEACON)
					continue;
				else
					block.setTypeIdAndData(35, color, true);
			}
		}
		
		//Change status in config
		ZoneWars.config.set("CapturePoints."+name+".state", currentStatus.toString());
		ZoneWars.savecfg();
	}

}
