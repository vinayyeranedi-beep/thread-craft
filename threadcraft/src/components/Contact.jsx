import { Instagram, MapPin, MessageCircle, Phone } from "lucide-react";
import { business, buildWhatsAppLink } from "../config/business.js";
import useReveal from "../hooks/useReveal.js";

const ROWS = [
  { icon: Phone, label: "Phone", value: business.phone, href: `tel:${business.phone.replace(/\s/g, "")}` },
  { icon: MessageCircle, label: "WhatsApp", value: business.phone, href: buildWhatsAppLink() },
  { icon: Instagram, label: "Instagram", value: business.instagram, href: `https://instagram.com/${business.instagram.replace("@", "")}` },
  { icon: MapPin, label: "Location", value: business.location, href: null },
];

export default function Contact() {
  const ref = useReveal();
  return (
    <section id="contact" className="border-t border-ink-line bg-ink-soft py-28">
      <div ref={ref} className="reveal mx-auto max-w-[1400px] px-6 md:px-10">
        <div className="grid grid-cols-1 gap-14 lg:grid-cols-[1fr_1fr]">
          <div>
            <p className="font-mono text-xs uppercase tracking-[0.2em] text-thread">Get in touch</p>
            <h2 className="mt-4 font-display text-4xl leading-[0.95] text-paper sm:text-5xl">
              {business.name}
            </h2>
            <p className="mt-4 max-w-sm text-paper-dim">
              Demo contact details below — swap these for the real business phone, WhatsApp, Instagram and
              location before this goes live.
            </p>
            <span className="mt-6 inline-block rounded-full border border-ink-line px-3 py-1 font-mono text-[10px] uppercase tracking-widest text-paper-dim">
              placeholder details for demo
            </span>
          </div>

          <div className="divide-y divide-ink-line border-t border-ink-line">
            {ROWS.map((row) => {
              const Icon = row.icon;
              const content = (
                <div className="flex items-center justify-between py-5">
                  <div className="flex items-center gap-4">
                    <Icon size={18} className="text-thread" />
                    <span className="text-sm text-paper-dim">{row.label}</span>
                  </div>
                  <span className="text-right text-sm text-paper">{row.value}</span>
                </div>
              );
              return row.href ? (
                <a key={row.label} href={row.href} target="_blank" rel="noopener noreferrer" className="block transition-colors hover:bg-ink/40">
                  {content}
                </a>
              ) : (
                <div key={row.label}>{content}</div>
              );
            })}
          </div>
        </div>
      </div>
    </section>
  );
}
