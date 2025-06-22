
package net.mcreator.highcmdforge.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;

import net.mcreator.highcmdforge.procedures.CMDSwordlvl3procProcedure;

import java.util.List;

public class CMDSwordlvl3Item extends SwordItem {
	public CMDSwordlvl3Item() {
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

			public Ingredient getRepairIngredient() {
				return Ingredient.of();
			}
		}, 3, -3f, new Item.Properties().fireResistant());
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(Component.literal("Level 3 CMDSWORD"));
	}

	@Override
	public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity) {
		boolean retval = super.onEntitySwing(itemstack, entity);
		CMDSwordlvl3procProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
		return retval;
	}
}
