#!/usr/bin/env python3
"""Converte .bbmodel (java_block) -> modelo JSON de item + texturas PNG, sem alterar geometria.
Uso: python3 tools/bbmodel_to_json.py"""
import json, base64, os, sys
ROOT = os.path.join(os.path.dirname(__file__), "..", "src/main/resources/assets/magia")
SRC = os.path.join(ROOT, "blockbench_sources")
# id do item -> bbmodel
ITEMS = {
    "amethyst_wand": "wands/amethyst-rod.bbmodel",
    "bone_wand": "wands/bone-wand.bbmodel",
    "celestial_wand": "wands/celestial_wand.bbmodel",
    "dragon_wand": "wands/dragon-rod.bbmodel",
    "emerald_wand": "wands/emerald_wand_refined.bbmodel",
    "magma_wand": "wands/magma_wand.bbmodel",
    "widow_wand": "wands/widow-rod.bbmodel",
    "witch_hat": "items/witch-hat.bbmodel",
    "witch_broom": "items/witch-broom.bbmodel",
}
def num(x): return int(x) if float(x).is_integer() else round(float(x), 5)

def convert(item_id, rel):
    d = json.load(open(os.path.join(SRC, rel)))
    scale = 16.0 / d["resolution"]["width"]
    textures = {}
    os.makedirs(os.path.join(ROOT, "textures/item"), exist_ok=True)
    for i, t in enumerate(d["textures"]):
        name = f"{item_id}_{i}"
        png = base64.b64decode(t["source"].split(",", 1)[1])
        open(os.path.join(ROOT, f"textures/item/{name}.png"), "wb").write(png)
        textures[str(i)] = f"magia:item/{name}"
    textures["particle"] = textures["0"]
    elements = []
    for e in d["elements"]:
        el = {"name": e["name"], "from": [num(v) for v in e["from"]], "to": [num(v) for v in e["to"]]}
        rot = e.get("rotation", [0, 0, 0])
        nz = [(ax, a) for ax, a in zip("xyz", rot) if abs(a) > 1e-6]
        if len(nz) > 1:
            sys.exit(f"{item_id}: elemento {e['name']} com rotação em vários eixos")
        if nz:
            el["rotation"] = {"angle": num(nz[0][1]), "axis": nz[0][0], "origin": [num(v) for v in e["origin"]]}
            if e.get("rescale"): el["rotation"]["rescale"] = True
        if e.get("light_emission"): el["light_emission"] = e["light_emission"]
        faces = {}
        for side, f in e["faces"].items():
            if f.get("texture") is None: continue
            faces[side] = {"uv": [num(round(v * scale, 5)) for v in f["uv"]], "texture": f"#{f['texture']}"}
            if f.get("rotation"): faces[side]["rotation"] = f["rotation"]
        el["faces"] = faces
        elements.append(el)
    model = {"textures": textures, "elements": elements, "display": d.get("display", {})}
    os.makedirs(os.path.join(ROOT, "models/item"), exist_ok=True)
    json.dump(model, open(os.path.join(ROOT, f"models/item/{item_id}.json"), "w"), indent=1)
    os.makedirs(os.path.join(ROOT, "items"), exist_ok=True)
    json.dump({"model": {"type": "minecraft:model", "model": f"magia:item/{item_id}"}},
              open(os.path.join(ROOT, f"items/{item_id}.json"), "w"))
    print(item_id, len(elements), "elementos,", len(textures) - 1, "texturas")

for k, v in ITEMS.items(): convert(k, v)
