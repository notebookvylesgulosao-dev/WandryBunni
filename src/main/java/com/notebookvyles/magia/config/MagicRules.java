package com.notebookvyles.magia.config;

/** Parâmetros confirmados; não representa ainda integração com atributos do Minecraft. */
public final class MagicRules {
    private MagicRules() {}

    public static final int SPELL_WHEEL_SLOTS = 8;
    public static final int TEST_UNLOCKED_SPELLS = 7;
    public static final String SPELL_WHEEL_DEFAULT_KEY = "G";

    public static final double BROOM_BLOCKS_PER_SECOND = 5.0;
    public static final double HAT_BASIC_SPELL_DAMAGE_BONUS = 0.25;
    public static final double HAT_MAX_HEALTH_BONUS_HEARTS = 5.0;
    public static final double HAT_SPECIAL_DAMAGE_BONUS_MIN = 0.05;
    public static final double HAT_SPECIAL_DAMAGE_BONUS_MAX = 0.10;

    public static final int SCROLL_STRUCTURE_CHANCE_PERCENT = 30;
    public static final int SCROLL_EMPTY_CHANCE_PERCENT = 20;
    public static final int EVIL_FORBIDDEN_SPELL_CHANCE_OUT_OF_100 = 5;

    public static final int CAST_ANIMATION_DURATION_MILLIS = 1000;
    public static final int CAST_ANIMATION_MAX_MILLIS = 1500;
    public static final int CAST_ANIMATION_SAMPLES = 60;
}
