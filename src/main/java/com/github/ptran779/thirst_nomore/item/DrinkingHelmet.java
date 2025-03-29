package com.github.ptran779.thirst_nomore.item;

//import com.github.ptran779.thirst_nomore.client.BottleStrapRender;

import com.github.ptran779.thirst_nomore.util.WaterContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// need custom rendering but later issue when work on curio
public class DrinkingHelmet extends WaterContainer implements Equipable{

  public DrinkingHelmet(Properties properties) {
    super(properties.durability(100));
  }

  @Override
  public EquipmentSlot getEquipmentSlot() {
    return EquipmentSlot.HEAD;
  }

  public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
    return this.swapWithEquipmentSlot(this, pLevel, pPlayer, pHand);
  }

  @Override
  public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
    pTooltipComponents.add(Component.translatable("tooltip.thirst_nomore.drinking_helmet"));
    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
  }
}

