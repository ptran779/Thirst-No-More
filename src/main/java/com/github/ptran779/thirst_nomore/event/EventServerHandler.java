package com.github.ptran779.thirst_nomore.event;

import com.github.ptran779.thirst_nomore.item.CamelPack;
import com.github.ptran779.thirst_nomore.item.DrinkingHelmet;
import com.stereowalker.survive.needs.IRealisticEntity;
import com.stereowalker.survive.needs.WaterData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import com.github.ptran779.config.ThirstNomoreConfigs;
import com.github.ptran779.thirst_nomore.ThirstNomore;
import com.github.ptran779.thirst_nomore.item.BottleStrap;
import com.github.ptran779.thirst_nomore.util.WaterContainer;

@Mod.EventBusSubscriber(modid = ThirstNomore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventServerHandler {
  public static int water_fill_upto = 20;

  private static int thirst_restore = 4;
  private static float hydration_restore = 1;
  public static int tick_rate = 200;
  public static int timer = 0;

  // load/reload config when received event
  @SubscribeEvent
  public static void onConfigLoaded(ModConfigEvent.Loading event) {
    loadConfig(event.getConfig());
  }

  @SubscribeEvent
  public static void onConfigReloaded(ModConfigEvent.Reloading event) {
    loadConfig(event.getConfig());
  }

  private static void loadConfig(ModConfig config) {
    if (config.getType() == ModConfig.Type.SERVER) {
      thirst_restore = ThirstNomoreConfigs.THIRST_RESTORE_PER_DRINK.get();
      hydration_restore = ThirstNomoreConfigs.QUENCH_RESTORE_PER_DRINK.get();
      tick_rate = ThirstNomoreConfigs.TICKS_PER_DRINK_CHECK.get();
      water_fill_upto = ThirstNomoreConfigs.WATER_FILL_UP_TO.get();

      // find that water_strap and change the value
      for (Item item : ForgeRegistries.ITEMS) {
        if (item instanceof BottleStrap bottleStrap) {
          bottleStrap.setMaxDrink(ThirstNomoreConfigs.MAX_USAGE_BOTTLE_STRAP.get());
        } else if (item instanceof CamelPack CamelPack) {
          CamelPack.setMaxDrink(ThirstNomoreConfigs.MAX_USAGE_CAMEL_PACK.get());
        } else if (item instanceof DrinkingHelmet DrinkingHelmet) {
          DrinkingHelmet.setMaxDrink(ThirstNomoreConfigs.MAX_USAGE_DRINKING_HELMET.get());
        }
      }
    }
  }

  // tick counter / control for auto water
  @SubscribeEvent
  public static void runTask(TickEvent.PlayerTickEvent event) {
    if (event.phase == TickEvent.Phase.START && event.player instanceof ServerPlayer serverPlayer){
      if (timer < tick_rate) {timer++;}
      else {
        timer = 0;
        for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD}) {
          if (tryDrinking(serverPlayer.getItemBySlot(slot), serverPlayer)) {break;};
        }
      }
    }
  }

  // attempt to try to drink given item stack and player doing it
  public static boolean tryDrinking(ItemStack targetItem, ServerPlayer serverPlayer){
    WaterData waterDat = ((IRealisticEntity) serverPlayer).getWaterData();
    if (waterDat.getWaterLevel() + thirst_restore >= water_fill_upto){
      return true;
    }

    if (targetItem.getItem() instanceof WaterContainer && WaterContainer.getNDrink(targetItem) > 0) {
      waterDat.drink(thirst_restore, waterDat.getHydrationLevel()+hydration_restore, 0, false);
      WaterContainer.setNDrink(targetItem, WaterContainer.getNDrink(targetItem) - 1);
      waterDat.save(serverPlayer);
      return true;
    }
    return false;
  }
}
