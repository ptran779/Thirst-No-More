package com.github.ptran779.thirst_nomore.client;

import net.minecraft.resources.ResourceLocation;
import com.github.ptran779.thirst_nomore.ThirstNomore;
import com.github.ptran779.thirst_nomore.item.BottleStrap;
import software.bernie.geckolib.model.GeoModel;

public class BottleStrapModel extends GeoModel<BottleStrap> {
    @Override
    public ResourceLocation getModelResource(BottleStrap waterBottleStrap) {
        return new ResourceLocation(ThirstNomore.MODID, "geo/bottle_strap.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BottleStrap waterBottleStrap) {
        return new ResourceLocation(ThirstNomore.MODID, "textures/armor/bottle_strap.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BottleStrap waterBottleStrap) {
        return new ResourceLocation(ThirstNomore.MODID, "animations/bottle_strap.animation.json");
    }
}
