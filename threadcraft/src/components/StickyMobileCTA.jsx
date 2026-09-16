import { MessageCircle, Sparkles } from "lucide-react";
import { buildWhatsAppLink } from "../config/business.js";

export default function StickyMobileCTA() {
  return (
    <div className="fixed inset-x-0 bottom-0 z-40 flex items-center gap-2 border-t border-ink-line bg-ink/95 p-3 backdrop-blur-md sm:hidden">
      <a
        href="#customize"
        className="flex flex-1 items-center justify-center gap-2 rounded-full bg-paper px-4 py-3 text-sm font-medium text-ink"
      >
        <Sparkles size={16} />
        Customize
      </a>
      <a
        href={buildWhatsAppLink()}
        target="_blank"
        rel="noopener noreferrer"
        className="flex flex-1 items-center justify-center gap-2 rounded-full bg-signal px-4 py-3 text-sm font-medium text-paper"
      >
        <MessageCircle size={16} />
        WhatsApp
      </a>
    </div>
  );
}
