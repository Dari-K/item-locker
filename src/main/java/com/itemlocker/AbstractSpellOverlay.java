package com.itemlocker;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.Overlay;

public abstract class AbstractSpellOverlay extends Overlay
{
	protected static final Color LOCKED_OVERLAY_COLOR = new Color(255, 0, 0, 64);

	protected final Client client;
	protected final ItemLockerPlugin plugin;

	public AbstractSpellOverlay(Client client, ItemLockerPlugin plugin)
	{
		this.client = client;
		this.plugin = plugin;
	}

	protected void renderLockedOverlay(Graphics2D graphics, Rectangle bounds)
	{
		if (bounds != null && bounds.width > 0 && bounds.height > 0)
		{
			graphics.setColor(LOCKED_OVERLAY_COLOR);
			graphics.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 4, 4);
		}
	}

	protected void applyTooltipClip(Graphics2D graphics, Rectangle tooltipBounds, Shape originalClip)
	{
		if (tooltipBounds != null && tooltipBounds.width > 0 && tooltipBounds.height > 0)
		{
			Area clipArea = new Area(originalClip != null ? originalClip : new Rectangle(0, 0, client.getCanvasWidth(), client.getCanvasHeight()));
			clipArea.subtract(new Area(tooltipBounds));
			graphics.setClip(clipArea);
		}
	}

	protected Rectangle findTooltipBoundsFromWidget(Widget container)
	{
		if (container == null || container.isHidden())
		{
			return null;
		}

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
			if (w <= 0 || h <= 0 || w > 220 || h > 200)
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
			return new Rectangle(union.x - 2, union.y - 2, union.width + 4, union.height + 4);
		}

		return null;
	}

	protected void addChildren(List<Widget> list, Widget[] children)
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

	public Spell findSpellForWidget(Widget widget)
	{
		if (widget == null)
		{
			return null;
		}

		Spell spell = Spell.forWidgetId(widget.getId());
		if (spell != null)
		{
			return spell;
		}

		if (widget.getName() != null && !widget.getName().isEmpty())
		{
			spell = Spell.forCleanName(widget.getName());
			if (spell != null)
			{
				return spell;
			}
		}

		if (widget.getText() != null && !widget.getText().isEmpty())
		{
			spell = Spell.forCleanName(widget.getText());
			if (spell != null)
			{
				return spell;
			}
		}

		if (widget.getTargetVerb() != null && !widget.getTargetVerb().isEmpty())
		{
			spell = Spell.forCleanName(widget.getTargetVerb());
			if (spell != null)
			{
				return spell;
			}
		}

		String[] actions = widget.getActions();
		if (actions != null)
		{
			for (String action : actions)
			{
				if (action != null && !action.isEmpty())
				{
					spell = Spell.forCleanName(action);
					if (spell != null)
					{
						return spell;
					}
				}
			}
		}

		// Only inspect children if this is an individual component/button (not a multi-spell container)
		if (widget.getWidth() > 0 && widget.getWidth() <= 60 && widget.getHeight() > 0 && widget.getHeight() <= 60)
		{
			Widget[] dynamicChildren = widget.getDynamicChildren();
			if (dynamicChildren != null)
			{
				for (Widget sub : dynamicChildren)
				{
					Spell subSpell = findSpellForWidget(sub);
					if (subSpell != null)
					{
						return subSpell;
					}
				}
			}

			Widget[] children = widget.getChildren();
			if (children != null)
			{
				for (Widget sub : children)
				{
					Spell subSpell = findSpellForWidget(sub);
					if (subSpell != null)
					{
						return subSpell;
					}
				}
			}

			Widget[] staticChildren = widget.getStaticChildren();
			if (staticChildren != null)
			{
				for (Widget sub : staticChildren)
				{
					Spell subSpell = findSpellForWidget(sub);
					if (subSpell != null)
					{
						return subSpell;
					}
				}
			}
		}

		return null;
	}

	protected Rectangle getWidgetBounds(Widget widget, Widget parent)
	{
		if (widget == null || widget.isHidden())
		{
			return null;
		}
		Rectangle bounds = widget.getBounds();
		if (bounds != null && bounds.width > 0 && bounds.height > 0 && bounds.x > 0 && bounds.y > 0)
		{
			return bounds;
		}
		int w = widget.getWidth();
		int h = widget.getHeight();
		if (w <= 0 || h <= 0)
		{
			return null;
		}
		Point parentLoc = (parent != null) ? parent.getCanvasLocation() : null;
		int x = (parentLoc != null) ? parentLoc.getX() + widget.getRelativeX() : widget.getRelativeX();
		int y = (parentLoc != null) ? parentLoc.getY() + widget.getRelativeY() : widget.getRelativeY();
		return new Rectangle(x, y, w, h);
	}
}

