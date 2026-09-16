// Demo designs shown in the "MAKE IT YOURS" customizer. Each one is drawn
// procedurally onto a canvas texture (see TshirtCustomizer.jsx) so the demo
// needs zero external image assets.
export const shirtColors = [
  { id: "black", label: "Black", hex: "#111114" },
  { id: "white", label: "White", hex: "#F3F1EA" },
  { id: "red", label: "Red", hex: "#8A2620" },
  { id: "blue", label: "Blue", hex: "#20344F" },
  { id: "green", label: "Green", hex: "#2B3B2E" },
  { id: "yellow", label: "Yellow", hex: "#C9A227" },
];

export const demoDesigns = [
  { id: "none", label: "No Design" },
  { id: "vinayaka-chavithi", label: "Vinayaka Chavithi" },
  { id: "minimal-logo", label: "Minimal Logo" },
  { id: "street-style", label: "Street Style" },
  { id: "college", label: "College" },
  { id: "sports", label: "Sports" },
  { id: "custom-print", label: "Custom Print" },
];

export const sizes = ["S", "M", "L", "XL", "XXL"];

// Demo pricing only — swap basePrice and tiers for real numbers before launch.
export const pricing = {
  basePrice: 499,
  currency: "₹",
  tiers: [
    { minQty: 1, discountPct: 0 },
    { minQty: 10, discountPct: 10 },
    { minQty: 50, discountPct: 20 },
    { minQty: 200, discountPct: 30 },
  ],
};

/** Returns { unit, total, discountPct } for a given quantity. */
export function estimatePrice(qty) {
  const safeQty = Math.max(1, qty);
  const tier = [...pricing.tiers].reverse().find((t) => safeQty >= t.minQty) ?? pricing.tiers[0];
  const unit = Math.round(pricing.basePrice * (1 - tier.discountPct / 100));
  return { unit, total: unit * safeQty, discountPct: tier.discountPct };
}
