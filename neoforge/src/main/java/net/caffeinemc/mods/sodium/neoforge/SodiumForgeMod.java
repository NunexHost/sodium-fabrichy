package net.caffeinemc.mods.sodium.neoforge;

import net.caffeinemc.mods.sodium.client.SodiumClientMod;
import net.caffeinemc.mods.sodium.client.gui.SodiumOptionsGUI;
import net.caffeinemc.mods.sodium.neoforge.iecompat.ImmersiveEngineeringCompat;
import net.caffeinemc.mods.sodium.neoforge.iecompat.SodiumConnectionRenderer;
import net.caffeinemc.mods.sodium.client.render.frapi.SodiumRenderer;
import net.caffeinemc.mods.sodium.neoforge.render.SpriteFinderCache;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("sodium")
public class SodiumForgeMod {
    public SodiumForgeMod() {
        SodiumClientMod.onInitialization(ModList.get().getModContainerById("sodium").get().getModInfo().getVersion().toString());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onResourceReload);
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((mc, prevScreen) -> SodiumOptionsGUI.createScreen(prevScreen)));
        RendererAccess.INSTANCE.registerRenderer(SodiumRenderer.INSTANCE);
    }

    public void onResourceReload(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(SpriteFinderCache.ReloadListener.INSTANCE);
        if (ImmersiveEngineeringCompat.isLoaded) {
            event.registerReloadListener(new SodiumConnectionRenderer());
        }
    }
}