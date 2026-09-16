import { Component } from "react";

export default class AppErrorBoundary extends Component {
  constructor(props) {
    super(props);
    this.state = { error: null };
  }

  static getDerivedStateFromError(error) {
    return { error };
  }

  componentDidCatch(error, info) {
    // eslint-disable-next-line no-console
    console.error("THREADCRAFT crashed:", error, info?.componentStack);
  }

  render() {
    if (this.state.error) {
      return (
        <div
          style={{
            minHeight: "100vh",
            background: "#0B0C10",
            color: "#EDE8DD",
            fontFamily: "system-ui, sans-serif",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            padding: "32px",
          }}
        >
          <div style={{ maxWidth: 560 }}>
            <p style={{ fontSize: 12, letterSpacing: "0.2em", textTransform: "uppercase", color: "#C6A24D", margin: 0 }}>
              Something broke
            </p>
            <h1 style={{ fontSize: 28, margin: "12px 0", lineHeight: 1.2 }}>
              The page hit an error instead of loading normally.
            </h1>
            <p style={{ color: "#C9C3B5", fontSize: 14, lineHeight: 1.6 }}>
              This is the actual error — copy it back to whoever's building this site so it can be fixed:
            </p>
            <pre
              style={{
                marginTop: 16,
                padding: 16,
                background: "#14161C",
                border: "1px solid #25272F",
                borderRadius: 12,
                fontSize: 12,
                lineHeight: 1.6,
                whiteSpace: "pre-wrap",
                wordBreak: "break-word",
                color: "#FF5A3C",
              }}
            >
              {String(this.state.error?.stack || this.state.error?.message || this.state.error)}
            </pre>
            <button
              onClick={() => window.location.reload()}
              style={{
                marginTop: 20,
                padding: "10px 20px",
                borderRadius: 999,
                border: "none",
                background: "#D6301F",
                color: "#EDE8DD",
                fontSize: 14,
                cursor: "pointer",
              }}
            >
              Reload
            </button>
          </div>
        </div>
      );
    }
    return this.props.children;
  }
}
