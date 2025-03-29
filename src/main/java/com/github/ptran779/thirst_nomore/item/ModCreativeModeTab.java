package com.github.ptran779.thirst_nomore.item;

import com.github.ptran779.thirst_nomore.ThirstNomore;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.network.chat.Component;

import java.awt.*;

public class ModCreativeModeTab {
  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
      DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ThirstNomore.MODID);

  public static final RegistryObject<CreativeModeTab> MOD_TAB = CREATIVE_MODE_TAB.register("thirst_no_more",
      ()-> CreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.BOTTLE_STRAP.get()))
          .title(Component.translatable("creativetab.thirst_no_more_tab"))
          .displayItems((pParameters, pOutput) -> {
            pOutput.accept(ItemInit.BOTTLE_STRAP.get());
            pOutput.accept(ItemInit.DRINKING_HELMET.get());
            pOutput.accept(ItemInit.CAMEL_PACK.get());
          })
          .build());

  public static void register(IEventBus eventBus) {
    CREATIVE_MODE_TAB.register(eventBus);
  }
}
