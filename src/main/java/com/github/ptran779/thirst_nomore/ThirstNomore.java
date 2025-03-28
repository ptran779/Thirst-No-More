package com.github.ptran779.thirst_nomore;

import com.github.ptran779.thirst_nomore.client.registerLayerRender;
import com.github.ptran779.thirst_nomore.curio.CuriosCompat;
import com.github.ptran779.thirst_nomore.event.EventServerHandler;
import com.github.ptran779.thirst_nomore.item.ModCreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.github.ptran779.config.ThirstNomoreConfigs;
import com.github.ptran779.thirst_nomore.item.ItemInit;
import com.github.ptran779.thirst_nomore.recipe.ModRecipesTWT;


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
        MinecraftForge.EVENT_BUS.register(this);

        // one thirst mod pls

        // config setup
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ThirstNomoreConfigs.CONFIG, "thirst_nomore-server.toml");

        // register item
        ItemInit.register(modEventBus);
        // creative tab
        ModCreativeModeTab.register(modEventBus);

        //register client render
        modEventBus.addListener(registerLayerRender::registerMeRender);

        // register recipes event
        ModRecipesTWT.register(modEventBus);
//        ModRecipesTWT.register(modEventBus);

        // register config event
        modEventBus.addListener(EventServerHandler::onConfigLoaded);
        modEventBus.addListener(EventServerHandler::onConfigReloaded);

        // compat
        if (ModList.get().isLoaded("curios")) {
            new CuriosCompat(modEventBus);
        };
    }
}
