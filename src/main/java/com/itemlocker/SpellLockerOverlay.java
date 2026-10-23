package com.itemlocker;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class SpellLockerOverlay extends AbstractSpellOverlay
{
	@Inject
	public SpellLockerOverlay(Client client, ItemLockerPlugin plugin)
	{
		super(client, plugin);
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.MANUAL);
		drawAfterInterface(InterfaceID.MAGIC_SPELLBOOK);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		Widget tooltip = client.getWidget(InterfaceID.MagicSpellbook.TOOLTIP);
		Rectangle tooltipBounds = findTooltipBoundsFromWidget(tooltip);
		Shape originalClip = graphics.getClip();

		try
		{
			applyTooltipClip(graphics, tooltipBounds, originalClip);

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
