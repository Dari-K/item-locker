package com.itemlocker;

import com.google.inject.Provides;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.KeyCode;
import net.runelite.api.Menu;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.MenuOpened;
import net.runelite.api.events.PostMenuSort;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetUtil;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.Text;

@Slf4j
@PluginDescriptor(
	name = "Item Locker"
)
public class ItemLockerPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ConfigManager configManager;

	@Inject
	private ItemLockerOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	private Set<Integer> unlockedItems = new HashSet<>();

	@Provides
	ItemLockerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ItemLockerConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		String csv = configManager.getConfiguration("itemlocker", "unlockedItems");
		if (csv != null && !csv.isEmpty()) {
			this.unlockedItems = Arrays.stream(csv.split(","))
			.map(String::trim)
			.filter(s -> !s.isEmpty())
			.map(s -> {
				try {
					return Integer.parseInt(s);
				} catch (NumberFormatException e) {
					return null;
				}
			})
			.filter(Objects::nonNull)
			.collect(Collectors.toSet());
		}
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	boolean getUnlocked(int itemId)
	{
		return this.unlockedItems.contains(itemId);
	}

	private void saveUnlockedItems() {
		configManager.setConfiguration
		("itemlocker",
		"unlockedItems",
		this.unlockedItems.stream()
			.map(String::valueOf)
			.collect(Collectors.joining(","))
		);
	}

	void setUnlocked(int itemId)
	{
		this.unlockedItems.add(itemId);
		this.saveUnlockedItems();
	}

	void setLocked(int itemId)
	{
		this.unlockedItems.remove(itemId);
		this.saveUnlockedItems();
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged configChanged)
	{
		if (configChanged.getGroup().equals("itemlocker"))
		{
			overlay.invalidateCache();
		}
	}

	@Subscribe
	public void onMenuOpened(final MenuOpened event)
	{
		if (!client.isKeyPressed(KeyCode.KC_SHIFT))
		{
			return;
		}

		final MenuEntry[] entries = event.getMenuEntries();
		for (int idx = entries.length - 1; idx >= 0; --idx)
		{
			final MenuEntry entry = entries[idx];
			final Widget w = entry.getWidget();

			if (w != null && WidgetUtil.componentToInterface(w.getId()) == InterfaceID.INVENTORY
				&& "Examine".equals(entry.getOption()) && entry.getIdentifier() == 10)
			{
				final int itemId = w.getItemId();
				final boolean unlocked = getUnlocked(itemId);

				client.getMenu().createMenuEntry(idx)
					.setOption(unlocked ? "Lock" : "Unlock")
					.setTarget(entry.getTarget())
					.setType(MenuAction.RUNELITE)
					.onClick(e ->
					{
						if (unlocked) {
							setLocked(itemId);
						} else {
							setUnlocked(itemId);
						}
					});
			}
		}
	}

	@Subscribe
	public void onPostMenuSort(PostMenuSort postMenuSort)
	{
		if (client.isMenuOpen())
		{
			return;
		}
		Menu root = client.getMenu();
		MenuEntry[] menuEntries = root.getMenuEntries();

		int idx = 0;
		for (MenuEntry entry : menuEntries)
		{
			if (getUnlocked(entry.getItemId())) {
				break;
			}

			final String option = Text.removeTags(entry.getOption()).toLowerCase();
			final Widget w = entry.getWidget();

			if (w != null && WidgetUtil.componentToInterface(w.getId()) == InterfaceID.INVENTORY
				&& (option.equals("drop") || option.equals("destroy")))
			{
				MenuEntry leftClickEntry = menuEntries[menuEntries.length - 1];
				menuEntries[menuEntries.length - 1] = entry;
				menuEntries[idx] = leftClickEntry;
				if (entry.getType() == MenuAction.CC_OP_LOW_PRIORITY)
				{
					entry.setType(MenuAction.CC_OP);
				}
				if (leftClickEntry.getType() == MenuAction.CC_OP_LOW_PRIORITY)
				{
					leftClickEntry.setType(MenuAction.CC_OP);
				}
				root.setMenuEntries(menuEntries);
				break;
			}
			idx++;
		}
	}
}
