package com.itemlocker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.runelite.api.gameval.ItemID;

public enum RuneType
{
	AIR(
		Arrays.asList(
			ItemID.AIRRUNE,
			ItemID.DUSTRUNE,
			ItemID.MISTRUNE,
			ItemID.SMOKERUNE
		),
		Arrays.asList(
			ItemID.STAFF_OF_AIR,
			ItemID.AIR_BATTLESTAFF,
			ItemID.MYSTIC_AIR_STAFF,
			ItemID.DUST_BATTLESTAFF,
			ItemID.MYSTIC_DUST_BATTLESTAFF,
			ItemID.MIST_BATTLESTAFF,
			ItemID.MYSTIC_MIST_BATTLESTAFF,
			ItemID.SMOKE_BATTLESTAFF,
			ItemID.MYSTIC_SMOKE_BATTLESTAFF
		)
	),
	WATER(
		Arrays.asList(
			ItemID.WATERRUNE,
			ItemID.MUDRUNE,
			ItemID.MISTRUNE,
			ItemID.STEAMRUNE
		),
		Arrays.asList(
			ItemID.STAFF_OF_WATER,
			ItemID.WATER_BATTLESTAFF,
			ItemID.MYSTIC_WATER_STAFF,
			ItemID.MUD_BATTLESTAFF,
			ItemID.MYSTIC_MUD_STAFF,
			ItemID.MIST_BATTLESTAFF,
			ItemID.MYSTIC_MIST_BATTLESTAFF,
			ItemID.STEAM_BATTLESTAFF,
			ItemID.MYSTIC_STEAM_BATTLESTAFF,
			ItemID.TOME_OF_WATER,
			ItemID.KODAI_WAND
		)
	),
	EARTH(
		Arrays.asList(
			ItemID.EARTHRUNE,
			ItemID.DUSTRUNE,
			ItemID.MUDRUNE,
			ItemID.LAVARUNE
		),
		Arrays.asList(
			ItemID.STAFF_OF_EARTH,
			ItemID.EARTH_BATTLESTAFF,
			ItemID.MYSTIC_EARTH_STAFF,
			ItemID.DUST_BATTLESTAFF,
			ItemID.MYSTIC_DUST_BATTLESTAFF,
			ItemID.MUD_BATTLESTAFF,
			ItemID.MYSTIC_MUD_STAFF,
			ItemID.LAVA_BATTLESTAFF,
			ItemID.MYSTIC_LAVA_STAFF,
			ItemID.TOME_OF_EARTH
		)
	),
	FIRE(
		Arrays.asList(
			ItemID.FIRERUNE,
			ItemID.SUNFIRERUNE,
			ItemID.SMOKERUNE,
			ItemID.STEAMRUNE,
			ItemID.LAVARUNE
		),
		Arrays.asList(
			ItemID.STAFF_OF_FIRE,
			ItemID.FIRE_BATTLESTAFF,
			ItemID.MYSTIC_FIRE_STAFF,
			ItemID.SMOKE_BATTLESTAFF,
			ItemID.MYSTIC_SMOKE_BATTLESTAFF,
			ItemID.STEAM_BATTLESTAFF,
			ItemID.MYSTIC_STEAM_BATTLESTAFF,
			ItemID.LAVA_BATTLESTAFF,
			ItemID.MYSTIC_LAVA_STAFF,
			ItemID.TOME_OF_FIRE
		)
	),
	COSMIC(
		Arrays.asList(
			ItemID.COSMICRUNE,
			ItemID.AETHERRUNE
		),
		Collections.emptyList()
	),
	SOUL(
		Arrays.asList(
			ItemID.SOULRUNE,
			ItemID.AETHERRUNE
		),
		Collections.emptyList()
	),
	MIND(
		Collections.singletonList(ItemID.MINDRUNE),
		Collections.emptyList()
	),
	CHAOS(
		Collections.singletonList(ItemID.CHAOSRUNE),
		Collections.emptyList()
	),
	DEATH(
		Collections.singletonList(ItemID.DEATHRUNE),
		Collections.emptyList()
	),
	BLOOD(
		Collections.singletonList(ItemID.BLOODRUNE),
		Collections.emptyList()
	),
	LAW(
		Collections.singletonList(ItemID.LAWRUNE),
		Collections.emptyList()
	),
	NATURE(
		Collections.singletonList(ItemID.NATURERUNE),
		Collections.emptyList()
	),
	WRATH(
		Collections.singletonList(ItemID.WRATHRUNE),
		Collections.emptyList()
	),
	ASTRAL(
		Collections.singletonList(ItemID.ASTRALRUNE),
		Collections.emptyList()
	),
	BODY(
		Collections.singletonList(ItemID.BODYRUNE),
		Collections.emptyList()
	);

	private final List<Integer> validRuneItemIds;
	private final List<Integer> equippedSources;

	RuneType(List<Integer> validRuneItemIds, List<Integer> equippedSources)
	{
		this.validRuneItemIds = validRuneItemIds;
		this.equippedSources = equippedSources;
	}

	public List<Integer> getValidRuneItemIds()
	{
		return validRuneItemIds;
	}

	public List<Integer> getEquippedSources()
	{
		return equippedSources;
	}
}

