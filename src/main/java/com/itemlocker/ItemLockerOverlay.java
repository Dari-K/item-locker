package com.itemlocker;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.inject.Inject;

import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import net.runelite.client.util.AsyncBufferedImage;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.ImageUtil;

class ItemLockerOverlay extends WidgetItemOverlay
{
	private final ItemManager itemManager;
	private final ItemLockerPlugin plugin;
	private final Cache<Long, Image> fillCache;

	@Inject
	private ItemLockerOverlay(ItemManager itemManager, ItemLockerPlugin plugin)
	{
		this.itemManager = itemManager;
		this.plugin = plugin;
		showOnEquipment();
		showOnInventory();
		showOnInterfaces(
			InterfaceID.BANKMAIN,
			InterfaceID.RAIDS_STORAGE_SIDE,
			InterfaceID.RAIDS_STORAGE_SHARED,
			InterfaceID.RAIDS_STORAGE_PRIVATE,
			InterfaceID.GRAVESTONE_RETRIEVAL,
			InterfaceID.GRAVESTONE_GENERIC,
			InterfaceID.BANK_DEPOSITBOX
		);
		fillCache = CacheBuilder.newBuilder()
			.concurrencyLevel(1)
			.maximumSize(256)
			.build();
	}

	@Override
	public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem)
	{
		final boolean unlocked = plugin.getUnlocked(itemId);
		if (unlocked) {
			return;
		}

		final Color color = Color.RED;

		Rectangle bounds = widgetItem.getCanvasBounds();

		final Image image = getFillImage(color, widgetItem.getId(), widgetItem.getQuantity());
		if (image != null)
		{
			graphics.drawImage(image, (int) bounds.getX(), (int) bounds.getY(), null);
		}
	}

	private Image getFillImage(Color color, int itemId, int qty)
	{
		long key = (((long) itemId) << 32) | qty;
		Image image = fillCache.getIfPresent(key);
		if (image == null)
		{
			final Color fillColor = ColorUtil.colorWithAlpha(color, 64);
			AsyncBufferedImage itemImage = itemManager.getImage(itemId, qty, false);
			image = ImageUtil.fillImage(itemImage, fillColor);
			fillCache.put(key, image);
			itemImage.onLoaded(() -> {
				Image loadedImage = ImageUtil.fillImage(itemImage, fillColor);
				fillCache.put(key, loadedImage);
			});
			image = fillCache.getIfPresent(key);
		}
		return image;
	}

	void invalidateCache()
	{
		fillCache.invalidateAll();
	}
}