package com.itemlocker;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class AutocastLockerOverlay extends AbstractSpellOverlay
{
	@Inject
	public AutocastLockerOverlay(Client client, ItemLockerPlugin plugin)
	{
		super(client, plugin);
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.MANUAL);
		drawAfterInterface(InterfaceID.AUTOCAST);
		drawAfterInterface(InterfaceID.COMBAT_INTERFACE);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		Widget info = client.getWidget(InterfaceID.Autocast.INFO);
		Rectangle tooltipBounds = findTooltipBoundsFromWidget(info);
		Shape originalClip = graphics.getClip();

		try
		{
			applyTooltipClip(graphics, tooltipBounds, originalClip);

			List<Widget> candidateWidgets = new ArrayList<>();

			// Check all root widgets of InterfaceID.AUTOCAST (0 to 19)
			for (int i = 0; i < 20; i++)
			{
				Widget rootWidget = client.getWidget(InterfaceID.AUTOCAST, i);
				if (rootWidget == null || rootWidget.isHidden())
				{
					continue;
				}

				// Skip tooltip/info components (children 2 to 5)
				if (i >= 2 && i <= 5)
				{
					continue;
				}

				candidateWidgets.add(rootWidget);
				addChildren(candidateWidgets, rootWidget.getDynamicChildren());
				addChildren(candidateWidgets, rootWidget.getChildren());
				addChildren(candidateWidgets, rootWidget.getStaticChildren());
				try
				{
					addChildren(candidateWidgets, rootWidget.getNestedChildren());
				}
				catch (Exception ignored)
				{
				}
			}

			if (candidateWidgets.isEmpty())
			{
				return null;
			}

			Set<Spell> renderedSpells = new HashSet<>();

			for (Widget widget : candidateWidgets)
			{
				if (widget == null || widget.isHidden())
				{
					continue;
				}

				int w = widget.getWidth();
				int h = widget.getHeight();
				// A spell button/icon in the autocast grid is roughly 24x24
				if (w <= 0 || h <= 0 || w > 60 || h > 60)
				{
					continue;
				}

				Spell spell = findSpellForWidget(widget);
				if (spell != null && !plugin.isSpellCastable(spell) && renderedSpells.add(spell))
				{
					Rectangle bounds = widget.getBounds();
					if (bounds == null || bounds.width <= 0 || bounds.height <= 0 || bounds.x <= 0 || bounds.y <= 0)
					{
						bounds = getWidgetBounds(widget, null);
					}
					renderLockedOverlay(graphics, bounds);
				}
			}
		}
		finally
		{
			graphics.setClip(originalClip);
		}

		return null;
	}
}

