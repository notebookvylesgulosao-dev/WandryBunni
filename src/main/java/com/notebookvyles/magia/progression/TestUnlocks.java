package com.notebookvyles.magia.progression;

import com.notebookvyles.magia.spells.SpellCatalog;
import java.util.LinkedHashSet;
import java.util.Set;

/** Estado inicial da fase de testes: os sete feitiços listados já começam desbloqueados. */
public final class TestUnlocks {
    private final Set<String> unlocked = new LinkedHashSet<>(SpellCatalog.ALL.keySet());

    public boolean isUnlocked(String spellId) { return unlocked.contains(spellId); }
    public Set<String> getUnlocked() { return Set.copyOf(unlocked); }

    public boolean unlockOnce(String spellId) {
        if (!SpellCatalog.ALL.containsKey(spellId)) return false;
        return unlocked.add(spellId);
    }
}
