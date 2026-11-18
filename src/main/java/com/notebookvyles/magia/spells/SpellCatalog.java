package com.notebookvyles.magia.spells;

import com.notebookvyles.magia.data.Alignment;
import com.notebookvyles.magia.data.SpellDefinition;
import com.notebookvyles.magia.data.StatusEffectSpec;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public final class SpellCatalog {
    private SpellCatalog() {}

    public static final Map<String, SpellDefinition> ALL = create();

    private static Map<String, SpellDefinition> create() {
        Map<String, SpellDefinition> m = new LinkedHashMap<>();
        add(m, new SpellDefinition("avada_kedavra", "Avada Kedavra", Alignment.EVIL,
                10, 30, 0, List.of(), "green", "custom_projectile; green_visuals; green_particles"));
        add(m, new SpellDefinition("corinficus", "Corinficus", Alignment.NORMAL,
                0, 20, 0, List.of(), "red",
                "impact_explosion_strength_unspecified_creeper_or_two_tnt; red_trail; red_visuals; red_particles"));
        add(m, new SpellDefinition("crucio", "Crucio", Alignment.EVIL,
                6, 0, 0, List.of(new StatusEffectSpec("damage_over_time", 3, 0)),
                "red", "custom_projectile; red_trail; decomposition_visual; 2_hearts_per_second_for_3_seconds"));
        add(m, new SpellDefinition("expelliarmus", "Expelliarmus", Alignment.NORMAL,
                1, 0, 10, List.of(), "white", "knockback_equivalent_to_knockback_iv; white_particles"));
        add(m, new SpellDefinition("imperius", "Imperius", Alignment.EVIL,
                5, 0, 20, List.of(new StatusEffectSpec("slowness", 5, Integer.MAX_VALUE),
                new StatusEffectSpec("immobilize", 5, 0)), "yellow",
                "full_movement_lock_for_5_seconds; no_mind_control"));
        add(m, new SpellDefinition("petrificus_totalis", "Petrificus Totalis", Alignment.NORMAL,
                0, 0, 10, List.of(new StatusEffectSpec("slowness", 8, Integer.MAX_VALUE)),
                "gray", "no_damage; no_unspecified_particles"));
        add(m, new SpellDefinition("sectumsempra", "Sectumsempra", Alignment.NORMAL,
                4, 0, 30, List.of(new StatusEffectSpec("poison", 2, 0)),
                "purple", "purple_visuals; purple_particles"));
        return Map.copyOf(m);
    }

    private static void add(Map<String, SpellDefinition> map, SpellDefinition definition) {
        map.put(definition.id(), definition);
    }
}
