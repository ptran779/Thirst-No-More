package com.github.ptran779.thirst_nomore.item;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import com.github.ptran779.thirst_nomore.client.BottleStrapRender;
import com.github.ptran779.thirst_nomore.util.WaterContainer;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.function.Consumer;
// need custom rendering but later issue when work on curio
public class BottleStrap extends WaterContainer implements Equipable, GeoItem{
  private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

  public BottleStrap(ArmorMaterial material, ArmorItem.Type type, Properties properties) {
    super(material, type, properties.durability(100));
  }

  @Override
  public void initializeClient(Consumer<IClientItemExtensions> consumer) {
    consumer.accept(new IClientItemExtensions() {
      private BottleStrapRender renderer;

      @Override
      public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
        if (this.renderer == null)
          this.renderer = new BottleStrapRender();

        // This prepares our GeoArmorRenderer for the current render frame.
        // These parameters may be null however, so we don't do anything further with them
        this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

        return this.renderer;
      }
    });
  }

  private PlayState predicate(AnimationState animationState) {
    return PlayState.CONTINUE;
  };

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    controllerRegistrar.add(new AnimationController(this, "controller", 0, this::predicate));
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }
}

