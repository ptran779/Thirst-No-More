package com.github.ptran779.thirst_nomore.client;

import com.github.ptran779.thirst_nomore.item.BottleStrap;
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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class BSRendererLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
  public BSRendererLayer(RenderLayerParent<T, M> pRenderer) {
    super(pRenderer);
  }

  @Override
  public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, T entity, float limbSwing, float limbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
    ItemStack stack = entity.getItemBySlot(EquipmentSlot.LEGS);
    if (stack.getItem() instanceof BottleStrap) {
      BSrender(stack, poseStack, multiBufferSource, light, entity, limbSwing, limbSwingAmount);
    }
  }

  public static void BSrender(ItemStack stack, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, LivingEntity entity, float limbSwing, float limbSwingAmount) {
    poseStack.pushPose();
    poseStack.translate(0, 1, 0); // Adjust for legs
    poseStack.mulPose(Axis.ZP.rotationDegrees(180));

    // Walking animation
    poseStack.translate(0, Mth.abs(Mth.cos(limbSwing * 0.666f) * limbSwingAmount * 0.1f), -Mth.cos(limbSwing * 0.666f) * limbSwingAmount * 0.4);
    poseStack.mulPose(Axis.XP.rotation(Mth.cos(limbSwing * 0.666f) * limbSwingAmount)); // Forward/back swing

    // Sneaking adjustment
    if (entity.isShiftKeyDown()) {
      poseStack.translate(0, -0.1, 0.2); // Lower position
    }

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

