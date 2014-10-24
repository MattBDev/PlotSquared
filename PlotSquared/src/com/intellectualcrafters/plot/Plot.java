/*
 * Copyright (c) IntellectualCrafters - 2014. You are not allowed to distribute
 * and/or monetize any of our intellectual property. IntellectualCrafters is not
 * affiliated with Mojang AB. Minecraft is a trademark of Mojang AB.
 * 
 * >> File = Plot.java >> Generated by: Citymonstret at 2014-08-09 01:43
 */

package com.intellectualcrafters.plot;

import com.intellectualcrafters.plot.database.DBFunc;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The plot class
 *
 * @author Citymonstret
 */
@SuppressWarnings("javadoc")
public class Plot implements Cloneable {

	/**
	 * plot ID
	 */
	public PlotId id;
	/**
	 * plot world
	 */
	public String world;
	/**
	 * plot owner
	 */
	public UUID owner;
	/**
	 * Deny Entry
	 */
	public boolean deny_entry;
	/**
	 * List of helpers (with plot permissions)
	 */
	public ArrayList<UUID> helpers;
	/**
	 * List of trusted users (with plot permissions)
	 */
	public ArrayList<UUID> trusted;
	/**
	 * List of denied players
	 */
	public ArrayList<UUID> denied;
	/**
	 * External settings class
	 */
	public PlotSettings settings;
	/**
	 * Delete on next save cycle?
	 */
	public boolean delete;
	/**
	 * Has the plot changed since the last save cycle?
	 */
	public boolean hasChanged = false;
    public boolean countsTowardsMax = false;

	/**
	 * Primary constructor
	 * 
	 * @param id
	 * @param owner
	 * @param plotBiome
	 * @param helpers
	 * @param denied
	 */
	public Plot(PlotId id, UUID owner, Biome plotBiome, ArrayList<UUID> helpers, ArrayList<UUID> denied, String world) {
		this.id = id;
		this.settings = new PlotSettings(this);
		this.settings.setBiome(plotBiome);
		this.owner = owner;
		this.deny_entry = this.owner == null;
		this.helpers = helpers;
		this.denied = denied;
		this.trusted = new ArrayList<UUID>();
		this.settings.setAlias("");
		this.settings.setPosition(PlotHomePosition.DEFAULT);
		this.delete = false;
		this.settings.setFlags(new Flag[0]);
		this.world = world;
	}

	/**
	 * Constructor for saved plots
	 * 
	 * @param id
	 * @param owner
	 * @param plotBiome
	 * @param helpers
	 * @param denied
	 * @param changeTime
	 * @param time
	 * @param merged
	 */
	public Plot(PlotId id, UUID owner, Biome plotBiome, ArrayList<UUID> helpers, ArrayList<UUID> trusted,
			ArrayList<UUID> denied, String alias,
			PlotHomePosition position, Flag[] flags, String world, boolean[] merged) {
		this.id = id;
		this.settings = new PlotSettings(this);
		this.settings.setBiome(plotBiome);
		this.owner = owner;
		this.deny_entry = this.owner != null;
		this.trusted = trusted;
		this.helpers = helpers;
		this.denied = denied;
		this.settings.setAlias(alias);
		this.settings.setPosition(position);
		this.settings.setMerged(merged);
		this.delete = false;
		if (flags != null) {
			this.settings.setFlags(flags);
		}
		else {
			this.settings.setFlags(new Flag[0]);
		}
		this.world = world;
	}

	/**
	 * Check if the plot has a set owner
	 * 
	 * @return false if there is no owner
	 */
	public boolean hasOwner() {
		return this.owner != null;
	}

	/**
	 * Set the owner
	 * 
	 * @param player
	 */
	public void setOwner(Player player) {
		this.owner = player.getUniqueId();
	}

	/**
	 * Check if the player is either the owner or on the helpers list
	 * 
	 * @param player
	 * @return true if the player is added as a helper or is the owner
	 */
	public boolean hasRights(Player player) {
		return PlotMain.hasPermission(player, "plots.admin")
				|| ((this.helpers != null) && this.helpers.contains(DBFunc.everyone))
				|| ((this.helpers != null) && this.helpers.contains(player.getUniqueId()))
				|| ((this.owner != null) && this.owner.equals(player.getUniqueId()))
				|| ((this.owner != null) && (this.trusted != null) && (Bukkit.getPlayer(this.owner) != null) && (this.trusted.contains(player.getUniqueId()) || this.trusted.contains(DBFunc.everyone)));
	}

	/**
	 * Should the player be allowed to enter?
	 * 
	 * @param player
	 * @return false if the player is allowed to enter
	 */
	public boolean deny_entry(Player player) {
		return (this.denied != null)
				&& ((this.denied.contains(DBFunc.everyone) && !this.hasRights(player)) || (!this.hasRights(player) && this.denied.contains(player.getUniqueId())));
	}

	/**
	 * Get the UUID of the owner
	 */
	public UUID getOwner() {
		return this.owner;
	}

	/**
	 * Get the plot ID
	 */
	public PlotId getId() {
		return this.id;
	}

	/**
	 * Get the plot World
	 */
	public World getWorld() {
		return Bukkit.getWorld(this.world);
	}

	/**
	 * Get a clone of the plot
	 * 
	 * @return
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		try {
			return super.clone();
		}
		catch (CloneNotSupportedException e) {
			return null;
		}
	}

	/**
	 * Deny someone (use DBFunc.addDenied() as well)
	 * 
	 * @param uuid
	 */
	public void addDenied(UUID uuid) {
		this.denied.add(uuid);
	}

	/**
	 * Add someone as a helper (use DBFunc as well)
	 * 
	 * @param uuid
	 */
	public void addHelper(UUID uuid) {
		this.helpers.add(uuid);
	}

	/**
	 * Add someone as a trusted user (use DBFunc as well)
	 * 
	 * @param uuid
	 */
	public void addTrusted(UUID uuid) {
		this.trusted.add(uuid);
	}

	/**
	 * Get plot display name
	 * 
	 * @return alias if set, else id
	 */
	public String getDisplayName() {
		if (this.settings.getAlias().length() > 1) {
			return this.settings.getAlias();
		}
		return this.getId().x + ";" + this.getId().y;
	}

	/**
	 * Remove a denied player (use DBFunc as well)
	 * 
	 * @param uuid
	 */
	public void removeDenied(UUID uuid) {
		this.denied.remove(uuid);
	}

	/**
	 * Remove a helper (use DBFunc as well)
	 * 
	 * @param uuid
	 */
	public void removeHelper(UUID uuid) {
		this.helpers.remove(uuid);
	}

	/**
	 * Remove a trusted user (use DBFunc as well)
	 * 
	 * @param uuid
	 */
	public void removeTrusted(UUID uuid) {
		this.trusted.remove(uuid);
	}

	/**
	 * Clear a plot
	 * 
	 * @param plr
	 *            initiator
	 */
	public void clear(Player plr) {
		PlotHelper.clear(plr, this);
	}

}
