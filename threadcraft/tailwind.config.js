/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,jsx}"],
  theme: {
    extend: {
      colors: {
        ink: {
          DEFAULT: "#0B0C10",
          soft: "#14161C",
          line: "#25272F",
        },
        paper: {
          DEFAULT: "#EDE8DD",
          dim: "#C9C3B5",
        },
        signal: {
          DEFAULT: "#D6301F",
          dim: "#A82418",
          glow: "#FF5A3C",
        },
        thread: {
          DEFAULT: "#C6A24D",
          soft: "#8C7638",
        },
      },
      fontFamily: {
        display: ["'Archivo Black'", "'Arial Narrow'", "sans-serif"],
        sans: ["'Inter'", "system-ui", "sans-serif"],
        mono: ["'JetBrains Mono'", "monospace"],
      },
      letterSpacing: {
        tightest: "-0.045em",
      },
      backgroundImage: {
        grain: "url('/images/grain.png')",
      },
    },
  },
  plugins: [],
};
