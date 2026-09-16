import { useEffect, useRef, useState } from "react";
import { ArrowUpRight, MousePointer2, Upload } from "lucide-react";
import { business } from "../config/business.js";
import LazyTshirtViewer from "./LazyTshirtViewer.jsx";

export default function Hero() {
  const [uploadedImageUrl, setUploadedImageUrl] = useState(null);
  const [uploadedName, setUploadedName] = useState("");
  const fileInputRef = useRef(null);

  useEffect(() => {
    return () => {
      if (uploadedImageUrl?.startsWith("blob:")) URL.revokeObjectURL(uploadedImageUrl);
    };
  }, [uploadedImageUrl]);

  function handleUpload(event) {
    const file = event.target.files?.[0];
    if (!file || !file.type.startsWith("image/")) return;

    if (uploadedImageUrl?.startsWith("blob:")) URL.revokeObjectURL(uploadedImageUrl);
    const nextUrl = URL.createObjectURL(file);
    setUploadedImageUrl(nextUrl);
    setUploadedName(file.name);
  }

  return (
    <section id="home" className="relative overflow-hidden bg-ink pt-32 pb-16 md:pt-40">
      {/* oversized wordmark bleeding behind the composition — the one bold move for this section */}
      <span
        aria-hidden
        className="text-outline font-display pointer-events-none absolute -top-6 left-1/2 hidden w-full -translate-x-1/2 select-none text-center text-[13vw] leading-none md:block"
      >
        {business.name}
      </span>

      <div className="relative mx-auto grid max-w-[1400px] grid-cols-1 items-center gap-12 px-6 md:px-10 lg:grid-cols-[1.05fr_1fr]">
        <div>
          <p className="hero-rise font-mono text-xs uppercase tracking-[0.2em] text-thread" style={{ animationDelay: "0.05s" }}>
            {business.tagline}
          </p>

          <h1 className="hero-rise mt-6 font-display text-[15vw] leading-[0.88] text-paper sm:text-[9vw] lg:text-[5.4vw]" style={{ animationDelay: "0.15s" }}>
            {business.headline[0]}
            <br />
            <span className="text-signal">{business.headline[1]}</span>
          </h1>

          <p className="hero-rise mt-7 max-w-md text-lg text-paper-dim" style={{ animationDelay: "0.3s" }}>
            {business.subhead}
          </p>

          <div className="hero-rise mt-10 flex flex-col gap-4 sm:flex-row" style={{ animationDelay: "0.42s" }}>
            <a
              href="#customize"
              className="group inline-flex items-center justify-center gap-2 rounded-full bg-signal px-8 py-4 text-sm font-medium text-paper transition-all duration-300 hover:bg-signal-glow hover:shadow-[0_0_30px_rgba(214,48,31,0.4)]"
            >
              Customize Your T-Shirt
              <ArrowUpRight size={16} className="transition-transform duration-300 group-hover:translate-x-0.5 group-hover:-translate-y-0.5" />
            </a>
            <a
              href="#contact"
              className="inline-flex items-center justify-center gap-2 rounded-full border border-ink-line px-8 py-4 text-sm font-medium text-paper transition-colors duration-300 hover:border-thread hover:text-thread"
            >
              Get a Quote
            </a>
          </div>

          <div className="hero-rise mt-14 grid max-w-sm grid-cols-3 gap-6 border-t border-ink-line pt-6" style={{ animationDelay: "0.55s" }}>
            <div>
              <p className="font-display text-2xl text-paper">6</p>
              <p className="mt-1 text-xs text-paper-dim">print methods</p>
            </div>
            <div>
              <p className="font-display text-2xl text-paper">72h</p>
              <p className="mt-1 text-xs text-paper-dim">turnaround</p>
            </div>
            <div>
              <p className="font-display text-2xl text-paper">1–5000</p>
              <p className="mt-1 text-xs text-paper-dim">order size</p>
            </div>
          </div>
        </div>

        <div className="relative h-[420px] sm:h-[520px] lg:h-[640px]">
          <div className="absolute inset-0 rounded-[32px] bg-[radial-gradient(circle_at_50%_45%,rgba(198,162,77,0.14),transparent_60%)]" />
          <LazyTshirtViewer
            color="#111114"
            view="front"
            designId={uploadedImageUrl ? "custom-upload" : "vinayaka-chavithi"}
            uploadedImageUrl={uploadedImageUrl}
            autoRotate
            className="h-full w-full"
          />

          <div className="absolute left-1/2 top-4 -translate-x-1/2">
            <button
              type="button"
              onClick={() => fileInputRef.current?.click()}
              className="inline-flex items-center gap-2 rounded-full border border-thread/70 bg-black/20 px-4 py-2 text-[10px] font-medium uppercase tracking-[0.18em] text-paper backdrop-blur-sm transition-all duration-300 hover:border-thread hover:bg-thread/10"
            >
              <Upload size={14} />
              {uploadedName ? "Replace PNG" : "Upload PNG"}
            </button>
            <input
              ref={fileInputRef}
              type="file"
              accept="image/png,image/jpeg,image/webp"
              onChange={handleUpload}
              className="hidden"
            />
          </div>

          <div className="pointer-events-none absolute bottom-4 left-1/2 flex -translate-x-1/2 items-center gap-2 text-xs text-paper-dim opacity-80 sm:bottom-2">
            <MousePointer2 size={14} />
            drag to rotate
          </div>
        </div>
      </div>
    </section>
  );
}
