import * as THREE from "three";

const SIZE = 512;

function baseCanvas() {
  const canvas = document.createElement("canvas");
  canvas.width = SIZE;
  canvas.height = SIZE;
  const ctx = canvas.getContext("2d");
  return { canvas, ctx };
}

function toTexture(canvas) {
  const texture = new THREE.CanvasTexture(canvas);
  texture.colorSpace = THREE.SRGBColorSpace;
  texture.needsUpdate = true;
  return texture;
}

const drawers = {
  "vinayaka-chavithi": (ctx) => {
    const cx = SIZE / 2;
    const cy = SIZE / 2 + 12;

    const gradient = ctx.createLinearGradient(0, 0, SIZE, SIZE);
    gradient.addColorStop(0, "#1c1b1c");
    gradient.addColorStop(0.45, "#2d1808");
    gradient.addColorStop(1, "#f39a1d");
    ctx.fillStyle = gradient;
    ctx.fillRect(0, 0, SIZE, SIZE);

    ctx.fillStyle = "rgba(255, 193, 98, 0.25)";
    ctx.beginPath();
    ctx.moveTo(70, 340);
    ctx.lineTo(270, 130);
    ctx.lineTo(442, 340);
    ctx.lineTo(190, 440);
    ctx.closePath();
    ctx.fill();

    ctx.translate(cx, cy);
    ctx.fillStyle = "#f4b14a";
    ctx.strokeStyle = "#f7d38d";
    ctx.lineWidth = 6;

    ctx.beginPath();
    ctx.ellipse(0, 18, 118, 96, 0, 0, Math.PI * 2);
    ctx.fill();
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(-68, 36);
    ctx.quadraticCurveTo(-112, -24, -86, -82);
    ctx.quadraticCurveTo(-38, -128, 0, -118);
    ctx.quadraticCurveTo(30, -110, 30, -72);
    ctx.quadraticCurveTo(30, -18, 66, 30);
    ctx.quadraticCurveTo(22, 80, -68, 36);
    ctx.fill();
    ctx.stroke();

    ctx.fillStyle = "#d88d25";
    ctx.beginPath();
    ctx.moveTo(-18, -4);
    ctx.lineTo(-22, -76);
    ctx.lineTo(0, -120);
    ctx.lineTo(24, -76);
    ctx.lineTo(18, -4);
    ctx.closePath();
    ctx.fill();

    ctx.fillStyle = "#f7cf78";
    ctx.beginPath();
    ctx.arc(0, -20, 52, 0, Math.PI * 2);
    ctx.fill();

    ctx.fillStyle = "#d8891b";
    ctx.beginPath();
    ctx.moveTo(-34, 88);
    ctx.lineTo(-12, 142);
    ctx.quadraticCurveTo(-2, 154, 10, 142);
    ctx.lineTo(26, 92);
    ctx.closePath();
    ctx.fill();

    ctx.fillStyle = "#f4b14a";
    ctx.beginPath();
    ctx.moveTo(-54, 46);
    ctx.quadraticCurveTo(-102, 28, -112, 86);
    ctx.quadraticCurveTo(-110, 120, -70, 118);
    ctx.quadraticCurveTo(-44, 110, -54, 46);
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(54, 46);
    ctx.quadraticCurveTo(100, 28, 110, 86);
    ctx.quadraticCurveTo(108, 120, 70, 118);
    ctx.quadraticCurveTo(44, 110, 54, 46);
    ctx.fill();

    ctx.strokeStyle = "#e7b55d";
    ctx.lineWidth = 4;
    ctx.beginPath();
    ctx.moveTo(-132, 116);
    ctx.quadraticCurveTo(-170, 156, -150, 210);
    ctx.moveTo(132, 116);
    ctx.quadraticCurveTo(170, 156, 150, 210);
    ctx.stroke();

    ctx.fillStyle = "#f1c76f";
    ctx.beginPath();
    ctx.arc(-18, 170, 20, 0, Math.PI * 2);
    ctx.arc(18, 170, 20, 0, Math.PI * 2);
    ctx.fill();

    ctx.fillStyle = "#f8e7b2";
    ctx.font = "900 50px 'Georgia', serif";
    ctx.textAlign = "center";
    ctx.textBaseline = "middle";
    ctx.fillText("Vinayaka", 0, 210);
    ctx.font = "italic 56px 'Georgia', serif";
    ctx.fillText("Chavithi", 0, 266);

    ctx.strokeStyle = "#f9d98a";
    ctx.lineWidth = 3;
    ctx.beginPath();
    ctx.moveTo(-80, 290);
    ctx.quadraticCurveTo(0, 315, 80, 290);
    ctx.stroke();

    ctx.translate(-cx, -cy);
  },
  "minimal-logo": (ctx) => {
    const c = SIZE / 2;
    ctx.strokeStyle = "#EDE8DD";
    ctx.lineWidth = 6;
    ctx.beginPath();
    ctx.arc(c, c, 90, 0, Math.PI * 2);
    ctx.stroke();
    ctx.fillStyle = "#EDE8DD";
    ctx.font = "700 64px Inter, sans-serif";
    ctx.textAlign = "center";
    ctx.textBaseline = "middle";
    ctx.fillText("TC", c, c + 6);
  },
  "street-style": (ctx) => {
    ctx.save();
    ctx.translate(SIZE / 2, SIZE / 2);
    ctx.rotate(-0.06);
    ctx.fillStyle = "#EDE8DD";
    ctx.font = "900 96px 'Archivo Black', sans-serif";
    ctx.textAlign = "center";
    ctx.textBaseline = "middle";
    ctx.fillText("STREET", 0, -10);
    ctx.strokeStyle = "#D6301F";
    ctx.lineWidth = 10;
    ctx.beginPath();
    ctx.moveTo(-190, 55);
    ctx.lineTo(190, 30);
    ctx.stroke();
    ctx.restore();
  },
  college: (ctx) => {
    const c = SIZE / 2;
    ctx.fillStyle = "#EDE8DD";
    ctx.font = "900 150px 'Archivo Black', sans-serif";
    ctx.textAlign = "center";
    ctx.textBaseline = "middle";
    ctx.fillText("26", c, c + 10);
    ctx.strokeStyle = "#C6A24D";
    ctx.lineWidth = 8;
    ctx.beginPath();
    ctx.arc(c, c, 170, Math.PI * 1.15, Math.PI * 1.85);
    ctx.stroke();
    ctx.font = "700 28px Inter, sans-serif";
    ctx.fillStyle = "#C6A24D";
    ctx.save();
    ctx.translate(c, c - 168);
    ctx.fillText("CLASS OF", 0, 0);
    ctx.restore();
  },
  sports: (ctx) => {
    const c = SIZE / 2;
    ctx.fillStyle = "#EDE8DD";
    ctx.font = "900 260px 'Archivo Black', sans-serif";
    ctx.textAlign = "center";
    ctx.textBaseline = "middle";
    ctx.fillText("7", c, c + 20);
    ctx.strokeStyle = "#EDE8DD";
    ctx.lineWidth = 4;
    ctx.strokeRect(70, 70, SIZE - 140, SIZE - 140);
  },
  "custom-print": (ctx) => {
    const c = SIZE / 2;
    const colors = ["#D6301F", "#C6A24D", "#EDE8DD"];
    for (let i = 0; i < 5; i++) {
      ctx.fillStyle = colors[i % colors.length];
      ctx.globalAlpha = 0.85;
      ctx.beginPath();
      const r = 150 - i * 26;
      const rot = (i * Math.PI) / 6;
      ctx.moveTo(c + r * Math.cos(rot), c + r * Math.sin(rot));
      ctx.lineTo(c + r * Math.cos(rot + 2.1), c + r * Math.sin(rot + 2.1));
      ctx.lineTo(c + r * Math.cos(rot + 4.2), c + r * Math.sin(rot + 4.2));
      ctx.closePath();
      ctx.fill();
    }
    ctx.globalAlpha = 1;
  },
};

/** Returns a transparent CanvasTexture for the given demo design id, or null for "none". */
export function generateDesignTexture(designId) {
  if (!designId || designId === "none" || !drawers[designId]) return null;
  const { canvas, ctx } = baseCanvas();
  ctx.clearRect(0, 0, SIZE, SIZE);
  drawers[designId](ctx);
  return toTexture(canvas);
}
