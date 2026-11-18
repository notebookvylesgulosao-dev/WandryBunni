package com.notebookvyles.magia.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Recarga por jogador e por feitiço, em ticks do servidor. Autoridade: servidor. */
public final class CooldownTracker {
    private final Map<UUID, Map<String, Long>> readyAt = new HashMap<>();

    /** Retorna true e inicia a recarga se o feitiço estiver disponível; false caso contrário. */
    public boolean tryUse(UUID player, String spellId, long nowTick, int cooldownTicks) {
        Map<String, Long> m = readyAt.computeIfAbsent(player, k -> new HashMap<>());
        long ready = m.getOrDefault(spellId, Long.MIN_VALUE);
        if (nowTick < ready) return false;
        if (cooldownTicks > 0) m.put(spellId, nowTick + cooldownTicks);
        return true;
    }

    public int remainingTicks(UUID player, String spellId, long nowTick) {
        long ready = readyAt.getOrDefault(player, Map.of()).getOrDefault(spellId, Long.MIN_VALUE);
        return (int) Math.max(0, ready - nowTick);
    }
}
