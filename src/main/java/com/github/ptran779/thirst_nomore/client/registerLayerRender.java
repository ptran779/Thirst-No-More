package com.github.ptran779.thirst_nomore.client;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class registerLayerRender {
  public static void registerMeRender(EntityRenderersEvent.AddLayers event) {
    // Add layer to player renderers (both skin types)
    addLayerToPlayer(event, "default");
    addLayerToPlayer(event, "slim");
  }
  private static void addLayerToPlayer(EntityRenderersEvent.AddLayers event, String skinType) {
    EntityRenderer<? extends Player> renderer = event.getSkin(skinType);
    if (renderer instanceof LivingEntityRenderer<?, ?> livingRenderer) {
      livingRenderer.addLayer(new BSRendererLayer(livingRenderer));
      livingRenderer.addLayer(new CPRendererLayer(livingRenderer));
    }
  }
}