import { whyChooseUs } from "../data/services.js";
import useCountUp from "../hooks/useCountUp.js";
import useReveal from "../hooks/useReveal.js";

function Stat({ item, index }) {
  const [ref, value] = useCountUp(item.value);
  const revealRef = useReveal();
  return (
    <div
      ref={(node) => {
        ref.current = node;
        revealRef.current = node;
      }}
      className="reveal border-t border-ink-line pt-6"
      style={{ transitionDelay: `${index * 80}ms` }}
    >
      <p className="font-display text-5xl text-paper sm:text-6xl">
        {value}
        <span className="text-thread">{item.suffix}</span>
      </p>
      <p className="mt-3 text-sm font-medium text-paper">{item.label}</p>
      <p className="mt-1 text-sm text-paper-dim">{item.description}</p>
    </div>
  );
}

export default function WhyChooseUs() {
  const headerRef = useReveal();
  return (
    <section id="why" className="bg-ink py-28">
      <div className="mx-auto max-w-[1400px] px-6 md:px-10">
        <div ref={headerRef} className="reveal mb-16 grid grid-cols-1 gap-8 lg:grid-cols-[1fr_1fr]">
          <h2 className="font-display text-4xl leading-[0.95] text-paper sm:text-5xl">
            Why businesses print with us instead of the shop down the road
          </h2>
          <p className="self-end text-paper-dim">
            High-quality printing, premium fabric, and fast delivery — without the minimum order sizes that shut
            small requests out. Affordable pricing scales the same way whether it's a bulk run or a single gift.
          </p>
        </div>

        <div className="grid grid-cols-1 gap-8 sm:grid-cols-2 lg:grid-cols-4">
          {whyChooseUs.map((item, i) => (
            <Stat key={item.label} item={item} index={i} />
          ))}
        </div>
      </div>
    </section>
  );
}
