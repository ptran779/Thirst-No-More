package com.github.ptran779.thirst_nomore.item;

//import com.github.ptran779.thirst_nomore.client.BottleStrapRender;

import com.github.ptran779.thirst_nomore.util.WaterContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Equipable;

// need custom rendering but later issue when work on curio
public class CamelPack extends WaterContainer implements Equipable{

  public CamelPack(Properties properties) {
    super(properties.durability(100));
  }

  @Override
  public EquipmentSlot getEquipmentSlot() {
    return EquipmentSlot.CHEST;
  }
}

