package com.github.ptran779.thirst_nomore;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.github.ptran779.config.ThirstNomoreConfigs;
import com.github.ptran779.thirst_nomore.event.EventHandler;
import com.github.ptran779.thirst_nomore.item.ItemInit;
import com.github.ptran779.thirst_nomore.recipe.ModRecipes;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(ThirstNomore.MODID)
public class ThirstNomore
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "thirst_nomore";
    // Directly reference a slf4j logger
    public ThirstNomore(FMLJavaModLoadingContext context)
    {
        // event setup
        IEventBus modEventBus = context.getModEventBus();

        // config setup
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ThirstNomoreConfigs.CONFIG, "thirst_nomore-server.toml");

        // register item
        ItemInit.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        // register recipes
        ModRecipes.register(modEventBus);

        modEventBus.addListener(EventHandler::onConfigLoaded);
        modEventBus.addListener(EventHandler::onConfigReloaded);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ItemInit.BOTTLE_STRAP);
        }
    }
}
