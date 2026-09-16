import { useEffect, useMemo, useRef, useState } from "react";
import { useFrame } from "@react-three/fiber";
import { Decal, useGLTF } from "@react-three/drei";
import * as THREE from "three";
import { buildTshirtGeometry, buildCollarGeometry } from "../utils/shirtGeometry";
import { generateDesignTexture } from "../utils/designTextures";

/**
 * Resolves the decal texture for the current selection. Uploaded images are
 * loaded with THREE.TextureLoader; demo designs are drawn on a canvas (see
 * designTextures.js). Both live in this file/chunk on purpose — it's the
 * lazily-loaded 3D bundle, so none of this (or three.js itself) ships in
 * the site's initial JS payload.
 */
function useDecalTexture(designId, uploadedImageUrl) {
  const [texture, setTexture] = useState(null);
  const prevRef = useRef(null);

  useEffect(() => {
    let cancelled = false;

    // Only dispose the outgoing texture once its replacement is ready, so
    // the Decal is never left pointing at an already-freed GPU texture
    // between renders.
    function swapIn(next) {
      if (cancelled) {
        next?.dispose();
        return;
      }
      prevRef.current?.dispose();
      prevRef.current = next;
      setTexture(next);
    }

    if (uploadedImageUrl) {
      const loader = new THREE.TextureLoader();
      loader.load(
        uploadedImageUrl,
        (loaded) => {
          loaded.colorSpace = THREE.SRGBColorSpace;
          swapIn(loaded);
        },
        undefined,
        () => swapIn(null)
      );
    } else {
      swapIn(generateDesignTexture(designId));
    }

    return () => {
      cancelled = true;
    };
  }, [designId, uploadedImageUrl]);

  // Final cleanup on unmount.
  useEffect(() => () => prevRef.current?.dispose(), []);

  return texture;
}

/**
 * ── SWAPPING IN A REAL GLB MODEL ────────────────────────────────────────
 * Drop a rigged/unrigged T-shirt export at:
 *     public/models/tshirt.glb
 * then flip USE_GLB_MODEL to true below. The rest of the app (color
 * control, decal placement, front/back rotation) keeps working as long as
 * the mesh you want tinted is named "Shirt" in the GLB — adjust the
 * `mesh.name === "Shirt"` check in GltfShirt() if yours is named
 * differently. Until a model is supplied, a procedurally built stand-in
 * (see src/utils/shirtGeometry.js) is used instead so the demo always runs.
 */
const USE_GLB_MODEL = false;
const GLB_PATH = "/models/tshirt.glb";

function GltfShirt({ color }) {
  const { scene } = useGLTF(GLB_PATH);
  const cloned = useMemo(() => scene.clone(true), [scene]);

  useMemo(() => {
    cloned.traverse((mesh) => {
      if (mesh.isMesh && mesh.name === "Shirt") {
        mesh.material = mesh.material.clone();
        mesh.material.color = new THREE.Color(color);
      }
    });
  }, [cloned, color]);

  return <primitive object={cloned} />;
}

function ProceduralShirt({ color, decalTexture }) {
  const geometry = useMemo(() => buildTshirtGeometry(), []);
  const collarGeometry = useMemo(() => buildCollarGeometry(), []);
  const meshRef = useRef();

  const material = useMemo(() => {
    const mat = new THREE.MeshPhysicalMaterial({
      color: new THREE.Color(color),
      roughness: 0.76,
      metalness: 0.03,
      sheen: 0.7,
      sheenRoughness: 0.5,
      sheenColor: new THREE.Color("#ffffff"),
      clearcoat: 0.12,
      clearcoatRoughness: 0.9,
    });
    return mat;
  }, []);

  useMemo(() => {
    material.color.set(color);
  }, [color, material]);

  const collarColor = useMemo(() => new THREE.Color(color).multiplyScalar(0.78), [color]);

  return (
    <group>
      <mesh
        ref={meshRef}
        geometry={geometry}
        material={material}
        castShadow
        receiveShadow
      >
        {decalTexture && (
          <Decal
            position={[0, 0.15, 0.24]}
            scale={[0.75, 0.75, 0.75]}
            map={decalTexture}
          >
            <meshPhysicalMaterial
              map={decalTexture}
              transparent
              polygonOffset
              polygonOffsetFactor={-4}
              roughness={0.6}
              depthTest
              depthWrite={false}
            />
          </Decal>
        )}
      </mesh>
      <mesh
        geometry={collarGeometry}
        position={[0, 1.13, 0.02]}
        rotation={[Math.PI / 2, 0, 0]}
      >
        <meshStandardMaterial color={collarColor} roughness={0.9} />
      </mesh>
    </group>
  );
}

export default function TshirtModel({
  color = "#111114",
  view = "front",
  designId = "none",
  uploadedImageUrl = null,
  float = true,
}) {
  const groupRef = useRef();
  const targetRotation = useRef(0);
  const decalTexture = useDecalTexture(designId, uploadedImageUrl);

  targetRotation.current = view === "back" ? Math.PI : 0;

  useFrame((state, delta) => {
    if (!groupRef.current) return;
    groupRef.current.rotation.y = THREE.MathUtils.damp(
      groupRef.current.rotation.y,
      targetRotation.current,
      4,
      delta
    );
    if (float) {
      groupRef.current.position.y = Math.sin(state.clock.elapsedTime * 0.9) * 0.045;
    }
  });

  return (
    <group ref={groupRef} dispose={null}>
      {USE_GLB_MODEL ? (
        <GltfShirt color={color} />
      ) : (
        <ProceduralShirt color={color} decalTexture={decalTexture} />
      )}
    </group>
  );
}

if (USE_GLB_MODEL) {
  useGLTF.preload(GLB_PATH);
}
