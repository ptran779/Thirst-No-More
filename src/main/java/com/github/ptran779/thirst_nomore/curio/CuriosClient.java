package com.github.ptran779.thirst_nomore.curio;

import com.github.ptran779.thirst_nomore.ThirstNomore;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import static com.github.ptran779.thirst_nomore.item.ItemInit.*;

//@Mod.EventBusSubscriber(modid = ThirstNomore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CuriosClient {
  @SubscribeEvent
  public static void register(FMLClientSetupEvent event) {
    event.enqueueWork(() -> {
      CuriosRendererRegistry.register(BOTTLE_STRAP.get(), BSRenderCurio::new);
      CuriosRendererRegistry.register(CAMEL_PACK.get(), CPRenderCurio::new);
      CuriosRendererRegistry.register(DRINKING_HELMET.get(), DHRenderCurio::new);
    });
  }
}