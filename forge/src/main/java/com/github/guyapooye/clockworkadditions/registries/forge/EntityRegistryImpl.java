package com.github.guyapooye.clockworkadditions.registries.forge;

import com.github.guyapooye.clockworkadditions.blocks.kinetics.pedals.PedalsEntity;
import com.github.guyapooye.clockworkadditions.registries.EntityRegistry;
import com.simibubi.create.foundation.data.CreateEntityBuilder;
import com.simibubi.create.foundation.utility.Lang;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import static com.github.guyapooye.clockworkadditions.ClockworkAdditions.REGISTRATE;

public class EntityRegistryImpl extends EntityRegistry{
    public static void sized(Object obj, float width, float height) {
        ((EntityType.Builder<?>) obj).sized(width, height);
    }
    public static <T extends Entity> CreateEntityBuilder<T, ?> register(String name, EntityType.EntityFactory<T> factory,
                                                                         NonNullSupplier<NonNullFunction<EntityRendererProvider.Context, EntityRenderer<? super T>>> renderer,
                                                                         MobCategory group, int range, int updateFrequency, boolean sendVelocity, boolean immuneToFire,
                                                                         NonNullConsumer propertyBuilder) {
        String id = Lang.asId(name);
        return (CreateEntityBuilder<T, ?>) REGISTRATE
                .entity(id, factory, group)
                .properties(b -> b.setTrackingRange(range)
                        .setUpdateInterval(updateFrequency)
                        .setShouldReceiveVelocityUpdates(sendVelocity))
                .properties((NonNullConsumer<EntityType.Builder<T>>)propertyBuilder)
                .properties(b -> {
                    if (immuneToFire)
                        b.fireImmune();
                })
                .renderer(renderer);
    }
}
