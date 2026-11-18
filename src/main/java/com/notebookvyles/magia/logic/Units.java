package com.notebookvyles.magia.logic;

/** Conversões de unidades. 1 coração = 2 pontos de vida; 1 segundo = 20 ticks. */
public final class Units {
    private Units() {}
    public static float heartsToHealth(double hearts) { return (float) (hearts * 2.0); }
    public static int secondsToTicks(double seconds) { return (int) Math.round(seconds * 20.0); }
}
