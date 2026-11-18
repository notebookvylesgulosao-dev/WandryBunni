package com.notebookvyles.magia.logic;

import com.notebookvyles.magia.client.CastAnimationCurve;

/**
 * Estado do lançamento (um por jogador). O feitiço só sai no instante em que a animação
 * alcança a posição de disparo; cliques durante a animação são ignorados; o evento de
 * disparo ocorre uma única vez e então inicia o retorno do braço.
 */
public final class CastStateMachine {
    public enum Phase { IDLE, PREPARING, RECOVERING }
    public enum Event { NONE, FIRE }

    /** Fração da animação em que o braço está estendido e o feitiço sai. */
    public static final double FIRE_POINT = 0.80;

    private final int totalTicks;
    private Phase phase = Phase.IDLE;
    private int tick;
    private boolean fired;

    public CastStateMachine(int totalTicks) { this.totalTicks = Math.max(2, totalTicks); }

    public Phase phase() { return phase; }

    /** Tenta iniciar a preparação. Falso se já houver uma animação em andamento. */
    public boolean start() {
        if (phase != Phase.IDLE) return false;
        phase = Phase.PREPARING; tick = 0; fired = false;
        return true;
    }

    /** Avança um tick; retorna FIRE exatamente uma vez quando a posição de ataque é alcançada. */
    public Event tick() {
        if (phase == Phase.IDLE) return Event.NONE;
        tick++;
        double t = tick / (double) totalTicks;
        Event e = Event.NONE;
        if (phase == Phase.PREPARING && !fired && t >= FIRE_POINT) {
            fired = true; phase = Phase.RECOVERING; e = Event.FIRE;
        }
        if (tick >= totalTicks) { phase = Phase.IDLE; tick = 0; }
        return e;
    }

    /** Progresso 0..1, com interpolação parcial entre ticks (partialTick 0..1). */
    public double progress(double partialTick) {
        if (phase == Phase.IDLE) return 0.0;
        return Math.min(1.0, (tick + partialTick) / totalTicks);
    }

    public CastAnimationCurve.Pose pose(double partialTick) {
        int samples = com.notebookvyles.magia.config.MagicRules.CAST_ANIMATION_SAMPLES;
        double p = progress(partialTick);
        return CastAnimationCurve.sample((int) Math.round(p * (samples - 1)), samples);
    }
}
