import { useEffect, useState } from "react";
import { Menu, X } from "lucide-react";
import { business } from "../config/business.js";

const LINKS = [
  { href: "#home", label: "Home" },
  { href: "#customize", label: "Customize" },
  { href: "#services", label: "Services" },
  { href: "#gallery", label: "Gallery" },
  { href: "#why", label: "About" },
  { href: "#contact", label: "Contact" },
];

export default function Navbar() {
  const [scrolled, setScrolled] = useState(false);
  const [open, setOpen] = useState(false);

  useEffect(() => {
    const onScroll = () => setScrolled(window.scrollY > 24);
    onScroll();
    window.addEventListener("scroll", onScroll, { passive: true });
    return () => window.removeEventListener("scroll", onScroll);
  }, []);

  useEffect(() => {
    document.body.style.overflow = open ? "hidden" : "";
    return () => {
      document.body.style.overflow = "";
    };
  }, [open]);

  return (
    <header
      className={`fixed inset-x-0 top-0 z-50 transition-all duration-500 ${
        scrolled ? "bg-ink/85 backdrop-blur-md border-b border-ink-line" : "bg-transparent"
      }`}
    >
      <div className="mx-auto flex h-20 max-w-[1400px] items-center justify-between px-6 md:px-10">
        <a href="#home" className="font-display text-xl tracking-tight text-paper">
          {business.name}
          <span className="ml-2 align-middle text-[10px] font-mono font-medium tracking-wide text-thread">
            demo
          </span>
        </a>

        <nav className="hidden items-center gap-9 lg:flex">
          {LINKS.map((link) => (
            <a
              key={link.href}
              href={link.href}
              className="text-sm text-paper-dim transition-colors duration-300 hover:text-paper"
            >
              {link.label}
            </a>
          ))}
        </nav>

        <div className="flex items-center gap-4">
          <a
            href="#customize"
            className="hidden rounded-full bg-signal px-6 py-2.5 text-sm font-medium text-paper transition-all duration-300 hover:bg-signal-glow hover:shadow-[0_0_24px_rgba(214,48,31,0.45)] sm:inline-block"
          >
            Get a Quote
          </a>
          <button
            aria-label={open ? "Close menu" : "Open menu"}
            aria-expanded={open}
            onClick={() => setOpen((v) => !v)}
            className="text-paper lg:hidden"
          >
            {open ? <X size={26} /> : <Menu size={26} />}
          </button>
        </div>
      </div>

      <div
        className={`overflow-hidden bg-ink border-b border-ink-line transition-[max-height] duration-500 ease-in-out lg:hidden ${
          open ? "max-h-96" : "max-h-0"
        }`}
      >
        <nav className="flex flex-col gap-1 px-6 pb-6">
          {LINKS.map((link) => (
            <a
              key={link.href}
              href={link.href}
              onClick={() => setOpen(false)}
              className="rounded-lg px-3 py-3 text-base text-paper-dim transition-colors hover:bg-ink-soft hover:text-paper"
            >
              {link.label}
            </a>
          ))}
          <a
            href="#customize"
            onClick={() => setOpen(false)}
            className="mt-2 rounded-full bg-signal px-6 py-3 text-center text-sm font-medium text-paper"
          >
            Get a Quote
          </a>
        </nav>
      </div>
    </header>
  );
}
