package net.lucca.mohard.util.mechanics.evolution;

import net.lucca.mohard.capabilities.ModCapabilities;
import net.lucca.mohard.enchantments.essences.EssenceBindingHelper;
import net.lucca.mohard.init.ModAttributes;
import net.lucca.mohard.itens.essence.EssenceDataHelper;
import net.lucca.mohard.itens.essence.EssenceItem;
import net.lucca.mohard.util.help.Methods;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber
public class PlayerEvolution {
    private static final Map<Player, NonNullList<ItemStack>> insideItems = new HashMap<>();

    public static void setStats(NonNullList<ItemStack> lista, Player player){
        double health = player.getMaxHealth();
        List<Attribute> attributes = Methods.getAttributes();
        Map<Attribute, Double> statsForPlayer = attributes.stream().collect(Collectors.toMap(attribute -> attribute, Attribute::getDefaultValue));
        double velocidade = 0.1D;
        statsForPlayer.put(Attributes.MOVEMENT_SPEED, velocidade);

        updateInsideItems(lista, player);
        EssenceBindingHelper.updateEntail(lista, player);

        for(ItemStack item : lista){
            if(item.getItem() instanceof EssenceItem essence){
                if(EssenceDataHelper.isUnactive(item)){
                    continue;
                }
                int upgradeLevel = EssenceDataHelper.getEssenceLevel(item);
                int negativeUpgradeLevel = EssenceDataHelper.getEssenceNegativeLevel(item);
                Map<Attribute, Double> essenceStats = essence.getEssenceData().essenceStats().getStats(upgradeLevel, negativeUpgradeLevel);
                for (Attribute attribute : attributes) {
                    RangedAttribute att = (RangedAttribute) attribute;
                    double value = essenceStats.get(att) + statsForPlayer.get(attribute);
                    statsForPlayer.put(attribute, Math.min(Math.max(att.getMinValue(), value), att.getMaxValue()));

                }
            }
        }
        statsForPlayer.put(Attributes.MOVEMENT_SPEED, velocidade + (velocidade * statsForPlayer.get(ModAttributes.AGILITY) / 125));
        updatePlayerHealth(player);
        player.getCapability(ModCapabilities.ENTAIL_CAPABILITY).ifPresent(essenceEntailCapability -> essenceEntailCapability.getEntail().applyBonus(statsForPlayer));
        if(!player.isLocalPlayer()) {
            statsForPlayer.forEach((attribute, aDouble) -> player.getAttribute(attribute).setBaseValue(aDouble));
            LevelMechanic.updateserverLevel(player.level);
        }

    }

    @SubscribeEvent
    public static void playerRespawn(PlayerEvent.PlayerRespawnEvent event){
        Player player = event.getEntity();
        PlayerEvolution.setStats(PlayerEvolution.getItems(player), player);
        player.setHealth(player.getMaxHealth());
    }

    @SubscribeEvent
    public static void playerLogIn(PlayerEvent.PlayerLoggedInEvent event){
        Player player = event.getEntity();
        PlayerEvolution.setStats(PlayerEvolution.getItems(player), player);
        player.level.players().forEach(PlayerEvolution::sendPlayersLoginLevelMessage);
    }

    @SubscribeEvent
    public static void playerRespawn(PlayerEvent.PlayerChangedDimensionEvent event){
        Player player = event.getEntity();
        PlayerEvolution.setStats(PlayerEvolution.getItems(player), player);
    }

    public static void updatePlayerHealth(Player player){
        if(player.isAlive()) {
            float damage = 0.01F;
            while (player.getHealth() <= damage) {
                damage = damage / 10;
            }
            player.hurt(DamageSource.MAGIC.bypassArmor().bypassInvul(), damage);

        }
    }


    private static void updateInsideItems(NonNullList<ItemStack> lista, Player player) {
        if(insideItems.containsKey(player)){
            if(insideItems.get(player).equals(lista)){
                return;
            } else {
                insideItems.remove(player);
            }
        }
        insideItems.put(player, lista);
    }


    public static NonNullList<ItemStack> getItems(Player player){
        NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
        player.getCapability(ModCapabilities.ALTAR_CAPABILITY).ifPresent(inv->{
            for(int i = 0; i < 9 ; i++){
                items.set(i, inv.getStackInSlot(i));
            }
        });
        return items;
    }


    public static NonNullList<ItemStack> getInsideItems(Player playerEntity) {
        return insideItems.containsKey(playerEntity) ? insideItems.get(playerEntity) :
                NonNullList.withSize(9, ItemStack.EMPTY);
    }

    public static double magicImunne(LivingEntity playerEntity){
        double count = 0;
        if(playerEntity.getAttribute(ModAttributes.MAGIC_RESISTANCE) != null){
            count = playerEntity.getAttributeValue(ModAttributes.MAGIC_RESISTANCE);
        }
        return count / 10;

    }
    public static void sendPlayerLevelMessage(Player player){
        player.sendSystemMessage(Component.translatable("mohard.server.player_level").withStyle(ChatFormatting.GRAY).append(Component.literal(""+Math.round(LevelMechanic.getPlayerLevel(player))).withStyle(ChatFormatting.DARK_AQUA).withStyle(ChatFormatting.UNDERLINE)));
    }
    public static void sendPlayersLoginLevelMessage(Player player){
        player.sendSystemMessage(Component.translatable("mohard.server.player_login_level").withStyle(ChatFormatting.GRAY).append(Component.literal(""+Math.round(LevelMechanic.SERVER_LEVEL)).withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.UNDERLINE)));
        player.sendSystemMessage(Component.translatable("mohard.server.player_login_variance").withStyle(ChatFormatting.GRAY).append(Component.literal(""+Math.round(LevelMechanic.VAR)).withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.UNDERLINE)));

    }
}
