package com.notebookvyles.magia.items;

import java.util.List;

/** Classes das varinhas permanecem sem atribuição até existir fonte confirmada. */
public final class WandCatalog {
    private WandCatalog() {}
    public record Wand(String id, String displayName, boolean modelPresent, String alignment) {}

    public static final List<Wand> ALL = List.of(
        new Wand("amethyst_wand", "Amethyst Wand", true, "UNCONFIRMED"),
        new Wand("bone_wand", "Bone Wand", true, "UNCONFIRMED"),
        new Wand("celestial_wand", "Celestial Wand", true, "UNCONFIRMED"),
        new Wand("dragon_wand", "Dragon Wand", false, "UNCONFIRMED"),
        new Wand("emerald_wand", "Emerald Wand", true, "UNCONFIRMED"),
        new Wand("magma_wand", "Magma Wand", true, "UNCONFIRMED"),
        new Wand("widow_wand", "Widow Wand", true, "UNCONFIRMED")
    );
}
