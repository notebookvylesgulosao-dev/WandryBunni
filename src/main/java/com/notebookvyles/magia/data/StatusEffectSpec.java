package com.notebookvyles.magia.data;

/** Especificação de efeito; duração expressa em segundos. */
public record StatusEffectSpec(String effectId, int durationSeconds, int amplifier) {}
