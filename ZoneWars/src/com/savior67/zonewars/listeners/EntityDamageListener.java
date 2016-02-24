package com.savior67.zonewars.listeners;

import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import com.savior67.zonewars.race.RaceBase;

public class EntityDamageListener implements Listener {
	
	//Turns off friendly fire
	@EventHandler
    public void onEntityDamageEvent(EntityDamageByEntityEvent event) {
		if(event.getDamager().getType() == EntityType.PLAYER && event.getEntity().getType() == EntityType.PLAYER) 
		{
			CraftPlayer cpDamager = (CraftPlayer) event.getDamager();
			CraftPlayer cpVictim = (CraftPlayer) event.getEntity();
			if(RaceBase.getRaceOf(cpDamager.getDisplayName()) == RaceBase.getRaceOf(cpVictim.getDisplayName()))
				event.setCancelled(true);
		}
		
	}

}
