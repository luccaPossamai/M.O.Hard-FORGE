package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.effects.ModBrewingRecipe;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, ModMain.MODID);


    public static void register(IEventBus bus){
        POTIONS.register(bus);
    }

    public static final RegistryObject<Potion> CORRUPTION_POTION = POTIONS.register("corruption", () ->
            new Potion(new MobEffectInstance(ModEffects.CORRUPTION.get(), 200, 0))
    );


    public static void createPotionRecipes() {
        BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD) , Ingredient.of(ModItems.MELTED_VILIO.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.CORRUPTION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.CORRUPTION_POTION.get()) , Ingredient.of(Items.GUNPOWDER), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.CORRUPTION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.CORRUPTION_POTION.get()) , Ingredient.of(Items.DRAGON_BREATH), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), ModPotions.CORRUPTION_POTION.get())));
    }
}
