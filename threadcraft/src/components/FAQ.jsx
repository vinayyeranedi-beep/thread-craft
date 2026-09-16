import { useState } from "react";
import { ChevronDown } from "lucide-react";
import useReveal from "../hooks/useReveal.js";

const faqs = [
  {
    q: "What's the minimum order size?",
    a: "There isn't one. We print single tees for personal projects and orders of several thousand for corporate or event runs, with the same quality checks either way.",
  },
  {
    q: "How long does printing take?",
    a: "Standard orders are ready within 72 hours. Larger bulk runs are scheduled around your event date — tell us your deadline and we'll confirm it's workable before you order.",
  },
  {
    q: "Can I see a sample before the full order?",
    a: "Yes. For bulk and corporate orders we print a sample piece first so you can approve colour, fit, and print quality before the full batch runs.",
  },
  {
    q: "Which print methods do you use?",
    a: "Screen printing, DTF, embroidery, vinyl, sublimation, and puff print. We'll recommend the right one for your design and fabric when you send it over.",
  },
  {
    q: "Do you deliver outside Vizianagaram?",
    a: "Yes, we ship across India. Delivery timelines depend on order size and destination — ask us for an estimate with your quote.",
  },
];

function Item({ item, isOpen, onToggle, index }) {
  const ref = useReveal();
  return (
    <div ref={ref} className="reveal border-b border-ink-line py-5" style={{ transitionDelay: `${index * 60}ms` }}>
      <button
        onClick={onToggle}
        aria-expanded={isOpen}
        className="flex w-full items-center justify-between gap-4 text-left"
      >
        <span className="font-display text-lg text-paper">{item.q}</span>
        <ChevronDown
          size={18}
          className={`shrink-0 text-thread transition-transform duration-300 ${isOpen ? "rotate-180" : ""}`}
        />
      </button>
      <div
        className={`grid overflow-hidden transition-all duration-300 ease-out ${
          isOpen ? "mt-3 grid-rows-[1fr] opacity-100" : "grid-rows-[0fr] opacity-0"
        }`}
      >
        <div className="overflow-hidden">
          <p className="max-w-xl text-sm leading-relaxed text-paper-dim">{item.a}</p>
        </div>
      </div>
    </div>
  );
}

export default function FAQ() {
  const [openIndex, setOpenIndex] = useState(0);
  const headerRef = useReveal();

  return (
    <section className="border-t border-ink-line bg-ink-soft py-28">
      <div className="mx-auto max-w-[820px] px-6 md:px-10">
        <div ref={headerRef} className="reveal mb-12 text-center">
          <p className="font-mono text-xs uppercase tracking-[0.2em] text-thread">Questions</p>
          <h2 className="mt-4 font-display text-4xl leading-[0.95] text-paper sm:text-5xl">Before you order</h2>
        </div>

        <div>
          {faqs.map((item, i) => (
            <Item
              key={item.q}
              item={item}
              index={i}
              isOpen={openIndex === i}
              onToggle={() => setOpenIndex(openIndex === i ? -1 : i)}
            />
          ))}
        </div>
      </div>
    </section>
  );
}
