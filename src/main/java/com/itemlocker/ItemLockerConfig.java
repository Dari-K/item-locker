package com.itemlocker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("itemlocker")
public interface ItemLockerConfig extends Config
{
    @ConfigItem(
        keyName = "unlockedItems",
        name = "Unlocked Items",
        description = "Stores all unlocked items as a CSV"
    )
    default String unlockedItems()
    {
        return "";
    }
}
