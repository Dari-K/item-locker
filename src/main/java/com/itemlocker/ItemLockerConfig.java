package com.itemlocker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(ItemLockerConfig.CONFIG_GROUP)
public interface ItemLockerConfig extends Config
{
	String CONFIG_GROUP = "itemlocker";
	String KEY_UNLOCKED_ITEMS = "unlockedItems";

	@ConfigItem(
		keyName = KEY_UNLOCKED_ITEMS,
		name = "Unlocked Items",
		description = "Stores all unlocked items as a CSV"
	)
	default String unlockedItems()
	{
		return "";
	}
}

