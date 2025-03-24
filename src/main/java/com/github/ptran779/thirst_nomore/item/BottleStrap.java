package com.github.ptran779.thirst_nomore.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import com.github.ptran779.thirst_nomore.util.WaterContainer;


public class BottleStrap extends WaterContainer implements Equipable{
//  private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

  public BottleStrap(Properties properties) {
    super(properties.durability(100));
  }

  @Override
  public EquipmentSlot getEquipmentSlot() {
    return EquipmentSlot.LEGS;
  }
}

