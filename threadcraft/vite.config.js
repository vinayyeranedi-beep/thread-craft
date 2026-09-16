import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  base: "/THREADCRAFT/",
  plugins: [react()],
  server: {
    host: true,
    port: 5173,
    // Auto-launch the browser at the correct http://localhost URL — the
    // most common way this project breaks is someone double-clicking
    // index.html and opening it as a file:// URL instead, which browsers
    // block ES modules from running under. Opening automatically here
    // removes that step entirely.
    open: true,
  },
  preview: {
    open: true,
  },
});
