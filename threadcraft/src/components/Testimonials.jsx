import { Quote } from "lucide-react";
import useReveal from "../hooks/useReveal.js";

// Demo quotes — swap in real customer testimonials before launch.
const testimonials = [
  {
    name: "Aditya R.",
    role: "College fest coordinator",
    quote:
      "Ordered 180 event tees with two days' notice. Every shirt matched the mockup, sizing was accurate across the whole batch.",
  },
  {
    name: "Priya N.",
    role: "Startup founder",
    quote:
      "We reorder our team merch here every quarter. Same quality every time, and they just get it right without back-and-forth.",
  },
  {
    name: "Coach Manoj",
    role: "Local football club",
    quote:
      "Jerseys held up through a full season of matches and washes without the print cracking or the numbers peeling.",
  },
];

function Card({ item, index }) {
  const ref = useReveal();
  return (
    <div
      ref={ref}
      className="reveal rounded-[24px] border border-ink-line bg-ink-soft p-8"
      style={{ transitionDelay: `${index * 90}ms` }}
    >
      <Quote size={22} className="text-thread" strokeWidth={1.5} />
      <p className="mt-6 text-[15px] leading-relaxed text-paper-dim">"{item.quote}"</p>
      <div className="mt-7 border-t border-ink-line pt-4">
        <p className="text-sm font-medium text-paper">{item.name}</p>
        <p className="text-xs text-paper-dim">{item.role}</p>
      </div>
    </div>
  );
}

export default function Testimonials() {
  const headerRef = useReveal();
  return (
    <section className="bg-ink py-28">
      <div className="mx-auto max-w-[1400px] px-6 md:px-10">
        <div ref={headerRef} className="reveal mb-14 max-w-xl">
          <p className="font-mono text-xs uppercase tracking-[0.2em] text-thread">Word of mouth</p>
          <h2 className="mt-4 font-display text-4xl leading-[0.95] text-paper sm:text-5xl">
            What people say after wearing it
          </h2>
        </div>

        <div className="grid grid-cols-1 gap-6 md:grid-cols-3">
          {testimonials.map((item, i) => (
            <Card key={item.name} item={item} index={i} />
          ))}
        </div>

        <p className="mt-10 text-center text-xs text-paper-dim">
          Demo testimonials shown above — replace with real customer quotes before this goes live.
        </p>
      </div>
    </section>
  );
}
