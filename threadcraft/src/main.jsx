import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import AppErrorBoundary from "./components/AppErrorBoundary.jsx";
import "./index.css";

// Last-resort net: if something throws before/outside React's own error
// boundaries (a module-evaluation error, an error in an async callback),
// make sure the page never just stays blank — show the real error instead.
function showFatalError(error) {
  const root = document.getElementById("root");
  if (!root || root.childElementCount > 0) return; // React already painted something
  root.innerHTML = `
    <div style="min-height:100vh;background:#0B0C10;color:#EDE8DD;font-family:system-ui,sans-serif;display:flex;align-items:center;justify-content:center;padding:32px;">
      <div style="max-width:560px;">
        <p style="font-size:12px;letter-spacing:0.2em;text-transform:uppercase;color:#C6A24D;margin:0;">Something broke</p>
        <h1 style="font-size:28px;margin:12px 0;line-height:1.2;">The page failed to load.</h1>
        <p style="color:#C9C3B5;font-size:14px;line-height:1.6;">Copy this error back to whoever's building this site:</p>
        <pre style="margin-top:16px;padding:16px;background:#14161C;border:1px solid #25272F;border-radius:12px;font-size:12px;line-height:1.6;white-space:pre-wrap;word-break:break-word;color:#FF5A3C;">${String(
          (error && (error.stack || error.message)) || error
        ).replace(/</g, "&lt;")}</pre>
      </div>
    </div>`;
}

window.addEventListener("error", (e) => showFatalError(e.error || e.message));
window.addEventListener("unhandledrejection", (e) => showFatalError(e.reason));

try {
  ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
      <AppErrorBoundary>
        <App />
      </AppErrorBoundary>
    </React.StrictMode>
  );
} catch (error) {
  showFatalError(error);
}
