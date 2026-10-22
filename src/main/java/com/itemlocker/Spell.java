package com.itemlocker;

import java.util.HashMap;
import java.util.Map;
import net.runelite.api.gameval.InterfaceID;

public enum Spell
{
	// Standard - Combat Strikes, Bolts, Blasts, Waves, Surges
	WIND_STRIKE(InterfaceID.MagicSpellbook.WIND_STRIKE, RuneType.AIR, RuneType.MIND),
	WATER_STRIKE(InterfaceID.MagicSpellbook.WATER_STRIKE, RuneType.WATER, RuneType.AIR, RuneType.MIND),
	EARTH_STRIKE(InterfaceID.MagicSpellbook.EARTH_STRIKE, RuneType.EARTH, RuneType.AIR, RuneType.MIND),
	FIRE_STRIKE(InterfaceID.MagicSpellbook.FIRE_STRIKE, RuneType.FIRE, RuneType.AIR, RuneType.MIND),

	WIND_BOLT(InterfaceID.MagicSpellbook.WIND_BOLT, RuneType.AIR, RuneType.CHAOS),
	WATER_BOLT(InterfaceID.MagicSpellbook.WATER_BOLT, RuneType.WATER, RuneType.AIR, RuneType.CHAOS),
	EARTH_BOLT(InterfaceID.MagicSpellbook.EARTH_BOLT, RuneType.EARTH, RuneType.AIR, RuneType.CHAOS),
	FIRE_BOLT(InterfaceID.MagicSpellbook.FIRE_BOLT, RuneType.FIRE, RuneType.AIR, RuneType.CHAOS),

	WIND_BLAST(InterfaceID.MagicSpellbook.WIND_BLAST, RuneType.AIR, RuneType.DEATH),
	WATER_BLAST(InterfaceID.MagicSpellbook.WATER_BLAST, RuneType.WATER, RuneType.AIR, RuneType.DEATH),
	EARTH_BLAST(InterfaceID.MagicSpellbook.EARTH_BLAST, RuneType.EARTH, RuneType.AIR, RuneType.DEATH),
	FIRE_BLAST(InterfaceID.MagicSpellbook.FIRE_BLAST, RuneType.FIRE, RuneType.AIR, RuneType.DEATH),

	WIND_WAVE(InterfaceID.MagicSpellbook.WIND_WAVE, RuneType.AIR, RuneType.BLOOD),
	WATER_WAVE(InterfaceID.MagicSpellbook.WATER_WAVE, RuneType.WATER, RuneType.AIR, RuneType.BLOOD),
	EARTH_WAVE(InterfaceID.MagicSpellbook.EARTH_WAVE, RuneType.EARTH, RuneType.AIR, RuneType.BLOOD),
	FIRE_WAVE(InterfaceID.MagicSpellbook.FIRE_WAVE, RuneType.FIRE, RuneType.AIR, RuneType.BLOOD),

	WIND_SURGE(InterfaceID.MagicSpellbook.WIND_SURGE, RuneType.AIR, RuneType.DEATH, RuneType.WRATH),
	WATER_SURGE(InterfaceID.MagicSpellbook.WATER_SURGE, RuneType.WATER, RuneType.AIR, RuneType.DEATH, RuneType.WRATH),
	EARTH_SURGE(InterfaceID.MagicSpellbook.EARTH_SURGE, RuneType.EARTH, RuneType.AIR, RuneType.DEATH, RuneType.WRATH),
	FIRE_SURGE(InterfaceID.MagicSpellbook.FIRE_SURGE, RuneType.FIRE, RuneType.AIR, RuneType.DEATH, RuneType.WRATH),

	// Standard - Debuffs & Binds
	CONFUSE(InterfaceID.MagicSpellbook.CONFUSE, RuneType.WATER, RuneType.EARTH, RuneType.BODY),
	WEAKEN(InterfaceID.MagicSpellbook.WEAKEN, RuneType.WATER, RuneType.EARTH, RuneType.BODY),
	CURSE(InterfaceID.MagicSpellbook.CURSE, RuneType.WATER, RuneType.EARTH, RuneType.BODY),
	BIND(InterfaceID.MagicSpellbook.BIND, RuneType.EARTH, RuneType.WATER, RuneType.NATURE),
	SNARE(InterfaceID.MagicSpellbook.SNARE, RuneType.EARTH, RuneType.WATER, RuneType.NATURE),
	ENTANGLE(InterfaceID.MagicSpellbook.ENTANGLE, RuneType.EARTH, RuneType.WATER, RuneType.NATURE),
	VULNERABILITY(InterfaceID.MagicSpellbook.VULNERABILITY, RuneType.EARTH, RuneType.WATER, RuneType.SOUL),
	ENFEEBLE(InterfaceID.MagicSpellbook.ENFEEBLE, RuneType.EARTH, RuneType.WATER, RuneType.SOUL),
	STUN(InterfaceID.MagicSpellbook.STUN, RuneType.EARTH, RuneType.WATER, RuneType.SOUL),
	TELEPORT_BLOCK(InterfaceID.MagicSpellbook.TELEPORT_BLOCK, RuneType.CHAOS, RuneType.DEATH, RuneType.LAW),

	// Standard - Utility, Alchemy & God Spells
	BONES_TO_BANANAS(InterfaceID.MagicSpellbook.BONES_BANANAS, RuneType.EARTH, RuneType.WATER, RuneType.NATURE),
	LOW_LEVEL_ALCHEMY(InterfaceID.MagicSpellbook.LOW_ALCHEMY, RuneType.FIRE, RuneType.NATURE),
	HIGH_LEVEL_ALCHEMY(InterfaceID.MagicSpellbook.HIGH_ALCHEMY, RuneType.FIRE, RuneType.NATURE),
	TELEKINETIC_GRAB(InterfaceID.MagicSpellbook.TELEGRAB, RuneType.AIR, RuneType.LAW),
	SUPERHEAT_ITEM(InterfaceID.MagicSpellbook.SUPERHEAT, RuneType.FIRE, RuneType.NATURE),
	CRUMBLE_UNDEAD(InterfaceID.MagicSpellbook.CRUMBLE_UNDEAD, RuneType.EARTH, RuneType.AIR, RuneType.CHAOS),
	IBAN_BLAST(InterfaceID.MagicSpellbook.IBAN_BLAST, RuneType.FIRE, RuneType.DEATH),
	MAGIC_DART(InterfaceID.MagicSpellbook.MAGIC_DART, RuneType.DEATH, RuneType.MIND),
	BONES_TO_PEACHES(InterfaceID.MagicSpellbook.BONES_PEACHES, RuneType.EARTH, RuneType.WATER, RuneType.NATURE),
	SARADOMIN_STRIKE(InterfaceID.MagicSpellbook.SARADOMIN_STRIKE, RuneType.FIRE, RuneType.BLOOD, RuneType.AIR),
	CLAWS_OF_GUTHIX(InterfaceID.MagicSpellbook.CLAWS_OF_GUTHIX, RuneType.FIRE, RuneType.BLOOD, RuneType.AIR),
	FLAMES_OF_ZAMORAK(InterfaceID.MagicSpellbook.FLAMES_OF_ZAMORAK, RuneType.FIRE, RuneType.BLOOD, RuneType.AIR),
	CHARGE(InterfaceID.MagicSpellbook.CHARGE, RuneType.FIRE, RuneType.BLOOD, RuneType.AIR),

	// Standard - Enchantment
	ENCHANT_1(InterfaceID.MagicSpellbook.ENCHANT_1, RuneType.WATER, RuneType.COSMIC),
	ENCHANT_2(InterfaceID.MagicSpellbook.ENCHANT_2, RuneType.EARTH, RuneType.COSMIC),
	ENCHANT_3(InterfaceID.MagicSpellbook.ENCHANT_3, RuneType.FIRE, RuneType.COSMIC),
	ENCHANT_4(InterfaceID.MagicSpellbook.ENCHANT_4, RuneType.EARTH, RuneType.COSMIC),
	ENCHANT_5(InterfaceID.MagicSpellbook.ENCHANT_5, RuneType.WATER, RuneType.EARTH, RuneType.COSMIC),
	ENCHANT_6(InterfaceID.MagicSpellbook.ENCHANT_6, RuneType.FIRE, RuneType.EARTH, RuneType.COSMIC),
	ENCHANT_7(InterfaceID.MagicSpellbook.ENCHANT_7, RuneType.BLOOD, RuneType.SOUL, RuneType.COSMIC),

	// Standard - Orb Charging
	CHARGE_WATER_ORB(InterfaceID.MagicSpellbook.CHARGE_WATER_ORB, RuneType.WATER, RuneType.COSMIC),
	CHARGE_EARTH_ORB(InterfaceID.MagicSpellbook.CHARGE_EARTH_ORB, RuneType.EARTH, RuneType.COSMIC),
	CHARGE_FIRE_ORB(InterfaceID.MagicSpellbook.CHARGE_FIRE_ORB, RuneType.FIRE, RuneType.COSMIC),
	CHARGE_AIR_ORB(InterfaceID.MagicSpellbook.CHARGE_AIR_ORB, RuneType.AIR, RuneType.COSMIC),

	// Standard - Teleports
	VARROCK_TELEPORT(InterfaceID.MagicSpellbook.VARROCK_TELEPORT, RuneType.FIRE, RuneType.AIR, RuneType.LAW),
	LUMBRIDGE_TELEPORT(InterfaceID.MagicSpellbook.LUMBRIDGE_TELEPORT, RuneType.EARTH, RuneType.AIR, RuneType.LAW),
	FALADOR_TELEPORT(InterfaceID.MagicSpellbook.FALADOR_TELEPORT, RuneType.WATER, RuneType.AIR, RuneType.LAW),
	HOUSE_TELEPORT(InterfaceID.MagicSpellbook.TELEPORT_TO_YOUR_HOUSE, RuneType.LAW, RuneType.AIR, RuneType.EARTH),
	CAMELOT_TELEPORT(InterfaceID.MagicSpellbook.CAMELOT_TELEPORT, RuneType.AIR, RuneType.LAW),
	ARDOUGNE_TELEPORT(InterfaceID.MagicSpellbook.ARDOUGNE_TELEPORT, RuneType.WATER, RuneType.LAW),
	WATCHTOWER_TELEPORT(InterfaceID.MagicSpellbook.WATCHTOWER_TELEPORT, RuneType.EARTH, RuneType.LAW),
	TROLLHEIM_TELEPORT(InterfaceID.MagicSpellbook.TROLLHEIM_TELEPORT, RuneType.FIRE, RuneType.LAW),
	APE_ATOLL_TELEPORT(InterfaceID.MagicSpellbook.APE_TELEPORT, RuneType.FIRE, RuneType.WATER, RuneType.LAW),
	KOUREND_TELEPORT(InterfaceID.MagicSpellbook.KOUREND_TELEPORT, RuneType.FIRE, RuneType.WATER, RuneType.LAW, RuneType.SOUL),

	// Ancient Magicks
	SMOKE_RUSH(InterfaceID.MagicSpellbook.SMOKE_RUSH, RuneType.AIR, RuneType.FIRE, RuneType.CHAOS, RuneType.DEATH),
	SHADOW_RUSH(InterfaceID.MagicSpellbook.SHADOW_RUSH, RuneType.AIR, RuneType.CHAOS, RuneType.DEATH, RuneType.SOUL),
	BLOOD_RUSH(InterfaceID.MagicSpellbook.BLOOD_RUSH, RuneType.BLOOD, RuneType.CHAOS, RuneType.DEATH),
	ICE_RUSH(InterfaceID.MagicSpellbook.ICE_RUSH, RuneType.WATER, RuneType.CHAOS, RuneType.DEATH),

	SMOKE_BURST(InterfaceID.MagicSpellbook.SMOKE_BURST, RuneType.AIR, RuneType.FIRE, RuneType.CHAOS, RuneType.DEATH),
	SHADOW_BURST(InterfaceID.MagicSpellbook.SHADOW_BURST, RuneType.AIR, RuneType.CHAOS, RuneType.DEATH, RuneType.SOUL),
	BLOOD_BURST(InterfaceID.MagicSpellbook.BLOOD_BURST, RuneType.BLOOD, RuneType.CHAOS, RuneType.DEATH),
	ICE_BURST(InterfaceID.MagicSpellbook.ICE_BURST, RuneType.WATER, RuneType.CHAOS, RuneType.DEATH),

	SMOKE_BLITZ(InterfaceID.MagicSpellbook.SMOKE_BLITZ, RuneType.AIR, RuneType.FIRE, RuneType.BLOOD, RuneType.DEATH),
	SHADOW_BLITZ(InterfaceID.MagicSpellbook.SHADOW_BLITZ, RuneType.AIR, RuneType.BLOOD, RuneType.DEATH, RuneType.SOUL),
	BLOOD_BLITZ(InterfaceID.MagicSpellbook.BLOOD_BLITZ, RuneType.BLOOD, RuneType.DEATH),
	ICE_BLITZ(InterfaceID.MagicSpellbook.ICE_BLITZ, RuneType.WATER, RuneType.BLOOD, RuneType.DEATH),

	SMOKE_BARRAGE(InterfaceID.MagicSpellbook.SMOKE_BARRAGE, RuneType.AIR, RuneType.FIRE, RuneType.BLOOD, RuneType.DEATH),
	SHADOW_BARRAGE(InterfaceID.MagicSpellbook.SHADOW_BARRAGE, RuneType.AIR, RuneType.BLOOD, RuneType.DEATH, RuneType.SOUL),
	BLOOD_BARRAGE(InterfaceID.MagicSpellbook.BLOOD_BARRAGE, RuneType.BLOOD, RuneType.DEATH, RuneType.SOUL),
	ICE_BARRAGE(InterfaceID.MagicSpellbook.ICE_BARRAGE, RuneType.WATER, RuneType.BLOOD, RuneType.DEATH),

	// Ancient Magicks - Teleports
	PADDEWWA_TELEPORT(InterfaceID.MagicSpellbook.ZAROSTELEPORT1, RuneType.LAW, RuneType.AIR, RuneType.FIRE),
	SENNTISTEN_TELEPORT(InterfaceID.MagicSpellbook.ZAROSTELEPORT2, RuneType.LAW, RuneType.SOUL),
	KHARYRLL_TELEPORT(InterfaceID.MagicSpellbook.ZAROSTELEPORT3, RuneType.LAW, RuneType.BLOOD),
	LASSAR_TELEPORT(InterfaceID.MagicSpellbook.ZAROSTELEPORT4, RuneType.LAW, RuneType.WATER),
	DAREEYAK_TELEPORT(InterfaceID.MagicSpellbook.ZAROSTELEPORT5, RuneType.LAW, RuneType.AIR, RuneType.FIRE),
	CARRALLANGAR_TELEPORT(InterfaceID.MagicSpellbook.ZAROSTELEPORT6, RuneType.LAW, RuneType.SOUL),
	ANNAKARL_TELEPORT(InterfaceID.MagicSpellbook.ZAROSTELEPORT7, RuneType.LAW, RuneType.BLOOD),
	GHORROCK_TELEPORT(InterfaceID.MagicSpellbook.ZAROSTELEPORT8, RuneType.LAW, RuneType.WATER),

	// Lunar Spells
	BAKE_PIE(InterfaceID.MagicSpellbook.BAKE_PIE, RuneType.ASTRAL, RuneType.FIRE, RuneType.WATER),
	MONSTER_EXAMINE(InterfaceID.MagicSpellbook.MONSTER_EXAMINE, RuneType.ASTRAL, RuneType.COSMIC, RuneType.MIND),
	HUMIDIFY(InterfaceID.MagicSpellbook.HUMIDIFY, RuneType.ASTRAL, RuneType.WATER, RuneType.FIRE),
	HUNTER_KIT(InterfaceID.MagicSpellbook.HUNTER_KIT, RuneType.ASTRAL, RuneType.EARTH),
	MOONCLAN_TELEPORT(InterfaceID.MagicSpellbook.TELE_MOONCLAN, RuneType.ASTRAL, RuneType.EARTH, RuneType.LAW),
	WATERBIRTH_TELEPORT(InterfaceID.MagicSpellbook.TELE_WATERBIRTH, RuneType.ASTRAL, RuneType.WATER, RuneType.LAW),
	BARBARIAN_TELEPORT(InterfaceID.MagicSpellbook.TELE_BARB_OUT, RuneType.ASTRAL, RuneType.FIRE, RuneType.LAW),
	SUPERGLASS_MAKE(InterfaceID.MagicSpellbook.SUPERGLASS, RuneType.ASTRAL, RuneType.FIRE, RuneType.AIR),
	TAN_LEATHER(InterfaceID.MagicSpellbook.TAN_LEATHER, RuneType.ASTRAL, RuneType.NATURE, RuneType.FIRE),
	KHAZARD_TELEPORT(InterfaceID.MagicSpellbook.TELE_KHAZARD, RuneType.ASTRAL, RuneType.EARTH, RuneType.LAW),
	DREAM(InterfaceID.MagicSpellbook.DREAM, RuneType.ASTRAL, RuneType.COSMIC, RuneType.BODY),
	STRING_JEWELLERY(InterfaceID.MagicSpellbook.STRING_JEWEL, RuneType.ASTRAL, RuneType.WATER, RuneType.EARTH),
	MAGIC_IMBUE(InterfaceID.MagicSpellbook.MAGIC_IMBUE, RuneType.ASTRAL, RuneType.FIRE, RuneType.WATER),
	FERTILE_SOIL(InterfaceID.MagicSpellbook.FERTILE_SOIL, RuneType.ASTRAL, RuneType.NATURE, RuneType.EARTH),
	FISHING_GUILD_TELEPORT(InterfaceID.MagicSpellbook.TELE_FISH, RuneType.ASTRAL, RuneType.WATER, RuneType.LAW),
	PLANK_MAKE(InterfaceID.MagicSpellbook.PLANK_MAKE, RuneType.ASTRAL, RuneType.NATURE, RuneType.EARTH),
	CATHERBY_TELEPORT(InterfaceID.MagicSpellbook.TELE_CATHER, RuneType.ASTRAL, RuneType.WATER, RuneType.LAW),
	RECHARGE_DRAGONSTONE(InterfaceID.MagicSpellbook.RECHARGE_DRAGONSTONE, RuneType.ASTRAL, RuneType.WATER, RuneType.SOUL),
	ICE_PLATEAU_TELEPORT(InterfaceID.MagicSpellbook.TELE_GHORROCK, RuneType.ASTRAL, RuneType.WATER, RuneType.LAW),
	ENERGY_TRANSFER(InterfaceID.MagicSpellbook.ENERGY_TRANS, RuneType.ASTRAL, RuneType.NATURE, RuneType.LAW),
	HEAL_OTHER(InterfaceID.MagicSpellbook.HEAL_OTHER, RuneType.ASTRAL, RuneType.LAW, RuneType.BLOOD),
	VENGEANCE_OTHER(InterfaceID.MagicSpellbook.VENGEANCE_OTHER, RuneType.ASTRAL, RuneType.DEATH, RuneType.EARTH),
	VENGEANCE(InterfaceID.MagicSpellbook.VENGEANCE, RuneType.ASTRAL, RuneType.DEATH, RuneType.EARTH),
	HEAL_GROUP(InterfaceID.MagicSpellbook.HEAL_GROUP, RuneType.ASTRAL, RuneType.LAW, RuneType.BLOOD),
	SPELLBOOK_SWAP(InterfaceID.MagicSpellbook.SPELLBOOK_SWAP, RuneType.ASTRAL, RuneType.COSMIC, RuneType.LAW),
	GEOMANCY(InterfaceID.MagicSpellbook.GEOMANCY, RuneType.ASTRAL, RuneType.NATURE, RuneType.EARTH),
	SPIN_FLAX(InterfaceID.MagicSpellbook.SPIN_FLAX, RuneType.ASTRAL, RuneType.NATURE, RuneType.AIR),
	OURANIA_TELEPORT(InterfaceID.MagicSpellbook.OURANIA_TELEPORT, RuneType.ASTRAL, RuneType.EARTH, RuneType.LAW),

	// Arceuus Spells
	REANIMATION_BASIC(InterfaceID.MagicSpellbook.REANIMATION_BASIC, RuneType.BODY, RuneType.NATURE),
	REANIMATION_ADEPT(InterfaceID.MagicSpellbook.REANIMATION_ADEPT, RuneType.BODY, RuneType.NATURE, RuneType.SOUL),
	REANIMATION_EXPERT(InterfaceID.MagicSpellbook.REANIMATION_EXPERT, RuneType.BODY, RuneType.NATURE, RuneType.BLOOD, RuneType.SOUL),
	REANIMATION_MASTER(InterfaceID.MagicSpellbook.REANIMATION_MASTER, RuneType.NATURE, RuneType.BLOOD, RuneType.SOUL),
	INFERIOR_DEMONBANE(InterfaceID.MagicSpellbook.INFERIOR_DEMONBANE, RuneType.FIRE, RuneType.CHAOS),
	SUPERIOR_DEMONBANE(InterfaceID.MagicSpellbook.SUPERIOR_DEMONBANE, RuneType.FIRE, RuneType.SOUL),
	DARK_DEMONBANE(InterfaceID.MagicSpellbook.DARK_DEMONBANE, RuneType.FIRE, RuneType.SOUL),
	MARK_OF_DARKNESS(InterfaceID.MagicSpellbook.MARK_OF_DARKNESS, RuneType.COSMIC, RuneType.SOUL),
	GHOSTLY_GRASP(InterfaceID.MagicSpellbook.GHOSTLY_GRASP, RuneType.AIR, RuneType.CHAOS),
	SKELETAL_GRASP(InterfaceID.MagicSpellbook.SKELETAL_GRASP, RuneType.AIR, RuneType.DEATH),
	UNDEAD_GRASP(InterfaceID.MagicSpellbook.UNDEAD_GRASP, RuneType.AIR, RuneType.BLOOD),
	WARD_OF_ARCEUUS(InterfaceID.MagicSpellbook.WARD_OF_ARCEUUS, RuneType.COSMIC, RuneType.NATURE, RuneType.SOUL),
	LESSER_CORRUPTION(InterfaceID.MagicSpellbook.LESSER_CORRUPTION, RuneType.DEATH, RuneType.SOUL),
	GREATER_CORRUPTION(InterfaceID.MagicSpellbook.GREATER_CORRUPTION, RuneType.BLOOD, RuneType.SOUL),
	DEMONIC_OFFERING(InterfaceID.MagicSpellbook.DEMONIC_OFFERING, RuneType.WRATH, RuneType.SOUL),
	SINISTER_OFFERING(InterfaceID.MagicSpellbook.SINISTER_OFFERING, RuneType.WRATH, RuneType.BLOOD),
	DEGRIME(InterfaceID.MagicSpellbook.DEGRIME, RuneType.NATURE, RuneType.EARTH),
	SHADOW_VEIL(InterfaceID.MagicSpellbook.SHADOW_VEIL, RuneType.FIRE, RuneType.COSMIC, RuneType.EARTH),
	VILE_VIGOUR(InterfaceID.MagicSpellbook.VILE_VIGOUR, RuneType.AIR, RuneType.BLOOD, RuneType.SOUL),
	DARK_LURE(InterfaceID.MagicSpellbook.DARK_LURE, RuneType.DEATH, RuneType.NATURE, RuneType.SOUL),
	DEATH_CHARGE(InterfaceID.MagicSpellbook.DEATH_CHARGE, RuneType.BLOOD, RuneType.DEATH, RuneType.SOUL),
	RESURRECT_LESSER_GHOST(InterfaceID.MagicSpellbook.RESURRECT_LESSER_GHOST, RuneType.COSMIC, RuneType.CHAOS, RuneType.SOUL),
	RESURRECT_LESSER_SKELETON(InterfaceID.MagicSpellbook.RESURRECT_LESSER_SKELETON, RuneType.COSMIC, RuneType.CHAOS, RuneType.SOUL),
	RESURRECT_LESSER_ZOMBIE(InterfaceID.MagicSpellbook.RESURRECT_LESSER_ZOMBIE, RuneType.COSMIC, RuneType.CHAOS, RuneType.SOUL),
	RESURRECT_SUPERIOR_GHOST(InterfaceID.MagicSpellbook.RESURRECT_SUPERIOR_GHOST, RuneType.COSMIC, RuneType.DEATH, RuneType.SOUL),
	RESURRECT_SUPERIOR_SKELETON(InterfaceID.MagicSpellbook.RESURRECT_SUPERIOR_SKELETON, RuneType.COSMIC, RuneType.DEATH, RuneType.SOUL),
	RESURRECT_SUPERIOR_ZOMBIE(InterfaceID.MagicSpellbook.RESURRECT_SUPERIOR_ZOMBIE, RuneType.COSMIC, RuneType.DEATH, RuneType.SOUL),
	RESURRECT_GREATER_GHOST(InterfaceID.MagicSpellbook.RESURRECT_GREATER_GHOST, RuneType.COSMIC, RuneType.BLOOD, RuneType.SOUL),
	RESURRECT_GREATER_SKELETON(InterfaceID.MagicSpellbook.RESURRECT_GREATER_SKELETON, RuneType.COSMIC, RuneType.BLOOD, RuneType.SOUL),
	RESURRECT_GREATER_ZOMBIE(InterfaceID.MagicSpellbook.RESURRECT_GREATER_ZOMBIE, RuneType.COSMIC, RuneType.BLOOD, RuneType.SOUL),
	TELEPORT_ARCEUUS_LIBRARY(InterfaceID.MagicSpellbook.TELEPORT_ARCEUUS_LIBRARY, RuneType.LAW, RuneType.COSMIC),
	TELEPORT_DRAYNOR_MANOR(InterfaceID.MagicSpellbook.TELEPORT_DRAYNOR_MANOR, RuneType.LAW, RuneType.COSMIC, RuneType.EARTH),
	TELEPORT_MIND_ALTAR(InterfaceID.MagicSpellbook.TELEPORT_MIND_ALTAR, RuneType.LAW, RuneType.COSMIC, RuneType.MIND),
	TELEPORT_RESPAWN(InterfaceID.MagicSpellbook.TELEPORT_RESPAWN, RuneType.LAW, RuneType.COSMIC, RuneType.SOUL),
	TELEPORT_SALVE_GRAVEYARD(InterfaceID.MagicSpellbook.TELEPORT_SALVE_GRAVEYARD, RuneType.LAW, RuneType.COSMIC, RuneType.SOUL),
	TELEPORT_FENKENSTRAIN_CASTLE(InterfaceID.MagicSpellbook.TELEPORT_FENKENSTRAIN_CASTLE, RuneType.LAW, RuneType.COSMIC, RuneType.EARTH),
	TELEPORT_WEST_ARDOUGNE(InterfaceID.MagicSpellbook.TELEPORT_WEST_ARDOUGNE, RuneType.LAW, RuneType.COSMIC),
	TELEPORT_HARMONY_ISLAND(InterfaceID.MagicSpellbook.TELEPORT_HARMONY_ISLAND, RuneType.LAW, RuneType.COSMIC, RuneType.NATURE),
	TELEPORT_CEMETERY(InterfaceID.MagicSpellbook.TELEPORT_CEMETERY, RuneType.LAW, RuneType.COSMIC, RuneType.BLOOD),
	TELEPORT_BARROWS(InterfaceID.MagicSpellbook.TELEPORT_BARROWS, RuneType.LAW, RuneType.COSMIC, RuneType.BLOOD, RuneType.SOUL),
	TELEPORT_APE_ATOLL_DUNGEON(InterfaceID.MagicSpellbook.TELEPORT_APE_ATOLL_DUNGEON, RuneType.LAW, RuneType.COSMIC, RuneType.BLOOD),
	TELEPORT_BATTLEFRONT(InterfaceID.MagicSpellbook.TELEPORT_BATTLEFRONT, RuneType.LAW, RuneType.COSMIC, RuneType.EARTH, RuneType.FIRE);

	private final int widgetId;
	private final RuneType[] requiredRunes;

	private static final Map<Integer, Spell> BY_WIDGET_ID = new HashMap<>();

	static
	{
		for (Spell spell : values())
		{
			BY_WIDGET_ID.put(spell.widgetId, spell);
		}
	}

	Spell(int widgetId, RuneType... requiredRunes)
	{
		this.widgetId = widgetId;
		this.requiredRunes = requiredRunes;
	}

	public int getWidgetId()
	{
		return widgetId;
	}

	public RuneType[] getRequiredRunes()
	{
		return requiredRunes;
	}

	public static Spell forWidgetId(int widgetId)
	{
		return BY_WIDGET_ID.get(widgetId);
	}
}

