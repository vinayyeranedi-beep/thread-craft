import * as THREE from "three";

/**
 * Builds a stylised, garment-shaped solid by extruding a T-shirt silhouette
 * and then displacing vertices outward from the centre plane to fake
 * fabric volume (chest, shoulders, hem taper). This is a deliberate
 * fallback for when a real GLB model isn't available yet — see
 * TshirtModel.jsx for where to swap in a loaded model instead.
 */
export function buildTshirtGeometry() {
  const shape = new THREE.Shape();

  // A more realistic tee silhouette: wider shoulders, gently tapering torso,
  // and a rounded hem that reads like a real garment instead of a flat slab.
  shape.moveTo(-1.38, 0.9);
  shape.quadraticCurveTo(-1.74, 0.86, -1.92, 0.32);
  shape.quadraticCurveTo(-1.96, -0.06, -1.7, -0.18);
  shape.quadraticCurveTo(-1.45, -0.28, -1.18, -0.22);
  shape.quadraticCurveTo(-0.96, -0.1, -0.9, 0.1);
  shape.lineTo(-0.82, 0.78);
  shape.lineTo(-0.92, -1.26);
  shape.quadraticCurveTo(-0.9, -1.46, -0.64, -1.5);
  shape.lineTo(0.64, -1.5);
  shape.quadraticCurveTo(0.9, -1.46, 0.92, -1.26);
  shape.lineTo(0.82, 0.78);
  shape.quadraticCurveTo(0.96, -0.1, 1.18, -0.22);
  shape.quadraticCurveTo(1.45, -0.28, 1.7, -0.18);
  shape.quadraticCurveTo(1.96, -0.06, 1.92, 0.32);
  shape.quadraticCurveTo(1.74, 0.86, 1.38, 0.9);
  shape.quadraticCurveTo(0.88, 1.22, 0.4, 1.3);
  shape.quadraticCurveTo(0.06, 1.36, 0, 1.38);
  shape.quadraticCurveTo(-0.06, 1.36, -0.4, 1.3);
  shape.quadraticCurveTo(-0.88, 1.22, -1.38, 0.9);

  const depth = 0.46;
  const geometry = new THREE.ExtrudeGeometry(shape, {
    depth,
    bevelEnabled: true,
    bevelThickness: 0.035,
    bevelSize: 0.03,
    bevelSegments: 6,
    curveSegments: 24,
  });

  geometry.translate(0, 0, -depth / 2);
  geometry.center();

  // Fake garment volume: push vertices outward along z proportionally to
  // how far they already are from the centre plane, weighted by a soft
  // falloff across chest height and torso width so the silhouette rounds
  // out like real fabric instead of staying a flat slab.
  const pos = geometry.attributes.position;
  const v = new THREE.Vector3();
  for (let i = 0; i < pos.count; i++) {
    v.fromBufferAttribute(pos, i);
    const widthFalloff = Math.max(0, 1 - Math.pow(v.x / 1.15, 2));
    const heightFalloff = Math.max(0, 1 - Math.pow((v.y - 0.15) / 1.15, 2));
    const chestBulge = 0.16 * widthFalloff * heightFalloff;
    const zSign = Math.sign(v.z) || 1;
    v.z += zSign * chestBulge * Math.min(1, Math.abs(v.z) * 4);
    pos.setXYZ(i, v.x, v.y, v.z);
  }
  geometry.computeVertexNormals();

  return geometry;
}

/** Flattened torus used for the ribbed neck collar. */
export function buildCollarGeometry() {
  const geo = new THREE.TorusGeometry(0.27, 0.045, 16, 48);
  geo.scale(1, 0.7, 0.55);
  return geo;
}
