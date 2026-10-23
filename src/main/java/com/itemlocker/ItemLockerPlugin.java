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
import net.runelite.api.EnumComposition;
import net.runelite.api.EnumID;
import net.runelite.api.ItemContainer;
import net.runelite.api.KeyCode;
import net.runelite.api.Menu;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.MenuOpened;
import net.runelite.api.events.PostMenuSort;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.VarbitID;
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
	private ItemLockerConfig config;

	@Inject
	private ItemLockerOverlay overlay;

	@Inject
	private SpellLockerOverlay spellOverlay;

	@Inject
	private AutocastLockerOverlay autocastOverlay;

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
		this.unlockedItems = parseUnlockedItems(config.unlockedItems());
		overlayManager.add(overlay);
		overlayManager.add(spellOverlay);
		overlayManager.add(autocastOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		overlayManager.remove(spellOverlay);
		overlayManager.remove(autocastOverlay);
		this.unlockedItems.clear();
	}

	boolean getUnlocked(int itemId)
	{
		return this.unlockedItems.contains(itemId);
	}

	private Set<Integer> parseUnlockedItems(String csv)
	{
		if (csv == null || csv.isEmpty())
		{
			return new HashSet<>();
		}
		return Arrays.stream(csv.split(","))
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
			.collect(Collectors.toCollection(HashSet::new));
	}

	private void saveUnlockedItems()
	{
		configManager.setConfiguration(
			ItemLockerConfig.CONFIG_GROUP,
			ItemLockerConfig.KEY_UNLOCKED_ITEMS,
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
		if (!configChanged.getGroup().equals(ItemLockerConfig.CONFIG_GROUP))
		{
			return;
		}

		if (configChanged.getKey().equals(ItemLockerConfig.KEY_UNLOCKED_ITEMS))
		{
			this.unlockedItems = parseUnlockedItems(configChanged.getNewValue());
		}

		overlay.invalidateCache();
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

	private static final int[] RUNE_POUCH_RUNE_VARBITS = {
		VarbitID.RUNE_POUCH_TYPE_1,
		VarbitID.RUNE_POUCH_TYPE_2,
		VarbitID.RUNE_POUCH_TYPE_3,
		VarbitID.RUNE_POUCH_TYPE_4,
		VarbitID.RUNE_POUCH_TYPE_5,
		VarbitID.RUNE_POUCH_TYPE_6
	};

	private static final int[] RUNE_POUCH_AMOUNT_VARBITS = {
		VarbitID.RUNE_POUCH_QUANTITY_1,
		VarbitID.RUNE_POUCH_QUANTITY_2,
		VarbitID.RUNE_POUCH_QUANTITY_3,
		VarbitID.RUNE_POUCH_QUANTITY_4,
		VarbitID.RUNE_POUCH_QUANTITY_5,
		VarbitID.RUNE_POUCH_QUANTITY_6
	};

	private boolean hasRunePouch(ItemContainer inventory)
	{
		return inventory.contains(ItemID.BH_RUNE_POUCH)
			|| inventory.contains(ItemID.BH_RUNE_POUCH_TROUVER)
			|| inventory.contains(ItemID.DIVINE_RUNE_POUCH)
			|| inventory.contains(ItemID.DIVINE_RUNE_POUCH_TROUVER)
			|| inventory.contains(ItemID.BR_RUNE_REPLACEMENT)
			|| inventory.contains(ItemID.PVPA_RUNE_REPLACEMENT)
			|| inventory.contains(ItemID.CASTLEWARS_RUNE_REPLACEMENT);
	}

	public boolean isSpellCastable(Spell spell)
	{
		ItemContainer equipment = client.getItemContainer(InventoryID.WORN);
		ItemContainer inventory = client.getItemContainer(InventoryID.INV);
		boolean hasRunePouch = inventory != null && hasRunePouch(inventory);

		// 1. If casting this spell would consume any locked combination rune, block it
		if (wouldConsumeLockedCombinationRune(spell, equipment, inventory, hasRunePouch))
		{
			return false;
		}

		// 2. Ensure every required rune has an active, unlocked source
		for (RuneType rune : spell.getRequiredRunes())
		{
			if (!hasActiveUnlockedSource(rune, equipment, inventory, hasRunePouch))
			{
				return false;
			}
		}
		return true;
	}

	private boolean wouldConsumeLockedCombinationRune(
		Spell spell,
		ItemContainer equipment,
		ItemContainer inventory,
		boolean hasRunePouch)
	{
		for (CombinationRune combo : CombinationRune.values())
		{
			int comboId = combo.getItemId();
			if (getUnlocked(comboId))
			{
				continue;
			}

			boolean playerHasComboRune = (inventory != null && inventory.contains(comboId))
				|| (hasRunePouch && isRuneInPouch(comboId));

			if (!playerHasComboRune)
			{
				continue;
			}

			// If the spell requires any element this combo rune provides:
			for (RuneType comboRuneType : combo.getRuneTypes())
			{
				if (spellRequiresRune(spell, comboRuneType))
				{
					// If this rune is consumed even with infinite sources (e.g. Sunfire runes),
					// OR if we don't have an equipped infinite source to protect it:
					if (combo.isConsumedEvenWithInfiniteSource() || !hasEquippedUnlockedSource(comboRuneType, equipment))
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	private boolean spellRequiresRune(Spell spell, RuneType runeType)
	{
		for (RuneType req : spell.getRequiredRunes())
		{
			if (req == runeType)
			{
				return true;
			}
		}
		return false;
	}

	private boolean hasEquippedUnlockedSource(RuneType rune, ItemContainer equipment)
	{
		if (equipment == null)
		{
			return false;
		}
		for (int sourceId : rune.getEquippedSources())
		{
			if (equipment.contains(sourceId) && getUnlocked(sourceId))
			{
				return true;
			}
		}
		return false;
	}

	private boolean isRuneInPouch(int targetItemId)
	{
		for (int i = 0; i < RUNE_POUCH_RUNE_VARBITS.length; i++)
		{
			int amount = client.getVarbitValue(RUNE_POUCH_AMOUNT_VARBITS[i]);
			if (amount > 0)
			{
				int runeVal = client.getVarbitValue(RUNE_POUCH_RUNE_VARBITS[i]);
				if (runeVal > 0)
				{
					EnumComposition runeEnum = client.getEnum(EnumID.RUNEPOUCH_RUNE);
					if (runeEnum != null)
					{
						int itemId = runeEnum.getIntValue(runeVal);
						if (itemId == targetItemId)
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean hasActiveUnlockedSource(RuneType rune, ItemContainer equipment, ItemContainer inventory, boolean hasRunePouch)
	{
		// 1. Check equipped infinite sources (e.g. staves, tomes)
		if (hasEquippedUnlockedSource(rune, equipment))
		{
			return true;
		}

		// 2. Check inventory runes
		if (inventory != null)
		{
			for (int runeId : rune.getValidRuneItemIds())
			{
				if (inventory.contains(runeId) && getUnlocked(runeId))
				{
					return true;
				}
			}
		}

		// 3. Check Rune Pouch runes
		if (hasRunePouch)
		{
			for (int runeId : rune.getValidRuneItemIds())
			{
				if (getUnlocked(runeId) && isRuneInPouch(runeId))
				{
					return true;
				}
			}
		}

		return false;
	}

	private Spell findSpellFromMenu(MenuEntry entry, Widget widget)
	{
		if (entry != null)
		{
			if (entry.getTarget() != null && !entry.getTarget().isEmpty())
			{
				Spell s = Spell.forCleanName(entry.getTarget());
				if (s != null)
				{
					return s;
				}
			}

			if (entry.getOption() != null && !entry.getOption().isEmpty())
			{
				Spell s = Spell.forCleanName(entry.getOption());
				if (s != null)
				{
					return s;
				}
			}

			if (entry.getOption() != null && entry.getTarget() != null)
			{
				Spell s = Spell.forCleanName(entry.getOption() + " " + entry.getTarget());
				if (s != null)
				{
					return s;
				}
			}
		}

		if (widget != null)
		{
			Spell s = Spell.forWidgetId(widget.getId());
			if (s != null)
			{
				return s;
			}
			return autocastOverlay.findSpellForWidget(widget);
		}

		return null;
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
		
		if (menuEntries.length == 0) {
			return;
		}

		MenuEntry leftClickEntry = menuEntries[menuEntries.length - 1];
		Widget w = leftClickEntry.getWidget();

		if (w == null)
		{
			return;
		}

		int interfaceId = WidgetUtil.componentToInterface(w.getId());

		if (interfaceId == InterfaceID.INVENTORY)
		{
			if (getUnlocked(leftClickEntry.getItemId()))
			{
				return;
			}

			MenuEntry newLeftClickEntry = root.createMenuEntry(-1);
			newLeftClickEntry.setOption("Locked");
			newLeftClickEntry.setType(MenuAction.CANCEL);
			return;
		}

		if (interfaceId == InterfaceID.MAGIC_SPELLBOOK)
		{
			Spell spell = findSpellFromMenu(leftClickEntry, w);
			if (spell != null && !isSpellCastable(spell))
			{
				MenuEntry newLeftClickEntry = root.createMenuEntry(-1);
				newLeftClickEntry.setOption("Locked");
				newLeftClickEntry.setType(MenuAction.CANCEL);
			}
			return;
		}

		if (interfaceId == InterfaceID.AUTOCAST || interfaceId == InterfaceID.COMBAT_INTERFACE)
		{
			Spell spell = findSpellFromMenu(leftClickEntry, w);
			if (spell != null && !isSpellCastable(spell))
			{
				MenuEntry newLeftClickEntry = root.createMenuEntry(-1);
				newLeftClickEntry.setOption("Locked");
				newLeftClickEntry.setType(MenuAction.CANCEL);
			}
		}
	}
}
