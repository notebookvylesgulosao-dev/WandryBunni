package com.notebookvyles.magia.client;

/**
 * Curva temporal de preparação/avanço. Fornece amostras intermediárias suaves.
 * A ligação a braço/mão/varinha exige um renderer de primeira e terceira pessoa.
 */
public final class CastAnimationCurve {
    private CastAnimationCurve() {}

    public static double smoothStep(double t) {
        t = Math.max(0.0, Math.min(1.0, t));
        return t * t * (3.0 - 2.0 * t);
    }

    public static Pose sample(int frame, int totalFrames) {
        double t = Math.max(0.0, Math.min(1.0, frame / (double) Math.max(1, totalFrames - 1)));
        // Recuo: 0-35%; avanço: 35-80%; sustentação final: 80-100%.
        double pullback = smoothStep(Math.min(1.0, t / 0.35));
        double thrust = smoothStep(Math.max(0.0, Math.min(1.0, (t - 0.35) / 0.45)));
        double returnBlend = smoothStep(Math.max(0.0, (t - 0.80) / 0.20));
        double x = -0.22 * pullback + 0.38 * thrust * (1.0 - returnBlend);
        double pitch = 0.18 * pullback - 0.32 * thrust * (1.0 - returnBlend);
        return new Pose(x, pitch, t >= 0.80);
    }

    public record Pose(double armOffset, double armPitch, boolean launchWindowReached) {}
}
