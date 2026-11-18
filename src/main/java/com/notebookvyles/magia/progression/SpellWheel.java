package com.notebookvyles.magia.progression;

import com.notebookvyles.magia.config.MagicRules;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Oito espaços fixos; não cria páginas nem rolagem. */
public final class SpellWheel {
    private final List<String> slots = new ArrayList<>(Collections.nCopies(MagicRules.SPELL_WHEEL_SLOTS, null));

    public List<String> getSlots() { return Collections.unmodifiableList(new ArrayList<>(slots)); }

    public boolean setSlot(int slot, String spellId, TestUnlocks unlocks) {
        if (slot < 0 || slot >= MagicRules.SPELL_WHEEL_SLOTS) return false;
        if (spellId != null && !unlocks.isUnlocked(spellId)) return false;
        if (spellId != null) {
            for (int i = 0; i < slots.size(); i++) {
                if (spellId.equals(slots.get(i))) slots.set(i, null);
            }
        }
        slots.set(slot, spellId);
        return true;
    }

    public void clearSlot(int slot) {
        if (slot >= 0 && slot < slots.size()) slots.set(slot, null);
    }

    /** Restaura slots salvos (persistência). Entradas inválidas/não desbloqueadas viram vazio; ignora excedentes além de 8. */
    public static SpellWheel restore(List<String> saved, TestUnlocks unlocks) {
        SpellWheel w = new SpellWheel();
        for (int i = 0; i < Math.min(saved.size(), MagicRules.SPELL_WHEEL_SLOTS); i++) {
            String id = saved.get(i);
            if (id != null && !id.isEmpty()) w.setSlot(i, id, unlocks);
        }
        return w;
    }
}
