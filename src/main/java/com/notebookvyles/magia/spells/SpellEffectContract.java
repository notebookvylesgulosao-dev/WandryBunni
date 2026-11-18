package com.notebookvyles.magia.spells;

/**
 * Contrato para o adaptador de gameplay no servidor.
 * Implementações concretas devem aplicar dano/efeitos uma única vez por colisão.
 */
public interface SpellEffectContract {
    void applyDamageInHearts(double hearts);
    void applyStatus(String effectId, int durationSeconds, int amplifier);
    void applyKnockbackEquivalentToLevel(int level);
    void applyExplosionWithNamedReference(String reference);
    void setFire(int seconds, double damagePerSecond);
    void immobilize(int seconds);
}
