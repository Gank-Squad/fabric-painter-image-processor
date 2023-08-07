package processing.colors;


/**
 * 
 * big array of all the colors
 *
 */
public class Colors {
	
	public static final Color[] colorArr = {
	// Color (String name, int lightness, Item item, int r, int g, int b)
			new Color("ink_sac", 1, 0x191919),
			new Color("ink_sac", 0, 0x151515),
			new Color("ink_sac", -1, 0x111111),
			new Color("ink_sac", -2, 0x0D0D0D),
			
			new Color("red_dye", 1, 0xFF0000),
			new Color("red_dye", 0, 0xD90000),
			new Color("red_dye", -1, 0xAF0000),
			new Color("red_dye", -2, 0x800000),
			
			new Color("green_dye", 1, 0x667F33),
			new Color("green_dye", 0, 0x586D2C),
			new Color("green_dye", -1, 0x475824),
			new Color("green_dye", -2, 0x34411A),
			
			new Color("cocoa_beans", 1, 0x664C33),
			new Color("cocoa_beans", 0, 0x57402B),
			new Color("cocoa_beans", -1, 0x463423),
			new Color("cocoa_beans", -2, 0x33261A),
			
			new Color("lapis_lazuli", 1, 0x324BAF),
			new Color("lapis_lazuli", 0, 0x2B3F95),
			new Color("lapis_lazuli", -1, 0x223378),
			new Color("lapis_lazuli", -2, 0x192557),
			
			new Color("purple_dye", 1, 0x7F3FB2),
			new Color("purple_dye", 0, 0x6D3699),
			new Color("purple_dye", -1, 0x582B7B),
			new Color("purple_dye", -2, 0x41205B),
			
			new Color("cyan_dye", 1, 0x4C7F98),
			new Color("cyan_dye", 0, 0x406B81),
			new Color("cyan_dye", -1, 0x335669),
			new Color("cyan_dye", -2, 0x26404D),
			
			new Color("light_gray_dye", 1, 0xA4A4A4),
			new Color("light_gray_dye", 0, 0x8B8B8B),
			new Color("light_gray_dye", -1, 0x6F6F6F),
			new Color("light_gray_dye", -2, 0x515151),
			
			new Color("gray_dye", 1, 0x9EA1B1),
			new Color("gray_dye", 0, 0x868996),
			new Color("gray_dye", -1, 0x6A6D77),
			new Color("gray_dye", -2, 0x4D4F57),
			
			new Color("pink_dye", 1, 0xF27FA5),
			new Color("pink_dye", 0, 0xD06D8E),
			new Color("pink_dye", -1, 0xAA5974),
			new Color("pink_dye", -2, 0x804357),
			
			new Color("lime_dye", 1, 0x7ECA19),
			new Color("lime_dye", 0, 0x6CAE15),
			new Color("lime_dye", -1, 0x578C11),
			new Color("lime_dye", -2, 0x40670C),
			
			new Color("yellow_dye", 1, 0xE1E132),
			new Color("yellow_dye", 0, 0xBEC02B),
			new Color("yellow_dye", -1, 0x989822),
			new Color("yellow_dye", -2, 0x707019),
			
			new Color("light_blue_dye", 1, 0x6191CD),
			new Color("light_blue_dye", 0, 0x537CAF),
			new Color("light_blue_dye", -1, 0x43658D),
			new Color("light_blue_dye", -2, 0x314968),
			
			new Color("magenta_dye", 1, 0xA546C8),
			new Color("magenta_dye", 0, 0x8C3BAB),
			new Color("magenta_dye", -1, 0x713089),
			new Color("magenta_dye", -2, 0x532365),
			
			new Color("orange_dye", 1, 0xC1712D),
			new Color("orange_dye", 0, 0xA66127),
			new Color("orange_dye", -1, 0x854E20),
			new Color("orange_dye", -2, 0x623917),
			
			new Color("bone_meal", 1, 0xF8F5EE),
			new Color("bone_meal", 0, 0xD7D4CE),
			new Color("bone_meal", -1, 0xB1AEA9),
			new Color("bone_meal", -2, 0x83817D),
			
			new Color("pumpkin_seeds", 1, 0xF4E6A1),
			new Color("pumpkin_seeds", 0, 0xD2C78A),
			new Color("pumpkin_seeds", -1, 0xACA272),
			new Color("pumpkin_seeds", -2, 0x807A55),
			
			new Color("melon_seeds", 1, 0x956C4C),
			new Color("melon_seeds", 0, 0x805D41),
			new Color("melon_seeds", -1, 0x694B36),
			new Color("melon_seeds", -2, 0x4E3827),
			
			new Color("flint", 1, 0x4B4B4B),
			new Color("flint", 0, 0x404040),
			new Color("flint", -1, 0x343434),
			new Color("flint", -2, 0x272727),
			
			new Color("gunpowder", 1, 0x979797),
			new Color("gunpowder", 0, 0x828282),
			new Color("gunpowder", -1, 0x6B6B6B),
			new Color("gunpowder", -2, 0x505050),
			
			new Color("nether_wart", 1, 0x6F0200),
			new Color("nether_wart", 0, 0x5F0100),
			new Color("nether_wart", -1, 0x4E0100),
			new Color("nether_wart", -2, 0x3A0100),
			
			new Color("prismarine_crystals", 1, 0x5AD5D0),
			new Color("prismarine_crystals", 0, 0x4DB7B2),
			new Color("prismarine_crystals", -1, 0x3D9490),
			new Color("prismarine_crystals", -2, 0x2D6D6A),
			
			new Color("grass", 1, 0x7DB037),
			new Color("grass", 0, 0x6C9730),
			new Color("grass", -1, 0x577B27),
			new Color("grass", -2, 0x405A1C),
			
			new Color("gold_nugget", 1, 0xF7EB4C),
			new Color("gold_nugget", 0, 0xD3CA41),
			new Color("gold_nugget", -1, 0xACA434),
			new Color("gold_nugget", -2, 0x7E7926),
			
			new Color("cobweb", 1, 0xC5C5C5),
			new Color("cobweb", 0, 0xA9A9AB),
			new Color("cobweb", -1, 0x888888),
			new Color("cobweb", -2, 0x646464),
			
			new Color("ice", 1, 0x9F9FFE),
			new Color("ice", 0, 0x8989DB),
			new Color("ice", -1, 0x6E6EB1),
			new Color("ice", -2, 0x515182),
			
			new Color("oak_leaves", 1, 0x007C00),
			new Color("oak_leaves", 0, 0x006A00),
			new Color("oak_leaves", -1, 0x005600),
			new Color("oak_leaves", -2, 0x003F00),
			
			new Color("snow", 1, 0xFFFFFF),
			new Color("snow", 0, 0xDCDCDC),
			new Color("snow", -1, 0xB4B4B4),
			new Color("snow", -2, 0x868686),
			
			new Color("ghast_tear", 1, 0x707070),
			new Color("ghast_tear", 0, 0x606060),
			new Color("ghast_tear", -1, 0x4F4F4F),
			new Color("ghast_tear", -2, 0x3B3B3B),
			
			new Color("lapis_block", 1, 0x4040FF),
			new Color("lapis_block", 0, 0x3737DC),
			new Color("lapis_block", -1, 0x2D2DB4),
			new Color("lapis_block", -2, 0x212187),
			
			new Color("dark_oak_log", 1, 0x8F7748),
			new Color("dark_oak_log", 0, 0x7B663E),
			new Color("dark_oak_log", -1, 0x645432),
			new Color("dark_oak_log", -2, 0x4B3F26),
			
			new Color("brick", 1, 0x993333),
			new Color("brick", 0, 0x842C2C),
			new Color("brick", -1, 0x6C2424),
			new Color("brick", -2, 0x511B1B),
			
			new Color("lapis_ore", 1, 0x4A80FF),
			new Color("lapis_ore", 0, 0x3F6EDC),
			new Color("lapis_ore", -1, 0x3459B3),
			new Color("lapis_ore", -2, 0x264184),
			
			new Color("emerald", 1, 0x00D93A),
			new Color("emerald", 0, 0x00BB32),
			new Color("emerald", -1, 0x009828),
			new Color("emerald", -2, 0x006F1D),
			
			new Color("birch_wood", 1, 0x815631),
			new Color("birch_wood", 0, 0x6F4A2A),
			new Color("birch_wood", -1, 0x5B3C22),
			new Color("birch_wood", -2, 0x432C19),
			
			new Color("egg", 1, 0xCFAF9F),
			new Color("egg", 0, 0xB39789),
			new Color("egg", -1, 0x927B70),
			new Color("egg", -2, 0x6B5B53),
			
			new Color("magma_cream", 1, 0x9D5124),
			new Color("magma_cream", 0, 0x88461F),
			new Color("magma_cream", -1, 0x703919),
			new Color("magma_cream", -2, 0x532A13),
			
			new Color("beetroot", 1, 0x95576C),
			new Color("beetroot", 0, 0x804B5D),
			new Color("beetroot", -1, 0x693D4C),
			new Color("beetroot", -2, 0x4D2E39),
			
			new Color("mycelium", 1, 0x6E6A87),
			new Color("mycelium", 0, 0x5F5C76),
			new Color("mycelium", -1, 0x4C4B5F),
			new Color("mycelium", -2, 0x383746),
			
			new Color("glowstone_dust", 1, 0xB58123),
			new Color("glowstone_dust", 0, 0x9D701F),
			new Color("glowstone_dust", -1, 0x7F5B19),
			new Color("glowstone_dust", -2, 0x5F4413),
			
			new Color("slime_ball", 1, 0x667434),
			new Color("slime_ball", 0, 0x57632C),
			new Color("slime_ball", -1, 0x475125),
			new Color("slime_ball", -2, 0x353C1C),
			
			new Color("spider_eye", 1, 0x9D4C4D),
			new Color("spider_eye", 0, 0x884142),
			new Color("spider_eye", -1, 0x6F3536),
			new Color("spider_eye", -2, 0x532728),
			
			new Color("soul sand", 1, 0x382823),
			new Color("soul sand", 0, 0x30231E),
			new Color("soul sand", -1, 0x271C18),
			new Color("soul sand", -2, 0x1E1512),
			
			new Color("brown_mushroom", 1, 0x856A62),
			new Color("brown_mushroom", 0, 0x735B54),
			new Color("brown_mushroom", -1, 0x5E4A44),
			new Color("brown_mushroom", -2, 0x453631),
			
			new Color("iron_nugget", 1, 0x565B5B),
			new Color("iron_nugget", 0, 0x4A4E4E),
			new Color("iron_nugget", -1, 0x3C3F3F),
			new Color("iron_nugget", -2, 0x2D2F2F),
			
			new Color("chorus_fruit", 1, 0x794857),
			new Color("chorus_fruit", 0, 0x683D4A),
			new Color("chorus_fruit", -1, 0x55323D),
			new Color("chorus_fruit", -2, 0x3F262D),
			
			new Color("purpur_block", 1, 0x4B3D5B),
			new Color("purpur_block", 0, 0x40344E),
			new Color("purpur_block", -1, 0x342A3F),
			new Color("purpur_block", -2, 0x27202F),
			
			new Color("podzol", 1, 0x4A3023),
			new Color("podzol", 0, 0x402A1E),
			new Color("podzol", -1, 0x342318),
			new Color("podzol", -2, 0x271A12),
			
			new Color("poisonous_potato", 1, 0x4C522A),
			new Color("poisonous_potato", 0, 0x414624),
			new Color("poisonous_potato", -1, 0x35391D),
			new Color("poisonous_potato", -2, 0x282B16),
			
			new Color("apple", 1, 0x8D3C2E),
			new Color("apple", 0, 0x7A3327),
			new Color("apple", -1, 0x642A20),
			new Color("apple", -2, 0x4B1F18),
			
			new Color("charcoal", 1, 0x251610),
			new Color("charcoal", 0, 0x1F120D),
			new Color("charcoal", -1, 0x1A0F0B),
			new Color("charcoal", -2, 0x130B08),
			
			new Color("crimson_nylium", 1, 0xBB2F30),
			new Color("crimson_nylium", 0, 0xA2292A),
			new Color("crimson_nylium", -1, 0x852122),
			new Color("crimson_nylium", -2, 0x641919),
			
			new Color("crimson_stem", 1, 0x913E5F),
			new Color("crimson_stem", 0, 0x7E3552),
			new Color("crimson_stem", -1, 0x682C44),
			new Color("crimson_stem", -2, 0x4E2133),
			
			new Color("crimson_hyphae", 1, 0x58181C),
			new Color("crimson_hyphae", 0, 0x4D1518),
			new Color("crimson_hyphae", -1, 0x3F1114),
			new Color("crimson_hyphae", -2, 0x2F0D0F),
			
			new Color("warped_nylium", 1, 0x167D84),
			new Color("warped_nylium", 0, 0x126B72),
			new Color("warped_nylium", -1, 0x0F575D),
			new Color("warped_nylium", -2, 0x0B4145),
			
			new Color("warped_stem", 1, 0x398C8A),
			new Color("warped_stem", 0, 0x317977),
			new Color("warped_stem", -1, 0x276361),
			new Color("warped_stem", -2, 0x1E4A49),
			
			new Color("warped_hyphae", 1, 0x552B3D),
			new Color("warped_hyphae", 0, 0x492535),
			new Color("warped_hyphae", -1, 0x3B1F2A),
			new Color("warped_hyphae", -2, 0x2C1720),
			
			new Color("warped_wart_block", 1, 0x14B283),
			new Color("warped_wart_block", 0, 0x119971),
			new Color("warped_wart_block", -1, 0x0E7D5C),
			new Color("warped_wart_block", -2, 0x0A5E45),
			
			new Color("cobbled_deepslate", 1, 0x636363),
			new Color("cobbled_deepslate", 0, 0x555555),
			new Color("cobbled_deepslate", -1, 0x454545),
			new Color("cobbled_deepslate", -2, 0x333333),
			
			new Color("raw_iron", 1, 0xD3AC90),
			new Color("raw_iron", 0, 0xB7937D),
			new Color("raw_iron", -1, 0x957A66),
			new Color("raw_iron", -2, 0x705A4B),
			
			new Color("glow_lichen", 1, 0x7BA291),
			new Color("glow_lichen", 0, 0x6B8C7E),
			new Color("glow_lichen", -1, 0x577367),
			new Color("glow_lichen", -2, 0x41554C),
	};
	
	public static Color RGBToColor(int r, int g, int b)
	{
		// get euclidean distance from given color and all colors in the array
		// keep track of the closest one and use that
		
		double minDiff = Integer.MAX_VALUE;
		int minIndex = 0;
		
		for (int i = 0; i < colorArr.length; i++)
		{
			double diff = Math.sqrt(
					Math.pow(Math.abs(colorArr[i].r) - Math.abs(r), 2) +
					Math.pow(Math.abs(colorArr[i].g) - Math.abs(g), 2) +
					Math.pow(Math.abs(colorArr[i].b) - Math.abs(b), 2)
				);
				
			if (diff < minDiff)
			{
				minDiff = diff;
				minIndex = i;
			}
		}
		
		
		
		return colorArr[minIndex];
	}
	
	public static Color HexToColor(int hex)
	{
		int r = hex >>> 16 & 255;
		int g = hex >>> 8 & 255;
		int b = hex >>> 0 & 255;
		
		return RGBToColor(r,g,b);
	}
	
	
	
	public static Color fromString(String s)
	{
		if (s.length() < 3)
			return null;
		
		int lightness = Integer.parseInt(s.substring(s.length() - 1)) - 2;
		String name = s.substring(0, s.length() - 1);
		
		for (int i = 0; i < colorArr.length; i++)
		{
			if (colorArr[i].name.equalsIgnoreCase(name) && colorArr[i].lightness == lightness)
				return colorArr[i];
		}
		
		return null;
	}
	
	public static String generatePaletteString()
	{
		String str = "\"gradient:";
		
		int max = 5;
		for (int i = 0; i < max; i++)
		{
			str += "rgb(" + colorArr[i].r + "," + colorArr[i].g + "," + colorArr[i].b + 
					(i == max - 1 ? ")" : "),");
		}
		
		return str + "\"";
	}
}
