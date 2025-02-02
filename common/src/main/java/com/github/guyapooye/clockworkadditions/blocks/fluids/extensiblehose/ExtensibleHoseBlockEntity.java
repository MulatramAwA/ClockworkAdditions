package com.github.guyapooye.clockworkadditions.blocks.fluids.extensiblehose;

import com.github.guyapooye.clockworkadditions.registries.BlockRegistry;
import com.github.guyapooye.clockworkadditions.registries.ConfigRegistry;
import com.github.guyapooye.clockworkadditions.util.PlatformUtil;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix4f;

import java.util.List;

import static com.github.guyapooye.clockworkadditions.util.WorldspaceUtil.getWorldSpace;

public abstract class ExtensibleHoseBlockEntity<Tank> extends SmartBlockEntity {

    protected BlockPos target;
    protected boolean isOrigin;
    protected Tank tank;

    public ExtensibleHoseBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        isOrigin = compound.getBoolean("IsOrigin");
        target = NbtUtils.readBlockPos(compound.getCompound("Target"));
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        if (target == null) return;
        compound.putBoolean("IsOrigin", isOrigin);
        compound.put("Target", NbtUtils.writeBlockPos(target));
    }

    public void detach() {
        target = null;
        tank = null;
        notifyUpdate();
        saveWithId();
    }

    public abstract Tank createTank();

    public void attach(BlockPos pos) {
        if (pos.equals(target)) return;
        if (target != null) {
            ExtensibleHoseBlockEntity old = BlockRegistry.EXTENSIBLE_HOSE.get().getBlockEntity(level, target);
            if (old != null) {
                old.detach();
            }
            detach();
        }
        target = pos;
        ExtensibleHoseBlockEntity targ = BlockRegistry.EXTENSIBLE_HOSE.get().getBlockEntity(level, target);
        if (targ == null) return;
        detach();
        target = pos;
        tank = createTank();
        targ.tank = tank;
        targ.attach(getBlockPos());

    }

    @Override
    public void tick() {
        super.tick();
        if (target == null) {
            detach();
            return;
        }
        ExtensibleHoseBlockEntity other = BlockRegistry.EXTENSIBLE_HOSE.get().getBlockEntity(level, target);
        if (other == null) {
            if (level.isLoaded(target))
                detach();
            return;
        }
        if (isOrigin == other.isOrigin) isOrigin = !other.isOrigin;
        if (getWorldSpace(level,worldPosition).sub(getWorldSpace(other.level, other.worldPosition)).lengthSquared() > Math.pow(ConfigRegistry.server().stretchables.hoseMaxLength.get(),2)) {
            target = null;
            other.target = null;
        }
    }
}
