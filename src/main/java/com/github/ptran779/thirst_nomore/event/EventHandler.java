package com.github.ptran779.thirst_nomore.event;

import com.mojang.logging.LogUtils;
import dev.ghen.thirst.foundation.common.capability.ModCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import com.github.ptran779.config.ThirstNomoreConfigs;
import com.github.ptran779.thirst_nomore.ThirstNomore;
import com.github.ptran779.thirst_nomore.item.BottleStrap;
import com.github.ptran779.thirst_nomore.util.WaterContainer;

@Mod.EventBusSubscriber(modid = ThirstNomore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class EventHandler {
//  private static int counter = 0;
  private static int max_drink = 10;
  private static int purity_min = 3;
  private static int thirst_restore = 4;
  private static int quench_restore = 2;
  private static int tick_rate = 200;

  // load/reload config when received event
  @SubscribeEvent
  public static void onConfigLoaded(ModConfigEvent.Loading event) {
    thirst_restore = ThirstNomoreConfigs.THIRST_RESTORE_PER_DRINK.get();
    quench_restore = ThirstNomoreConfigs.QUENCH_RESTORE_PER_DRINK.get();
    tick_rate = ThirstNomoreConfigs.TICKS_PER_DRINK_CHECK.get();
    purity_min = ThirstNomoreConfigs.PURITY_MINIMUM.get();

    // find that water_strap and change the value
    for (Item item : ForgeRegistries.ITEMS) {
      if (item instanceof BottleStrap bottleStrap) {
        bottleStrap.setMaxDrink(ThirstNomoreConfigs.MAX_USAGE_BOTTLE_STRAP.get());
        break;
      }
    }
  }

  @SubscribeEvent
  public static void onConfigReloaded(ModConfigEvent.Reloading event) {
    onConfigLoaded(null);
  }

  // tick counter / control for auto water
  private static int timer = 0;
  @SubscribeEvent
  public static void runTask(TickEvent.PlayerTickEvent event) {
    if (event.phase == TickEvent.Phase.START && event.player instanceof ServerPlayer serverPlayer){
      if (timer < tick_rate) {timer++;}
      else {
        timer = 0;
        ItemStack targetItem = serverPlayer.getItemBySlot(EquipmentSlot.LEGS);  // more check here later WIP
        if (targetItem.getItem() instanceof WaterContainer targetContainer) {
          serverPlayer.getCapability(ModCapabilities.PLAYER_THIRST, (Direction) null).ifPresent(cap -> {
            if (cap.getThirst() <= 20 - thirst_restore && WaterContainer.getNDrink(targetItem) > 0){
              cap.drink(serverPlayer, thirst_restore, quench_restore); //
              WaterContainer.setNDrink(targetItem, WaterContainer.getNDrink(targetItem)-1);
            }
          });
        };
      }
    }
  }
}
