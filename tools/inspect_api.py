#!/usr/bin/env python3
"""Lê tools/inspect_request.txt e imprime assinaturas do código-fonte decompilado do Minecraft.
Linhas: 'class Nome' (assinaturas de métodos/campos), 'grep REGEX' (busca em todos os fontes)."""
import glob, os, re, subprocess, sys, zipfile
jars = []
for base in (os.path.expanduser("~"), os.getcwd()):
    for dp, dn, fn in os.walk(base):
        for f in fn:
            if f.endswith("sources.jar") and "minecraft" in (dp + f).lower() and "fabric-api" not in (dp + f).lower() and "/magia" not in f.lower():
                jars.append(os.path.join(dp, f))
jars = sorted(set(jars))
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
                    if sig.match(l) and "return " not in l and "private " not in l and not l.strip().startswith(("if", "for", "//", "*", "new ", "throw", ".", "builder", "super")) and l.startswith(("\tpublic", "\tprotected", "\tstatic", "\tabstract", "\tdefault", "\t\tpublic static", "\t\tpublic ")):
                        print(l.rstrip()[:200])
    elif kind == "show":
        name, a, b = arg.split()
        for f in files:
            if os.path.basename(f) == name + ".java":
                ls = open(f, errors="ignore").read().splitlines()
                print("--", f.replace(out, ""))
                for i in range(int(a) - 1, min(int(b), len(ls))): print(f"{i+1}: {ls[i]}"[:200])
    elif kind == "javap":
        cls = arg.replace(".", "/") + ".class"
        found = None
        for base in (os.path.expanduser("~"), os.getcwd()):
            for dp, dn, fn in os.walk(base):
                for f in fn:
                    if f.endswith(".jar") and "sources" not in f:
                        jp = os.path.join(dp, f)
                        try:
                            if cls in zipfile.ZipFile(jp).namelist(): found = jp; break
                        except Exception: pass
                if found: break
            if found: break
        print("jar:", found)
        if found: print(subprocess.run(["javap", "-public", "-cp", found, arg], capture_output=True, text=True).stdout[:6000])
    elif kind == "grep":
        n = 0
        for f in files:
            for i, l in enumerate(open(f, errors="ignore")):
                if re.search(arg, l):
                    print(f.replace(out, "")[-70:], i + 1, l.strip()[:180]); n += 1
                    if n > 40: break
            if n > 40: break
