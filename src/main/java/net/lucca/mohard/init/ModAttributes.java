package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ModMain.MODID);


    public static void register(IEventBus bus){
        ATTRIBUTES.register(bus);
    }

    public static final Attribute PHYSICAL_DAMAGE = register("additional.physical_damage", new RangedAttribute("attribute.name.additional.physical_damage", 1.0, -2000.0, 2000.0).setSyncable(true));
    public static final Attribute PROJECTILE_DAMAGE = register("additional.projectile_damage", new RangedAttribute("attribute.name.additional.projectile_damage", 1.0, -2000.0, 2000.0).setSyncable(true));
    public static final Attribute MAGIC_DAMAGE = register("additional.magic_damage", new RangedAttribute("attribute.name.additional.magic_damage", 1.0, -2000.0, 2000.0).setSyncable(true));
    public static final Attribute RAW_ARMOR = register("additional.raw_armor", new RangedAttribute("attribute.name.additional.raw_armor", 0.0, -2048.0, 2048.0).setSyncable(true));
    public static final Attribute ARMOR_PENETRATION = register("additional.armor_penetration", new RangedAttribute("attribute.name.additional.armor_penetration", 0.0, -100.0, 100.0).setSyncable(true));
    public static final Attribute AGILITY = register("additional.agility", new RangedAttribute("attribute.name.additional.agility", 0.0, -100.0, 100.0).setSyncable(true));
    public static final Attribute INTELLECT = register("additional.intellect", new RangedAttribute("attribute.name.additional.intellect", 0.0, -100.0, 100.0).setSyncable(true));
    public static final Attribute MAGIC_RESISTANCE = register("additional.magic_resistance", new RangedAttribute("attribute.name.additional.magic_resistance", 0.0, -10.0, 10.0).setSyncable(true));


    private static Attribute register(String name, Attribute att){
        ATTRIBUTES.register(name, () -> att);
        return att;
    }
}
