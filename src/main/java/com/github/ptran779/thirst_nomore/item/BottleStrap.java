package com.github.ptran779.thirst_nomore.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import com.github.ptran779.thirst_nomore.util.WaterContainer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class BottleStrap extends WaterContainer implements Equipable{
//  private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

  public BottleStrap(Properties properties) {
    super(properties.durability(100));
  }

  @Override
  public EquipmentSlot getEquipmentSlot() {
    return EquipmentSlot.LEGS;
  }

  public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
    return this.swapWithEquipmentSlot(this, pLevel, pPlayer, pHand);
  }

  @Override
  public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
    pTooltipComponents.add(Component.translatable("tooltip.thirst_nomore.bottle_strap"));
    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
  }
}

