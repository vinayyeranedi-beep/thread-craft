import { steps } from "../data/services.js";
import useReveal from "../hooks/useReveal.js";

function Step({ step, index, total }) {
  const ref = useReveal();
  return (
    <div ref={ref} className="reveal relative flex-1" style={{ transitionDelay: `${index * 90}ms` }}>
      <div className="flex items-center gap-4 lg:flex-col lg:items-start lg:gap-8">
        <span className="font-display text-3xl text-ink-line lg:text-5xl">{step.number}</span>
        <div className="h-px flex-1 bg-ink-line lg:hidden" />
      </div>
      <h3 className="mt-4 font-display text-lg text-paper lg:mt-8 lg:text-xl">{step.title}</h3>
      <p className="mt-2 max-w-[220px] text-sm text-paper-dim">{step.description}</p>
      {index < total - 1 && (
        <div className="absolute right-[-1.25rem] top-6 hidden h-px w-8 bg-ink-line lg:block" />
      )}
    </div>
  );
}

export default function HowItWorks() {
  const headerRef = useReveal();
  return (
    <section className="border-y border-ink-line bg-ink-soft py-28">
      <div className="mx-auto max-w-[1400px] px-6 md:px-10">
        <div ref={headerRef} className="reveal mb-16 max-w-xl">
          <p className="font-mono text-xs uppercase tracking-[0.2em] text-thread">The process</p>
          <h2 className="mt-4 font-display text-4xl leading-[0.95] text-paper sm:text-5xl">
            From idea to garment in four steps
          </h2>
        </div>

        <div className="flex flex-col gap-10 lg:flex-row lg:gap-6">
          {steps.map((step, i) => (
            <Step key={step.number} step={step} index={i} total={steps.length} />
          ))}
        </div>
      </div>
    </section>
  );
}
