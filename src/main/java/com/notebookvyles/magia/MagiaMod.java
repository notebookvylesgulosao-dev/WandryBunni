package com.notebookvyles.magia;

import com.notebookvyles.magia.spells.SpellCatalog;
import com.notebookvyles.magia.items.WandCatalog;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ponto de entrada Fabric. Esta primeira entrega prepara os dados do mod;
 * registro/renderização e integração de gameplay serão adicionados por etapas.
 */
public final class MagiaMod implements ModInitializer {
    public static final String MOD_ID = "magia";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Magia Arcana iniciando: {} feitiços e {} varinhas catalogados.",
                SpellCatalog.ALL.size(), WandCatalog.ALL.size());
        LOGGER.warn("Build-base: os catálogos estão definidos, mas os sistemas de jogo ainda não estão conectados.");
    }
}
