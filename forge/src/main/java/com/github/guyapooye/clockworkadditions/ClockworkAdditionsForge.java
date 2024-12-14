package com.github.guyapooye.clockworkadditions;

import com.github.guyapooye.clockworkadditions.registries.forge.BlockRegistryImpl;
import com.github.guyapooye.clockworkadditions.registries.ConfigRegistryImpl;
import com.github.guyapooye.clockworkadditions.registries.forge.EntityRegistryImpl;
import com.github.guyapooye.clockworkadditions.registries.BlockRegistry;
import com.github.guyapooye.clockworkadditions.util.forge.PlatformUtilImpl;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.github.guyapooye.clockworkadditions.ClockworkAdditions.*;

@Mod(MOD_ID)
public class ClockworkAdditionsForge {
    public ClockworkAdditionsForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(modEventBus);

        ClockworkAdditions.init();
        PlatformUtilImpl.runWhenOn(Dist.CLIENT, this::initClient);

        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        ConfigRegistryImpl.register(modLoadingContext);

        BlockRegistryImpl.register();
    }

    private void initClient() {}
}