import json, re, urllib.request
def get(u): return urllib.request.urlopen(u, timeout=30).read().decode()
mc = "1.21.11"
loader = next(v["version"] for v in json.loads(get("https://meta.fabricmc.net/v2/versions/loader")) if v["stable"])
yarn = json.loads(get(f"https://meta.fabricmc.net/v2/versions/yarn/{mc}"))[0]["version"]
meta = get("https://maven.fabricmc.net/net/fabricmc/fabric-api/fabric-api/maven-metadata.xml")
api = [v for v in re.findall(r"<version>([^<]+)</version>", meta) if v.endswith("+" + mc)][-1]
s = open("gradle.properties").read()
for k, v in (("loader_version", loader), ("yarn_mappings", yarn), ("fabric_api_version", api)):
    s = re.sub(rf"{k}=.*", f"{k}={v}", s)
open("gradle.properties", "w").write(s)
print(s)
