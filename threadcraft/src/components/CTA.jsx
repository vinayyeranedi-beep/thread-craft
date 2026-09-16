import { ArrowUpRight, MessageCircle } from "lucide-react";
import { buildWhatsAppLink } from "../config/business.js";
import useReveal from "../hooks/useReveal.js";

export default function CTA() {
  const ref = useReveal();
  return (
    <section className="relative overflow-hidden bg-ink py-28">
      <span
        aria-hidden
        className="text-outline font-display pointer-events-none absolute left-1/2 top-1/2 hidden w-full -translate-x-1/2 -translate-y-1/2 select-none text-center text-[11vw] leading-none md:block"
      >
        WEAR IT
      </span>
      <div ref={ref} className="reveal relative mx-auto max-w-3xl px-6 text-center md:px-10">
        <h2 className="font-display text-4xl leading-[0.95] text-paper sm:text-6xl">
          READY TO WEAR
          <br />
          YOUR IDEA?
        </h2>
        <p className="mx-auto mt-6 max-w-md text-paper-dim">
          Tell us what you want to print. We'll turn your idea into something you can wear.
        </p>
        <div className="mt-10 flex flex-col items-center justify-center gap-4 sm:flex-row">
          <a
            href="#customize"
            className="group inline-flex items-center justify-center gap-2 rounded-full bg-signal px-8 py-4 text-sm font-medium text-paper transition-all duration-300 hover:bg-signal-glow hover:shadow-[0_0_30px_rgba(214,48,31,0.4)]"
          >
            Start Your Design
            <ArrowUpRight size={16} className="transition-transform duration-300 group-hover:translate-x-0.5 group-hover:-translate-y-0.5" />
          </a>
          <a
            href={buildWhatsAppLink()}
            target="_blank"
            rel="noopener noreferrer"
            className="inline-flex items-center justify-center gap-2 rounded-full border border-ink-line px-8 py-4 text-sm font-medium text-paper transition-colors duration-300 hover:border-thread hover:text-thread"
          >
            <MessageCircle size={16} />
            WhatsApp Us
          </a>
        </div>
      </div>
    </section>
  );
}
