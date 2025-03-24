package com.github.ptran779.thirst_nomore.recipe;

import com.github.ptran779.config.ThirstNomoreConfigs;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import com.github.ptran779.thirst_nomore.ThirstNomore;
import com.github.ptran779.thirst_nomore.util.WaterContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ThirstRefillRecipe extends ShapelessRecipe {
  public ThirstRefillRecipe(ResourceLocation pId, String pGroup, CraftingBookCategory pCategory, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
    super(pId, pGroup, pCategory, pResult, pIngredients);
  }

  public boolean matches(CraftingContainer container, Level level) {
    // 1. Check vanilla ingredient requirements
    if (!super.matches(container, level)) return false;

    // 2. Additional NBT validation (e.g., ensure potion is water)
    boolean hasValidPotion = false;
    for (int i = 0; i < container.getContainerSize(); i++) {
      ItemStack stack = container.getItem(i);
      if (stack.is(Items.POTION)) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.getString("Potion").equals("minecraft:water")) {
          hasValidPotion = true;
          break;
        }
      }
    }
    return hasValidPotion;
  }

  @Override
  public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
    NonNullList<ItemStack> remaining = super.getRemainingItems(container);
    for (int i = 0; i < container.getContainerSize(); i++) {
      ItemStack stack = container.getItem(i);
      if (stack.is(Items.POTION)) {
        // Replace potion with glass bottle
        remaining.set(i, new ItemStack(Items.GLASS_BOTTLE));
      }
    }

    return remaining;
  }

  @Override
  public @NotNull ItemStack assemble(CraftingContainer container, RegistryAccess pRegistryAccess) {
    // Find the input bottle strap
    ItemStack waterItem = ItemStack.EMPTY;
    for (int i = 0; i < container.getContainerSize(); i++) {
      ItemStack stack = container.getItem(i);
      if (stack.getItem() instanceof WaterContainer) {
        waterItem = stack;
        break;
      }
    }

    if (waterItem.isEmpty()) return ItemStack.EMPTY;

    // Sum "purity" values from all non-container ingredients  --WIP check purity level later
    int totalPurity = 0;
    for (int i = 0; i < container.getContainerSize(); i++) {
      ItemStack stack = container.getItem(i);
      if (stack == waterItem) continue; // Skip the container itself

      CompoundTag tag = stack.getTag();
      if (tag != null && tag.contains("Purity") && tag.getInt("Purity") >= ThirstNomoreConfigs.PURITY_MINIMUM.get()) {
        totalPurity += 1;
      }
    }
    // durability check here -- already confirm it's a WaterContainer
    int new_totalPurity = waterItem.getOrCreateTag().getInt(WaterContainer.NDRINKTAG) + totalPurity;
    if (new_totalPurity> ((WaterContainer) waterItem.getItem()).getMaxDrink()){return ItemStack.EMPTY;}  // cannot fill exceed

    // Create modified result
    ItemStack result = waterItem.copy();
    WaterContainer.setNDrink(result, new_totalPurity);
    return result;
  }

  public static class Serializer implements RecipeSerializer<ThirstRefillRecipe> {
    private static final ResourceLocation NAME = new ResourceLocation(ThirstNomore.MODID, "thirst_refill");
    @Override
    public ThirstRefillRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
      // Parse group and category
      String group = GsonHelper.getAsString(pJson, "group", "");
      CraftingBookCategory category = CraftingBookCategory.CODEC.byName(
          GsonHelper.getAsString(pJson, "category", null),
          CraftingBookCategory.MISC
      );

      // Parse ingredients
      NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(pJson, "ingredients"));
      if (nonnulllist.isEmpty()) {
        throw new JsonParseException("No ingredients for shapeless recipe");
      } else {
        ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "result"));
        return new ThirstRefillRecipe(pRecipeId, group, category, itemstack, nonnulllist);
      }
    }

    private static NonNullList<Ingredient> itemsFromJson(JsonArray pIngredientArray) {
      NonNullList<Ingredient> nonnulllist = NonNullList.create();

      for(int i = 0; i < pIngredientArray.size(); ++i) {
        Ingredient ingredient = Ingredient.fromJson(pIngredientArray.get(i), false);
        if (true || !ingredient.isEmpty()) { // FORGE: Skip checking if an ingredient is empty during shapeless recipe deserialization to prevent complex ingredients from caching tags too early. Can not be done using a config value due to sync issues.
          nonnulllist.add(ingredient);
        }
      }

      return nonnulllist;
    }

    @Override
    public @Nullable ThirstRefillRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
      String s = pBuffer.readUtf();
      CraftingBookCategory craftingbookcategory = pBuffer.readEnum(CraftingBookCategory.class);
      int i = pBuffer.readVarInt();
      NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

      for(int j = 0; j < nonnulllist.size(); ++j) {
        nonnulllist.set(j, Ingredient.fromNetwork(pBuffer));
      }

      ItemStack itemstack = pBuffer.readItem();
      return new ThirstRefillRecipe(pRecipeId, s, craftingbookcategory, itemstack, nonnulllist);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, ThirstRefillRecipe pRecipe) {
      pBuffer.writeUtf(pRecipe.getGroup());
      pBuffer.writeEnum(pRecipe.category());
      pBuffer.writeVarInt(pRecipe.getIngredients().size());

      for(Ingredient ingredient : pRecipe.getIngredients()) {
        ingredient.toNetwork(pBuffer);
      }

      pBuffer.writeItem(pRecipe.getResultItem(null));
    }
  }
}
