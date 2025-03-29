package com.github.ptran779.thirst_nomore.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import com.github.ptran779.thirst_nomore.ThirstNomore;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipesTWT {
  public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
      DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ThirstNomore.MODID);

  public static final RegistryObject<RecipeSerializer<TWTRefillRecipe>> THIRST_refill =
      SERIALIZERS.register("thirst_refill", TWTRefillRecipe.Serializer::new);

  public static void register(IEventBus eventBus) {
    SERIALIZERS.register(eventBus);
  }
}