import { Boxes, Briefcase, GraduationCap, PenTool, Shirt, Trophy, ArrowRight } from "lucide-react";
import { services } from "../data/services.js";
import useReveal from "../hooks/useReveal.js";

const ICONS = { Shirt, GraduationCap, Briefcase, Trophy, Boxes, PenTool };

function ServiceCard({ service, index }) {
  const ref = useReveal();
  const Icon = ICONS[service.icon];
  return (
    <div
      ref={ref}
      className="reveal group relative overflow-hidden rounded-[24px] border border-ink-line bg-ink-soft p-8 transition-colors duration-500 hover:border-thread/60"
      style={{ transitionDelay: `${(index % 3) * 60}ms` }}
    >
      <span className="font-mono absolute right-6 top-6 text-xs text-paper-dim/60">{service.tag}</span>
      <div className="mb-8 flex h-12 w-12 items-center justify-center rounded-full border border-ink-line text-thread transition-all duration-500 group-hover:border-thread group-hover:bg-thread/10">
        <Icon size={22} strokeWidth={1.5} />
      </div>
      <h3 className="font-display text-xl text-paper">{service.title}</h3>
      <p className="mt-3 text-sm leading-relaxed text-paper-dim">{service.description}</p>
      <a
        href="#contact"
        className="mt-6 inline-flex items-center gap-1.5 text-sm text-paper-dim opacity-0 transition-all duration-500 group-hover:translate-x-1 group-hover:text-thread group-hover:opacity-100"
      >
        Enquire <ArrowRight size={14} />
      </a>
      <div className="pointer-events-none absolute -bottom-16 -right-16 h-40 w-40 rounded-full bg-signal/0 blur-3xl transition-all duration-700 group-hover:bg-signal/10" />
    </div>
  );
}

export default function Services() {
  const headerRef = useReveal();
  return (
    <section id="services" className="bg-ink py-28">
      <div className="mx-auto max-w-[1400px] px-6 md:px-10">
        <div ref={headerRef} className="reveal mb-14 flex flex-col items-end justify-between gap-4 md:flex-row">
          <div className="max-w-xl">
            <p className="font-mono text-xs uppercase tracking-[0.2em] text-thread">What we print</p>
            <h2 className="mt-4 font-display text-4xl leading-[0.95] text-paper sm:text-5xl">
              Six ways to put your idea on fabric
            </h2>
          </div>
          <p className="max-w-sm text-sm text-paper-dim">
            Every order — from a single tee to five thousand — goes through the same quality checks before it
            leaves the shop.
          </p>
        </div>

        <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
          {services.map((service, i) => (
            <ServiceCard key={service.id} service={service} index={i} />
          ))}
        </div>
      </div>
    </section>
  );
}
