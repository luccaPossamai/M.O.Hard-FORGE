package net.lucca.mohard.init;

import net.lucca.mohard.enchantments.essences.EssenceEntailBinding;
import net.lucca.mohard.itens.essence.essenceEntails.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModBindings {
    public static void register(){}
    public static final Map<String, EssenceEntailBinding> BINDINGS_MAP = new HashMap<>();
    public static final List<EssenceEntailBinding> BINDINGS = new ArrayList<>();


    public static final EssenceEntailBinding EMPTY_BINDING = binding("empty_binding", new EssenceEntail());

    public static final EssenceEntailBinding REFLECTION = binding("reflection", new ReflectionEntail());
    public static final EssenceEntailBinding LIFE_STEAL = binding("life_steal", new LifeStealEntail());
    public static final EssenceEntailBinding REGENERATION = binding("regeneration", new RegenerationEntail());
    public static final EssenceEntailBinding HEALTH_DISCHARGE = binding("health_discharge", new HealthDischargeEntail());

    private static EssenceEntailBinding binding(String name, EssenceEntail entail){
        EssenceEntailBinding binding = new EssenceEntailBinding(entail, name);
        BINDINGS_MAP.put(name, binding);
        if(!name.equals("empty_binding")) BINDINGS.add(binding);
        return binding;
    }
}
