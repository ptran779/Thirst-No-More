package com.github.ptran779.thirst_nomore.client;

import com.github.ptran779.thirst_nomore.item.BottleStrap;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BottleStrapRender extends GeoArmorRenderer<BottleStrap> {
    public BottleStrapRender() {
        super(new BottleStrapModel());
    }
}
