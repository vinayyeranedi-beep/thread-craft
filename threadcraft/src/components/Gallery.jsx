import { galleryItems } from "../data/services.js";
import useReveal from "../hooks/useReveal.js";

// Deterministic per-category gradients + pattern so every tile is visually
// distinct without needing external photography for the demo.
const CATEGORY_STYLE = {
  Streetwear: { from: "#1c1d22", to: "#3a1614", accent: "#D6301F" },
  College: { from: "#1b1f2b", to: "#2c2413", accent: "#C6A24D" },
  Corporate: { from: "#14161c", to: "#242832", accent: "#EDE8DD" },
  Sports: { from: "#1a1f18", to: "#0f2a1c", accent: "#4E9A6B" },
  Events: { from: "#221420", to: "#3a1533", accent: "#B85BC9" },
};

function GalleryTile({ item, index }) {
  const ref = useReveal();
  const style = CATEGORY_STYLE[item.category];
  return (
    <div
      ref={ref}
      className="reveal group relative aspect-[4/5] overflow-hidden rounded-[20px] border border-ink-line"
      style={{ transitionDelay: `${(index % 3) * 70}ms` }}
    >
      <div
        className="absolute inset-0 transition-transform duration-700 ease-out group-hover:scale-110"
        style={{ background: `linear-gradient(160deg, ${style.from}, ${style.to})` }}
      />
      <div
        className="absolute inset-0 opacity-40 mix-blend-screen transition-opacity duration-700 group-hover:opacity-70"
        style={{
          background: `radial-gradient(circle at 30% 20%, ${style.accent}55, transparent 55%)`,
        }}
      />
      <div className="absolute inset-0 flex flex-col justify-end p-6 transition-transform duration-500 group-hover:-translate-y-1">
        <p className="font-mono text-[11px] uppercase tracking-[0.15em]" style={{ color: style.accent }}>
          {item.category}
        </p>
        <p className="mt-1 font-display text-lg text-paper">{item.title}</p>
      </div>
      <div className="absolute inset-0 opacity-0 ring-1 ring-inset ring-thread/60 transition-opacity duration-500 group-hover:opacity-100" />
    </div>
  );
}

export default function Gallery() {
  const headerRef = useReveal();
  return (
    <section id="gallery" className="bg-ink-soft py-28">
      <div className="mx-auto max-w-[1400px] px-6 md:px-10">
        <div ref={headerRef} className="reveal mb-14 max-w-xl">
          <p className="font-mono text-xs uppercase tracking-[0.2em] text-thread">Recent work</p>
          <h2 className="mt-4 font-display text-4xl leading-[0.95] text-paper sm:text-5xl">
            A few things we've printed
          </h2>
        </div>

        <div className="grid grid-cols-2 gap-4 sm:grid-cols-3 md:gap-6">
          {galleryItems.map((item, i) => (
            <GalleryTile key={item.id} item={item} index={i} />
          ))}
        </div>
      </div>
    </section>
  );
}
