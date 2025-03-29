package com.github.ptran779.thirst_nomore.curio;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;

import static com.github.ptran779.thirst_nomore.event.EventServerHandler.*;

public class CuriosServer {
  @SubscribeEvent
  public static void tickEvent(TickEvent.PlayerTickEvent event) {
    if (event.phase == TickEvent.Phase.START && event.player instanceof ServerPlayer serverPlayer){
      if (!(timer < tick_rate)) {
        CuriosApi.getCuriosHelper().getCuriosHandler(serverPlayer).ifPresent(handler -> {
          handler.getCurios().forEach((identifier, stacksHandler) -> {
            for (int i = 0; i < stacksHandler.getSlots(); i++) {
              if (tryDrinking(stacksHandler.getStacks().getStackInSlot(i), serverPlayer)){break;};
            }
          });
        });
      }
    }
  }
}