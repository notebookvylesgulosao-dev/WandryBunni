package com.notebookvyles.magia;

import com.notebookvyles.magia.logic.*;
import com.notebookvyles.magia.logic.CastStateMachine.Event;
import com.notebookvyles.magia.progression.*;
import com.notebookvyles.magia.spells.SpellCatalog;
import java.util.*;

public final class LogicTests {
    static int fails = 0;
    static void check(boolean ok, String name) { System.out.println((ok ? "OK   " : "FAIL ") + name); if (!ok) fails++; }

    public static void main(String[] a) {
        // Unidades
        check(Units.heartsToHealth(10) == 20f, "10 corações = 20 HP");
        check(Units.secondsToTicks(1.5) == 30, "1,5 s = 30 ticks");

        // Catálogo conforme especificação
        check(SpellCatalog.ALL.size() == 7, "7 feitiços");
        check(SpellCatalog.ALL.get("avada_kedavra").damageHearts() == 10, "Avada 10 corações");
        check(SpellCatalog.ALL.get("crucio").damageHearts() == 6, "Crucio 6 corações (2/s x 3s)");
        check(SpellCatalog.ALL.get("imperius").cooldownSeconds() == 20, "Imperius recarga 20s");

        // Recarga
        CooldownTracker cd = new CooldownTracker(); UUID p = UUID.randomUUID();
        int expelliarmus = Units.secondsToTicks(10);
        check(cd.tryUse(p, "expelliarmus", 100, expelliarmus), "1º uso permitido");
        check(!cd.tryUse(p, "expelliarmus", 299, expelliarmus), "bloqueado antes de 10s");
        check(cd.tryUse(p, "expelliarmus", 300, expelliarmus), "liberado aos 10s");
        check(cd.tryUse(UUID.randomUUID(), "expelliarmus", 100, expelliarmus), "recarga é por jogador");

        // Dano único por colisão
        HitGuard g = new HitGuard(); UUID t = UUID.randomUUID();
        check(g.registerHit(t), "1º hit aplica");
        check(!g.registerHit(t), "2º hit no mesmo alvo ignorado");
        g.consume();
        check(!g.registerHit(UUID.randomUUID()), "projétil consumido não atinge mais");

        // Animação: dispara 1x, só no ponto certo, ignora cliques repetidos
        CastStateMachine c = new CastStateMachine(20); // 1 s
        check(c.start(), "início aceito");
        check(!c.start(), "clique repetido ignorado");
        int fires = 0, fireTick = -1;
        for (int i = 1; i <= 20; i++) {
            if (c.tick() == Event.FIRE) { fires++; fireTick = i; }
            if (i < 16) check(fires == 0 || false, "sem disparo antecipado (tick " + i + ")");
            if (i < 20) check(!c.start(), "clique durante animação ignorado"); // spam
        }
        check(fires == 1, "exatamente 1 disparo");
        check(fireTick == 16, "disparo no tick 16 (80%)");
        check(c.phase() == CastStateMachine.Phase.IDLE, "volta ao repouso");
        check(c.start(), "novo lançamento após terminar");
        double prev = -1; boolean monotonic = true;
        for (int i = 0; i < 60; i++) { double pr = c.progress(0) ; }
        check(c.pose(0.5) != null, "pose interpolada disponível");

        // Chapéu
        check(HatBonus.basicMultiplier(true) == 1.25, "chapéu +25% básicos");
        check(HatBonus.basicMultiplier(false) == 1.0, "sem chapéu sem bônus");
        check(HatBonus.extraMaxHealth(true) == 10.0 && HatBonus.extraMaxHealth(false) == 0.0, "+5 corações só equipado");
        check(HatBonus.specialMultiplier(true, OptionalDouble.empty()) == 1.0, "especial indefinido = sem bônus");
        check(HatBonus.clampHealthAfterRemoval(40f, 20f) == 20f, "vida limitada ao novo máximo");
        check(HatBonus.clampHealthAfterRemoval(0.2f, 20f) == 1f, "remoção não mata o jogador");

        // Roda de feitiços
        TestUnlocks u = new TestUnlocks(); SpellWheel w = new SpellWheel();
        check(u.getUnlocked().size() == 7, "7 desbloqueados na fase de testes");
        check(w.getSlots().size() == 8, "exatamente 8 espaços");
        check(!w.setSlot(8, "crucio", u), "slot 9 recusado");
        check(w.setSlot(0, "crucio", u) && w.setSlot(3, "crucio", u) && w.getSlots().get(0) == null, "sem feitiço duplicado na roda");
        check(!w.setSlot(1, "inexistente", u), "feitiço não desbloqueado recusado");
        check(SpellWheel.restore(Arrays.asList("imperius", null, "x", "a","b","c","d","e","f","g"), u).getSlots().size() == 8, "restore limita a 8");

        // Pergaminhos
        List<String> cand = List.of("a", "b", "c", "d"), forb = List.of("f1", "f2", "f3");
        int empty = 0, forbidden = 0, N = 100000; Random r = new Random(1);
        for (int i = 0; i < N; i++) {
            var res = ScrollRoll.roll(cand, forb, true, Set.of(), r::nextInt);
            if (res.kind() == ScrollRoll.Kind.EMPTY) empty++;
            else if (res.spellId().startsWith("f")) forbidden++;
        }
        check(Math.abs(empty / (double) N - 0.20) < 0.01, "~20% vazios");
        check(Math.abs(forbidden / (double) N - 0.04) < 0.01, "Evil: 5% dos 80% restantes proibidos (~4% total, sem chance extra)");
        check(ScrollRoll.roll(cand, forb, false, Set.of("a","b","c","d"), new Random(2)::nextInt).kind() != ScrollRoll.Kind.UNLOCKED, "duplicata nunca desbloqueia");

        System.out.println(fails == 0 ? "\nTODOS OS TESTES PASSARAM" : "\nFALHAS: " + fails);
        System.exit(fails);
    }
}
