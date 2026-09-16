// ─────────────────────────────────────────────────────────────────────────
// BUSINESS CONFIGURATION
// Everything the client will want to change before going live lives here.
// Swap these values and the whole site updates — no need to touch any
// component. Contact details below are placeholders for the demo and are
// clearly not real.
// ─────────────────────────────────────────────────────────────────────────

export const business = {
  name: "THREADCRAFT",
  tagline: "Custom T-Shirt Printing",
  headline: ["WEAR YOUR", "IDEAS."],
  subhead:
    "Premium custom T-shirt printing for events, teams, brands and everyday style.",

  // Replace with a logo image path (e.g. "/images/logo.svg") to swap the
  // wordmark for a real mark. Leave null to keep the text logo.
  logo: null,

  phone: "+91 90000 00000",
  whatsappNumber: "9190000 00000".replace(/\s/g, ""), // digits only, country code first
  instagram: "@threadcraft.demo",
  location: "Vizianagaram, Andhra Pradesh",

  isDemo: true,
};

// Build once so every "WhatsApp us" button shares the exact same link.
// Replace WHATSAPP_NUMBER above and this updates everywhere automatically.
export const WHATSAPP_NUMBER = business.whatsappNumber;

export function buildWhatsAppLink(message = "Hi! I'd like to talk about a custom print order.") {
  return `https://wa.me/${WHATSAPP_NUMBER}?text=${encodeURIComponent(message)}`;
}
