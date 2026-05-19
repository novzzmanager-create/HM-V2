(function () {

  const moduleCache = {};

  const CDN_MAP = {
    "kernelsu": () => ({
      exec: window.exec,
      spawn: window.spawn,
      toast: window.toast,
      fullScreen: window.fullScreen
    })
  };

  function matchCDN(url) {
    for (let k in CDN_MAP) {
      if (url.includes(k)) return CDN_MAP[k]();
    }
    return null;
  }

  async function fetchCode(url) {
    const res = await fetch(url);
    return await res.text();
  }

  function transform(code) {

    code = code.replace(/export function (\w+)/g, "exports.$1 = function");
    code = code.replace(/export const (\w+)/g, "exports.$1 =");
    code = code.replace(/export default/g, "exports.default =");

    code = code.replace(
      /import\s+\{([^}]+)\}\s+from\s+['"]([^'"]+)['"]/g,
      "const {$1} = await importModule('$2')"
    );

    return code;
  }

  async function importModule(url) {

    if (moduleCache[url]) return moduleCache[url];

    const cdn = matchCDN(url);
    if (cdn) {
      moduleCache[url] = cdn;
      return cdn;
    }

    try {
      let code = await fetchCode(url);
      code = transform(code);

      const exports = {};

      const fn = new Function(
        "exports",
        "importModule",
        `
        return (async () => {
          ${code}
          return exports;
        })();
      `
      );

      const result = await fn(exports, importModule);

      moduleCache[url] = result;
      return result;

    } catch (e) {
      console.error("module error:", url, e);
      return {};
    }
  }

  window.importModule = importModule;

})();