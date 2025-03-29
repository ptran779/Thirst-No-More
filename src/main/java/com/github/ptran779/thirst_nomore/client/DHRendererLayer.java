package com.github.ptran779.thirst_nomore.client;

import com.github.ptran779.thirst_nomore.item.CamelPack;
import com.github.ptran779.thirst_nomore.item.DrinkingHelmet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class DHRendererLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
  public DHRendererLayer(RenderLayerParent<T, M> pRenderer) {
    super(pRenderer);
  }

  @Override
  public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, T entity, float limbSwing, float limbSwingAmount, float pPartialTick, float pAgeInTicks, float headYaw, float headPitch) {
    ItemStack stack = entity.getItemBySlot(EquipmentSlot.HEAD);
    if (stack.getItem() instanceof DrinkingHelmet) {
      DHRender(stack, poseStack, multiBufferSource, light, entity, headYaw, headPitch);
    }
  }

  public static void DHRender(ItemStack stack, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, LivingEntity entity, float headYaw, float headPitch) {
    poseStack.pushPose();
    // Sneaking adjustment
    if (entity.isShiftKeyDown()) {
      poseStack.translate(0, 0.25, 0); // Lower position
    }
    poseStack.mulPose(Axis.YP.rotationDegrees(headYaw));
    poseStack.mulPose(Axis.XP.rotationDegrees(headPitch));
    poseStack.translate(0, -0.625, 0);
    poseStack.mulPose(Axis.ZP.rotationDegrees(180));

    // Get item renderer components
    Minecraft mc = Minecraft.getInstance();
    ItemRenderer itemRenderer = mc.getItemRenderer();
    BakedModel bakedModel = itemRenderer.getModel(stack, entity.level(), null, 0);

    // Render the item
    itemRenderer.render(stack, ItemDisplayContext.NONE, false, poseStack, multiBufferSource, light,
        OverlayTexture.NO_OVERLAY, bakedModel);
    poseStack.popPose();

  }
}

