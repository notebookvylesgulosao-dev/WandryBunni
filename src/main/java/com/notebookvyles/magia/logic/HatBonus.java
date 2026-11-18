package com.notebookvyles.magia.logic;

import com.notebookvyles.magia.config.MagicRules;
import java.util.OptionalDouble;

/**
 * Bônus do chapéu. Só vale com o chapéu equipado (a camada de integração deve consultar o
 * slot da cabeça a cada tick e aplicar/remover o modificador de vida máxima por ID fixo).
 * Percentuais individuais dos feitiços especiais NÃO foram definidos: retorna vazio até serem informados.
 */
public final class HatBonus {
    private HatBonus() {}

    public static double basicMultiplier(boolean hatEquipped) {
        return hatEquipped ? 1.0 + MagicRules.HAT_BASIC_SPELL_DAMAGE_BONUS : 1.0;
    }

    /** specialBonus: fração definida para o feitiço (0.05..0.10) ou vazio se não definida. */
    public static double specialMultiplier(boolean hatEquipped, OptionalDouble specialBonus) {
        if (!hatEquipped || specialBonus.isEmpty()) return 1.0;
        double b = specialBonus.getAsDouble();
        if (b < MagicRules.HAT_SPECIAL_DAMAGE_BONUS_MIN || b > MagicRules.HAT_SPECIAL_DAMAGE_BONUS_MAX)
            throw new IllegalArgumentException("Bônus especial fora de 5%-10%: " + b);
        return 1.0 + b;
    }

    public static double extraMaxHealth(boolean hatEquipped) {
        return hatEquipped ? MagicRules.HAT_MAX_HEALTH_BONUS_HEARTS * 2.0 : 0.0;
    }

    /** Vida atual após remover o chapéu: nunca acima do novo máximo e nunca abaixo de 1 (sem matar). */
    public static float clampHealthAfterRemoval(float current, float newMax) {
        return Math.max(1.0f, Math.min(current, newMax));
    }
}
