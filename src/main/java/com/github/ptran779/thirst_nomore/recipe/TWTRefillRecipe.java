package com.github.ptran779.thirst_nomore.recipe;

import com.github.ptran779.config.ThirstNomoreConfigs;
import com.github.ptran779.thirst_nomore.event.EventServerHandler;
import com.github.ptran779.thirst_nomore.util.RecipeHelper;
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
import com.github.ptran779.thirst_nomore.ThirstNomore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TWTRefillRecipe extends ShapelessRecipe {
  public TWTRefillRecipe(ResourceLocation pId, String pGroup, CraftingBookCategory pCategory, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
    super(pId, pGroup, pCategory, pResult, pIngredients);
  }

  @Override
  public @NotNull NonNullList<ItemStack> getRemainingItems(@NotNull CraftingContainer container) {
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
    ItemStack waterItem = RecipeHelper.findWaterContainer(container);
    if (waterItem.isEmpty()) {return ItemStack.EMPTY;}

    // Sum "purity" values from all non-container ingredients  --WIP check purity level later
    int waterAdd = 0;
    for (int i = 0; i < container.getContainerSize(); i++) {
      ItemStack stack = container.getItem(i);
      if (stack == waterItem) continue; // Skip the container itself

      // special case for potion/water thing
      if (stack.is(Items.POTION)) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("Potion") && tag.getString("Potion").equals("survive:purified_water")) {
          waterAdd += 1;
        }
      } else // for all other items. Mostly so mod pack dev can add custom recipe in. NBT might be an issue... for another day...
      {
        waterAdd += 1;
      }
    }
    // durability check here -- already confirm it's a WaterContainer
    return RecipeHelper.compute_new_drink(waterItem, waterAdd);
  }

  public static class Serializer implements RecipeSerializer<TWTRefillRecipe> {
    private static final ResourceLocation NAME = new ResourceLocation(ThirstNomore.MODID, "thirst_refill");
    @Override
    public TWTRefillRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
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
        return new TWTRefillRecipe(pRecipeId, group, category, itemstack, nonnulllist);
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
    public @Nullable TWTRefillRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
      String s = pBuffer.readUtf();
      CraftingBookCategory craftingbookcategory = pBuffer.readEnum(CraftingBookCategory.class);
      int i = pBuffer.readVarInt();
      NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

      for(int j = 0; j < nonnulllist.size(); ++j) {
        nonnulllist.set(j, Ingredient.fromNetwork(pBuffer));
      }

      ItemStack itemstack = pBuffer.readItem();
      return new TWTRefillRecipe(pRecipeId, s, craftingbookcategory, itemstack, nonnulllist);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, TWTRefillRecipe pRecipe) {
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
