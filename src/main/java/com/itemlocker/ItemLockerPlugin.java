package com.itemlocker;

import com.google.inject.Provides;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.KeyCode;
import net.runelite.api.Menu;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.MenuOpened;
import net.runelite.api.events.PostMenuSort;
import net.runelite.api.widgets.InterfaceID;
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
	private static final String UNLOCK_KEY_PREFIX = "unlock_";
	@Inject
	private Client client;

	@Inject
	private ConfigManager configManager;

	@Inject
	private ItemLockerOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Provides
	ItemLockerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ItemLockerConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	boolean getUnlocked(int itemId)
	{
		String unlocked = configManager.getConfiguration("itemlocker", UNLOCK_KEY_PREFIX + itemId);
		if (unlocked == null || unlocked.isEmpty())
		{
			return false;
		}
		return true;
	}

	void setUnlocked(int itemId)
	{
		configManager.setConfiguration("itemlocker", UNLOCK_KEY_PREFIX + itemId, "1");
	}

	void setLocked(int itemId)
	{
		configManager.unsetConfiguration("itemlocker", UNLOCK_KEY_PREFIX + itemId);
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

				client.createMenuEntry(idx)
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
