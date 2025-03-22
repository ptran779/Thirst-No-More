package com.github.ptran779.thirst_nomore.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.github.ptran779.thirst_nomore.ThirstNomore;
import com.github.ptran779.thirst_nomore.util.ExtraMaterial;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ThirstNomore.MODID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    // water containter stuff
    public static final RegistryObject<Item> BOTTLE_STRAP = ITEMS.register("bottle_strap",
            () -> new BottleStrap(ExtraMaterial.bottle_strap, ArmorItem.Type.LEGGINGS, new Item.Properties()));
}
