package com.github.guyapooye.clockworkadditions;

import com.github.guyapooye.clockworkadditions.registries.fabric.BlockRegistryImpl;
import com.github.guyapooye.clockworkadditions.registries.ConfigRegistryImpl;
import com.github.guyapooye.clockworkadditions.registries.fabric.EntityRegistryImpl;
import com.github.guyapooye.clockworkadditions.registries.events.ClientEventRegistry;
import com.github.guyapooye.clockworkadditions.registries.*;
import com.github.guyapooye.clockworkadditions.util.PlatformUtil;
import com.github.guyapooye.clockworkadditions.util.fabric.PlatformUtilImpl;
import io.github.fabricators_of_create.porting_lib.util.ItemGroupUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import static com.github.guyapooye.clockworkadditions.ClockworkAdditions.*;

public class ClockworkAdditionsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ClockworkAdditions.init();
        PlatformUtil.runWhenOn(EnvType.CLIENT, this::initClient);
        ConfigRegistryImpl.register();
        BlockRegistryImpl.register();
        REGISTRATE.register();
    }

    private void initClient() {
        ClientEventRegistry.register();
        ClockworkAdditionsClient.init();
    }
}