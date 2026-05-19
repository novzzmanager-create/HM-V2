(function () {

  const moduleCache = {};
  const plugins = {};

  window.exec = function (cmd, options = {}) {

    return new Promise((resolve, reject) => {

      if (!window.Hypers || !window.Hypers.exec) {
        reject({
          errno: -1,
          stdout: "",
          stderr: "Engine not ready"
        });
        return;
      }

      try {

        const res = window.Hypers.exec(cmd, JSON.stringify(options));

        try {
          resolve(JSON.parse(res));
        } catch {
          resolve({
            errno: 0,
            stdout: res,
            stderr: ""
          });
        }

      } catch (e) {
        reject({
          errno: -1,
          stderr: e.toString()
        });
      }
    });
  };

  window.toast = function (msg) {
    if (window.Hypers && window.Hypers.toast) {
      window.Hypers.toast(msg);
    }
  };


  window.fullScreen = function (state) {
    if (window.Hypers && window.Hypers.fullScreen) {
      window.Hypers.fullScreen(state);
    }
  };

  window.spawn = function (cmd, args = [], options = {}) {

    if (!window.Hypers || !window.Hypers.spawn) return null;

    try {
      return window.Hypers.spawn(
        cmd,
        JSON.stringify(args),
        JSON.stringify(options)
      );
    } catch {
      return null;
    }
  };

  window.registerPlugin = function (name, fn) {
    plugins[name] = fn;
  };

  window.runPlugin = function (name, data) {
    if (plugins[name]) {
      return plugins[name](data);
    }
    return null;
  };

  window.importModule = async function (url) {

    if (moduleCache[url]) return moduleCache[url];

    try {
      const res = await fetch(url);
      let code = await res.text();

      code = code
        .replace(/export function (\w+)/g, "exports.$1 = function")
        .replace(/export const (\w+)\\s*=/g, "exports.$1 =")
        .replace(/export default/g, "exports.default =");

      const exports = {};

      new Function("exports", code)(exports);

      moduleCache[url] = exports;
      return exports;

    } catch {
      return {};
    }
  };

  window.OS = {
    version: "1.0",
    ready: true
  };

})();