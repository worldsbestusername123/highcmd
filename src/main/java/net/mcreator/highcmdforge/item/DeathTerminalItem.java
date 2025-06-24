
package net.mcreator.highcmdforge.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;

import net.mcreator.highcmdforge.procedures.DeathTerminalEntitySwingsItemProcedure;
import net.mcreator.highcmdforge.CMDProtectedEntities;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DeathTerminalItem extends SwordItem {
	public DeathTerminalItem() {
		super(new Tier() {
			public int getUses() {
				return 9318283;
			}

			public float getSpeed() {
				return 23123213f;
			}

			public float getAttackDamageBonus() {
				return Float.POSITIVE_INFINITY;
			}

			public int getLevel() {
				return Integer.MAX_VALUE;
			}

			public int getEnchantmentValue() {
				return Integer.MAX_VALUE;
			}

			public @NotNull Ingredient getRepairIngredient() {
				return Ingredient.of();
			}
		}, 3, -3f, new Item.Properties().fireResistant());
	}

	@Override
	public void appendHoverText(@NotNull ItemStack itemstack, Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.literal("§4 Terminal."));
	}

	@Override
	public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
		CMDProtectedEntities.setProtected(pEntity);
	}
	
	@Override
	public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity) {
		boolean originalValue = super.onEntitySwing(itemstack, entity);
		DeathTerminalEntitySwingsItemProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
		return originalValue;
	}
}
