# Magia Arcana — código-fonte inicial

Projeto-base **Fabric para Minecraft 1.21.11**, criado a partir dos arquivos enviados.
Os 20 arquivos `.bbmodel` originais foram copiados sem alterações para
`src/main/resources/assets/magia/blockbench_sources/`. As texturas incorporadas
foram extraídas para `textures/source/` para inspeção e futura integração.

## Ambiente

- Minecraft Java Edition 26.3
- Fabric Loader 0.20.1
- Fabric API 0.166.0+26.3
- Fabric Loom 1.19-SNAPSHOT
- Gradle 9.6
- Java 21

As versões seguem a página oficial de desenvolvimento Fabric consultada em
17/11/2026. É necessário instalar Java 21 para compilar este projeto.

## O que este primeiro código já contém

- Estrutura Gradle/Fabric para 26.3.
- Ponto de entrada do mod.
- Catálogo declarativo dos sete feitiços com os números e tempos fornecidos.
- Catálogo das sete varinhas, sem inventar suas classes.
- Catálogo dos cinco props e efeitos informados.
- Parâmetros confirmados do chapéu, vassoura, pergaminhos e roda de feitiços.
- Estado inicial de testes com os sete feitiços desbloqueados.
- Estrutura lógica de oito espaços fixos.
- Curva matemática com amostras intermediárias para a animação de lançamento.
- Contrato para efeitos de combate no lado servidor.
- Modelos Blockbench e texturas originais preservados.

## O que ainda NÃO está implementado

Esta entrega é uma **base de código-fonte**, não um mod completo pronto para jogar.
Ainda faltam as integrações reais com as APIs/renderização do Minecraft:

1. Registrar itens, entidades de projétil, entidades da vassoura e equipamento.
2. Renderizar os `.bbmodel` no jogo. Os arquivos estão preservados; ainda não foram
   convertidos para um renderer de item/entidade. Não foi criado modelo genérico substituto.
3. Criar renderer de projéteis, partículas e trilhas com sincronização servidor/cliente.
4. Implementar colisão, dano, recarga e efeitos no servidor.
5. Implementar a tela circular de seleção, keybind configurável e persistência.
6. Ligar a curva de animação ao braço/varinha em primeira e terceira pessoa e sincronizar
   o evento de disparo.
7. Implementar a vassoura montável, controles de voo, recuperação segura e partículas.
8. Implementar o chapéu no slot da cabeça e os atributos temporários.
9. Implementar pergaminhos, geração em estruturas e tabela de recompensa.
10. Criar receitas somente depois de receber as imagens/posições/quantidades exatas.

## Pendências identificadas nos arquivos enviados

- Não há `Dragon Wand.bbmodel`; o modelo dessa varinha está ausente.
- Os três esquemas de receita não vieram no ZIP.
- Não há animações Blockbench nos modelos analisados.
- As classes Good/Evil/Normal das varinhas não foram especificadas individualmente.
- A lista completa dos dez feitiços do sistema de pergaminhos e a identificação dos três
  proibidos não foram fornecidas. Por isso, nenhuma tabela inventada foi adicionada.
- A força exata da explosão Corinficus não foi numericamente definida; a especificação
  textual foi preservada sem escolher um valor arbitrário.

## Compilar

Na pasta do projeto, com Java 21 e Gradle 9.6 instalado:

```bash
gradle build
```

O JAR, quando a compilação for concluída com sucesso, ficará em:

```text
build/libs/magia-arcana-0.1.0.jar
```

## Importante

Não foi possível executar uma compilação ou teste dentro do Minecraft neste ambiente.
O código deve ser tratado como primeira base de desenvolvimento; não instale como se
todos os sistemas do prompt já estivessem prontos.
