package com.github.ptran779.thirst_nomore.util;

import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;

public class RecipeHelper {
  public static ItemStack findWaterContainer(CraftingContainer container) {
    ItemStack waterItem = ItemStack.EMPTY;
    for (int i = 0; i < container.getContainerSize(); i++) {
      ItemStack stack = container.getItem(i);
      if (stack.getItem() instanceof WaterContainer) {
        waterItem = stack;
        break;
      }
    }
    return waterItem;
  }
  public static ItemStack compute_new_drink(ItemStack waterItem, int waterAdd) {
    int new_totalPurity = WaterContainer.getNDrink(waterItem) + waterAdd;
    if (new_totalPurity> ((WaterContainer) waterItem.getItem()).getMaxDrink()){return ItemStack.EMPTY;}  // cannot fill exceed

    // Create modified result
    ItemStack result = waterItem.copy();
    WaterContainer.setNDrink(result, new_totalPurity);
    return result;
  }
}
