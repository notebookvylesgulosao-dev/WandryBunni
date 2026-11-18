package com.notebookvyles.magia.entities;

import java.util.List;

public final class PropCatalog {
    private PropCatalog() {}
    public record Prop(String id, String[] colors, String trail, String onHit) {}

    public static final List<Prop> ALL = List.of(
        new Prop("amethyst", new String[]{"purple"}, "purple", "none_specified"),
        new Prop("bone", new String[]{"white"}, "bone_fragments", "blindness_2_seconds"),
        new Prop("celestial", new String[]{"blue"}, "blue", "glowing_15_seconds; poison_3_seconds"),
        new Prop("dragon", new String[]{"red", "orange"}, "red_orange_fire", "fire_3_seconds; 2_damage_per_second"),
        new Prop("widow", new String[]{"black", "white", "gray", "red"}, "four_colors_simultaneously", "none_specified")
    );
}
