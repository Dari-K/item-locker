package com.itemlocker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class SpellLockerOverlay extends Overlay
{
	private static final Color LOCKED_OVERLAY_COLOR = new Color(255, 0, 0, 64);

	private final Client client;
	private final ItemLockerPlugin plugin;

	@Inject
	public SpellLockerOverlay(Client client, ItemLockerPlugin plugin)
	{
		this.client = client;
		this.plugin = plugin;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.MANUAL);
		drawAfterInterface(InterfaceID.MAGIC_SPELLBOOK);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		Rectangle tooltipBounds = getActiveTooltipBounds();
		Shape originalClip = graphics.getClip();

		try
		{
			// If a tooltip / hover info dialog is active, subtract its bounds from the clip
			// so that NO red pixels can EVER be rendered over the dialog area
			if (tooltipBounds != null && tooltipBounds.width > 0 && tooltipBounds.height > 0)
			{
				Area clipArea = new Area(originalClip != null ? originalClip : new Rectangle(0, 0, client.getCanvasWidth(), client.getCanvasHeight()));
				clipArea.subtract(new Area(tooltipBounds));
				graphics.setClip(clipArea);
			}

			for (Spell spell : Spell.values())
			{
				Widget widget = client.getWidget(spell.getWidgetId());
				if (widget == null || widget.isHidden())
				{
					continue;
				}

				if (!plugin.isSpellCastable(spell))
				{
					Rectangle bounds = widget.getBounds();
					if (bounds != null && bounds.width > 0 && bounds.height > 0)
					{
						graphics.setColor(LOCKED_OVERLAY_COLOR);
						graphics.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 4, 4);
					}
				}
			}
		}
		finally
		{
			graphics.setClip(originalClip);
		}

		return null;
	}

	private Rectangle getActiveTooltipBounds()
	{
		Widget tooltip = client.getWidget(InterfaceID.MagicSpellbook.TOOLTIP);
		return findTooltipBoundsFromWidget(tooltip);
	}

	private Rectangle findTooltipBoundsFromWidget(Widget container)
	{
		if (container == null || container.isHidden())
		{
			return null;
		}

		// Inspect children to find the popup bounds
		List<Widget> allChildren = new ArrayList<>();
		addChildren(allChildren, container.getDynamicChildren());
		addChildren(allChildren, container.getChildren());
		addChildren(allChildren, container.getStaticChildren());
		try
		{
			addChildren(allChildren, container.getNestedChildren());
		}
		catch (Exception ignored)
		{
		}

		Point parentLoc = container.getCanvasLocation();
		Rectangle union = null;

		for (Widget child : allChildren)
		{
			if (child == null || child.isHidden())
			{
				continue;
			}

			int w = child.getWidth();
			int h = child.getHeight();
			if (w <= 0 || h <= 0)
			{
				continue;
			}

			// Must be reasonable size for a tooltip component (skip fullscreen container layers)
			if (w > 220 || h > 200)
			{
				continue;
			}

			int x = child.getCanvasLocation() != null ? child.getCanvasLocation().getX() : -1;
			int y = child.getCanvasLocation() != null ? child.getCanvasLocation().getY() : -1;

			if ((x <= 0 || y <= 0) && parentLoc != null)
			{
				x = parentLoc.getX() + child.getRelativeX();
				y = parentLoc.getY() + child.getRelativeY();
			}

			if (x > 0 && y > 0 && (w > 12 || h > 12))
			{
				Rectangle cb = new Rectangle(x, y, w, h);
				union = (union == null) ? new Rectangle(cb) : union.union(cb);
			}
		}

		if (union != null)
		{
			// Add a slight margin (2px) to ensure no border bleed
			return new Rectangle(union.x - 2, union.y - 2, union.width + 4, union.height + 4);
		}

		return null;
	}

	private void addChildren(List<Widget> list, Widget[] children)
	{
		if (children != null)
		{
			for (Widget w : children)
			{
				if (w != null)
				{
					list.add(w);
				}
			}
		}
	}
}
