/*
 * Scaling Health
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.silentchaos512.scalinghealth.objects.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.silentchaos512.scalinghealth.ScalingHealth;
import net.silentchaos512.scalinghealth.objects.Registration;

import javax.annotation.Nullable;
import java.util.List;

public class HealingItem extends Item {
    private static final int USE_TIME = 5 * 20;

    private final float healAmount;
    private final int healSpeed;
    private final int effectDuration;

    public HealingItem(float healAmount, int healSpeed) {
        super(new Item.Properties().stacksTo(16).tab(ScalingHealth.SH));
        this.healAmount = healAmount;
        this.healSpeed = healSpeed;
        this.effectDuration = (int) (this.healAmount * 100 * 20 * 2 / this.healSpeed);
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return USE_TIME;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getHealth() < player.getMaxHealth() && !player.hasEffect(Registration.BANDAGED.get())) {
            player.startUsingItem(hand);
            return ActionResult.success( stack);
        }
        return ActionResult.fail(stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entityLiving) {
        if (!world.isClientSide) {
            entityLiving.addEffect(new EffectInstance(Registration.BANDAGED.get(),
                    this.effectDuration, this.healSpeed, false, false));
            stack.shrink(1);

            if (entityLiving instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entityLiving;
                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
        return stack;
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (count % 10 == 0) {
            player.playSound(SoundEvents.ARMOR_EQUIP_LEATHER,
                    1.25f, (float) (1.1f + 0.05f * ScalingHealth.RANDOM.nextGaussian()));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new TranslationTextComponent("item.scalinghealth.healing_item.value",
                (int) (this.healAmount * 100),
                this.effectDuration / 20));
        tooltip.add(new TranslationTextComponent("item.scalinghealth.healing_item.howToUse",
                USE_TIME / 20));
    }
}
