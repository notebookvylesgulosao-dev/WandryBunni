package com.notebookvyles.magia.data;

import java.util.List;

/**
 * Valores declarativos extraídos do prompt. Dano é armazenado em corações,
 * sem converter ou arredondar as definições originais.
 */
public record SpellDefinition(
        String id,
        String displayName,
        Alignment alignment,
        double damageHearts,
        int chargeSeconds,
        int cooldownSeconds,
        List<StatusEffectSpec> effects,
        String visual,
        String specialBehavior
) {
    public SpellDefinition {
        effects = List.copyOf(effects);
    }
}
