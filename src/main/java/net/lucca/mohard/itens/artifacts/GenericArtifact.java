package net.lucca.mohard.itens.artifacts;

import net.lucca.mohard.enchantments.ArtifactEnchantment;
import net.lucca.mohard.init.ModAttributes;
import net.lucca.mohard.init.ModEnchantments;
import net.lucca.mohard.network.BaseNetwork;
import net.lucca.mohard.network.packets.ServerboundHurtItemPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;


public class GenericArtifact extends Item implements Vanishable {

    private final double multiplier;
    protected final int cooldown;
    protected final boolean hasDurability;

    public GenericArtifact(Properties properties, double multiplier, int cooldown) {
        this(properties, multiplier, cooldown, false);
    }
    public GenericArtifact(Properties properties, double multiplier, int cooldown, boolean hasDurability) {
        super(properties);
        this.multiplier = multiplier;
        this.cooldown = cooldown;
        this.hasDurability = hasDurability;
    }
    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 15;
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack p_150899_) {
        return this.hasDurability && super.isBarVisible(p_150899_);
    }

    @Override
    public @Nullable EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.MAINHAND;
    }

    protected boolean canCast(Player player, ItemStack itemStack){
        if(player.isCreative()) return true;
        return !player.getCooldowns().isOnCooldown(this) && isEnabled(itemStack);
    }

    protected boolean isEnabled(ItemStack p_41141_) {
        return (p_41141_.getDamageValue()) < p_41141_.getMaxDamage() - 1 || !this.hasDurability;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack p_41456_) {
        return this.getBaseMultiplier() != 0;
    }

    protected void hurtItem(Player player, ItemStack stack){
        if(player instanceof LocalPlayer) {
            BaseNetwork.CHANNEL.sendToServer(new ServerboundHurtItemPacket(stack, stack.getEquipmentSlot()));
        } else {
            stack.hurtAndBreak(1, player, entity1 -> entity1.broadcastBreakEvent(stack.getEquipmentSlot()));
        }
    }



    public int getBaseCooldown() {
        return this.cooldown;
    }

    public double getBaseMultiplier() {
        return multiplier;
    }

    public double getMagicMultiplier(LivingEntity entity, ItemStack itemStack){
        double pishogueMultiplier = getPishogueMultiplier(itemStack);
        double baneMultiplier = getBaneMultiplier(itemStack);
        return this.getBaseMultiplier() + baneMultiplier + pishogueMultiplier;
    }

    private double getPishogueMultiplier(ItemStack itemStack){
        return (EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.PISHOGUE.get(), itemStack) * 2) / 10D;
    }

    private double getBaneMultiplier(ItemStack itemStack){
        float halfBane = EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.HALF_BANE.get(), itemStack) * (-0.5F);
        float doubleBane = EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.DOUBLE_BANE.get(), itemStack) * (0.5F);
        return halfBane + doubleBane;
    }

    protected void generateCooldown(Player player, ItemStack itemStack){
        if(player.isCreative()) return;
        generateCooldown(player, itemStack, this.getBaseCooldown());

    }
    protected void generateCooldown(Player player, ItemStack itemStack, int cooldown){
        if(player.isCreative()) return;
        player.getCooldowns().addCooldown(this, this.getCooldown(player, itemStack, cooldown));
    }

    protected int getCooldown(Player player, ItemStack itemStack, int cooldown){
        double playerIntellect = player.getAttributeValue(ModAttributes.INTELLECT); //0 - 100
        double percentLost = 1 - (playerIntellect / 125);
        return (int) ((cooldown * (1 + this.getBaneMultiplier(itemStack))) * percentLost);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack item,
                                @Nullable Level world,
                                List<Component> tooltip,
                                TooltipFlag flagIn) {

        super.appendHoverText(item, world, tooltip, flagIn);
        String base = "artifact.description.";
        if (getMagicMultiplier(null, item) != 0) tooltip.add(Component.translatable(base + "magic_multiplier")
                .append(": " + getMagicMultiplier(null, item))
                .withStyle(ChatFormatting.BLUE));
        if (Minecraft.getInstance().player != null) {
            tooltip.add(Component.translatable(base + "cooldown").append(": " + this.getCooldown(Minecraft.getInstance().player, item, this.getBaseCooldown()) / 20F + "s").withStyle(ChatFormatting.BLUE));
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment instanceof ArtifactEnchantment || enchantment.equals(Enchantments.MOB_LOOTING) || enchantment.category.canEnchant(this);
    }

    protected void onHit(Player player, Entity entity, boolean crit){
        if(crit) {
            player.level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, player.getSoundSource(), 1.0F, 1.0F);
            if (player instanceof ServerPlayer) {
                ((ServerPlayer) player).getLevel().getChunkSource().broadcastAndSend(player, new ClientboundAnimatePacket(entity, 5));
            }
        } else{
            player.level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_STRONG, player.getSoundSource(), 1.0F, 1.0F);
        }
    }

}
