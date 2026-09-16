import { useEffect, useRef, useState } from "react";
import { Ban, Circle, Flame, GraduationCap, Hash, Minus, MessageCircle, Plus, Sparkles, X } from "lucide-react";
import { shirtColors, demoDesigns, sizes, estimatePrice } from "../data/products.js";
import { buildWhatsAppLink } from "../config/business.js";
import LazyTshirtViewer from "./LazyTshirtViewer.jsx";
import useReveal from "../hooks/useReveal.js";

const DESIGN_ICONS = {
  none: Ban,
  "minimal-logo": Circle,
  "street-style": Flame,
  college: GraduationCap,
  sports: Hash,
  "custom-print": Sparkles,
};

const GLASS =
  "border border-white/10 bg-white/[0.06] backdrop-blur-xl shadow-[0_8px_30px_rgba(0,0,0,0.45)]";

function Tooltip({ children }) {
  return (
    <span
      className="pointer-events-none absolute -top-9 left-1/2 -translate-x-1/2 whitespace-nowrap rounded-md border border-ink-line bg-ink px-2 py-1 text-[10px] font-medium text-paper opacity-0 shadow-lg transition-all duration-200 group-hover:-translate-y-0.5 group-hover:opacity-100"
    >
      {children}
    </span>
  );
}

function ColorSwatches({ colorId, setColorId, size = 34 }) {
  return (
    <div className="flex items-center gap-2.5">
      {shirtColors.map((c) => {
        const active = colorId === c.id;
        return (
          <div key={c.id} className="group relative">
            <button
              aria-label={c.label}
              aria-pressed={active}
              onClick={() => setColorId(c.id)}
              className={`rounded-full transition-all duration-300 ease-out hover:scale-110 ${
                active
                  ? "ring-2 ring-thread ring-offset-2 ring-offset-ink shadow-[0_0_14px_rgba(198,162,77,0.55)]"
                  : "ring-1 ring-white/15 hover:ring-white/35"
              }`}
              style={{ width: size, height: size, backgroundColor: c.hex }}
            />
            <Tooltip>{c.label}</Tooltip>
          </div>
        );
      })}
    </div>
  );
}

function ViewSwitch({ view, setView }) {
  return (
    <div className={`relative flex w-[132px] rounded-full p-1 ${GLASS}`}>
      <span
        className="absolute inset-y-1 left-1 w-[calc(50%-4px)] rounded-full bg-signal transition-transform duration-300 ease-out"
        style={{ transform: view === "back" ? "translateX(100%)" : "translateX(0)" }}
      />
      {["front", "back"].map((v) => (
        <button
          key={v}
          onClick={() => setView(v)}
          className={`relative z-10 flex-1 py-2 text-[11px] font-medium uppercase tracking-wide transition-colors duration-300 ${
            view === v ? "text-paper" : "text-paper-dim hover:text-paper"
          }`}
        >
          {v}
        </button>
      ))}
    </div>
  );
}

function DesignThumbs({ designId, uploadedUrl, onSelect, size = 44, direction = "row" }) {
  return (
    <div className={`flex items-center gap-2 ${direction === "col" ? "flex-col" : ""}`}>
      {demoDesigns.map((d) => {
        const Icon = DESIGN_ICONS[d.id] ?? Circle;
        const active = designId === d.id && !uploadedUrl;
        return (
          <div key={d.id} className="group relative shrink-0">
            <button
              onClick={() => onSelect(d.id)}
              aria-pressed={active}
              className={`flex items-center justify-center rounded-xl transition-all duration-300 ease-out hover:-translate-y-0.5 hover:scale-105 ${
                active
                  ? "border border-thread bg-thread/15 text-paper shadow-[0_0_14px_rgba(198,162,77,0.35)]"
                  : "border border-white/10 bg-white/[0.05] text-paper-dim hover:border-white/25 hover:text-paper"
              }`}
              style={{ width: size, height: size }}
            >
              <Icon size={size * 0.42} strokeWidth={1.6} />
            </button>
            <Tooltip>{d.label}</Tooltip>
          </div>
        );
      })}
    </div>
  );
}

function SizeChips({ size, setSize }) {
  return (
    <div className="flex items-center gap-1.5">
      {sizes.map((s) => (
        <button
          key={s}
          onClick={() => setSize(s)}
          aria-pressed={size === s}
          className={`h-9 w-9 rounded-lg text-xs font-medium transition-all duration-300 ${
            size === s
              ? "bg-signal text-paper"
              : "border border-ink-line bg-ink text-paper-dim hover:border-paper-dim hover:text-paper"
          }`}
        >
          {s}
        </button>
      ))}
    </div>
  );
}

function QuantityStepper({ qty, setQty }) {
  return (
    <div className="flex items-center gap-3 rounded-full border border-ink-line bg-ink px-1.5 py-1.5">
      <button
        onClick={() => setQty(Math.max(1, qty - 1))}
        aria-label="Decrease quantity"
        className="flex h-7 w-7 items-center justify-center rounded-full text-paper-dim transition-colors hover:bg-ink-soft hover:text-paper"
      >
        <Minus size={14} />
      </button>
      <span className="w-8 text-center text-sm text-paper">{qty}</span>
      <button
        onClick={() => setQty(qty + 1)}
        aria-label="Increase quantity"
        className="flex h-7 w-7 items-center justify-center rounded-full text-paper-dim transition-colors hover:bg-ink-soft hover:text-paper"
      >
        <Plus size={14} />
      </button>
    </div>
  );
}

function OrderBar({ size, setSize, qty, setQty, colorId, designId, uploadedName }) {
  const { unit, total, discountPct } = estimatePrice(qty);
  const colorLabel = shirtColors.find((c) => c.id === colorId)?.label ?? "Black";
  const designLabel = uploadedName || demoDesigns.find((d) => d.id === designId)?.label || "No design";

  const message = [
    "Hi! I'd like a quote for:",
    `• ${qty} × T-shirt, size ${size}`,
    `• Colour: ${colorLabel}`,
    `• Design: ${designLabel}`,
    `Estimated: ₹${total.toLocaleString("en-IN")} (₹${unit}/unit)`,
  ].join("\n");

  return (
    <div className="mt-5 flex flex-col gap-6 rounded-[28px] border border-ink-line bg-ink-soft p-5 sm:flex-row sm:items-center sm:justify-between sm:p-6">
      <div className="flex flex-wrap items-start gap-6">
        <div>
          <p className="mb-2 font-mono text-[9px] uppercase tracking-[0.2em] text-paper-dim">Size</p>
          <SizeChips size={size} setSize={setSize} />
        </div>
        <div>
          <p className="mb-2 font-mono text-[9px] uppercase tracking-[0.2em] text-paper-dim">Quantity</p>
          <QuantityStepper qty={qty} setQty={setQty} />
        </div>
      </div>

      <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:gap-6">
        <div>
          <p className="font-mono text-[9px] uppercase tracking-[0.2em] text-paper-dim">Estimated price</p>
          <p className="mt-1 flex flex-wrap items-baseline gap-2">
            <span className="font-display text-2xl text-paper">₹{total.toLocaleString("en-IN")}</span>
            {discountPct > 0 && (
              <span className="text-xs font-medium text-thread">{discountPct}% bulk discount</span>
            )}
          </p>
          <p className="text-xs text-paper-dim">₹{unit} / unit</p>
        </div>
        <a
          href={buildWhatsAppLink(message)}
          target="_blank"
          rel="noopener noreferrer"
          className="inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-full bg-signal px-6 py-3.5 text-sm font-medium text-paper transition-all duration-300 hover:bg-signal-glow hover:shadow-[0_0_24px_rgba(214,48,31,0.4)]"
        >
          <MessageCircle size={16} />
          Get this quote
        </a>
      </div>
    </div>
  );
}

export default function TshirtCustomizer() {
  const [colorId, setColorId] = useState(shirtColors[0].id);
  const [view, setView] = useState("front");
  const [designId, setDesignId] = useState("vinayaka-chavithi");
  const [uploadedUrl, setUploadedUrl] = useState(null);
  const [uploadedName, setUploadedName] = useState("");
  const [size, setSize] = useState("M");
  const [qty, setQty] = useState(1);
  const fileInputRef = useRef(null);
  const objectUrlRef = useRef(null);
  const revealRef = useReveal();

  const color = shirtColors.find((c) => c.id === colorId)?.hex ?? "#111114";

  function selectDesign(id) {
    if (objectUrlRef.current) {
      URL.revokeObjectURL(objectUrlRef.current);
      objectUrlRef.current = null;
    }
    setUploadedUrl(null);
    setUploadedName("");
    setDesignId(id);
  }

  function handleUpload(event) {
    const file = event.target.files?.[0];
    if (!file || !file.type.startsWith("image/")) return;

    if (objectUrlRef.current) URL.revokeObjectURL(objectUrlRef.current);
    const url = URL.createObjectURL(file);
    objectUrlRef.current = url;

    setUploadedUrl(url);
    setDesignId("custom-upload");
    setUploadedName(file.name);
  }

  function clearUpload() {
    if (objectUrlRef.current) {
      URL.revokeObjectURL(objectUrlRef.current);
      objectUrlRef.current = null;
    }
    setUploadedUrl(null);
    setUploadedName("");
    setDesignId("none");
    if (fileInputRef.current) fileInputRef.current.value = "";
  }

  useEffect(() => {
    return () => {
      if (objectUrlRef.current) URL.revokeObjectURL(objectUrlRef.current);
    };
  }, []);

  return (
    <section id="customize" className="relative bg-ink-soft py-20 md:py-28">
      <div className="mx-auto max-w-[1400px] px-4 md:px-10">
        <div ref={revealRef} className="reveal mb-6 flex items-end justify-between gap-6 md:mb-8">
          <div>
            <p className="font-mono text-xs uppercase tracking-[0.2em] text-thread">Interactive customizer</p>
            <h2 className="mt-2 font-display text-3xl leading-[0.95] text-paper sm:text-4xl">MAKE IT YOURS</h2>
          </div>
          <p className="hidden max-w-xs text-right text-sm text-paper-dim sm:block">
            Drag to rotate. See it before you order it.
          </p>
        </div>

        {/* ── Stage: the T-shirt fills the frame, controls float on top ── */}
        <div className="relative h-[74vh] min-h-[480px] max-h-[780px] w-full overflow-hidden rounded-[32px] border border-ink-line bg-gradient-to-b from-ink to-ink-soft">
          <LazyTshirtViewer
            color={color}
            view={view}
            designId={designId}
            uploadedImageUrl={uploadedUrl}
            autoRotate={false}
            enableZoom={false}
            className="absolute inset-0 h-full w-full"
          />

          {/* Desktop / tablet floating controls — pointer-events off on the
              wrapper so empty space still drags the shirt, on for panels */}
          <div className="pointer-events-none absolute inset-0 hidden lg:block">
            <div className="panel-in pointer-events-auto absolute left-1/2 top-6 -translate-x-1/2" style={{ animationDelay: "0.05s" }}>
              <div className={`flex flex-col items-center gap-2 rounded-2xl px-5 py-3 ${GLASS}`}>
                <ColorSwatches colorId={colorId} setColorId={setColorId} />
                <span className="font-mono text-[9px] uppercase tracking-[0.25em] text-paper-dim">Colour</span>
              </div>
            </div>

            <div className="panel-in pointer-events-auto absolute right-6 top-6" style={{ animationDelay: "0.15s" }}>
              <ViewSwitch view={view} setView={setView} />
            </div>

            <div className="panel-in pointer-events-auto absolute bottom-6 left-6" style={{ animationDelay: "0.25s" }}>
              <div className={`flex flex-col gap-2 rounded-2xl px-4 py-3 ${GLASS}`}>
                <span className="font-mono text-[9px] uppercase tracking-[0.25em] text-paper-dim">Design</span>
                <DesignThumbs designId={designId} uploadedUrl={uploadedUrl} onSelect={selectDesign} />
              </div>
            </div>

            <div className="panel-in pointer-events-auto absolute bottom-6 right-6" style={{ animationDelay: "0.35s" }}>
              {uploadedUrl ? (
                <div className={`flex items-center gap-2 rounded-full py-2 pl-2 pr-3 ${GLASS}`}>
                  <span className="flex h-8 w-8 items-center justify-center rounded-full bg-thread/20 text-thread">
                    <Sparkles size={15} />
                  </span>
                  <span className="max-w-[110px] truncate text-xs text-paper">{uploadedName}</span>
                  <button
                    onClick={clearUpload}
                    aria-label="Remove uploaded design"
                    className="text-paper-dim transition-colors hover:text-paper"
                  >
                    <X size={14} />
                  </button>
                </div>
              ) : (
                <button
                  onClick={() => fileInputRef.current?.click()}
                  className={`group flex items-center gap-2.5 rounded-full py-2.5 pl-2.5 pr-4 transition-all duration-300 hover:-translate-y-0.5 ${GLASS}`}
                >
                  <span className="flex h-8 w-8 items-center justify-center rounded-full bg-signal text-paper transition-transform duration-300 group-hover:rotate-90">
                    <Plus size={16} />
                  </span>
                  <span className="text-xs font-medium uppercase tracking-wide text-paper">Upload design</span>
                </button>
              )}
            </div>
          </div>

          {/* Mobile dock — compact, bottom-anchored, doesn't cover the shirt */}
          <div className="pointer-events-none absolute inset-x-0 bottom-0 flex flex-col gap-2 p-3 lg:hidden">
            <div className={`pointer-events-auto flex items-center justify-between gap-3 rounded-2xl px-3 py-2.5 ${GLASS}`}>
              <ColorSwatches colorId={colorId} setColorId={setColorId} size={26} />
              <div className="h-6 w-px bg-white/10" />
              <ViewSwitch view={view} setView={setView} />
            </div>

            <div className={`pointer-events-auto flex items-center gap-2 overflow-x-auto rounded-2xl px-3 py-2.5 no-scrollbar ${GLASS}`}>
              <DesignThumbs designId={designId} uploadedUrl={uploadedUrl} onSelect={selectDesign} size={38} />
              <div className="h-8 w-px shrink-0 bg-white/10" />
              {uploadedUrl ? (
                <div className="flex shrink-0 items-center gap-1.5 rounded-full bg-thread/15 py-1.5 pl-1.5 pr-2.5">
                  <Sparkles size={13} className="text-thread" />
                  <span className="max-w-[70px] truncate text-[11px] text-paper">{uploadedName}</span>
                  <button onClick={clearUpload} aria-label="Remove uploaded design" className="text-paper-dim">
                    <X size={12} />
                  </button>
                </div>
              ) : (
                <button
                  onClick={() => fileInputRef.current?.click()}
                  className="flex shrink-0 items-center gap-1.5 rounded-full bg-signal py-2 pl-2 pr-3 text-paper"
                >
                  <Plus size={14} />
                  <span className="text-[11px] font-medium uppercase tracking-wide">Upload</span>
                </button>
              )}
            </div>
          </div>

          <input
            ref={fileInputRef}
            type="file"
            accept="image/png,image/jpeg,image/webp"
            onChange={handleUpload}
            className="hidden"
          />
        </div>

        <p className="mt-4 text-center text-xs text-paper-dim">
          Preview and pricing shown here are for demo purposes — your final quote is confirmed over WhatsApp.
        </p>

        <OrderBar
          size={size}
          setSize={setSize}
          qty={qty}
          setQty={setQty}
          colorId={colorId}
          designId={designId}
          uploadedName={uploadedName}
        />
      </div>
    </section>
  );
}
