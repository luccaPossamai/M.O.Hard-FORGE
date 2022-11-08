package net.lucca.mohard.itens.armors;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.init.ModModelLayers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.extensions.IForgeItem;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class MagicArmor extends DyeableArmorItem implements IForgeItem {

    public MagicArmor(EquipmentSlot equip, Properties properties) {
        super(ModArmorMaterials.MAGIC, equip, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                HumanoidModel<?> model = slot.equals(EquipmentSlot.LEGS) ? new HumanoidModel<>(ModModelLayers.PLAYER_FIT_INNER_ARMOR.bakeRoot())
                        : new HumanoidModel<>(ModModelLayers.PLAYER_FIT_OUTER_ARMOR.bakeRoot());

                model.crouching = original.crouching;
                model.riding = original.riding;
                model.young = original.young;

                return model;
            }
        });
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String typeS = type == null ? "" : "_overlay";
        if (slot.getIndex() == 1) {
            return new ResourceLocation(ModMain.MODID, "textures/models/armor/magic_layer_2"+typeS+".png").toString();
        }
        return new ResourceLocation(ModMain.MODID, "textures/models/armor/magic_layer_1"+typeS+".png").toString();
    }


}
