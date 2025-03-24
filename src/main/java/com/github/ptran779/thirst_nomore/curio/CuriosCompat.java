package com.github.ptran779.thirst_nomore.curio;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class CuriosCompat {
  public CuriosCompat(IEventBus modEventBus) {
    if (FMLEnvironment.dist == Dist.CLIENT) {
      modEventBus.addListener(CuriosClient::register);
    }
    MinecraftForge.EVENT_BUS.addListener(CuriosServer::tickEvent);
  }
}
