#!/usr/bin/env python3
"""Lê tools/inspect_request.txt e imprime assinaturas do código-fonte decompilado do Minecraft.
Linhas: 'class Nome' (assinaturas de métodos/campos), 'grep REGEX' (busca em todos os fontes)."""
import glob, os, re, subprocess, sys, zipfile
jars = [j for j in glob.glob(os.path.expanduser("~/.gradle/caches/**/*sources.jar"), recursive=True)
        if "minecraft" in j.lower() and "fabric-api" not in j.lower()]
jars += glob.glob("**/*minecraft*-sources.jar", recursive=True)
print("JARS:", jars)
out = "/tmp/mcsrc"; os.makedirs(out, exist_ok=True)
for j in jars: zipfile.ZipFile(j).extractall(out)
files = glob.glob(out + "/**/*.java", recursive=True)
print("arquivos fonte:", len(files))
sig = re.compile(r"^\s+(public|protected|static|abstract|default|private)?.*[\w>\]]\s+\w+\(.*|^\s+(public|protected)\s.*;|^\s+[A-Z_]+\(.*\)[,;]\s*$")
for line in open("tools/inspect_request.txt"):
    line = line.strip()
    if not line or line.startswith("#"): continue
    kind, _, arg = line.partition(" ")
    print("\n=====", line)
    if kind == "class":
        for f in files:
            if os.path.basename(f) == arg + ".java":
                print("--", f.replace(out, ""))
                for l in open(f, errors="ignore"):
                    if sig.match(l) and "return " not in l and not l.strip().startswith(("if", "for", "//", "*")):
                        print(l.rstrip()[:200])
    elif kind == "grep":
        n = 0
        for f in files:
            for i, l in enumerate(open(f, errors="ignore")):
                if re.search(arg, l):
                    print(f.replace(out, "")[-70:], i + 1, l.strip()[:180]); n += 1
                    if n > 40: break
            if n > 40: break
