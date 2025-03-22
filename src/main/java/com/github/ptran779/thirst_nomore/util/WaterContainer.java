package com.github.ptran779.thirst_nomore.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public abstract class WaterContainer extends ArmorItem {
  private int max_drink=1;  // custom usage separate from durability system
  public static final String NDRINKTAG = "n_drink"; //  for setting up number of drink tag

  public WaterContainer(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
    super(pMaterial, pType, pProperties);
  }

  public int getMaxDrink() {
    return max_drink;
  }

  public void setMaxDrink(int max_drink) {
    this.max_drink = max_drink;
  }

  public static int getNDrink(ItemStack stack) {
    return (stack.getOrCreateTag().getInt(WaterContainer.NDRINKTAG));
  }

  public static void setNDrink(ItemStack stack, int n_drink) {
    WaterContainer item = (WaterContainer) stack.getItem();
    CompoundTag stackTags = stack.getOrCreateTag();
    stackTags.putInt(NDRINKTAG, n_drink);

    // calculate durability  -- WIP need a better handler for this
    int dmg = (int) ((1-((float)n_drink/ item.getMaxDrink()))*item.getMaxDamage());
    stackTags.putInt("Damage", dmg);
  }
}
