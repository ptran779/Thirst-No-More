package com.github.ptran779.thirst_nomore.curio;

import com.github.ptran779.thirst_nomore.client.BSRendererLayer;
import com.github.ptran779.thirst_nomore.item.BottleStrap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;


public class BSRenderCurio implements ICurioRenderer {
  @Override
  public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack,
                                                                        SlotContext slotContext,
                                                                        PoseStack poseStack,
                                                                        RenderLayerParent<T, M> renderLayerParent,
                                                                        MultiBufferSource multiBufferSource,
                                                                        int light,
                                                                        float limbSwing,
                                                                        float limbSwingAmount,
                                                                        float partialTicks,
                                                                        float ageInTicks,
                                                                        float netHeadYaw,
                                                                        float headPitch) {
    if (stack.getItem() instanceof BottleStrap) {  // are we wearing pant? -- maybe check for the item itself
      BSRendererLayer.BSrender(stack, poseStack, multiBufferSource, light, slotContext.entity(), limbSwing, limbSwingAmount);
    }
  }
}

