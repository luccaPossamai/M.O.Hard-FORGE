package net.lucca.mohard.capabilities.entail;

import net.lucca.mohard.capabilities.ModCapabilities;
import net.lucca.mohard.init.ModBindings;
import net.lucca.mohard.itens.essence.essenceEntails.EssenceEntail;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EssenceEntailCapabilityProvider implements ICapabilitySerializable<CompoundTag> {

    private EssenceEntailCapability ENTAIL_CAPABILITY;

    @Nonnull
    private EssenceEntailCapability getEntailCapability() {
        if (ENTAIL_CAPABILITY == null) {
            ENTAIL_CAPABILITY = new EssenceEntailCapability(ModBindings.EMPTY_BINDING.getEssenceEntail());
        }
        return ENTAIL_CAPABILITY;
    }

    private final LazyOptional<EssenceEntailCapability> lazyCap = LazyOptional.of(this::getEntailCapability);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ModCapabilities.ENTAIL_CAPABILITY) {
            return (LazyOptional<T>)(lazyCap);
        }
        return LazyOptional.empty();
    }


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("Entail", this.getEntailCapability().getEntail().toString());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        EssenceEntail entail = EssenceEntail.Helper.getEntailByName(nbt.getString("Entail"));
        this.getEntailCapability().setEntail(entail);
    }
}
