package com.notebookvyles.magia.logic;

import com.notebookvyles.magia.config.MagicRules;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.IntUnaryOperator;

/**
 * Sorteio do pergaminho. A lista dos 10 feitiços e dos 3 proibidos ainda não foi fornecida:
 * o chamador passa os candidatos da classe do jogador; nada é inventado aqui.
 * 20% de pergaminho vazio; para Evil, 5/100 das possibilidades gerais são proibidos (sem chance extra).
 */
public final class ScrollRoll {
    public enum Kind { EMPTY, UNLOCKED, DUPLICATE, NO_CANDIDATES }
    public record Result(Kind kind, String spellId) {}

    private ScrollRoll() {}

    /** rng.applyAsInt(n) deve devolver inteiro em [0, n). */
    public static Result roll(List<String> classCandidates, List<String> forbidden, boolean evilPlayer,
                              Set<String> alreadyUnlocked, IntUnaryOperator rng) {
        if (rng.applyAsInt(100) < MagicRules.SCROLL_EMPTY_CHANCE_PERCENT) return new Result(Kind.EMPTY, null);
        String pick = null;
        if (evilPlayer && !forbidden.isEmpty()
                && rng.applyAsInt(100) < MagicRules.EVIL_FORBIDDEN_SPELL_CHANCE_OUT_OF_100) {
            pick = forbidden.get(rng.applyAsInt(forbidden.size()));
        } else {
            List<String> pool = new ArrayList<>(classCandidates);
            pool.removeAll(forbidden);
            if (pool.isEmpty()) return new Result(Kind.NO_CANDIDATES, null);
            pick = pool.get(rng.applyAsInt(pool.size()));
        }
        return alreadyUnlocked.contains(pick) ? new Result(Kind.DUPLICATE, pick) : new Result(Kind.UNLOCKED, pick);
    }
}
