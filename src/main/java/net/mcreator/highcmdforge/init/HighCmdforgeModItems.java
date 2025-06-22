
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.highcmdforge.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.ForgeSpawnEggItem;

import net.minecraft.world.item.Item;

import net.mcreator.highcmdforge.item.DeathTerminalItem;
import net.mcreator.highcmdforge.item.CMDSwordlvl3Item;
import net.mcreator.highcmdforge.item.CMDSwordlvl2Item;
import net.mcreator.highcmdforge.item.CMDSwordItem;
import net.mcreator.highcmdforge.HighCmdforgeMod;

public class HighCmdforgeModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, HighCmdforgeMod.MODID);
	public static final RegistryObject<Item> CMD_SWORD = REGISTRY.register("cmd_sword", () -> new CMDSwordItem());
	public static final RegistryObject<Item> CMD_SWORDLVL_2 = REGISTRY.register("cmd_swordlvl_2", () -> new CMDSwordlvl2Item());
	public static final RegistryObject<Item> CMD_SWORDLVL_3 = REGISTRY.register("cmd_swordlvl_3", () -> new CMDSwordlvl3Item());
	public static final RegistryObject<Item> DEATH_TERMINAL = REGISTRY.register("death_terminal", () -> new DeathTerminalItem());
	public static final RegistryObject<Item> TERMINAL_SPAWN_EGG = REGISTRY.register("terminal_spawn_egg", () -> new ForgeSpawnEggItem(HighCmdforgeModEntities.TERMINAL, -1, -1, new Item.Properties()));
	// Start of user code block custom items
	// End of user code block custom items
}
