
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.highcmdforge.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.mcreator.highcmdforge.HighCmdforgeMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class HighCmdforgeModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HighCmdforgeMod.MODID);
	public static final RegistryObject<CreativeModeTab> CMD_TAB = REGISTRY.register("cmd_tab",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.high_cmdforge.cmd_tab")).icon(() -> new ItemStack(HighCmdforgeModItems.DEATH_TERMINAL.get())).displayItems((parameters, tabData) -> {
				tabData.accept(HighCmdforgeModItems.CMD_SWORD.get());
				tabData.accept(HighCmdforgeModItems.CMD_SWORDLVL_2.get());
				tabData.accept(HighCmdforgeModItems.CMD_SWORDLVL_3.get());
				tabData.accept(HighCmdforgeModItems.DEATH_TERMINAL.get());
			}).build());

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			tabData.accept(HighCmdforgeModItems.TERMINAL_SPAWN_EGG.get());
		}
	}
}
