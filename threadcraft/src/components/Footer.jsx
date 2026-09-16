import { business } from "../config/business.js";

export default function Footer() {
  return (
    <footer className="bg-ink py-10">
      <div className="mx-auto flex max-w-[1400px] flex-col items-center justify-between gap-4 px-6 text-xs text-paper-dim md:flex-row md:px-10">
        <p>
          © {new Date().getFullYear()} {business.name}. All rights reserved.
        </p>
        {business.isDemo && (
          <span className="inline-flex items-center gap-2 rounded-full border border-ink-line px-3 py-1.5 font-mono uppercase tracking-widest">
            <span className="h-1.5 w-1.5 rounded-full bg-signal" />
            Interactive 3D Demo
          </span>
        )}
        <p>Built for a client presentation — not the final brand.</p>
      </div>
    </footer>
  );
}
