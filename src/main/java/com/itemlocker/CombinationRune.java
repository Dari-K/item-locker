package com.itemlocker;

import java.util.Arrays;
import java.util.List;
import net.runelite.api.gameval.ItemID;

public enum CombinationRune
{
	MIST(ItemID.MISTRUNE, false, RuneType.AIR, RuneType.WATER),
	DUST(ItemID.DUSTRUNE, false, RuneType.AIR, RuneType.EARTH),
	MUD(ItemID.MUDRUNE, false, RuneType.WATER, RuneType.EARTH),
	SMOKE(ItemID.SMOKERUNE, false, RuneType.AIR, RuneType.FIRE),
	STEAM(ItemID.STEAMRUNE, false, RuneType.WATER, RuneType.FIRE),
	LAVA(ItemID.LAVARUNE, false, RuneType.EARTH, RuneType.FIRE),
	AETHER(ItemID.AETHERRUNE, false, RuneType.COSMIC, RuneType.SOUL),
	SUNFIRE(ItemID.SUNFIRERUNE, true, RuneType.FIRE);

	private final int itemId;
	private final boolean consumedEvenWithInfiniteSource;
	private final List<RuneType> runeTypes;

	CombinationRune(int itemId, boolean consumedEvenWithInfiniteSource, RuneType... runeTypes)
	{
		this.itemId = itemId;
		this.consumedEvenWithInfiniteSource = consumedEvenWithInfiniteSource;
		this.runeTypes = Arrays.asList(runeTypes);
	}

	public int getItemId()
	{
		return itemId;
	}

	public boolean isConsumedEvenWithInfiniteSource()
	{
		return consumedEvenWithInfiniteSource;
	}

	public List<RuneType> getRuneTypes()
	{
		return runeTypes;
	}
}
