package net.lucca.mohard.itens.essence;

import com.mojang.blaze3d.platform.InputConstants;
import net.lucca.mohard.config.ModClientConfig;
import net.lucca.mohard.enchantments.essences.EssenceBindingHelper;
import net.lucca.mohard.enchantments.essences.EssenceEntailBinding;
import net.lucca.mohard.init.ModBindings;
import net.lucca.mohard.init.ModEssences;
import net.lucca.mohard.util.help.Methods;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;


public class EssenceItem extends Item {


    private final EssenceData essenceData;
    private final int color1;
    private final int color2;
    private final EssencePredicate<LivingEntity> condition;
    private final boolean alternative;
    private boolean hasOwnTexture = false;

    public EssenceItem(Properties properties, EssenceData essenceData, int color1, int color2, EssencePredicate<LivingEntity> condition){
        super(properties);
        this.essenceData = essenceData;
        this.color1 = color1;
        this.color2 = color2;
        this.condition = condition;
        this.alternative = !condition.test(null);
    }

    public EssenceItem withOwnTexture(boolean bool){
        this.hasOwnTexture = bool;
        return this;
    }

    public boolean hasOwnTexture() {
        return this.hasOwnTexture;
    }

    public boolean isAlternative() {
        return this.alternative;
    }

    @OnlyIn(Dist.CLIENT)
    public int getColor(int p_195983_1_) {
        return p_195983_1_ == 0 ? this.color1 : this.color2;
    }
    public EntityType<?> getType() {
        return this.getEssenceData().entityType();
    }

    public boolean testCondition(LivingEntity livingEntity){
        return this.condition.test(livingEntity);
    }

    public EssenceData getEssenceData() {
        return essenceData;
    }



    public boolean isValid(){
        return !(EssenceDataHelper.isBoss(this) || this.isAlternative());
    }

    public CompoundTag getEntityNbtTag(CompoundTag tag){
        CompoundTag tag2 = tag.copy();
        if(this.equals(ModEssences.KILLER_BUNNY_ESSENCE.get())){
            tag2.putInt("RabbitType", 99);
        }
        if(this.equals(ModEssences.BRUNO_ESSENCE.get())){
            tag2.putString("CustomName", "Bruno");

        }
        if(this.equals(ModEssences.BLUE_AXOLOTL_ESSENCE.get())){
            tag2.putInt("Variant", 4);
        }

        return tag2;
    }


    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return !EssenceBindingHelper.canBind(p_41453_);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack item,
                                @Nullable Level world,
                                List<Component> tooltip,
                                TooltipFlag flagIn) {

        super.appendHoverText(item, world, tooltip, flagIn);
        if(Minecraft.getInstance().player != null) {
            LocalPlayer player = Minecraft.getInstance().player;
            boolean shiftPressed = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 340);
            if (item.getItem() instanceof EssenceItem essence && (!ModClientConfig.hidenEssenceStats.get() || shiftPressed)) {
                boolean unactive = EssenceDataHelper.isUnactive(item);
                if(EssenceBindingHelper.getItemEntailBinding(item) != ModBindings.EMPTY_BINDING){
                    EssenceEntailBinding essenceEntailBinding = EssenceBindingHelper.getItemEntailBinding(item);
                    tooltip.add(Component.translatable(essenceEntailBinding.getDescription()+".name").withStyle(ChatFormatting.GOLD));
                }

                if (!unactive) {
                    int upgradeLevel = EssenceDataHelper.getEssenceLevel(item);
                    int negativeUpgradeLevel = EssenceDataHelper.getEssenceNegativeLevel(item);
                    List<MutableComponent> lista = EssenceDataHelper.ESSENCE_DESCRIPTION;
                    EssenceData data = essence.getEssenceData();
                    Map<Attribute, Double> doubleList = data.essenceStats().getStats(upgradeLevel, negativeUpgradeLevel);
                    Map<Attribute, Double> rawDoubleList = data.essenceStats().getStats(0, 0);
                    List<Attribute> attributes = Methods.getAttributes();
                    tooltip.add(Component.translatable("essence.stats.description").withStyle(ChatFormatting.GRAY));
                    for (Attribute attribute : attributes) {
                        int index = attributes.indexOf(attribute);
                        ChatFormatting color = ChatFormatting.BLUE;
                        double value = doubleList.get(attribute);
                        double rawValue = rawDoubleList.get(attribute);
                        if (value != rawValue) {
                            if (value <= 0) {
                                color = ChatFormatting.DARK_PURPLE;
                            } else {
                                color = ChatFormatting.GREEN;
                            }
                        }
                        if (value != 0 || value != rawValue) {
                            String signal = value > 0 ? "+" : "";
                            tooltip.add(Component.literal(" "+signal + value + " ").withStyle(color).append(lista.get(index).withStyle(ChatFormatting.BLUE)));
                        }
                    }
                } else {
                    tooltip.add(Component.translatable("essence.description.unactive").withStyle(ChatFormatting.WHITE));

                }
            } else {
                tooltip.add(Component.translatable("essence.description.shift").withStyle(ChatFormatting.AQUA));
            }
        }
    }





}
