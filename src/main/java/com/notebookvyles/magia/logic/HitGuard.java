package com.notebookvyles.magia.logic;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/** Garante que um projétil aplique dano/efeitos apenas uma vez por alvo e se consuma na colisão. */
public final class HitGuard {
    private final Set<UUID> hit = new HashSet<>();
    private boolean consumed;

    /** true somente na primeira vez em que o alvo é atingido por este projétil. */
    public boolean registerHit(UUID target) {
        if (consumed) return false;
        return hit.add(target);
    }

    public void consume() { consumed = true; }
    public boolean isConsumed() { return consumed; }
}
